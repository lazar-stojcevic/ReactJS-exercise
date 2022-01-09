import axios from "axios";
import LogInService from "@/Services/LogInService";

const URL = 'http://localhost:8080/boat';

class BoatService{
    getAllBoats(){
        const headers = {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };
        return axios.get(URL + "/getAll", {headers});
    }

    getOneBoat(id){
        const headers = {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };
        return axios.get(URL + "/getOne/" + id, {headers});
    }

    getAdditionalServicesOfBoat(cottageId) {
        const headers = {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };
        return axios.get(URL + '/additionalServices/' + cottageId, {headers});
    }

    subscribeBoat(userId, instructorId){
        const headers = {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };
        let data = {};
        data.userId = userId;
        data.advertiserId = instructorId;
        return axios.put(URL + '/subscribe/', JSON.stringify(data),
            {headers} );
    }

    unsubscribeBoat(userId, boatId){
        const headers = {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };
        let data = {};
        data.userId = userId;
        data.advertiserId = boatId;
        return axios.put(URL + '/unsubscribe/', JSON.stringify(data),
            {headers} );
    }
}
export default new BoatService()
