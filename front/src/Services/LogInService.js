import axios from "axios";

const LOGIN_URL = 'http://localhost:9000/rest/logIn';

class LogInService{
    login(user){
        return axios.post(LOGIN_URL, user);
    }
}

export default new LogInService()