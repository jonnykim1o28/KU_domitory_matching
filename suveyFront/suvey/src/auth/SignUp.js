import React, { useState } from 'react';
import { sendCodeToEmail, signup } from '../api/ApiService';

const SignUp = () => {
    const [formData, setFormData] = useState({
        username: '',
        email: '',
        password: '',
    });
    const [showVerificationInput, setShowVerificationInput] = useState(false);
    
    
    
    const handleChange = (e) => {
        setFormData({
            ...formData,
            [e.target.name]: e.target.value,
        });
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        // Handle form submission logic here
        console.log(formData);
    };

    const handleSignUp = () => {
        signup(formData);
    };

    const handleVerificationClick = () => {
        sendCodeToEmail(formData.email).then((response) => {
            if (response.status === 400) {
                alert('이미 가입된 이메일입니다.');
                setShowVerificationInput(false);
            } else {
                setShowVerificationInput(true);
                
            }
        });
        //이메일이 이미 있으면 다시 입력하게 하고
        //사용가능한 이메일이면 이메일 입력필드를 잠근다.
    };

    const handleEmailVerification =() =>{

    }

    return (
        <div>
            <h2>Sign Up</h2>
            <form onSubmit={handleSubmit}>
                <div>
                    <label htmlFor="email">이메일: </label>
                    <input
                        type="email"
                        id="email"
                        name="email"
                        value={formData.email}
                        onChange={handleChange}
                        disabled
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
                            onChange={handleChange}
                        />
                        <input type='button' value='확인' onSubmit={handleEmailVerification} />
                    </div>
                )}
                <div>
                    <label htmlFor="username">아이디: </label>
                    <input
                        type="text"
                        id="username"
                        name="username"
                        value={formData.username}
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
                    />
                </div>
                <button type='submit'>취소</button>
                <button type="submit" onClick={handleSignUp}>회원가입</button>
            </form>
        </div>
    );
};

export default SignUp;