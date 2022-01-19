import LogInService from "@/Services/LogInService";
import axios from "axios";

const URL = process.env.VUE_APP_BACKEND_URL + 'grade';

class GradeService{

    getAllGradesForRevision(){
        const headers = {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };
        return axios.get(URL + '/revision', {headers});
    }

    enableGrade(gradeId){
        const headers = {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };
        return axios.get(URL + '/enable/' + gradeId, {headers});
    }

    disableGrade(gradeId){
        const headers = {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };
        return axios.get(URL + '/disable/' + gradeId, {headers});
    }

    getAllGradeOfInstructor(instructorId){
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
