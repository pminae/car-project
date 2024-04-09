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

    //폼태그 객체 생성
    //<form><input name="uploadFiles" value=""/></form>
    const carFormData = new FormData();

    //파일 타입의 input에 변경이 일어났을 때 (이미지를 등록했을 때)
    const onChangeImage = (e)=>{
        //name : uploadFiles
        const {name} = e.target;
        console.dir(e.target);
        //파일이 업로드 되었을 때 이벤트가 발생한 input의 files 속성이 있을 때
        //files의 길이가 0보다 클 때
        if (e.target.files && e.target.files.length>0){
            //폼태그에 속성 추가하기
            //<input name="upload" value = File/>
            carFormData.append(name, e.target.files[0]);
        }
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

    //등록 버튼 클릭 시 
    const onSubmit=(e) => {
        //전송 요청 이벤트 제거
        e.preventDefault();
        //입력이 다 되었는지 체크 후 함수 호출
        if(formData.brand && formData.model && formData.color 
            && formData.registerNumber && formData.year && formData.price && formData.dealerId){
            carInsert();
        }
    }

    //carLogin 함수
    async function carInsert(){
        //form에 전달한 속성을 다 추가
        carFormData.append("brand", formData.brand);
        carFormData.append("model", formData.model);
        carFormData.append("color", formData.color);
        carFormData.append("registerNumber", formData.registerNumber);
        carFormData.append("year", formData.year);
        carFormData.append("price", formData.price);
        carFormData.append("dealerId", formData.dealerId);

        try{
            const response = await axios.post("http://localhost:8081/addCar", carFormData, {
                //form 전송 ---> content type을 multipart/form-data로 수정
                headers : {
                    "Content-type":"multipart/form-data"
                }
            });
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
                            <td>차량이미지1</td>
                            <td><input type="file" className='custom-file-input' name="uploadFiles" onChange={onChangeImage}/></td>
                        </tr>
                        <tr>
                            <td>차량이미지2</td>
                            <td><input type="file" className='custom-file-input' name="uploadFiles" onChange={onChangeImage}/></td>
                        </tr>
                        <tr>
                            <td>차량이미지3</td>
                            <td><input type="file" className='custom-file-input' name="uploadFiles" onChange={onChangeImage}/></td>
                        </tr>
                        <tr>
                            <td>차량이미지4</td>
                            <td><input type="file" className='custom-file-input' name="uploadFiles" onChange={onChangeImage}/></td>
                        </tr>
                        <tr>
                            <td>차량이미지5</td>
                            <td><input type="file" className='custom-file-input' name="uploadFiles" onChange={onChangeImage}/></td>
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