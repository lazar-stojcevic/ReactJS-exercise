import axios from "axios";
import LogInService from "@/Services/LogInService";

const URL = 'http://localhost:8080/tax';

class TaxService{

    getCurrentTax(){
        const headers = {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };
        return axios.get(URL + '/current', {headers});
    }

    changeTax(newTax){
        let headers = {
            'Accept': 'application/json;charset=utf-8',
            'Content-Type': 'application/json;charset=utf-8',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };
        return axios.put(URL + '/newTax/' + newTax,{}, {headers});
    }

    getIncome(data){
        let headers = {
            'Accept': 'application/json;charset=utf-8',
            'Content-Type': 'application/json;charset=utf-8',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };

        return axios.post(URL + '/calculateIncome', JSON.stringify(data), {headers});
    }

}

export default new TaxService()