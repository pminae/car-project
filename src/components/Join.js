import axios from 'axios';
import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';

function Join() {

    //페이지 이동시에 필요한 navitage만들기
    const navigate = useNavigate();
    
    const [formData, setFormData] = useState({
        name : "",
        email : "",
        password : "",
        address : ""
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
            name : "",
            email : "",
            password : "",
            address : ""
        })
    }

    //로그인버튼 클릭 시 
    const onSubmit=(e) => {
        //전송 요청 이벤트 제거
        e.preventDefault();
        //입력이 다 되었는지 체크 후 함수 호출
        if(formData.name && formData.password && formData.email && formData.address){
            memberJoin();
        }
    }

    //carLogin 함수
    async function memberJoin(){
        try{
            const response = await axios.post("http://localhost:8081/member/join", formData);
            //응답으로 "ok" or "fail"
            if(response.data == "ok"){
                navigate("/login");
            }
        }
        catch(e){
            console.log(e);
        }
    }   

    return (  
        <div>
            <h2>회원가입</h2>
            <form onSubmit={onSubmit}>
                <table>
                    <tbody>
                        <tr>
                            <td>이메일</td>
                            <td><input type="text" name="email" onChange={onChange} value={formData.username}/></td>
                        </tr>
                        <tr>
                            <td>패스워드</td>
                            <td><input type="password" name="password" onChange={onChange} value={formData.password}/></td>
                        </tr>
                        <tr>
                            <td>이름</td>
                            <td><input type="text" name="name" onChange={onChange} value={formData.name}/></td>
                        </tr>
                        <tr>
                            <td>주소</td>
                            <td><input type="text" name="address" onChange={onChange} value={formData.address}/></td>
                        </tr>
                        <tr>
                            <td colSpan={2}>
                                <button type="submit">회원가입</button>
                                <button type="reset" onClick={onReset}>취소</button>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </form>
        </div>
    );
}

export default Join;
