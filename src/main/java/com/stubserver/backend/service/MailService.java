package com.stubserver.backend.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;

    @Value("${app.mail.from:no-reply@domain.com}")
    private String fromAddress;

    private final ConcurrentHashMap<String, String> templateCache = new ConcurrentHashMap<>();

    @Async
    public void sendEmail(String to, String subject, String templatePath,
                          Map<String, String> placeholders) {
        try {
            String html = templateCache.computeIfAbsent(templatePath, this::loadTemplate);

            for (Map.Entry<String, String> entry : placeholders.entrySet()) {
                String value = entry.getValue() != null ? entry.getValue() : "";
                html = html.replace("${" + entry.getKey() + "}", value);
            }
            html = html.replaceAll("\\$\\{[^}]+}", "");

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(fromAddress);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(html, true);
            mailSender.send(message);
        } catch (Exception e) {
            log.error("Email sending failed to {}: {}", to, e.getMessage());
        }
    }

    private String loadTemplate(String templatePath) {
        ClassPathResource res = new ClassPathResource(templatePath);
        try (InputStream is = res.getInputStream()) {
            return new String(is.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Cannot load email template: " + templatePath, e);
        }
    }
}
