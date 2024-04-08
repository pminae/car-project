import './App.css';
import Header from './components/Header';
import CarList from './components/CarList';
import Login from './components/Login';
import { useState } from 'react';
import { Route, Routes } from 'react-router-dom';
import Join from './components/Join';
import CarAdd from './components/CarAdd';

function App() {
  //로그인 한 상태인지 상태관리 
  const [isAuthenticated, setAuth] = useState(false);
  return (
    <div className="App">
      {/* header에 로그아웃 버튼 클릭하면 isAuthenticated = false로 변경 */}
      <Header isAuthenticated={isAuthenticated} setAuth={setAuth}/>
      <Routes>
        <Route path='/' element={<CarList/>}/>
        {/* 로그인 페이지에서 로그인 되면 isAuthenticated = true로 변경 */}
        <Route path='/login' element={<Login setAuth={setAuth}/>}/>
        <Route path='/join' element={<Join/>}/>
        <Route path='/addCar' element={<CarAdd/>}/>
      </Routes>
    </div>
  );
}

export default App;
