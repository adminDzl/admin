package com.wolves.service.system.email;

import com.wolves.entity.system.MailModel;

public interface EmailService {
	public void emailManage(MailModel mail);
	public void sendEmail(MailModel mail);
}
