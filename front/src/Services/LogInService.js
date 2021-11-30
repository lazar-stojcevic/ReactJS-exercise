import axios from "axios";

const LOGIN_URL = 'http://localhost:8080/auth/login';

class LogInService{
    access_token = null;

    login(user){
        const headers = {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        };
        const body = {
            'username': user.username,
            'password': user.password
        };
        return axios.post(LOGIN_URL, body, {headers}).then((res) => {
            this.access_token = res.accessToken;
        });
    }

    tokenIsPresent() {
        return this.access_token !== undefined && this.access_token != null;
    }

    getToken() {
        return this.access_token;
    }

    //TODO: TREBA DODATI NA BEKU DA VRACA I USERA
    getUser(){
        return this.access_token.user;
    }
}

export default new LogInService()
