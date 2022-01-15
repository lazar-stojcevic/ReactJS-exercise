import LogInService from "@/Services/LogInService";
import axios from "axios";

const URL = 'http://localhost:8080/income';

class IncomeService{

    getInstructorIncome(data){
        const headers = {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };
        return axios.post(URL + '/instructorIncome', JSON.stringify(data), {headers});
    }
    getCottageOwnerIncome(data){
        let headers = {
            'Accept': 'application/json;charset=utf-8',
            'Content-Type': 'application/json;charset=utf-8',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };

        return axios.post(URL + '/calculateCottageOwnerIncome', JSON.stringify(data), {headers});
    }

    getBoatOwnerIncome(data){
        let headers = {
            'Accept': 'application/json;charset=utf-8',
            'Content-Type': 'application/json;charset=utf-8',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };

        return axios.post(URL + '/calculateBoatOwnerIncome', JSON.stringify(data), {headers});
    }


}export default new IncomeService()