import React, { useState, useEffect } from 'react';
import { useNavigate, Link, useLocation } from 'react-router-dom';
import axios from 'axios';
import './LoginPage.css';

function LoginPage() {
    const navigate = useNavigate();
    const location = useLocation();

    // 1. URL에서 좌석 번호 읽기 (예: localhost:3000/login?seatNo=5)
    // 💡 URLSearchParams를 사용하면 주소창의 파라미터를 쉽게 가져옵니다.
    const queryParams = new URLSearchParams(location.search);
    const seatNoFromUrl = parseInt(queryParams.get('seatNo')) || 1; // 없으면 기본 1번

    // 2. 입력값 상태 관리 (seatNo 포함)
    const [loginData, setLoginData] = useState({
        userId: '',
        userPw: '',
        seatNo: seatNoFromUrl // 👈 주소창에서 읽은 번호를 초기값으로 설정
    });

    // 💡 주소창의 번호가 바뀌면 바로 반영되도록 효과 추가
    useEffect(() => {
        setLoginData(prev => ({ ...prev, seatNo: seatNoFromUrl }));
    }, [seatNoFromUrl]);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setLoginData({ ...loginData, [name]: value });
    };

    const handleLogin = async (e) => {
        e.preventDefault();

        try {
            // 백엔드의 UserService.login(LoginRequestDTO)으로 전달
            const response = await axios.post('http://localhost:8080/api/users/login', loginData);

            if (response.data) {
                const user = response.data; // 서버가 DB에서 확인한 진짜 정보 (UserResponseDTO)
                alert(`${user.name}님, ${user.seatNo}번 좌석에서 환영합니다!`);

                if (user.role === 'ADMIN') {
                    navigate('/admin');
                } else {
                    // 💡 서버에서 준 '진짜 좌석번호'가 담긴 user 객체를 다음 페이지로 전달
                    navigate('/main', { state: { user: user } });
                }
            }
        } catch (error) {
            console.error("로그인 실패:", error);
            alert("로그인 실패: 아이디, 비밀번호 또는 좌석 번호를 확인하세요.");
        }
    };

    return (
        <div className="login-container">
            <div className="login-box">
                <h2 className="login-title">PC-Kiosk Login</h2>

                {/* 💡 현재 인식된 좌석 번호를 사용자에게 보여줌 */}
                <div className="seat-info-badge">
                    현재 좌석: <strong>{loginData.seatNo}</strong>번
                </div>

                <form onSubmit={handleLogin}>
                    <div className="login-input-group">
                        <label>아이디</label>
                        <input
                            type="text"
                            name="userId"
                            className="login-input"
                            value={loginData.userId}
                            onChange={handleChange}
                            placeholder="아이디를 입력하세요"
                            required
                        />
                    </div>
                    <div className="login-input-group">
                        <label>비밀번호</label>
                        <input
                            type="password"
                            name="userPw"
                            className="login-input"
                            value={loginData.userPw}
                            onChange={handleChange}
                            placeholder="비밀번호를 입력하세요"
                            required
                        />
                    </div>
                    <button type="submit" className="login-btn">로그인</button>
                </form>
                <div className="login-footer">
                    계정이 없으신가요?
                    <Link to="/join" className="join-link">회원가입</Link>
                </div>
            </div>
        </div>
    );
}

export default LoginPage;