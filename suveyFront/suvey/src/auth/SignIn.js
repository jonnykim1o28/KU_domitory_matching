import {Link} from 'react-router-dom';
import { signin } from '../api/ApiService';
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
function SignIn() {


    const [formData, setFormData] = useState({
        nickname: '',
        password: '',
    });
    
    const navigate = useNavigate();
    
    const handleLogin = () => {
        signin(formData).then((response) =>{
            if(response.status === 200){
                navigate('/');
            }
            else{
                alert('아이디 또는 비밀번호가 일치하지 않습니다.');
            }
        });
    }

    const handleNicknameChange = (e) => {
        setFormData({
            ...formData,
            nickname: e.target.value,
        });
    }

    const handlePasswordChange = (e) => {
        setFormData({
            ...formData,
            password: e.target.value,
        });
    }

    return (
        <div>
            <h1>logo</h1>
            <div className='login_main'>
                <input type="text" placeholder="닉네임" onChange={handleNicknameChange}></input>
                <input type="password" placeholder="비밀번호" onChange={handlePasswordChange}></input>
            </div>
            <input type="submit" value="로그인" onClick={handleLogin}></input>

            <div className='find_signup'>
                <Link to="/findIDandPW">아이디/비밀번호 찾기</Link>
                <Link to="/signup">회원가입</Link>        
            </div>
            
        </div>
    );





}

export default SignIn;