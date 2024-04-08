import axios from 'axios';
import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';

function Login({setAuth}) {
    //페이지 이동시에 필요한 navitage만들기
    const navigate = useNavigate();
    
    const [formData, setFormData] = useState({
        username:"",
        password:""
    })

    //input에 변경이 일어났을 때 상태 업데이트
    const onChange =(e)=>{
        const {name, value} = e.target;
        setFormData({
            ...formData,
            [name] : value
        })
    }

    //input값 초기화
    const onReset = ()=>{
        setFormData({
            username:"",
            password:""
        })
    }

    //로그인버튼 클릭 시 
    const onSubmit=(e) => {
        //전송 요청 이벤트 제거
        e.preventDefault();
        //입력이 다 되었는지 체크 후 함수 호출
        if(formData.username && formData.password){
            memberLogin();
        }
    }

    //carLogin 함수
    async function memberLogin(){
        try{
            const response = await axios.post("http://localhost:8081/member/login", formData);
            //로그인 성공시 받은 토큰을 세션스토리지(브라우저 저장소)에 저장
            //response.data {grantType : "Bearer", accessToken: "...", accessToken :"..."}
            //문자열로 변환 "Bearer access토큰값"
            const jwtToken = response.data.grantType + " " + response.data.accessToken;
            //sessionStorage에 저장
            sessionStorage.setItem("jwt", jwtToken);
            //로그인 상태를 true로 변경
            setAuth(true);
            //로그인 성공하면 메인페이지 이동
            navigate("/");
        }
        catch(e){
            console.log(e);
        }
    }   

    return (
        <div>
            <h2>로그인</h2>
            <form onSubmit={onSubmit}>
                <table>
                    <tbody>
                        <tr>
                            <td>이메일</td>
                            <td><input type="text" name="username" onChange={onChange} value={formData.username}/></td>
                        </tr>
                        <tr>
                            <td>패스워드</td>
                            <td><input type="password" name="password" onChange={onChange} value={formData.password}/></td>
                        </tr>
                        <tr>
                            <td colSpan={2}>
                                <button type="submit">로그인</button>
                                <button type="reset" onClick={onReset}>취소</button>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </form>
        </div>
    );
}

export default Login;
