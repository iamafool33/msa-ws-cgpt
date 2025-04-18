// src/api/axios.js (또는 api/axiosInstance.js 등)
import axios from 'axios';

const instance = axios.create({
  baseURL: 'http://localhost:8080/api',
});

instance.interceptors.request.use((config) => {
  const token = localStorage.getItem('token');
  if (token && !token.startsWith("Bearer ")) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

export default instance;
