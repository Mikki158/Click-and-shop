import React, { useState } from 'react'
import './Navbar.css'
import logo from '../../assets/logo.svg'
import search_icon from '../../assets/search_icon.svg'
import axios from "axios"

const Navbar = () => {

    const [modal, setModal] = useState(false);
    
    const [username, setUsername] = useState('');
    const [authCode, setAuthCode] = useState('');

    function Login() {
        console.log("Login")
        console.log(username)
        console.log(authCode)
        const url = 'http://localhost:8080/api/auth/checkauthcode';
        const data = {
            username: username,
            authCode: authCode
        };

        axios.post(url, data)
            .then(response => {
                toggleModal;
                console.log(response);
            })
            .catch(function(error) {
                console.log(error);
            });
    }

    const usernameHandler = (e) => {
        setUsername(e.target.value)
    }

    const authCodeHandler = (e) => {
        setAuthCode(e.target.value)
    }

    const toggleModal = () => {
        setUsername('')
        setAuthCode('')
        setModal(!modal);
    }

    if(modal) {
        document.body.classList.add('active-modal')
    } else {
        document.body.classList.remove('active-modal')
    }

    return (
    <>
        <div className='navbar'>
            <img src={logo} alt=""  className='logo'/>

            <div className='search-box'>
                <input type='text' placeholder='Search'/>
                {/* <img src={search_icon} alt="" /> */}
            </div>

            <ul>
                <li>Корзина</li>
                <li>Заказы</li>
                <li>Избранное</li>
                <li onClick={toggleModal}>Профиль</li>
            </ul>
        </div>
        
        {modal && (
            <div className='modal'>
                <div onClick={toggleModal} className="overlay"></div>
                <div className="modal-content">
                    <div className="header">
                        <div className="text">Авторизация</div>
                        <div className="underline"></div>
                    </div>
                    <div className="inputs">
                        <div className="input">
                            <input type="text" placeholder='@username' value={username} onChange={(e) => usernameHandler(e)}/>
                        </div>
                        <div className="input">
                            <input type="text" placeholder='Код авторизации' value={authCode} onChange={(e) => authCodeHandler(e)}/>
                        </div>
                    </div>
                    <button onClick={Login}>Login</button>
                </div>
            </div>
        )}
    </>
  )
}

export default Navbar