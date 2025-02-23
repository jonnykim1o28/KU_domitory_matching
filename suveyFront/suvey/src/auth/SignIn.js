import {Link} from 'react-router-dom';
import { signin } from '../api/ApiService';
import { useState } from 'react';
function SignIn() {
    const [formData, setFormData] = useState({
        username: '',
        password: '',
    });
    const handleLogin = () => {
        signin(formData).then((response) =>{
            if(response.status === 200){
                <Link to={'/'} />
            }
            else{
                alert('아이디 또는 비밀번호가 일치하지 않습니다.');
            }
        });
    }
    return (
        <div>
            <h1>logo</h1>
            <div className='login_main'>
                <input type="text" placeholder="아이디"></input>
                <input type="password" placeholder="비밀번호"></input>
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