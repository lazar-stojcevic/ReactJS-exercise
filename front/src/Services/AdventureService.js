import axios from "axios";
import LogInService from "@/Services/LogInService";

const URL = 'http://localhost:8080/adventure';

class AdventureService{
    adventureId = ''

    getAllAdventures(){
        const headers = {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };
        return axios.get(URL, {headers});
    }

    getAdventureById(adventureId){
        const headers = {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };
        return axios.get(URL + '/' + adventureId, {headers});
    }

    getAllAdventuresOfFishingInstructor(instructorId){
        const headers = {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };
        return axios.get(URL + '/instructor/' + instructorId, {headers});
    }

    saveAdventure(adventure, userId){
        const headers = {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };
        let data = JSON.stringify(adventure);
        return axios.post(URL + '/' + userId, data, {headers});
    }

    deleteAdventure(adventureId, headers){
        return axios.delete(URL + '/' + adventureId, {headers});
    }

    updateAdventure(adventure, headers){
        let data = JSON.stringify(adventure);
        return axios.put(URL, data, {headers});
    }
    setAdventureId(id){
        this.adventureId = id;
    }
    getAdventureId(){
        return this.adventureId;
    }
}

export default new AdventureService()