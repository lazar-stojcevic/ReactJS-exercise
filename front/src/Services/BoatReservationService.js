import axios from "axios";
import LogInService from "@/Services/LogInService";

const URL = 'http://localhost:8080/boatReservation';

class BoatReservationService {
    getAllAvailableBoatTerms(dateFromPar, dateToPar, persons){
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
        return axios.post(URL + '/availableBoats/', JSON.stringify(body), {headers});
    }

    reserveBoat(boatId, selectedServices, from, to){
        const headers = {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };
        let data = {};
        data.cottageId = boatId;
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

    getAllNextActionsOfBoat(id) {
        const headers = {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };
        return axios.get(URL + '/nextActions/' + id, {headers});
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

    getAllPastTermsByCustomerId(customerId){
        const headers = {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };
        return axios.get(URL + '/pastCustomerReservation/' + customerId, {headers});
    }
}
export default new BoatReservationService()
