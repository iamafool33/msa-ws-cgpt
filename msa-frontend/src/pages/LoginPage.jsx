import { useState } from 'react';
import axios from '../api/axios';
import { useNavigate } from 'react-router-dom';
import { TextField, Button, Container, Typography, Box } from '@mui/material';
import { parseJwt } from '../utils/jwt';

function LoginPage() {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate();



  const handleLogin = async () => {
    try {
      const res = await axios.post('/user/login', { username, password });
      localStorage.setItem('token', res.data.token);

      const user = parseJwt(res.data.token);
      localStorage.setItem('username', user.sub); // 사용자 이름 저장

      alert('로그인 성공');
      navigate('/board');
    } catch (err) {
      alert('로그인 실패');
    }
  };

  return (
    <Container maxWidth="sm">
      <Box mt={10} p={4} boxShadow={3} borderRadius={2}>
        <Typography variant="h5" mb={3}>로그인</Typography>
        <TextField
          label="아이디"
          fullWidth
          margin="normal"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
        />
        <TextField
          label="비밀번호"
          type="password"
          fullWidth
          margin="normal"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />
        <Button
          variant="contained"
          fullWidth
          onClick={handleLogin}
          sx={{ mt: 2 }}
        >
          로그인
        </Button>
        
         {/* ✅ 여기 추가! */}
         <Typography variant="body2" align="center" mt={2}>
           아직 계정이 없으신가요?{" "}
           <a href="/signup">회원가입</a>
         </Typography>
        
        
      </Box>
    </Container>
  );
}

export default LoginPage;
