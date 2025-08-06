package com.example.demo.controller;

import com.example.demo.service.PersonalToolService;
import dev.langchain4j.agent.tool.ToolSpecification;
import dev.langchain4j.agent.tool.ToolSpecifications;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;

import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.model.chat.request.ChatRequest;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.chat.response.StreamingChatResponseHandler;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiStreamingChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/langchain")
@CrossOrigin(origins = "*", maxAge = 3600)
public class LangChainController {

    @Value("${openai.api.key}")
    private String openAiApiKey;

    @Value("${openai.base-url}")
    private String openAiBaseUrl;


    // 将model声明为final,避免并发问题
    private final OpenAiChatModel model;

    // 在构造函数中初始化model
    public LangChainController(@Value("${openai.api.key}") String openAiApiKey,
                               @Value("${openai.base-url}") String openAiBaseUrl) {
        this.model = OpenAiChatModel.builder()
                .baseUrl(openAiBaseUrl)
                .apiKey(openAiApiKey)
                .modelName("deepseek-chat")
                .build();
    }

    @GetMapping("/chat1")
    public String chat1(String message) {

        Assistant assistant = AiServices.builder(Assistant.class)
                .tools(new PersonalToolService())
                .chatModel(model)
                .build();

     return  assistant.chat(message);


    }

    @GetMapping("/chatStream")
    public Flux<String> chatStream(String message) {


        StreamingChatModel streamingModel = OpenAiStreamingChatModel.builder()
                .baseUrl(openAiBaseUrl)
                .apiKey(openAiApiKey)
                .modelName("deepseek-chat")
                .build();
        return Flux.create(fluxSink -> {
            streamingModel.chat(message, new StreamingChatResponseHandler() {

                @Override
                public void onPartialResponse(String s) {
                    fluxSink.next(s);
                }

                @Override
                public void onCompleteResponse(ChatResponse chatResponse) {
                    fluxSink.complete();
                }

                @Override
                public void onError(Throwable throwable) {
                    fluxSink.error(throwable);
                }
            });
        });


    }


    @PostMapping("/chat")
    public String chat(@RequestBody Map<String, Object> request ) {
        try {
            String message = (String) request.get("message");
            return model.chat(message);
        } catch (Exception e) {
            return "错误: " + e.getMessage();
        }
    }

    // 创建一个Map来存储每个用户的聊天记忆
    private final Map<String, MessageWindowChatMemory> userMemories = new ConcurrentHashMap<>();

    @PostMapping("/chatWithMemory")
    public String chatWithMemory(@RequestBody Map<String, Object> request) {
        try {
            String userId = (String) request.get("userId"); // 从请求中获取用户ID
            String message = (String) request.get("message");

            // 检查是否是清除对话的请求
            if (message != null && (message.contains("请清除对话") || message.contains("请清空对话") ||
                    message.toLowerCase().contains("clear") || message.toLowerCase().contains("reset"))) {
                // 清除该用户的聊天记忆
                userMemories.remove(userId);
                return "已清除对话内容，开始新的对话。";
            }

            // 获取或创建用户的聊天记忆
            MessageWindowChatMemory chatMemory = userMemories.computeIfAbsent(userId, k ->
                    MessageWindowChatMemory.builder()
                            .id(k)
                            .maxMessages(300)
                            .build()
            );

            // 创建AI服务
            Assistant assistant = AiServices.builder(Assistant.class)
                    .chatMemory(chatMemory)
                    .tools(new PersonalToolService())
                    .chatModel(model)
                    .build();

            String response = assistant.chat(message);

            // 更新内存中的聊天记忆
            userMemories.put(userId, chatMemory);
            return response;
        } catch (Exception e) {
            return "错误: " + e.getMessage();
        }
    }


    // 清除指定用户的对话记忆
    @PostMapping("/clearMemory")
    public String clearMemory(@RequestBody Map<String, Object> request) {
        try {
            String userId = (String) request.get("userId");
            if (userId == null || userId.isEmpty()) {
                return "错误: 用户ID不能为空";
            }

            // 清除该用户的聊天记忆
            userMemories.remove(userId);
            return "已成功清除用户 " + userId + " 的对话内容";
        } catch (Exception e) {
            return "错误: " + e.getMessage();
        }
    }

    // 获取所有用户的记忆状态（用于调试）
    @GetMapping("/memoryStatus")
    public Map<String, Object> getMemoryStatus() {
        Map<String, Object> status = new ConcurrentHashMap<>();
        status.put("totalUsers", userMemories.size());
        status.put("userIds", userMemories.keySet());
        return status;
    }

    @SystemMessage("你是一个友善的AI助手")
    interface Assistant {
        @UserMessage("{{message}}")
        String chat(String message);
    }


    // 请求体类

} 