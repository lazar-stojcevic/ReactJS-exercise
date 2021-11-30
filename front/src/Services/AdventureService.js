import axios from "axios";

const URL = 'http://localhost:8080/adventure';

class AdventureService{

    //TODO: U HEADERU TREBA PROSLEDITI JWT
    getAllAdventures(headers){
        return axios.get(URL, {headers});
    }

    getAdventureById(adventureId, headers){
        return axios.get(URL + '/' + adventureId, {headers});
    }

    getAllAdventuresOfFishingInstructor(instructorId, headers){
        return axios.get(URL + '/instructor/' + instructorId, {headers});
    }

    saveAdventure(adventure, headers){
        let data = JSON.stringify(adventure);
        return axios.post(URL, data, {headers});
    }

    deleteAdventure(adventureId, headers){
        return axios.delete(URL + '/' + adventureId, {headers});
    }

    updateAdventure(adventure, headers){
        let data = JSON.stringify(adventure);
        return axios.put(URL, data, {headers});
    }
}

export default new AdventureService()