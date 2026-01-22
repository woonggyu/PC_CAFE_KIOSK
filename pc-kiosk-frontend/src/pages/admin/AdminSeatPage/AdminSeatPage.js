import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import './AdminSeatPage.css'; // 👈 CSS 임포트

function AdminSeatPage() {
    const navigate = useNavigate();
    const [seats, setSeats] = useState([]);
    const [selectedSeat, setSelectedSeat] = useState(null);

    // 제어용 상태
    const [targetUserId, setTargetUserId] = useState('');
    const [inputTime, setInputTime] = useState(0);

    const fetchSeats = async () => {
        try {
            const response = await axios.get('http://localhost:8080/api/admin/seats');
            setSeats(response.data);
        } catch (error) {
            console.error("좌석 로딩 실패:", error);
        }
    };

    useEffect(() => {
        fetchSeats();
    }, []);

    const handleControl = async (status) => {
        try {
            await axios.patch(`http://localhost:8080/api/admin/seats/${selectedSeat.seatNo}`, {
                status: status,
                addTime: parseInt(inputTime) || 0,
                userId: targetUserId || selectedSeat.userId
            });
            alert("처리가 완료되었습니다.");
            closeModal();
            fetchSeats();
        } catch (error) {
            alert("실패: " + (error.response?.data?.message || error.message));
        }
    };

    const closeModal = () => {
        setSelectedSeat(null);
        setTargetUserId('');
        setInputTime(0);
    };

    return (
        <div className="admin-seat-container">
            <header className="admin-seat-header">
                <button onClick={() => navigate('/admin')} className="back-btn">← 뒤로가기</button>
                <h2 className="admin-seat-title">🖥️ 실시간 좌석 제어</h2>
            </header>

            <div className="seat-grid">
                {seats.map(seat => (
                    <div
                        key={seat.seatNo}
                        className={`seat-card ${seat.status === 'USING' ? 'using' : ''}`}
                        onClick={() => {
                            setSelectedSeat(seat);
                            if(seat.userId) setTargetUserId(seat.userId);
                        }}
                    >
                        <div className="seat-no">{seat.seatNo}</div>
                        {seat.status === 'USING' ? (
                            <>
                                <div className="user-id">{seat.userId}</div>
                                <div className="remain-time">{seat.remainTime}분</div>
                            </>
                        ) : (
                            <div className="empty-text">빈 좌석</div>
                        )}
                    </div>
                ))}
            </div>

            {selectedSeat && (
                <div className="modal-overlay">
                    <div className="control-modal">
                        <h3 style={{color: '#61dafb'}}>{selectedSeat.seatNo}번 좌석 관리</h3>

                        <div className="input-group">
                            <label className="input-label">사용자 아이디</label>
                            <input
                                type="text"
                                className="admin-input"
                                value={targetUserId}
                                onChange={(e) => setTargetUserId(e.target.value)}
                                placeholder="회원 아이디 입력"
                                disabled={selectedSeat.status === 'USING'}
                            />
                        </div>

                        <div className="input-group">
                            <label className="input-label">추가할 시간(분)</label>
                            <input
                                type="number"
                                className="admin-input"
                                value={inputTime}
                                onChange={(e) => setInputTime(e.target.value)}
                            />
                        </div>

                        <div className="modal-btns">
                            {selectedSeat.status === 'EMPTY' ? (
                                <button onClick={() => handleControl('USING')} className="btn-on">좌석 강제 켜기</button>
                            ) : (
                                <>
                                    <button onClick={() => handleControl('USING')} className="btn-add">시간 충전</button>
                                    <button onClick={() => handleControl('EMPTY')} className="btn-off">강제 종료</button>
                                </>
                            )}
                            <button onClick={closeModal} className="btn-close">닫기</button>
                        </div>
                    </div>
                </div>
            )}
        </div>
    );
}

export default AdminSeatPage;