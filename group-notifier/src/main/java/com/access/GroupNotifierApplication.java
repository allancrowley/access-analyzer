package com.access;

import com.access.dto.AttackAttemptDto;
import com.access.service.GroupNotifierService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.List;
import java.util.function.Consumer;

@SpringBootApplication
@Slf4j
@RequiredArgsConstructor
public class GroupNotifierApplication {
    @Value("${app.mail.notifier.subject:attack attempt}")
    private String subject = "Attack attempt from ip ";
    final JavaMailSender mailSender;
    final GroupNotifierService groupNotifierService;

    public static void main(String[] args) {
        SpringApplication.run(GroupNotifierApplication.class, args);
    }

    @Bean
    Consumer<AttackAttemptDto> groupNotifierConsumer() {
        return this::sendingMail;
    }

    private void sendingMail(AttackAttemptDto attackAttemptDto) {
        log.debug("received attack attempt data {}", attackAttemptDto);
        List<String> services = attackAttemptDto.services();
        List<String> emails = groupNotifierService.getMails(services);
        log.debug("received mail addresses are: {}", String.join(", ", emails));
        for (int i = 0; i < emails.size(); i++) {
            SimpleMailMessage smm = new SimpleMailMessage();
            smm.setTo(emails.get(i));
            smm.setText(getText(services.get(i), attackAttemptDto.timestamp(), attackAttemptDto.subnet()));
            smm.setSubject(getSubject(attackAttemptDto.subnet()));
            mailSender.send(smm);
            log.debug("Mail sent to: {}", emails.get(i));
        }
    }

    private String getSubject(String subnet) {
        String res = subject + subnet;
        log.debug("subject: {} ", res);
        return res;
    }

    private String getText(String serviceName, Long timestamp, String subnet) {
        String text = String.format("Service %s attack attempt at %d from ip %s", serviceName,
                timestamp, subnet);
        return text;
    }
}
