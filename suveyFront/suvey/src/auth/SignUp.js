import React, { useEffect, useState } from 'react';
import { sendCodeToEmail, signup, verificationEmail, checkNickname } from '../api/ApiService';
import { Link, useNavigate } from 'react-router-dom';

const SignUp = () => {

    const navigate = useNavigate();

    const [formData, setFormData] = useState({
        nickname: '',
        email: '',
        password: '',
    });

    const [signupSatisfied, setSignupSatisfied] = useState(false);


    const [emailSatisfied, setEmailSatisfied] = useState(false);    
    const [nicknameSatisfied, setNicknameSatisfied] = useState(false);
    const [passwordCheck, setPasswordCheck] = useState('');
    const [showVerificationInput, setShowVerificationInput] = useState(false);
    const [showEmailVerificationInput, setShowEmailVerificationInput] = useState(false);
    const [verificationCode, setVerificationCode] = useState('');

    useEffect(() => {   
        setSignupSatisfied((formData.password === passwordCheck)&&emailSatisfied);
    }, [formData.password,passwordCheck, emailSatisfied]);

    
    
    const handleChange = (e) => {
        setFormData({
            ...formData,
            [e.target.name]: e.target.value,
        });
    };
    
    const handlePasswordCheck = (e) => {
        setPasswordCheck(e.target.value);
        console.log(passwordCheck);
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        // Handle form submission logic here
        console.log(formData);
    };

    const handleSignUp = () => {
        if(signupSatisfied){
            signup(formData).then((response) => {
                alert('회원가입이 완료되었습니다.');
                navigate('/');
            });
        }
        else if(!emailSatisfied){
            alert('이메일 인증을 완료해주세요.');
        }
        else if (formData.password !== passwordCheck) {
            alert('비밀번호가 일치하지 않습니다.');
        }
    };

    /////////////////////////////////
    //이메일 인증 관련
    const handleVerificationClick = () => {
        sendCodeToEmail(formData.email).then((response) => {
            if (response.status === 400) {
                alert('이미 가입된 이메일입니다.');
                setShowEmailVerificationInput(false);
                setShowVerificationInput(false);
            } else {
                alert('인증번호가 전송되었습니다.');
                setShowVerificationInput(true);
                setShowEmailVerificationInput(true);
                
            }
        });
  
    };

    const handleVerificationCodeChange = (e) => {
        setVerificationCode(e.target.value);
    };

    const handleCodeVerification =() =>{
        console.log(formData.email);
        console.log(verificationCode);
        verificationEmail(formData.email, verificationCode).then((response) => {
            if(response.status === 200){
                alert('인증 성공');
                setEmailSatisfied(true);
            }else{
                alert('코드를 다시 확인해주세요.');
            }
        });
        setEmailSatisfied(true);
    }
    /////////////////////////////////
    

    const handleNicknameCheck = () => {
        checkNickname(formData.nickname).then((response) => {
            if(response.status === 200){
                alert('사용 가능한 아이디입니다.');
                setNicknameSatisfied(true);
            }
            else{
                alert('이미 사용중인 아이디입니다.');
            }
        });
    }

    /////////////////////////////////
    //취소 버튼
    const handleCancle = () => {
        navigate(-1);    
    }
    /////////////////////////////////



    return (
        <div>
            <h2>Sign Up</h2>
            <form onSubmit={handleSubmit}>
                <div disabled={emailSatisfied}>
                    <label htmlFor="email">이메일: </label>
                    <input
                        type="email"
                        id="email"
                        name="email"
                        value={formData.email}
                        onChange={handleChange}
                        disabled={showEmailVerificationInput}
                    />
                    <input type="button" value="인증번호" onClick={handleVerificationClick} />
                </div>
                {showVerificationInput && (
                    <div>
                        <label htmlFor="verificationCode">인증번호 입력: </label>
                        <input
                            type="text"
                            id="verificationCode"
                            name="verificationCode"
                            value={verificationCode}
                            onChange={handleVerificationCodeChange}
                            disabled={emailSatisfied}
                        />
                        <input type='button' value='확인' onClick={handleCodeVerification} />
                    </div>
                )}
                <div>
                    <label htmlFor="nickname">아이디: </label>
                    <input
                        type="text"
                        id="nickname"
                        name="nickname"
                        value={formData.nickname}
                        onChange={handleChange}
                        disabled={nicknameSatisfied}
                    />
                    <button type='button' onClick={handleNicknameCheck}>중복확인</button>
                </div>
                <div>
                    <label htmlFor="password">비밀번호: </label>
                    <input  
                        type="password"
                        id="password"
                        name="password"
                        value={formData.password}
                        onChange={handleChange}
                    />
                </div>
                <div>
                    <label htmlFor="password">비밀번호 확인: </label>
                    <input
                        type='password'
                        id='passwordCheck'
                        name='passwordCheck'
                        value={passwordCheck}
                        onChange={handlePasswordCheck}
                    />
                </div>
                <button type='submit' onClick={handleCancle}>취소</button>
                <button type="submit" onClick={handleSignUp} disabled={!signupSatisfied}>회원가입</button>
            </form>
        </div>
    );
};

export default SignUp;