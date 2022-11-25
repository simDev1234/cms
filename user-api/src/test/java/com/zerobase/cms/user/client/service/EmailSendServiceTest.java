package com.zerobase.cms.user.client.service;

import com.zerobase.cms.user.client.MailGunClient;
import com.zerobase.cms.user.client.mailgun.SendMailForm;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EmailSendServiceTest {

    @Autowired
    private MailGunClient mailGunClient;

    @Test
    public void emailTest(){
        SendMailForm form = SendMailForm.builder()
                .from("simdev1234@gmail.com")
                .to("simdev1234@gmail.com")
                .subject("Test email from zero base")
                .text("my text")
                .build();

        Assertions.assertNotNull(mailGunClient.sendEmail(form).getBody());
    }

}