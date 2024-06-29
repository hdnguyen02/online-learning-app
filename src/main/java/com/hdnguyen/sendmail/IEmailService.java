package com.hdnguyen.sendmail;

public interface IEmailService {
    boolean sendSimpleMail(EmailDetail details);
    void sendMailWithAttachment(EmailDetail details);
}
