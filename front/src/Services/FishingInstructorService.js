import axios from "axios";
import LogInService from "@/Services/LogInService";

const FISHING_INSTRUCTOR_URL = process.env.VUE_APP_BACKEND_URL + 'fishingInstructor';

class FishingInstructorService{

    getAllFishingInstructors(){
        const headers = {
            'Accept': 'application/json',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };
        return axios.get(FISHING_INSTRUCTOR_URL + '/getAll', {headers});
    }

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

    changeFishingInstructor(instructor){
        const headers = {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };
        let data = {};
        data.id = instructor.id;
        data.firstname = instructor.firstname;
        data.lastName = instructor.lastName;
        data.phone = instructor.phone;
        data.street = instructor.address.street;
        data.city = instructor.address.city;
        data.country = instructor.address.country;
        return axios.put(FISHING_INSTRUCTOR_URL, JSON.stringify(data), {headers});
    }

    addHoliday(fromDate, toDate, userId){
        const headers = {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };
        let data = {};
        data.fromDate = fromDate + ' 00:00';
        data.toDate = toDate + ' 00:00';
        return axios.put(FISHING_INSTRUCTOR_URL + '/holiday/' + userId, JSON.stringify(data),
            {headers} );
    }

    requestForDeleting(userId){
        const headers = {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };
        return axios.put(FISHING_INSTRUCTOR_URL + '/deletingRequest/' + userId, {}, {headers});
    }

    isCustomerSubscribedToInstructor(userId, instructorId){
        const headers = {
            'Accept': 'application/json',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };
        return axios.get(FISHING_INSTRUCTOR_URL + '/isSubcribed/' + instructorId + '/' + userId, {headers});
    }
    subscribeInstructor(userId, instructorId){
        const headers = {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };
        let data = {};
        data.userId = userId;
        data.advertiserId = instructorId;
        return axios.put(FISHING_INSTRUCTOR_URL + '/subscribe/', JSON.stringify(data),
            {headers} );
    }

    unsubscribeInstructor(userId, instructorId){
        const headers = {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };
        let data = {};
        data.userId = userId;
        data.advertiserId = instructorId;
        return axios.put(FISHING_INSTRUCTOR_URL + '/unsubscribe/', JSON.stringify(data),
            {headers} );
    }
}
export default new FishingInstructorService()
