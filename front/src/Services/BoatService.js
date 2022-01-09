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

    getAvailablePeriodsOfBoat(boatId){
        const headers = {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };
        return axios.get(URL + '/availablePeriod/' + boatId, {headers});
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
    create(newBoat){
        let headers = {
            'Accept': 'application/json;charset=utf-8',
            'Content-Type': 'application/json;charset=utf-8',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };
        return axios.post(URL, newBoat, {headers}).then();
    }

    update(newBoat){
        let headers = {
            'Accept': 'application/json;charset=utf-8',
            'Content-Type': 'application/json;charset=utf-8',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };
        return axios.put(URL, newBoat, {headers}).then();
    }

    delete(id){
        let headers = {
            'Accept': 'application/json;charset=utf-8',
            'Content-Type': 'application/json;charset=utf-8',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };
        return axios.delete(URL + '/' + id, {headers}).then();

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
export default new BoatService()
