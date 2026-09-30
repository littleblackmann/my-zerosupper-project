import axios from 'axios';

const api = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL || '/api',
  timeout: 15000,
  headers: {
    'Content-Type': 'application/json',
  },
});

api.interceptors.request.use((config) => {
  const token = localStorage.getItem('userToken');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

export function saveSession(session) {
  localStorage.setItem('userToken', session.token);
  localStorage.setItem('userId', String(session.userId));
  localStorage.setItem('userEmail', session.email);
  localStorage.setItem('userRole', session.role);
  window.dispatchEvent(new Event('zerosupper-auth-changed'));
}

export function clearSession() {
  localStorage.removeItem('userToken');
  localStorage.removeItem('userId');
  localStorage.removeItem('userEmail');
  localStorage.removeItem('userRole');
  window.dispatchEvent(new Event('zerosupper-auth-changed'));
}

export function apiErrorMessage(error, fallback = '操作失敗，請稍後再試。') {
  return error.response?.data?.message || fallback;
}

export default api;
