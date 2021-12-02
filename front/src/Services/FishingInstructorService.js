import axios from "axios";
import LogInService from "@/Services/LogInService";

const FISHING_INSTRUCTOR_URL = 'http://localhost:8080/fishingInstructor';

class FishingInstructorService{

    getFishingInstructorById(userId){
        const headers = {
            'Accept': 'application/json',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };
        return axios.get(FISHING_INSTRUCTOR_URL + '/' + userId, {headers});
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
        return axios.put(FISHING_INSTRUCTOR_URL + '/password', JSON.stringify(data), {headers});
    }
}
export default new FishingInstructorService()