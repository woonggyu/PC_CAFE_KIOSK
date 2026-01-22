// 관리자 페이지 경로
import AdminMainPage from './pages/admin/AdminMainPage/AdminMainPage';
import AdminSeatPage from './pages/admin/AdminSeatPage/AdminSeatPage';
import AdminOrderPage from './pages/admin/AdminOrderPage/AdminOrderPage';

//사용자 페이지 경로
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import LoginPage from './pages/user/LoginPage/LoginPage';
import JoinPage from './pages/user/JoinPage/JoinPage';
import MainPage from './pages/user/MainPage/MainPage';
import UserOrderPage from "./pages/user/UserOrderPage/UserOrderPage";
import './App.css';

function App() {
  return (
      <Router>
        <Routes>
          {/* 첫 화면은 로그인 페이지 */}
          <Route path="/" element={<LoginPage />} />
          {/* /join 주소로 오면 회원가입 페이지 */}
          <Route path="/join" element={<JoinPage />} />
          {/*관리자 페이지 */}
           <Route path="/admin" element={<AdminMainPage />} />
            <Route path="/admin/seats" element={<AdminSeatPage />} />
            <Route path="/admin/orders" element={<AdminOrderPage />} />
            {/*사용자 페이지 (메인)*/}
            <Route path="/main" element={<MainPage />} />
            <Route path="/order" element={<UserOrderPage />} />
        </Routes>
      </Router>
  );
}

export default App;