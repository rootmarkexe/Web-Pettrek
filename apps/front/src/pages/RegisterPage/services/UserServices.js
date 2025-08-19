import $api from "../http";


export const UserService = {
    async fetchUsers(){
        return $api.get('/users')
    }
}  