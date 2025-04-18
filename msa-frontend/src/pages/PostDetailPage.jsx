import { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import axios from '../api/axios';
import {
  Typography,
  Container,
  Button,
  Box,
  Card,
  CardContent,
  Divider,
  Stack
} from '@mui/material';

function PostDetailPage() {
  const { id } = useParams();
  const [post, setPost] = useState(null);
  const navigate = useNavigate();

  const fetchPost = async () => {
    try {
      const res = await axios.get(`/board/${id}`);
      setPost(res.data);
    } catch (err) {
      alert('게시글 조회 실패');
      navigate('/board');
    }
  };

  useEffect(() => {
    fetchPost();
  }, [id]);

  if (!post) return null;

  return (
    <Container maxWidth="md" sx={{ mt: 8 }}>
      <Card
        elevation={3}
        sx={{
          maxWidth: 600,
          mx: 'auto', // 가운데 정렬
          p: 3,
          borderRadius: 2,
        }}
      >
        <CardContent>
          <Typography variant="h4" gutterBottom>
            {post.title}
          </Typography>

          <Typography variant="subtitle2" color="text.secondary" sx={{ mb: 1 }}>
            작성자: {post.writer}
          </Typography>

          <Divider sx={{ my: 2 }} />

          <Typography variant="body1" sx={{ whiteSpace: 'pre-line' }}>
            {post.content}
          </Typography>

          <Stack direction="row" justifyContent="flex-end" mt={4}>
            <Button variant="contained" onClick={() => navigate('/board')}>
              목록으로
            </Button>
          </Stack>
        </CardContent>
      </Card>
    </Container>
  );
}

export default PostDetailPage;
