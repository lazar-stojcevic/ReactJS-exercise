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
}
export default new FishingInstructorService()