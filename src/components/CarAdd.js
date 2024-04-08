import axios from 'axios';
import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';


    
function CarAdd() {

    //페이지 이동시에 필요한 navitage만들기
    const navigate = useNavigate();
    
    const [formData, setFormData] = useState({
        brand : "", 
        model : "",
        color : "", 
        registerNumber : "", 
        year : "", 
        price : "", 
        dealerId : "1"
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
            brand : "", 
            model :"",
            color : "", 
            registerNumber : "", 
            year : "", 
            price : "", 
            dealerId : ""
        })
    }

    //로그인버튼 클릭 시 
    const onSubmit=(e) => {
        //전송 요청 이벤트 제거
        e.preventDefault();
        //입력이 다 되었는지 체크 후 함수 호출
        if(formData.brand && formData.model && formData.color 
            && formData.registerNumber && formData.year && formData.price && formData.dealerId){
            addCar();
        }
    }

    //carLogin 함수
    async function addCar(){
        try{
            const response = await axios.post("http://localhost:8081/addCar", formData);
            //응답으로 "ok" or "fail"
            if(response.data == "ok"){
                navigate("/");
            }
        }
        catch(e){
            console.log(e);
        }
    }   


    return (  
        <div>
            <h2>차량 등록</h2>
            <form onSubmit={onSubmit}>
                <table>
                    <tbody>
                        <tr>
                            <td>브랜드</td>
                            <td><input type="text" name="brand" onChange={onChange} value={formData.brand}/></td>
                        </tr>
                        <tr>
                            <td>모델</td>
                            <td><input type="text" name="model" onChange={onChange} value={formData.model}/></td>
                        </tr>
                        <tr>
                            <td>색상</td>
                            <td><input type="text" name="color" onChange={onChange} value={formData.color}/></td>
                        </tr>
                        <tr>
                            <td>등록번호</td>
                            <td><input type="text" name="registerNumber" onChange={onChange} value={formData.registerNumber}/></td>
                        </tr>
                        <tr>
                            <td>연식</td>
                            <td><input type="text" name="year" onChange={onChange} value={formData.year}/></td>
                        </tr>
                        <tr>
                            <td>가격</td>
                            <td><input type="text" name="price" onChange={onChange} value={formData.price}/></td>
                        </tr>
                        <tr>
                            <td colSpan={2}>
                                <button type="submit">등록</button>
                                <button type="reset" onClick={onReset}>취소</button>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </form>
        </div>
    );
}

export default CarAdd;