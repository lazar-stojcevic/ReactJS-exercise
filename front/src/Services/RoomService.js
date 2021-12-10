import axios from "axios";
import LogInService from "@/Services/LogInService";

const URL = 'http://localhost:8080/room';

class RoomService{

    getRoomById(cottageId){
        const headers = {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };
        return axios.get(URL + '/cottage/' + cottageId, {headers});
    }

    create(newRoom){
        let headers = {
            'Accept': 'application/json;charset=utf-8',
            'Content-Type': 'application/json;charset=utf-8',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };
        alert(LogInService.userId);
        return axios.post(URL, newRoom, {headers}).then();
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

export default new RoomService()