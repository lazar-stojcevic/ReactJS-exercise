import axios from "axios";
import LogInService from "@/Services/LogInService";

const URL = 'http://localhost:8080/admin';
const USER_URL = 'http://localhost:8080/user';

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
        return axios.get(USER_URL + '/notEnabled', {headers});
    }

    saveAdmin(admin){
        const headers = {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };
        return axios.post(URL ,JSON.stringify(admin) ,{headers});
    }

    changeAdmin(admin){
        const headers = {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };
        let data = {};
        data.id = admin.id;
        data.firstname = admin.firstname;
        data.lastName = admin.lastName;
        data.phone = admin.phone;
        data.street = admin.address.street;
        data.city = admin.address.city;
        data.country = admin.address.country;
        return axios.put(URL ,JSON.stringify(data) ,{headers});
    }

    changePassword(newPassword){
        const headers = {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };
        let data = {userId: LogInService.userId, password: newPassword};
        return axios.put(URL+ '/changePassword' ,JSON.stringify(data) ,{headers});
    }

    async acceptUserRequest(userId){
        const headers = {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };
        return axios.put(USER_URL+ '/enable/' + userId, null ,{headers});
    }

    async notAcceptUserRequest(userId){
        const headers = {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };
        return axios.delete(USER_URL+ '/disable/' + userId ,{headers});
    }
}
export default new AdminService()