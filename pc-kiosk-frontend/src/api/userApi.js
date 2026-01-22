import axiosInstance from './axiosConfig';

// 로그인 요청 함수
export const login = async (loginData) => {
    try {
        const response = await axiosInstance.post('/api/users/login', loginData);
        return response.data; // 성공 시 유저 정보 반환
    } catch (error) {
        // 백엔드에서 던진 에러 메시지 처리
        throw error.response ? error.response.data : new Error('서버 연결 실패');
    }
};