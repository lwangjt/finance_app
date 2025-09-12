import axios from 'axios';

const API_URL = '/api/auth';

interface SignupData {
  firstName: string;
  lastName: string;
  email: string;
  password: string;
}

interface SigninData {
  email: string;
  password: string;
}

interface AuthResponse {
  token: string;
  type: string;
  id: number;
  email: string;
  firstName: string;
  lastName: string;
}

const signup = async (userData: SignupData) => {
  const response = await axios.post(`${API_URL}/signup`, userData);
  return response.data;
};

const signin = async (credentials: SigninData): Promise<AuthResponse> => {
  const response = await axios.post(`${API_URL}/signin`, credentials);
  return response.data as AuthResponse;
};

const authService = {
  signup,
  signin,
};

export default authService;
