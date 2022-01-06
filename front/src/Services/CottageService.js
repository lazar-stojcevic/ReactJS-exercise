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
}

export default new CottageService()
