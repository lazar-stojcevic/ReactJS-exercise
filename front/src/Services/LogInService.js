import axios from "axios";
import Navbar from "@/components/Navbar";

const LOGIN_URL = 'http://localhost:8080/auth/login';

//AKO NE PRORADI BRISANJE U LOCALSTORAGE ONDA CE SE CUVATI U POLJIMA DOLE
class LogInService{
    user = '';
    userId = '';
    userRole = '';
    accessToken = '';
    expiresIn = '';

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
            Navbar.methods.setUserRole(this.userRole);
        });
    }

    logout(){
        this.userId = '';
        this.userRole = '';
        this.accessToken = '';
        this.expiresIn = '';
        //za nav bar isto
        Navbar.methods.setUserRole('');
        this.$router.push("/login");
    }

    //TODO: IZMENITI LOGIKU
    tokenIsPresent() {
        return this.accessToken !== undefined && this.accessToken != null;
    }

    //TODO: IZMENITI LOGIKU
    getToken() {
        return this.accessToken;
    }
}

export default new LogInService()
