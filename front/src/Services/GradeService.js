import LogInService from "@/Services/LogInService";
import axios from "axios";

const URL = 'http://localhost:8080/grade';

class GradeService{

    getAllGradesForRevision(){
        const headers = {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };
        return axios.get(URL + '/revision', {headers});
    }

    async enableGrade(gradeId){
        const headers = {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };
        return axios.put(URL + '/enable/' + gradeId, {}, {headers});
    }

    async deleteGrade(gradeId){
        const headers = {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };
        return axios.delete(URL + '/' + gradeId, {headers});
    }

}
export default new GradeService()