import { makeAutoObservable } from 'mobx';
import { AuthService } from 'pages/RegisterPage/services/AuthServices.js';
import axios from 'axios';
import { API_URL } from '../http';

export default class Store{
  user = {};
  isAuth = false;
  isLoading = false;

  constructor() {
    makeAutoObservable(this);
  }


  setAuth = (bool) => {
    this.isAuth = bool;
  };

  setUser = (user) => {
    this.user = user;
  };

  setLoading = (bool) => {
    this.isLoading = bool;
  };


  get store() {
    return {
      user: this.user,
      isAuth: this.isAuth,
      };
  }


  login = async (email, password) => {
    try {
      const response = await AuthService.login(email, password);
      localStorage.setItem('token', response.data.accessToken);
      this.setAuth(true);
      this.setUser(response.data.user);
    } catch (e) {  
      console.log(e.response?.data?.message);
    }
  };

  registration = async (email, password) => {
    try {
      const response = await AuthService.registration(email, password);
      localStorage.setItem('token', response.data.accessToken);
      this.setAuth(true);
      this.setUser(response.data.user);
    } catch (e) {
      console.log(e.response?.data?.message);
    }
  };

  logout = async () => {
    try {
      await AuthService.logout();
      localStorage.removeItem('token');
      this.setAuth(false); 
      this.setUser({});
    } catch (e) {
      console.log(e.response?.data?.message);
    }
  };

  checkAuth = async () => {
    try {
      this.setLoading(true);
      const response = await axios.get(`${API_URL}/auth/refresh-token`, {
        withCredentials: true
      });
      localStorage.setItem('token', response.data.accessToken);
      this.setAuth(true);
      this.setUser(response.data.user);
    } catch (e) {
      console.log(e.response?.data?.message);
    } finally {
      this.setLoading(false);
    }
  };
}


