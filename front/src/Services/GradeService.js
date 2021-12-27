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

    async getAllGradeOfInstructor(instructorId){
        const headers = {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };
        return axios.get(URL + '/instructor/' + instructorId, {headers});
    }

    // data je GradeToSaveDto {
    //     private int rating;
    //     private String revision;
    //     private long entityId;
    //     //I-instructor, BO-boatOwner, CO-cottageOwner, C-cottage, B-boat
    //     private String entityType;
    // }
    saveGrade(data){
        const headers = {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };
        return axios.post(URL, JSON.stringify(data), {headers});
    }

}
export default new GradeService()