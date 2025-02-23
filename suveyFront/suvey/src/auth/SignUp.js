import React, { useEffect, useState } from 'react';
import { sendCodeToEmail, signup } from '../api/ApiService';
import { Link } from 'react-router-dom';

const SignUp = () => {

    const [formData, setFormData] = useState({
        username: '',
        email: '',
        password: '',
    });

    const [signupSatisfied, setSignupSatisfied] = useState(false);


    const [emailSatisfied, setEmailSatisfied] = useState(false);    
    const [passwordCheck, setPasswordCheck] = useState('');
    const [showVerificationInput, setShowVerificationInput] = useState(false);
    const [showEmailVerificationInput, setShowEmailVerificationInput] = useState(false);
    const [verificationCode, setVerificationCode] = useState('');

    useEffect(() => {   
        setSignupSatisfied((formData.password === passwordCheck)&&emailSatisfied);
    }, []);

    
    
    const handleChange = (e) => {
        setFormData({
            ...formData,
            [e.target.name]: e.target.value,
        });
    };
    
    const handlePasswordCheck = (e) => {
        setPasswordCheck(e.target.value);
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        // Handle form submission logic here
        console.log(formData);
    };

    const handleSignUp = () => {
        if(signupSatisfied){
            signup(formData).then((response) => {
                <Link to={'/signin'}/>
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
        sendCodeToEmail(verificationCode).then((response) => {
            if(response.status === 200){
                alert('인증 성공');
            }else{
                alert('코드를 다시 확인해주세요.');
            }
        });
        setEmailSatisfied(true);
    }
    /////////////////////////////////
    

    /////////////////////////////////
    //취소 버튼
    const handleCancle = () => {
        <Link to={'/'}/>
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
                            onChange={handleVerificationCodeChange}
                        />
                        <input type='button' value='확인' onSubmit={handleCodeVerification} />
                    </div>
                )}
                <div>
                    <label htmlFor="username">아이디: </label>
                    <input
                        type="text"
                        id="username"
                        name="username"
                        value={formData.username}
                        onChange={handleChange}
                    />
                </div>
                <div>
                    <label htmlFor="password">비밀번호: </label>
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
                        id='password_check'
                        name='password_check'
                        value={formData.password_check}
                        onChange={handlePasswordCheck}
                    />
                </div>
                <button type='submit' onClick={handleCancle}>취소</button>
                //비밀번호와 비밀번호 확인이 같으면 회원가입 버튼 활성화
                <button type="submit" onClick={handleSignUp}>회원가입</button>
            </form>
        </div>
    );
};

export default SignUp;