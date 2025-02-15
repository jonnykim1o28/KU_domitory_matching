import {Link} from 'react-router-dom';

function SignIn() {
    return (
        <div>
            <h1>logo</h1>
            <div className='login_main'>
                <input type="text" placeholder="아이디"></input>
                <input type="password" placeholder="비밀번호"></input>
            </div>
            <input type="submit" value="로그인"></input>

            <div className='find_signup'>
                <Link to="/findIDandPW">아이디/비밀번호 찾기</Link>
                <Link to="/signup">회원가입</Link>        
            </div>
            
        </div>
    );





}

export default SignIn;