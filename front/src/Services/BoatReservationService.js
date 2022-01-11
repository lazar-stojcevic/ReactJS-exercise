import axios from "axios";
import LogInService from "@/Services/LogInService";

const URL = 'http://localhost:8080/boatReservation';

class BoatReservationService {
    getAllAvailableBoatTerms(dateFromPar, dateToPar, persons, city, country, captain){
        let headers = {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };
        let body = {
            dateFrom: dateFromPar.replaceAll('T', ' '),
            dateTo: dateToPar.replaceAll('T', ' '),
            persons: persons,
            city: city,
            country: country,
            id: LogInService.userId,
            captain: captain
        }
        return axios.post(URL + '/availableBoats/', JSON.stringify(body), {headers});
    }

    getAllReservationsOfOwnerForCalendar(ownerId){
        const headers = {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };
        return axios.get(URL + '/calendar/' + ownerId, {headers});
    }

    getAllReservationsOfBoatForCalendar(boatId){
        const headers = {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };
        return axios.get(URL + '/calendarBoat/' + boatId, {headers});
    }

    reserveBoat(boatId, selectedServices, from, to, captain){
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
        data.captain = captain;

        return axios.put(URL + '/reserveTerm/', JSON.stringify(data), {headers});
    }

    reserveBoatForCustomer(boatId, selectedServices, from, to, customerId, captain){
        const headers = {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };
        let data = {};
        data.cottageId = boatId;
        data.userId = customerId;
        data.from = from;
        data.to = to;
        data.services = selectedServices;
        data.captain = captain;

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

    getAllReservationsOfOwner(id){
        const headers = {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };
        return axios.get(URL + '/ownerReservation/'+id, {headers});
    }

    AddPeriodForFastReservation(period) {
        const headers = {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };
        return axios.put(URL + '/fastReservationPeriod/', JSON.stringify(period), {headers});
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

    getAllPastTermsByCustomerIdWithPutComplaint(customerId){
        const headers = {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };
        return axios.get(URL + '/pastCustomerReservationWithOutComplaint/' + customerId, {headers});
    }
}
export default new BoatReservationService()
