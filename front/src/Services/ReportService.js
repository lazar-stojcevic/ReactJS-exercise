import LogInService from "@/Services/LogInService";
import axios from "axios";

const URL = 'http://localhost:8080/report';

class ReportService{

    getAllReportsForProcessing(){
        const headers = {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };
        return axios.get(URL + '/notProcessed', {headers});
    }

    penalizeCustomer(reportId){
        const headers = {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };
        return axios.put(URL + '/penalize/' + reportId, {}, {headers});
    }

    haveMercyOnCustomer(reportId){
        const headers = {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };
        return axios.put(URL + '/mercy/' + reportId, {}, {headers});
    }

    makeReport(data){
        const headers = {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };
        return axios.post(URL, JSON.stringify(data), {headers})
    }
}
export default new ReportService()