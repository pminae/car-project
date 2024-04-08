import React, { useEffect } from 'react';
import { Link } from 'react-router-dom';

function Header({isAuthenticated, setAuth}) {
    const logout = () => {
        sessionStorage.removeItem("jwt");
        setAuth(false);
    }

    //컴포넌트가 화면에 마운트 될 때 sessionStorage에 jwt가 존재하면
    //isAuthenticated 값을 true로 변경
    useEffect(()=>{
        if(sessionStorage.getItem("jwt")){
            setAuth(true);
        }
    },[])

    return (
        <div>
            <nav className="navbar navbar-expand-lg bg-body-tertiary">
                <div className="container-fluid">
                    <h2>Green Car</h2>
                    <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                        <span className="navbar-toggler-icon"></span>
                    </button>
                    <div className="collapse navbar-collapse" id="navbarSupportedContent">
                        <ul className="navbar-nav me-auto mb-2 mb-lg-0">
                            <li className="nav-item">
                                <Link to="/">Home</Link>
                            </li>
                            {isAuthenticated ? 
                            <li className="nav-item">
                                <Link onClick={logout}>로그아웃</Link>
                            </li> : <><li className="nav-item">
                                <Link to="/login">로그인</Link>
                            </li><li className="nav-item">
                                <Link to="/join">회원가입</Link>
                            </li></>
                            }
                            <li className="nav-item">
                                <Link to="/addCar">자동차등록</Link>
                            </li>
                        </ul>
                        <form className="d-flex" role="search">
                            <input className="form-control me-2" type="search" placeholder="Search" aria-label="Search" />
                            <button className="btn btn-outline-success" type="submit">Search</button>
                        </form>
                    </div>
                </div>
            </nav>
        </div>
    );
}

export default Header;