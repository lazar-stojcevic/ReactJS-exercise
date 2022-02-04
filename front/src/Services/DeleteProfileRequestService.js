import LogInService from "@/Services/LogInService";
import axios from "axios";

const URL = process.env.VUE_APP_BACKEND_URL + 'deleteRequest';
const URL_USER = process.env.VUE_APP_BACKEND_URL + 'user';

class DeleteProfileRequestService{

    getAllNotReviewedRequests(){
        const headers = {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };
        return axios.get(URL + '/notReviewed' ,{headers});
    }

    //DATA PREDSTAVLJA MakeDeleteProfileRequestDto{String reason; long userId;}
    saveDeleteProfileRequest(data){
        const headers = {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };
        return axios.post(URL, JSON.stringify(data) ,{headers});
    }

    answerOnDeleteRequest(data){
        const headers = {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };
        return axios.put(URL_USER + '/answerOnRequestFroDeleting', JSON.stringify(data), {headers});
    }

}
export default new DeleteProfileRequestService()
