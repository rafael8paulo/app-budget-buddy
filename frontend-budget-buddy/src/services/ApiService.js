// services/ApiService.js
import axios from 'axios';
import ApiConfig from './ApiConfig';

const apiService = axios.create({
  baseURL: ApiConfig.baseURL,
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json',
  },
});

export default apiService;