package com.suvey.suvey.domain.user.service;

import com.suvey.suvey.domain.user.entity.UserEntity;
import com.suvey.suvey.domain.user.exception.DuplicateEmailException;
import com.suvey.suvey.domain.user.repository.UserRepository;
import com.suvey.suvey.global.mail.MailService;
import com.suvey.suvey.global.redis.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Duration;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserService {

    /*
    *
    * */
    private static final String AUTH_CODE_PREFIX = "AuthCode";
    private final UserRepository userRepository;
    private final MailService mailService;
    private final RedisService redisService;
    /*
    *
    * */


    @Value("${spring.mail.auth-code-expiration-millis}")
    private long authCodeExpirationMillis;

    public void create(UserEntity user) {
        userRepository.save(user);
    }

    public boolean checkDuplicateNickName(String nickname) {
        if (userRepository.findByNickname(nickname)==null){
            return false;
        }
        else{
            return true;
        }
    }

    public UserEntity getByCredentials(String nickname, String password) {
        final UserEntity originalUser = userRepository.findByNicknameAndPassword(nickname, password);

        if (originalUser != null) {
            return originalUser;
        }

        return null;
    }

    public void sendCodeToEmail(String toEmail){
        if(this.checkDuplicateEmail(toEmail)) {
            String title = "MyBoard 이메일 인증 번호";
            String authCode = this.createCode();
            mailService.sendEmail(toEmail, title, authCode);

            // Todo 이메일 인증 요청 시 인증 번호 Redis에 저장 ( key = "AuthCode " + Email / value = AuthCode )

            redisService.setValues(AUTH_CODE_PREFIX + toEmail, authCode, Duration.ofMillis(this.authCodeExpirationMillis));
        }
        else{
            throw new DuplicateEmailException("The email address is already in use.");
        }
    }

    private boolean checkDuplicateEmail(String email){
        Optional<UserEntity> user = userRepository.findByEmail(email);
        return user.isPresent() ? false: true;
    }

    private String createCode(){
        int length= 6;
        try {
            Random random = SecureRandom.getInstanceStrong();
            StringBuilder builder = new StringBuilder();
            for(int i=0; i<length; i++){
                builder.append(random.nextInt(10));
            }
            return builder.toString();
        }catch (NoSuchAlgorithmException e){
            throw new RuntimeException();
        }
    }

    public void verifiedCode(String email, String authCode){
        this.checkDuplicateEmail(email);
        String redisAuthCode = redisService.getValues(AUTH_CODE_PREFIX+email);
        boolean authResult = redisService.checkExistsValue(redisAuthCode) && redisAuthCode.equals(authCode);

        if(!authResult)
            throw new RuntimeException("verifiedCode Failed");

    }
}
