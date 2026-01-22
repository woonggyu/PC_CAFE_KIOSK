import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import './AdminOrderPage.css';

function AdminOrderPage() {
    const navigate = useNavigate();
    const [orders, setOrders] = useState([]);

    // 1. 서버에서 대기 중인 주문 목록 가져오기
    const fetchOrders = async () => {
        try {
            const response = await axios.get('http://localhost:8080/api/orders?status=WAITING');
            setOrders(response.data);
        } catch (error) {
            console.error("주문 목록 로딩 실패:", error);
        }
    };

    // 페이지 접속 시 최초 1회 및 이후 주기적 업데이트
    useEffect(() => {
        fetchOrders();
        const timer = setInterval(fetchOrders, 10000); // 10초마다 새 주문 확인
        return () => clearInterval(timer);
    }, []);

    // 2. 조리 완료 처리 (상태 변경 API 호출)
    const handleComplete = async (orderId) => {
        if (window.confirm('조리가 완료되었습니까?')) {
            try {
                await axios.patch(`http://localhost:8080/api/orders/${orderId}/status?newStatus=COMPLETED`);
                alert("완료 처리되었습니다.");
                fetchOrders(); // 목록 새로고침
            } catch (error) {
                alert("처리 중 오류가 발생했습니다.");
            }
        }
    };

    return (
        <div className="admin-order-container">
            <header className="admin-order-header">
                <button onClick={() => navigate('/admin')} className="order-back-btn">← 뒤로가기</button>
                <h2 className="order-title">
                    🍔 실시간 주문 접수 <span className="order-count-badge">{orders.length}</span>
                </h2>
            </header>

            <div className="order-grid">
                {orders.length > 0 ? (
                    orders.map(order => (
                        <div key={order.orderId} className="order-card">
                            <div className="card-top">
                                <span className="seat-label">{order.seatNo}번 좌석</span>
                                <span className="order-time-text">
                                    {new Date(order.createdAt).toLocaleTimeString([], {hour: '2-digit', minute:'2-digit'})}
                                </span>
                            </div>

                            <div className="menu-info-box">
                                {/* 💡 OrderItem 리스트를 순회하며 메뉴 표시 */}
                                {order.items && order.items.map((item, idx) => (
                                    <h3 key={idx} className="menu-name-text">
                                        {item.productName} x {item.quantity}
                                    </h3>
                                ))}
                                <p className="price-text">총 {order.totalPrice.toLocaleString()}원</p>
                                <p className="user-id-text">주문자: {order.userId}</p>
                            </div>

                            <button
                                onClick={() => handleComplete(order.orderId)}
                                className="complete-action-btn"
                            >
                                조리 완료
                            </button>
                        </div>
                    ))
                ) : (
                    <div className="empty-order-msg">대기 중인 주문이 없습니다.</div>
                )}
            </div>
        </div>
    );
}

export default AdminOrderPage;