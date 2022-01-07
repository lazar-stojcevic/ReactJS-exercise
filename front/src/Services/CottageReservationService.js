import axios from "axios";
import LogInService from "@/Services/LogInService";

const URL = 'http://localhost:8080/cottageReservation';

class CottageReservationService {

    getAllAvailableCottagesTerms(dateFromPar, dateToPar, persons){
        let headers = {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };
        let body = {
            dateFrom: dateFromPar.replaceAll('T', ' '),
            dateTo: dateToPar.replaceAll('T', ' '),
            persons: persons,
            id: LogInService.userId
        }
        return axios.post(URL + '/availableCottages/', JSON.stringify(body), {headers});
    }

    reserveCottage(cottageId, selectedServices, from, to){
        const headers = {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };
        let data = {};
        data.cottageId = cottageId;
        data.userId = LogInService.userId;
        data.from = from;
        data.to = to;
        data.services = selectedServices;

        return axios.put(URL + '/reserveTerm/', JSON.stringify(data), {headers});
    }

    getAllFutureTermsByCustomerId(customerId){
        const headers = {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };
        return axios.get(URL + '/futureCustomerReservation/' + customerId, {headers});
    }

    getAllFastReservations(){
        const headers = {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };
        return axios.get(URL + '/fastReservations/', {headers});
    }

    cancelReservation(reservation){
        const headers = {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };
        let data = {};
        data.reservationId = reservation.id;
        data.userId = LogInService.userId;

        return axios.put(URL + '/cancelTerm/', JSON.stringify(data), {headers});
    }

    getAllPastTermsByCustomerId(customerId){
        const headers = {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };
        return axios.get(URL + '/pastCustomerReservation/' + customerId, {headers});
    }

    AddPeriodForFastReservation(period) {
        const headers = {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };

        return axios.put(URL + '/fastReservationPeriod/', JSON.stringify(period), {headers});
    }

    ReserveFastReservation(reservationId){
        const headers = {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };
        let data = {};
        data.reservationId = reservationId;
        data.userId = LogInService.userId;

        return axios.put(URL + '/reserveFastReservation/', JSON.stringify(data), {headers});
    }
}
export default new CottageReservationService()
