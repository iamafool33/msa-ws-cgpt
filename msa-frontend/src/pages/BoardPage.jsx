import { useEffect, useState } from 'react';
import axios from '../api/axios';
import { useNavigate, Link } from 'react-router-dom';
import {
  Container, TextField, Button, Typography,
  Box, Card, CardContent, Stack
} from '@mui/material';

function BoardPage() {
  const [posts, setPosts] = useState([]);
  const [newPost, setNewPost] = useState({ title: '', content: '' });
  const navigate = useNavigate();

  const fetchPosts = async () => {
    try {
      const res = await axios.get('/board');
      setPosts(res.data);
    } catch (err) {
      console.error('게시글 조회 에러:', err.response?.data || err.message);
      alert('게시글 조회 실패 (로그인 필요)');
      navigate('/');
    }
  };

  const createPost = async () => {
    const writer = localStorage.getItem('username');
    try {
      await axios.post('/board', { ...newPost, writer });
      setNewPost({ title: '', content: '' });
      fetchPosts();
    } catch (err) {
      alert('게시글 등록 실패');
    }
  };

  const deletePost = async (id) => {
    try {
      await axios.delete(`/board/${id}`);
      fetchPosts();
    } catch (err) {
      alert('삭제 실패');
    }
  };

  const handleEdit = (post) => {
    const newTitle = prompt("새 제목:", post.title);
    const newContent = prompt("새 내용:", post.content);
    if (newTitle && newContent) {
      updatePost(post.id, newTitle, newContent);
    }
  };

  const updatePost = async (id, title, content) => {
    try {
      await axios.put(`/board/${id}`, { title, content });
      fetchPosts();
    } catch (err) {
      alert('수정 실패');
    }
  };

  useEffect(() => {
    fetchPosts();
  }, []);

  return (
    <Container maxWidth="md" sx={{ mt: 5 }}>
      <Typography variant="h4" gutterBottom>📋 게시판</Typography>

      <Box sx={{ mb: 3 }}>
        <TextField
          label="제목"
          fullWidth
          value={newPost.title}
          onChange={(e) => setNewPost({ ...newPost, title: e.target.value })}
          sx={{ mb: 2 }}
        />
        <TextField
          label="내용"
          fullWidth
          multiline
          rows={4}
          value={newPost.content}
          onChange={(e) => setNewPost({ ...newPost, content: e.target.value })}
          sx={{ mb: 2 }}
        />
        <Button variant="contained" onClick={createPost}>
          게시글 등록
        </Button>
      </Box>

      <Stack spacing={2}>
        {posts.map((post) => (
          <Card key={post.id}>
            <CardContent>
              <Typography
                component={Link}
                to={`/board/${post.id}`}
                variant="h6"
                sx={{ textDecoration: 'none', color: 'inherit' }}
              >
                {post.title}
              </Typography>
              <Typography variant="body2" color="text.secondary" gutterBottom>
                작성자: {post.writer}
              </Typography>
              <Typography sx={{ my: 1 }}>{post.content}</Typography>
              <Stack direction="row" spacing={1}>
                <Button size="small" variant="outlined" onClick={() => handleEdit(post)}>수정</Button>
                <Button size="small" variant="outlined" color="error" onClick={() => deletePost(post.id)}>삭제</Button>
              </Stack>
            </CardContent>
          </Card>
        ))}
      </Stack>
    </Container>
  );
}

export default BoardPage;
