import React from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import './MainPage.css';

function MainPage() {
    const location = useLocation();
    const navigate = useNavigate();
    const user = location.state?.user;

    // 💡 먹거리 주문 페이지로 이동하는 함수
    const goToOrder = () => {
        navigate('/order', {
            state: {
                user: user,
                seatNo: user.seatNo // 💡 서버에서 받아온 진짜 좌석 번호 전달
            }
        });
    };

    const handleLogout = () => {
        if (window.confirm("사용을 종료하시겠습니까?")) {
            navigate('/');
        }
    };

    if (!user) {
        return <div style={{color:'white', padding:'20px'}}>로그인이 필요한 서비스입니다.</div>;
    }

    return (
        <div className="pc-main-container">
            <div className="desktop-icons"></div>

            <div className="pc-widget">
                <div className="widget-header">
                    {/* 💡 수정 포인트: NO. 15 대신 user.seatNo를 사용합니다. */}
                    <span className="seat-number">NO. {user.seatNo || "???"}</span>
                    <span style={{fontSize:'12px', color:'#888'}}>USER MODE</span>
                </div>

                <div className="user-info">
                    <div className="info-row">
                        <span className="info-label">사용자</span>
                        <span className="info-value">{user.name} 님</span>
                    </div>
                    <div className="info-row">
                        <span className="info-label">아이디</span>
                        <span className="info-value">{user.userId}</span>
                    </div>
                    <div className="remain-time-box">
                        <span className="info-label">남은 시간</span>
                        <span className="time">{user.remainTime}분</span>
                    </div>
                </div>

                <div className="widget-btns">
                    <button className="widget-btn" onClick={goToOrder}>먹거리 주문</button>
                    <button className="widget-btn">요금제 구매</button>
                    <button className="widget-btn">자리 이동</button>
                    <button className="widget-btn">메시지</button>
                    <button className="widget-btn btn-exit" onClick={handleLogout}>사용 종료</button>
                </div>
            </div>
        </div>
    );
}

export default MainPage;