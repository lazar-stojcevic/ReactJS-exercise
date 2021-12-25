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
}
export default new ComplaintService()
