import axios from "axios";
import LogInService from "@/Services/LogInService";

const URL = 'http://localhost:8080/complaint';

class ComplaintService{
    writeComplaint(id, text){
        const headers = {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };
        let data = {};
        data.id = id.toString();
        data.text = text.toString();

        return axios.post(URL + "/adventure" , JSON.stringify(data), {headers});
    }

    writeComplaintForCottage(id, text){
        const headers = {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };
        let data = {};
        data.id = id.toString();
        data.text = text.toString();

        return axios.post(URL + "/cottage" , JSON.stringify(data), {headers});
    }

     async reviewComplaint(data){
        const headers = {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };
        return axios.put(URL + "/reviewComplaint", JSON.stringify(data), {headers});
    }

    loadAllNotReviewedComplaints(){
        const headers = {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };
        return axios.get(URL + "/notReviewedComplaints", {headers});
    }

}
export default new ComplaintService()
