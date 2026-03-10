export interface AuthResponse {
  token: string;
  refreshToken: string;
  email: string;
  role: string;
  firstName: string;
  lastName: string;
  id: number;
}