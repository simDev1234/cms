package com.zerobase.cms.user.application;

import com.zerobase.cms.user.client.MailGunClient;
import com.zerobase.cms.user.client.mailgun.SendMailForm;
import com.zerobase.cms.user.domain.SignUpForm;
import com.zerobase.cms.user.domain.model.Customer;
import com.zerobase.cms.user.domain.model.Seller;
import com.zerobase.cms.user.exception.CustomException;
import com.zerobase.cms.user.exception.ErrorCode;
import com.zerobase.cms.user.service.customer.SignUpCustomerService;
import com.zerobase.cms.user.service.seller.SignUpSellerService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignUpApplication {

    private final MailGunClient mailGunClient;

    private final SignUpCustomerService signUpCustomerService;

    private final SignUpSellerService signUpSellerService;

    public void customerVerify(String email, String code){
        signUpCustomerService.verifyEmail(email, code);
    }

    public String customerSignUp(SignUpForm form){

        if (signUpCustomerService.isEmailExist(form.getEmail())) {
            throw new CustomException(ErrorCode.ALREADY_REGISTERED_USER);
        }

        Customer c = signUpCustomerService.signUp(form);

        // 이메일 전송
        String verificationCode = getRandomCode();

        System.out.println(verificationCode);

        SendMailForm sendMailForm = SendMailForm.builder()
                .from("simdev1234@gmail.com")
                .to("simdev1234@gmail.com")
                .subject("Verification Email")
                .text(getVerificationEmailBody(form.getEmail(), form.getName(), "customer", verificationCode))
                .build();

        mailGunClient.sendEmail(sendMailForm);

        // 이메일 인증 코드, 유효기간 설정
        signUpCustomerService.changeCustomerValidateEmail(c.getId(), verificationCode);

        return "회원 가입에 성공하였습니다.";

    }

    public void sellerVerify(String email, String code){
        signUpSellerService.verifyEmail(email, code);
    }

    public String sellerSignUp(SignUpForm form){

        if (signUpSellerService.isEmailExist(form.getEmail())) {
            throw new CustomException(ErrorCode.ALREADY_REGISTERED_USER);
        }

        Seller s = signUpSellerService.signUp(form);

        // 이메일 전송
        String verificationCode = getRandomCode();

        System.out.println(verificationCode);

        SendMailForm sendMailForm = SendMailForm.builder()
                .from("simdev1234@gmail.com")
                .to("simdev1234@gmail.com")
                .subject("Verification Email")
                .text(getVerificationEmailBody(form.getEmail(), form.getName(), "seller", verificationCode))
                .build();

        mailGunClient.sendEmail(sendMailForm);

        // 이메일 인증 코드, 유효기간 설정
        signUpSellerService.changeCustomerValidateEmail(s.getId(), verificationCode);

        return "회원 가입에 성공하였습니다.";

    }

    private String getRandomCode(){
        return RandomStringUtils.random(10, true, true);
    }

    private String getVerificationEmailBody(String email, String name, String type, String code){

        StringBuilder builder = new StringBuilder();

        return builder.append("Hello").append(name).append("! Please Click Link for verification.\n \n")
                .append("http//localhost:8081/signup/verify" + type + "?email=")
                .append(email)
                .append("&code=")
                .append(code).toString();
    }

}
