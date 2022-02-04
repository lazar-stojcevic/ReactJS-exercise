import axios from "axios";

const REGISTER_URL = process.env.VUE_APP_BACKEND_URL + 'auth/signup';

class RegistrationService{
    register(newUser){
        let headers = {
            'Accept': 'application/json;charset=utf-8',
            'Content-Type': 'application/json;charset=utf-8'
        };
        console.log(JSON.stringify(newUser));
        return axios.post(REGISTER_URL, newUser, {headers}).then();
    }
}

export default new RegistrationService()
