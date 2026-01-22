import React, { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import axios from 'axios';
import './JoinPage.css'; // 👈 CSS 분리 적용

function JoinPage() {
    const navigate = useNavigate();

    // 회원가입 필드 상태 관리
    const [formData, setFormData] = useState({
        userId: '',
        userPw: '',
        name: '',
        userEmail: '',
        birth: '',
        gender: 'M', // 기본값 남성
        phone: ''
    });

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData({ ...formData, [name]: value });
    };

    const handleJoin = async (e) => {
        e.preventDefault();
        try {
            // 스프링 부트 회원가입 API 호출
            await axios.post('http://localhost:8080/api/users/join', formData);
            alert("회원가입이 완료되었습니다! 로그인을 해주세요.");
            navigate('/'); // 로그인 페이지로 이동
        } catch (error) {
            console.error("회원가입 에러:", error);
            alert("회원가입에 실패했습니다. 입력 정보를 확인해주세요.");
        }
    };

    return (
        <div className="join-container">
            <div className="join-box">
                <h2 className="join-title">회원가입</h2>
                <form onSubmit={handleJoin}>
                    <div className="join-input-group">
                        <label>아이디</label>
                        <input type="text" name="userId" className="join-input" onChange={handleChange} required />
                    </div>
                    <div className="join-input-group">
                        <label>비밀번호</label>
                        <input type="password" name="userPw" className="join-input" onChange={handleChange} required />
                    </div>
                    <div className="join-input-group">
                        <label>이름</label>
                        <input type="text" name="name" className="join-input" onChange={handleChange} required />
                    </div>
                    <div className="join-input-group">
                        <label>이메일</label>
                        <input type="email" name="userEmail" className="join-input" onChange={handleChange} />
                    </div>
                    <div className="join-input-group">
                        <label>생년월일 (YYYY-MM-DD)</label>
                        <input type="text" name="birth" className="join-input" placeholder="2000-01-01" onChange={handleChange} />
                    </div>
                    <div className="join-input-group">
                        <label>성별</label>
                        <div className="gender-group">
                            <button type="button" className={`gender-btn ${formData.gender === 'M' ? 'active' : ''}`} onClick={() => setFormData({...formData, gender: 'M'})}>남성</button>
                            <button type="button" className={`gender-btn ${formData.gender === 'F' ? 'active' : ''}`} onClick={() => setFormData({...formData, gender: 'F'})}>여성</button>
                        </div>
                    </div>
                    <div className="join-input-group">
                        <label>전화번호</label>
                        <input type="text" name="phone" className="join-input" placeholder="010-0000-0000" onChange={handleChange} />
                    </div>

                    <button type="submit" className="join-submit-btn">가입하기</button>
                </form>
                <div className="join-footer">
                    이미 계정이 있으신가요?
                    <Link to="/" className="login-link">로그인</Link>
                </div>
            </div>
        </div>
    );
}

export default JoinPage;