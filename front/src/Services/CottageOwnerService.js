import axios from "axios";
import LogInService from "@/Services/LogInService";

const COTTAGE_OWNER_URL = 'http://localhost:8080/cottageOwner';

class CottageOwnerService{

    getAllCottageOwners(){
        const headers = {
            'Accept': 'application/json',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };
        return axios.get(COTTAGE_OWNER_URL, {headers});
    }

    getCottageOwnerById(userId){
        const headers = {
            'Accept': 'application/json',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };
        return axios.get(COTTAGE_OWNER_URL + '/' + userId, {headers});
    }

    changePassword(newPassword, userId){
        const headers = {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };
        let data = {};
        data.password = newPassword;
        data.userId = userId;
        return axios.put(COTTAGE_OWNER_URL + '/password', JSON.stringify(data), {headers});
    }

    changeOwner(user){
        const headers = {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };
        let data = {};
        data.id = user.id;
        data.firstname = user.firstname;
        data.lastName = user.lastName;
        data.phone = user.phone;
        data.street = user.address.street;
        data.city = user.address.city;
        data.country = user.address.country;
        return axios.put(COTTAGE_OWNER_URL, data, {headers});
    }

    requestForDeleting(userId){
        const headers = {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };
        return axios.put(COTTAGE_OWNER_URL + '/deletingRequest/' + userId, {}, {headers});
    }

    getMyCottages(userId){
        const headers = {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };
        return axios.get(COTTAGE_OWNER_URL + '/cottages/'+ userId, {headers});
    }
}
export default new CottageOwnerService()