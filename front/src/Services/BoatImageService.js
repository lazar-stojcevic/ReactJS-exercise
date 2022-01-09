import axios from "axios";
import LogInService from "@/Services/LogInService";

const URL = 'http://localhost:8080/boatImage';

class BoatImageService{

    getImageById(boatId){
        const headers = {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };
        return axios.get(URL + '/' + boatId, {headers});
    }

    create(newImage){
        let headers = {
            'Accept': 'application/json;charset=utf-8',
            'Content-Type': 'application/json;charset=utf-8',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };
        return axios.post(URL, newImage, {headers}).then();
    }

    delete(id){
        let headers = {
            'Accept': 'application/json;charset=utf-8',
            'Content-Type': 'application/json;charset=utf-8',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };
        return axios.delete(URL + '/' + id, {headers}).then();
    }
}

export default new BoatImageService()