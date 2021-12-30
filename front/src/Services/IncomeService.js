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

}export default new IncomeService()