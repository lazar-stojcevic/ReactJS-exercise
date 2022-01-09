import axios from "axios";
import LogInService from "@/Services/LogInService";

const URL = 'http://localhost:8080/cottage';

class CottageService{
    getCottageById(cottageId){
        const headers = {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };
        return axios.get(URL + '/' + cottageId, {headers});
    }

    getAllCottages(){
        const headers = {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };
        return axios.get(URL + "/getAll", {headers});
    }
    create(newCottage){
        let headers = {
            'Accept': 'application/json;charset=utf-8',
            'Content-Type': 'application/json;charset=utf-8',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };
        return axios.post(URL, newCottage, {headers}).then();
    }

    update(newCottage){
        let headers = {
            'Accept': 'application/json;charset=utf-8',
            'Content-Type': 'application/json;charset=utf-8',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };
        return axios.put(URL, newCottage, {headers}).then();
    }

    delete(id){
        let headers = {
            'Accept': 'application/json;charset=utf-8',
            'Content-Type': 'application/json;charset=utf-8',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };
        return axios.delete(URL + '/' + id, {headers}).then();

    }

    getAdditionalServicesOfCottage(cottageId){
        const headers = {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };
        return axios.get(URL + '/additionalServices/' + cottageId, {headers});
    }

    getAvailablePeriodsOfCottage(cottageId){
        const headers = {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };
        return axios.get(URL + '/availablePeriod/' + cottageId, {headers});
    }

    subscribeCottage(userId, instructorId){
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

    unsubscribeCottage(userId, instructorId){
        const headers = {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };
        let data = {};
        data.userId = userId;
        data.advertiserId = instructorId;
        return axios.put(URL + '/unsubscribe/', JSON.stringify(data),
            {headers} );
    }

    IsUserSubscribed(userId, cottageId){
        const headers = {
            'Accept': 'application/json',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };
        return axios.get(URL + '/isSubcribed/' + cottageId + '/' + userId, {headers});
    }

    AddAdditionalService(newService){
        let headers = {
            'Accept': 'application/json;charset=utf-8',
            'Content-Type': 'application/json;charset=utf-8',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };
        return axios.put(URL+ '/additionalService/', newService, {headers}).then();
    }

    AddAvailablePeriod(newPeriod){
        let headers = {
            'Accept': 'application/json;charset=utf-8',
            'Content-Type': 'application/json;charset=utf-8',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };
        return axios.put(URL+ '/availablePeriod/', newPeriod, {headers}).then();
    }

    DeleteAdditionalService(id){
        let headers = {
            'Accept': 'application/json;charset=utf-8',
            'Content-Type': 'application/json;charset=utf-8',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };
        return axios.delete(URL + '/additionalService/' + id, {headers}).then();
    }
}

export default new CottageService()
