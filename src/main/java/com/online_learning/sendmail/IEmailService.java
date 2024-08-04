package com.online_learning.sendmail;

public interface IEmailService {
    boolean sendSimpleMail(EmailDetail details);
    void sendMailWithAttachment(EmailDetail details);
}
