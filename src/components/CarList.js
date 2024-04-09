import axios from 'axios';
import React from 'react';
import useAsync from '../customHook/useAsync';

//useAsync()에 전달할 callback 함수
async function getCars(){
    const response = await axios.get("http://localhost:8081/cars");
    return response.data;
}

function CarList() {
    //state = {loading :false, data : null, error : null}
    const state = useAsync(getCars);
    //구조분해할당
    const {loading, data, error} = state;
    if (loading) return <div>로딩중</div>;
    if (error) return <div>에러가 발생했습니다.</div>;
    if (!data) return null; 

    return ( 
        <div>
            <table className='table'>
                <thead>
                    <tr>
                        <th>차량이미지</th>
                        <th>브랜드</th>
                        <th>모델</th>
                        <th>색상</th>
                        <th>가격</th>
                    </tr>
                </thead>
                <tbody>
                    {/* 서버에서 list로 보내면 배열로 받는다 */}
                    {data.map((car,index)=>
                        <tr key={index}>
                            <td><img src={"http://localhost:8081/image?image="+car.imgName} width={200}/></td>
                            <td>{car.brand}</td>
                            <td>{car.model}</td>
                            <td>{car.color}</td>
                            <td>{car.price}</td>
                        </tr>
                    )}
                </tbody>
            </table>
        </div>
    );
}

export default CarList;