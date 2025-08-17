import { AuthService } from 'pages/RegisterPage/services/AuthServices.js';
import axios from 'axios';
import { API_URL } from '../http';

let user = {};
let isAuth = false;
let isLoading = false;

const setAuth = (bool) => {
    isAuth = bool;
};

const setUser = (newUser) => {
    user = newUser;
};

const setLoading = (bool) =>{
    isLoading = bool;
}

const getStore = () => {
    return { user, isAuth };
};

const login = async (email, password) => {
    try {
        const response = await AuthService.login(email, password);
        console.log(response);
        localStorage.setItem('token', response.data.accessToken);
        setAuth(true);
        setUser(response.data.user);
    } catch (e) {  
        console.log(e.response?.data?.message);
    }
};

const registration = async (fullname, birthday, email, password) => {
    try {
        const response = await AuthService.registration(fullname, birthday, email, password);
        console.log(response.data);
        localStorage.setItem('token', response.data.accessToken);
        setAuth(true);
        setUser(response.data.user);
    } catch (e) {
        console.log(e.response?.data?.message);
    }
};

const logout = async () => {
    try {
        await AuthService.logout();
        localStorage.removeItem('token');
        setAuth(false); 
        setUser({});
    } catch (e) {
        console.log(e.response?.data?.message);
    }
};

const checkAuth = async () => {
    try{
        setLoading(true);
        const response = await axios.get(`${API_URL}/refresh`, {withCredentials: true});
        localStorage.setItem('token', response.data.accessToken);
        setAuth(true);
        setUser(response.data.user);
    }catch(e){
        console.log(e.response?.data?.message);
    }finally{
        setLoading(false);
    }
}


export const store = {
    setAuth,
    setUser,
    getStore,
    login,
    registration,
    logout,
    checkAuth,
    user,
    isAuth,
    isLoading
};