import axios from "axios";
import LogInService from "@/Services/LogInService";

const URL = 'http://localhost:8080/cottage';

class CottageService{
    getCottageById(cottageId){
        const headers = {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };
        return axios.get(URL + '/' + cottageId, {headers});
    }
}

export default new CottageService()
