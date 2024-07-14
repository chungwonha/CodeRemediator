package com.chung.cyber.code.remediator;

import dev.langchain4j.chain.ConversationalChain;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiModelName;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
public class RemediationService {

    @Value("${openai.api.key}")
    private String apiKey;

    public String remediate(String filePath, String instruction) {
        try {
            String entireCode = readEntireFile(filePath);
            String remediatedCode = callLangChain4j(entireCode, instruction);
            return saveRemediatedCode(filePath, remediatedCode);
        } catch (IOException e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }

    private String readEntireFile(String filePath) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filePath)));
    }

    private String callLangChain4j(String entireCode, String instruction) {
        OpenAiChatModel model = OpenAiChatModel.builder()
                .apiKey(apiKey)
                .modelName(OpenAiModelName.GPT_4)
                .temperature(0.7)
                .build();

        ConversationalChain chain = ConversationalChain.builder()
                .chatLanguageModel(model)
                .build();

        String prompt = "Rewrite the following entire Java code according to this instruction:\n" +
                "Instruction: " + instruction + "\n\n" +
                "Code:\n" + entireCode + "\n" +
                "Please provide the entire rewritten code. "+
                "\nPlease provide the remediated Java code. If you generate any non-Java code or explanations, please format them as comments using /* */ for multi-line comments or // for single-line comments.";


        String response = chain.execute(prompt);
        String result2 = extractJavaCode(response);
        log.info("result2: " + result2);
        return result2;
    }

    public static String extractJavaCode(String response) {
        List<String> javaCodeBlocks = new ArrayList<>();

        // Regular expression pattern to match Java code blocks
        // This pattern looks for code between ``` markers with "java" specified
        String regex = "```java\\s*(.*?)\\s*```";
        Pattern pattern = Pattern.compile(regex, Pattern.DOTALL);
        Matcher matcher = pattern.matcher(response);

        while (matcher.find()) {
            String codeBlock = matcher.group(1).trim();
            // Remove square brackets at the beginning and end of the code block
//            codeBlock = removeSquareBrackets(codeBlock);
            javaCodeBlocks.add(codeBlock);
        }

        return removeSquareBrackets(javaCodeBlocks.toString());
    }

    private static String removeSquareBrackets(String code) {
        // Remove square brackets at the beginning of the code
        code = code.replaceAll("^\\s*\\[", "");
        // Remove square brackets at the end of the code
        code = code.replaceAll("\\]\\s*$", "");
        return code.trim();
    }



    private String saveRemediatedCode(String filePath, String remediatedCode) throws IOException {
        Files.write(Paths.get(filePath), remediatedCode.getBytes());
        return "Remediation successful. File updated.";
    }
}

