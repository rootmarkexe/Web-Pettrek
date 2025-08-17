import $api from "../http";


export const AuthService = {
    async login(email, password){
        return $api.post('/login', {email, password})
    },

    async registration(fullname, birthday, email, password){
        return $api.post('/registration', {fullname, birthday, email, password})
    },

    async logout(){
        return $api.post('/logout')
    }
}   