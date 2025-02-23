package com.suvey.suvey.domain.user.controller;

import com.suvey.suvey.domain.user.dto.NickNameDTO;
import com.suvey.suvey.domain.user.dto.UserDTO;
import com.suvey.suvey.domain.user.entity.UserEntity;
import com.suvey.suvey.domain.user.exception.DuplicateEmailException;
import com.suvey.suvey.domain.user.service.UserService;
import com.suvey.suvey.global.dto.ResponseDTO;
import com.suvey.suvey.global.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final TokenProvider tokenProvider;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticate(@RequestBody UserDTO userDTO) {
        UserEntity user = userService.getByCredentials(userDTO.getNickname(), userDTO.getPassword());

        if (user != null) {
            final String token = tokenProvider.create(user);
            final UserDTO responseUserDTO = UserDTO.builder()
                    .email(user.getEmail())
                    .id(user.getId())
                    .nickname(user.getNickname())
                    .token(token)
                    .build();
            System.out.println(responseUserDTO);
            return ResponseEntity.ok().body(responseUserDTO);
        } else {
            ResponseDTO responseDTO = ResponseDTO.builder()
                    .data(null)
                    .error("Login failed")
                    .build();
            //왜 여기서 responseDTO를 담으면 404 에러가 뜨는지
            // -> ResponseDTO에 @Data 부재시 이런 문제가 발생
            //스프링 프레임워크는 내부의 HttpMessageConverter가 responseDTO를 직렬화하게 되는데 이때 responseDTO에 getter가 없으면 필드에 접근할 수 없다.
            //따라서 직렬화에 실패하게 되므로 에러가 뜨게 되는 것이다.

            /*
            직렬화의 흐름
            1. ResponseEntity 객체 반환:

            컨트롤러 메서드에서 ResponseEntity 객체를 반환하면, 스프링이 HTTP 응답을 생성하기 위해 ResponseEntity의 본문(body)를 처리합니다.

            2.HTTP 메시지 컨버터(HttpMessageConverter):

            스프링의 **HttpMessageConverter**가 ResponseDTO 객체를 JSON 또는 XML과 같은 형식으로 변환(직렬화)합니다.
            기본적으로 JSON 변환에는 Jackson 라이브러리가 사용됩니다.

            3.Jackson 직렬화:

            Jackson은 ResponseDTO 객체의 필드 값을 JSON으로 변환합니다.
            이 과정에서 Getter 메서드(getError(), getData())를 사용하여 필드 값을 읽습니다.
            Getter가 없으면 필드에 접근하지 못해 직렬화 에러가 발생합니다.
            */

            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> register(@RequestBody UserDTO userDTO) {

        if (userDTO == null)
            throw new RuntimeException("UserDTO is empty(Sign up)");

        UserEntity user = userDTO.userDTOToEntity();

        userService.create(user);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    //Todo  nickname 중복 확인을 해야 회원가입 권한을 넘겨줌
    @GetMapping("/nicknameCheck")
    public ResponseEntity<?> isDuplicate(@RequestParam String nickname) {

        if (!nickname.equals("") && !userService.checkDuplicateNickName(nickname)) {
            log.info("NickName Check Succeed");
            NickNameDTO nickNameDTO = NickNameDTO.builder().valid(true).build();
            return ResponseEntity.ok().body(nickNameDTO);
        } else {
            log.info("NickName Check Failed");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/emails/verification-requests")
    public ResponseEntity sendMessage(@RequestParam("email") String email) {
        try {
            userService.sendCodeToEmail(email);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (DuplicateEmailException e) {
            log.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error occurred."); // HTTP 500
        }

    }

    @PostMapping("/emails/verifications")
    public ResponseEntity verificationEmail(@RequestParam("email") String email, @RequestParam("code") String code) {
        try {
            userService.verifiedCode(email, code);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error occurred.");
        }
    }

}
