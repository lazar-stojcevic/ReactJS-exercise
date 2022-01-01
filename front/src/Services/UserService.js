import LogInService from "@/Services/LogInService";
import axios from "axios";

const URL = 'http://localhost:8080/user';
class UserService{

    getAllUsers() {
        const headers = {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };
        return axios.get(URL + '/allUsersExceptAdmins', {headers});
    }

    disableUser(userId) {
        const headers = {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };
        return axios.put(URL + '/disableUser/' + userId, {}, {headers});
    }

    enableUser(userId) {
        const headers = {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };
        return axios.put(URL + '/enable/' + userId, {}, {headers});
    }

}export default new UserService()