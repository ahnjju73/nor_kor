package com.example.north_defector.controller;

import com.example.north_defector.config.flows.SessionMapper;
import com.example.north_defector.domain.User;
import com.example.north_defector.domain.UserPw;
import com.example.north_defector.domain.UserSession;
import com.example.north_defector.object.OriginObject;
import com.example.north_defector.object.SessionRequest;
import com.example.north_defector.object.request.*;
import com.example.north_defector.object.response.SessionResponse;
import com.example.north_defector.repository.UserPwRepository;
import com.example.north_defector.repository.UserRepository;
import com.example.north_defector.service.SignService;
import com.example.north_defector.utils.AutoKey;
import com.example.north_defector.utils.EmailSender;
import com.example.north_defector.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ExecutorService;

@RestController
@RequiredArgsConstructor
public class SignController extends OriginObject {

    private final UserRepository usersRepository;
    private final UserPwRepository userPasswordsRepository;
    private final SignService signService;
    private final AutoKey autoKey;
    private final ExecutorService executorService;

    private final EmailSender emailSender;

    @SessionMapper(checkSession = false)
    @PostMapping("/joinus")
    @Transactional
    public SessionResponse joinUs(SessionRequest request){
        JoinRequest joinEmailRequest = map(request.getParam(), JoinRequest.class);
        joinEmailRequest.checkValidation();

        signService.checkIfUserByEmail(joinEmailRequest.getEmail());

//        String userId = autoKey.makeGetKey("users");
        String code = Utils.numberGen(6, 1);
        User users = usersRepository.findByEmail(joinEmailRequest.getEmail());
        users.setName(joinEmailRequest.getName());
        users.setEmail(joinEmailRequest.getEmail());
        usersRepository.save(users);

        UserPw userPassword = new UserPw();
        userPassword.setUserNo(users.getUserNo());
        userPassword.setUser(users);
        userPassword.makePassword(joinEmailRequest.getPassword());
        userPasswordsRepository.save(userPassword);
//        sendValidationCode(users, users.getEmail(), users.getName());
        UserSession userSession = signService.setSession(users);
        SessionResponse sessionResponse = signService.setResponseData(users, userSession.getSessionKey());
        return sessionResponse;
    }

    private void sendValidationCode(User users, String email, String name) {
//        executorService.submit(() -> {
//            ValidationEmailScriptter loginValidationEmailScriptter = new ValidationEmailScriptter(email, "[" + users.getJoinCode() + "] 회원가입 이메일 인증번호가 발송되었습니다.");
//            loginValidationEmailScriptter.sender(name, users.getJoinCode());
//        });
        emailSender.sendEmail(email, getSubject(users.getJoinCode()), users.getName(), users.getJoinCode());

    }

    private String getSubject(String joinCode){
        return "[인증번호: ${joincode}] 회사 이메일 인증을 진행해 주세요.".replace("${joincode}", joinCode);
    }

    @Transactional
    @SessionMapper(checkSession = false)
    @PutMapping("/request/email-validation")
    public void requestEmailValidationCode(SessionRequest request){
        EmailRequest emailRequest = map(request.getParam(), EmailRequest.class);
        emailRequest.checkValidation();
        User session = usersRepository.findByEmail(emailRequest.getEmail());
        if(!bePresent(session)){
            session = new User();
            session.setEmail(emailRequest.getEmail());
        }else{
            if(bePresent(session.getName()))
                withException("100-001");
        }

        String code = Utils.numberGen(6, 1);
        session.setJoinCode(code);
        session.setJoinCodeAt(LocalDateTime.now());
        usersRepository.save(session);
        sendValidationCode(session, session.getEmail(), session.getName());
    }

    @Transactional
    @SessionMapper(checkSession = false)
    @PutMapping("/confirm/email-validation")
    public void confirmUserEmailByCode(SessionRequest request){
        EmailCodeRequest emailRequest = map(request.getParam(), EmailCodeRequest.class);
        emailRequest.checkValidation();
        User session = usersRepository.findByEmail(emailRequest.getEmail());
        LocalDateTime joinCodeAt = session.getJoinCodeAt();
        String joinCode = session.getJoinCode();
        long between = ChronoUnit.MINUTES.between(joinCodeAt, LocalDateTime.now());
        if (between > 3)
            withException("100-002");
        if(!bePresent(joinCode) || !joinCode.equals(emailRequest.getCode()))
            withException("100-003");
        usersRepository.save(session);
    }

    @SessionMapper
    @GetMapping("/hello-world")
    public SessionResponse helloWorld(SessionRequest request){
        User users = request.getSession();
        SessionResponse sessionResponse = signService.setResponseData(users, request.getSessionKey());
        return sessionResponse;
    }

    @Transactional
    @SessionMapper(checkSession = false)
    @PostMapping("/login")
    public SessionResponse loginWithEmail(SessionRequest request){
        LoginEmailRequest loginEmailRequest = map(request.getParam(), LoginEmailRequest.class);
        UserPw userPasswordByUserEmail = userPasswordsRepository.findByUser_Email(loginEmailRequest.getEmail());
        if(!bePresent(userPasswordByUserEmail))
            withException("");
        userPasswordByUserEmail.loginWithPassword(loginEmailRequest.getPassword());
        User users = userPasswordByUserEmail.getUser();
//        User users = null;
        UserSession userSession = signService.setSession(users);
        SessionResponse sessionResponse = signService.setResponseData(users, userSession.getSessionKey());
        return sessionResponse;
    }

    @Transactional
    @SessionMapper
    @PutMapping("/change-profile")
    public void changeProfile(SessionRequest request){
        EditProfileReq editProfileReq = map(request.getParam(), EditProfileReq.class);
        User user = request.getSession();
        user.setProfile(editProfileReq.getProfile());
        usersRepository.save(user);
    }

}
