import React, { useState, useEffect } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import axios from 'axios';
import './UserOrderPage.css';

function UserOrderPage() {
    const navigate = useNavigate();
    const location = useLocation();

    // 💡 MainPage에서 넘겨준 state 정보 확인
    const userInfo = location.state?.user;
    const seatNo = location.state?.user?.seatNo || location.state?.seatNo || "미지정";

    const [menuItems, setMenuItems] = useState([]);
    const [currentCat, setCurrentCat] = useState('전체');
    const [cart, setCart] = useState([]);

    // 1. 상품 목록 가져오기 (백엔드 ProductController와 연결)
    useEffect(() => {
        const fetchProducts = async () => {
            try {
                // 백엔드에서 ProductResponseDTO 리스트를 받아옴
                const response = await axios.get('http://localhost:8080/api/products');
                setMenuItems(response.data);
            } catch (error) {
                console.error("상품 로딩 실패:", error);
                alert("메뉴판을 불러오지 못했습니다.");
            }
        };
        fetchProducts();
    }, []);

    // 2. 카테고리 자동 생성
    const categories = ['전체', ...new Set(menuItems.map(item => item.categoryName).filter(Boolean))];

    // 3. 장바구니 추가
    const addToCart = (item) => {
        setCart([...cart, item]);
    };

    // 4. 총 금액
    const totalPrice = cart.reduce((acc, item) => acc + item.price, 0);

    // 5. 서버로 주문 전송 (String userId 반영)
    const submitOrder = async () => {
        if (cart.length === 0) return alert("메뉴를 선택해주세요!");
        if (!userInfo) return alert("사용자 정보가 없습니다. 다시 로그인해주세요.");

        if (window.confirm(`${cart.length}개 메뉴를 주문하시겠습니까?`)) {
            try {
                const orderPayload = {
                    userId: userInfo.userId, // 💡 String 타입 (예: "admin")
                    seatNo: seatNo,
                    totalPrice: totalPrice,
                    items: cart.map(item => ({
                        productId: item.productId,
                        quantity: 1,
                        price: item.price
                    }))
                };

                const response = await axios.post('http://localhost:8080/api/orders', orderPayload);
                alert(response.data); // "주문이 완료되었습니다" 메시지
                setCart([]);
                navigate('/main', { state: { user: userInfo } });
            } catch (error) {
                console.error("주문 에러:", error);
                alert("주문 중 서버 오류가 발생했습니다.");
            }
        }
    };

    return (
        <div className="order-page-container">
            <header className="order-header">
                <div className="header-left">
                    <button onClick={() => navigate('/main', { state: { user: userInfo } })} className="back-btn">←</button>
                    <h2>PC방 맛집 <span className="seat-badge">NO.{seatNo}</span></h2>
                </div>
                <div className="user-badge">{userInfo?.name}님 접속 중</div>
            </header>

            <nav className="category-tabs">
                {categories.map(cat => (
                    <button
                        key={cat}
                        className={`category-item ${currentCat === cat ? 'active' : ''}`}
                        onClick={() => setCurrentCat(cat)}
                    >
                        {cat}
                    </button>
                ))}
            </nav>

            <main className="menu-grid">
                {menuItems
                    .filter(item => currentCat === '전체' || item.categoryName === currentCat)
                    .map(item => (
                        <div key={item.productId} className="menu-card" onClick={() => addToCart(item)}>
                            <div className="menu-img-wrapper">
                                {item.imageUrl ? (
                                    <img src={item.imageUrl} alt={item.name} className="menu-img" />
                                ) : (
                                    <div className="menu-img-placeholder">NO IMAGE</div>
                                )}
                            </div>
                            <div className="menu-info">
                                <p className="menu-name">{item.name}</p>
                                <p className="menu-price">{item.price.toLocaleString()}원</p>
                            </div>
                        </div>
                    ))
                }
            </main>

            {cart.length > 0 && (
                <div className="cart-footer">
                    <div className="cart-info">
                        <span className="cart-count">선택한 메뉴 <strong>{cart.length}</strong>개</span>
                        <span className="cart-total">{totalPrice.toLocaleString()}원</span>
                    </div>
                    <button className="order-btn" onClick={submitOrder}>주문하기</button>
                </div>
            )}
        </div>
    );
}

export default UserOrderPage;