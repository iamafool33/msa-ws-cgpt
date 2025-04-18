import { BrowserRouter, Routes, Route } from 'react-router-dom';
import LoginPage from './pages/LoginPage';
import BoardPage from './pages/BoardPage';
import SignupPage from './pages/SignupPage';
import PostDetailPage from './pages/PostDetailPage';

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<LoginPage />} />         {/* 기본 경로 로그인 */}
        <Route path="/board" element={<BoardPage />} />    {/* 로그인 성공 시 이동 */}
        <Route path="/signup" element={<SignupPage />} />  {/* 회원가입 */}
        <Route path="/board/:id" element={<PostDetailPage />} /> {/* ✅ 상세보기 */}
      </Routes>
    </BrowserRouter>
  );
}

export default App;
