import React from 'react';
import { useNavigate } from 'react-router-dom';
import './AdminMainPage.css'; // 👈 CSS 분리 적용

function AdminMainPage() {
    const navigate = useNavigate();

    // 관리자 메뉴 구성 데이터
    const adminMenus = [
        { id: 'seats', title: '🖥️ 좌석 모니터링', path: '/admin/seats', color: '#4caf50' },
        { id: 'orders', title: '🍔 주문 접수', path: '/admin/orders', color: '#ff9800' },
        { id: 'users', title: '👤 회원 관리', path: '/admin/users', color: '#2196f3' },
        { id: 'sales', title: '📊 매출 통계', path: '/admin/sales', color: '#9c27b0' },
    ];

    return (
        <div className="admin-main-container">
            <header className="admin-main-header">
                <h1>ADMIN DASHBOARD</h1>
                <button onClick={() => navigate('/')} className="admin-logout-btn">
                    로그아웃
                </button>
            </header>

            <div className="admin-menu-grid">
                {adminMenus.map(menu => (
                    <div
                        key={menu.id}
                        className="admin-menu-card"
                        style={{ borderTop: `10px solid ${menu.color}` }} // 상단 포인트 색상은 인라인으로 유지
                        onClick={() => navigate(menu.path)}
                    >
                        <h2>{menu.title}</h2>
                        <p>바로가기</p>
                    </div>
                ))}
            </div>
        </div>
    );
}

export default AdminMainPage;