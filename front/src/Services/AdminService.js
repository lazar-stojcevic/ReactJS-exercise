import axios from "axios";
import LogInService from "@/Services/LogInService";

const URL = 'http://localhost:8080/admin';
const AUTH_URL = 'http://localhost:8080/auth';

class AdminService{

    getAdminById(userId){
        const headers = {
            'Accept': 'application/json',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };
        return axios.get(URL + '/' + userId, {headers});
    }

    getAllNotEnabledUsers(){
        const headers = {
            'Accept': 'application/json',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };
        return axios.get(AUTH_URL + '/notEnabled', {headers});
    }
}
export default new AdminService()