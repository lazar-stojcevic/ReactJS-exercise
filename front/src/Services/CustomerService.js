import axios from "axios";
import LogInService from "@/Services/LogInService";

const CUSTOMER_URL = 'http://localhost:8080/customer';

class CustomerService{

    getCustomerId(userId){
        const headers = {
            'Accept': 'application/json',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };
        return axios.get(CUSTOMER_URL + '/' + userId, {headers});
    }

    changePassword(newPassword, userId){
        const headers = {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };
        let data = {};
        data.password = newPassword;
        data.userId = userId;
        return axios.put(CUSTOMER_URL + '/password', JSON.stringify(data), {headers});
    }

    changeCustomer(customer){
        const headers = {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };
        let data = {};
        data.id = customer.id;
        data.firstname = customer.firstname;
        data.lastName = customer.lastName;
        data.phone = customer.phone;
        data.street = customer.address.street;
        data.city = customer.address.city;
        data.country = customer.address.country;
        return axios.put(CUSTOMER_URL, data, {headers});
    }

    deleteCustomerRequest(requestText){
        const headers = {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };
        let data = {};
        data.id = LogInService.userId;
        data.requestText = requestText;
        return axios.post(CUSTOMER_URL + '/delete', data, {headers});
    }

    getCustomerPenaltyPoints(userId){
        const headers = {
            'Accept': 'application/json',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };
        return axios.get(CUSTOMER_URL + '/penalty/' + userId, {headers});
    }

    getAllCustomers(){
        const headers = {
            'Accept': 'application/json',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };
        return axios.get(CUSTOMER_URL, {headers});
    }

    getAllCustomerInstructorsSubscriptions(userId){
        const headers = {
            'Accept': 'application/json',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };
        return axios.get(CUSTOMER_URL + '/instructorSubscriptions/' + userId, {headers});
    }

    getAllCustomerCottagesSubscriptions(userId){
        const headers = {
            'Accept': 'application/json',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };
        return axios.get(CUSTOMER_URL + '/cottageSubscriptions/' + userId, {headers});
    }

    getAllCustomerBoatsSubscriptions(userId){
        const headers = {
            'Accept': 'application/json',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };
        return axios.get(CUSTOMER_URL + '/boatSubscriptions/' + userId, {headers});
    }
}
export default new CustomerService()
