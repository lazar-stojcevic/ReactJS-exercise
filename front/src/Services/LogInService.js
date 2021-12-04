import axios from "axios";

const LOGIN_URL = 'http://localhost:8080/auth/login';

//AKO NE PRORADI BRISANJE U LOCALSTORAGE ONDA CE SE CUVATI U POLJIMA DOLE
class LogInService{
    user = '';
    userId = localStorage.getItem('userId');
    userRole = localStorage.getItem('userRole');
    accessToken = localStorage.getItem('accessToken');
    expiresIn = localStorage.getItem('expiresIn');

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
            this.userId = res.data.id;
            this.userRole = res.data.role;
            this.accessToken = res.data.accessToken;
            this.expiresIn = res.data.expiresIn;
            localStorage.setItem("userId", res.data.id)
            localStorage.setItem("userRole", res.data.role)
            localStorage.setItem("accessToken", res.data.accessToken)
            localStorage.setItem("expiresIn", res.data.expiresIn)
        });
    }

    getRole(){
        return localStorage.getItem("userRole")
    }

    logout(){
        this.userId = '';
        this.userRole = '';
        this.accessToken = '';
        this.expiresIn = '';
        localStorage.setItem("userId", '.')
        localStorage.setItem("userRole", '.')
        localStorage.setItem("accessToken", '.')
        localStorage.setItem("expiresIn", '.')
    }

    tokenIsPresent() {
        return this.accessToken !== undefined && this.accessToken != null;
    }
}

export default new LogInService()
