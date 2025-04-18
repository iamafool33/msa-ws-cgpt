import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from '../api/axios';
import {
  TextField,
  Button,
  Container,
  Typography,
  Box
} from '@mui/material';

function SignupPage() {
  const [form, setForm] = useState({ username: '', password: '' });
  const navigate = useNavigate();

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSignup = async () => {
    try {
      await axios.post('/user/signup', form);
      alert('회원가입 성공! 로그인 페이지로 이동합니다.');
      navigate('/');
    } catch (err) {
      alert('회원가입 실패');
      console.error(err);
    }
  };

  return (
    <Container maxWidth="sm">
      <Box mt={10} p={4} boxShadow={3} borderRadius={2}>
        <Typography variant="h5" mb={3}>회원가입</Typography>
        <TextField
          label="아이디"
          name="username"
          fullWidth
          margin="normal"
          value={form.username}
          onChange={handleChange}
        />
        <TextField
          label="비밀번호"
          name="password"
          type="password"
          fullWidth
          margin="normal"
          value={form.password}
          onChange={handleChange}
        />
        <Button
          variant="contained"
          fullWidth
          onClick={handleSignup}
          sx={{ mt: 2 }}
        >
          가입하기
        </Button>
      </Box>
    </Container>
  );
}

export default SignupPage;
