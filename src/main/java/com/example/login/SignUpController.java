package com.example.login;

import com.example.login.Event.SendSignUpEmailVerificationCodeEvent;
import com.example.login.Event.SendSignUpPhoneVerificationCodeEvent;
import com.example.login.Event.SendSignUpSuccessEvent;
import com.example.login.Response.ErrorResponse;
import com.example.login.VO.CertificationForm;
import com.example.login.VO.SignUpForm;
import com.example.login.Validator.CertificationValidator;
import com.example.login.Validator.SignUpValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.net.URI;

@Controller
public class SignUpController {
    private CertificationValidator certificationValidator;
    private SignUpValidator signUpValidator;
    private SignUpService signUpService;
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public SignUpController(CertificationValidator certificationValidator, SignUpValidator signUpValidator, SignUpService signUpService, ApplicationEventPublisher applicationEventPublisher) {
        this.certificationValidator = certificationValidator;
        this.signUpValidator = signUpValidator;
        this.signUpService = signUpService;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @InitBinder("certificationForm")
    protected void initCertificationBinder(DataBinder binder){
        binder.setValidator(certificationValidator);
    }

    @InitBinder("signUpForm")
    protected void initSignUpBinder(DataBinder binder){
        binder.setValidator(signUpValidator);
    }

    @GetMapping("/signup/certification")
    public String goCertificationPage(){
        return "signup_certification";
    }

    @GetMapping("/signup/form")
    public ModelAndView goSignUpFormPage(HttpSession session){
        String name = (String) session.getAttribute("name");
        String phone = (String) session.getAttribute("phone");

        ModelAndView modelAndView = new ModelAndView();

        if(name == null || phone == null){
            modelAndView.setViewName("redirect:/signup/certification");

            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.addError("session", "유효하지 않은 접근이거나 세션 정보가 만료되었습니다.");

            modelAndView.addObject("errorResponse", errorResponse);
            return modelAndView;
        }

        modelAndView.setViewName("signup_form");
        return modelAndView;
    }

    @GetMapping("/signup/success")
    public String goSignUpSuccessPage(){
        return "signup_success";
    }

    @PostMapping("/signup/certification")
    public ModelAndView processCertification(@Validated @ModelAttribute("certificationForm") CertificationForm form,
                                               BindingResult bindingResult, HttpSession session){
        ModelAndView modelAndView = new ModelAndView();

        if(bindingResult.hasErrors()){
            modelAndView.setViewName("redirect:/signup/certification");

            ErrorResponse errorResponse = new ErrorResponse();

            for(FieldError error: bindingResult.getFieldErrors()){
                errorResponse.addError(error.getField(), error.getDefaultMessage());
            }

            modelAndView.addObject("errorResponse", errorResponse);
            return modelAndView;
        }

        session.setAttribute("name", form.getName());
        session.setAttribute("phone", form.getPhone());

        modelAndView.setViewName("redirect:/signup/form");
        return modelAndView;
    }

    @PostMapping("/signup/valid/request-phone-auth")
    public String requestPhoneAuthKey(@RequestParam("phone") String phone){
        SendSignUpPhoneVerificationCodeEvent event = new SendSignUpPhoneVerificationCodeEvent(phone);

        applicationEventPublisher.publishEvent(event);
        return "verification code sent to " + phone;
    }

    @PostMapping("/signup/valid/check-phone-auth")
    public boolean checkPhoneAuthKey(@RequestParam("phone") String phone, @RequestParam("code") String code){
        return signUpService.checkPhoneAuthKey(phone, code);
    }

    @PostMapping("/signup/valid/username-duplication")
    public boolean checkUsernameDuplication(@RequestParam("username") String username){
        return signUpService.checkIdDuplication(username);
    }

    @PostMapping("/signup/valid/request-email-auth-key")
    public void requestEmailAuthKey(@RequestParam("email") String email){
        SendSignUpEmailVerificationCodeEvent event = new SendSignUpEmailVerificationCodeEvent(email);

        applicationEventPublisher.publishEvent(event);
    }

    @PostMapping("/signup/valid/email")
    public boolean checkEmailAuth(@RequestParam("email") String email, @RequestParam("code") String code){
        return signUpService.checkEmailAuthKey(email, code);
    }

    @PostMapping("/signup/form")
    public ResponseEntity<?> processSignUpForm(HttpSession session, @Validated @ModelAttribute("signUpForm") SignUpForm form){
        String name = (String) session.getAttribute("name");
        String phone = (String) session.getAttribute("phone");

        if(name == null || phone == null){
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.addError("session", "유효하지 않은 접근이거나 세션 정보가 만료되었습니다.");

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }

        signUpService.requestUserCreate(form);

        session.invalidate();

        SendSignUpSuccessEvent event = new SendSignUpSuccessEvent(form.getUsername(), form.getEmail());

        applicationEventPublisher.publishEvent(event);

        return ResponseEntity.created(URI.create("/users/create")).build();
    }
}
