import axios from "axios";

const REGISTER_URL = 'http://localhost:8080/auth/signup';

class RegistrationService{
    register(newUser){
        let headers = {
            'Accept': 'application/json;charset=utf-8',
            'Content-Type': 'application/json;charset=utf-8'
        };
        return axios.post(REGISTER_URL, newUser, {headers}).then();
    }
}

export default new RegistrationService()
