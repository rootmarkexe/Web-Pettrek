import $api from "../http";


export const AuthService = {
    async login(email, password){
        return $api.post('/auth/signin', {email, password})
    },

    async registration(email, password){
        return $api.post('/auth/signup', {email, password})
    },

    async logout(){
        return $api.post('/auth/logout')
    }
}   