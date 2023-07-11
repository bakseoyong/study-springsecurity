package com.example.login.EventHandler;
import com.example.login.Event.SendSignUpPhoneVerificationCodeEvent;
import com.example.login.SMSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class SMSEventHandler {
    private SMSService smsService;

    @Autowired
    public SMSEventHandler(SMSService smsService){
        this.smsService = smsService;
    }

    @EventListener
    public void handleSendSignUpVerificationCodeEvent(SendSignUpPhoneVerificationCodeEvent event){
        String phone = event.getPhone();

        smsService.sendVerificationCode(phone);
    }
}
