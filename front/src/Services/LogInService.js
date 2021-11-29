import axios from "axios";

const LOGIN_URL = 'http://localhost:8080/auth/login';

class LogInService{
    access_token = '';

    login(user){
        const headers = {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        };
        const body = {
            'email': user.email,
            'password': user.password
        };
        return axios.post(LOGIN_URL, body, {headers}).then((res) => {
            this.access_token = res.data.accessToken;
        });

    }

    tokenIsPresent() {
        return this.access_token !== undefined && this.access_token != null;
    }

    getToken() {
        return this.access_token;
    }
}

export default new LogInService()
