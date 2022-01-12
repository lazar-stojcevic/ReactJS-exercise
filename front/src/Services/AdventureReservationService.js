import axios from "axios";
import LogInService from "@/Services/LogInService";

const URL = 'http://localhost:8080/adventureReservation';

class AdventureReservationService {

    getAllReservationsOfInstructorForCalendar(instructorId){
        const headers = {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };
        return axios.get(URL + '/calendar/' + instructorId, {headers});
    }

    getAllFreeFastReservations(adventureId){
        const headers = {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };
        return axios.get(URL + '/fast/' + adventureId, {headers});
    }

    getCurrentReservationOfInstructor(instructorId){
        const headers = {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };
        return axios.get(URL + '/current/' + instructorId, {headers})
    }

    getAllAvailableAdventureTerms(dateFromPar, dateToPar, persons, city, country){

            let headers = {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + LogInService.accessToken
            };
            let body = {
                dateFrom: dateFromPar.replaceAll('T', ' '),
                dateTo: dateToPar.replaceAll('T', ' '),
                persons: persons,
                id: LogInService.userId,
                city: city,
                country: country
            }
        return axios.post(URL + '/availableAdventures/', JSON.stringify(body), {headers});
    }

    getAdventureTermById(termId){
        let headers = {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };
        return axios.get(URL + '/term/'+ termId, {headers});
    }

    getAllReservedTerms(adventureId){
        const headers = {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };
        return axios.get(URL + '/nextReservations/' + adventureId, {headers});
    }

    getAllPastReservationsOfInstructor(instructorId){
        const headers = {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };
        return axios.get(URL + '/pastReservation/' + instructorId, {headers});
    }

    saveFastReservation(reservation, instructorId, adventureId){
        const headers = {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };
        let data = {};
        data.instructorId = instructorId;
        data.adventureId = adventureId;
        data.lastDateToReserve = reservation.lastDateToReserve.replaceAll('T', ' ');
        data.adventureStart = reservation.reservationStart.replaceAll('T', ' ');
        data.length = reservation.length;
        data.discount = reservation.discount;
        data.addServices = reservation.addServices;
        return axios.post(URL + '/makeFastReservation', JSON.stringify(data), {headers});
    }

    reserveAdventure(reservationId, selectedServices){
        const headers = {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };
        let data = {};
        data.reservationId = reservationId;
        data.userId = LogInService.userId;
        data.services = selectedServices;

        return axios.put(URL + '/reserveTerm/', JSON.stringify(data), {headers});
    }

    reserveAdventureOnAction(reservationId){
        const headers = {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };
        let data = {};
        data.reservationId = reservationId;
        data.userId = LogInService.userId;
        data.services = [];

        return axios.put(URL + '/reserveTermOnAction/', JSON.stringify(data), {headers});
    }

    getAllFutureTermsByCustomerId(customerId){
        const headers = {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };
        return axios.get(URL + '/futureCustomerReservation/' + customerId, {headers});
    }

    getAllPastTermsByCustomerIdWithPutComplaint(customerId){
        const headers = {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };
        return axios.get(URL + '/pastCustomerReservationWithOutComplaint/' + customerId, {headers});
    }

    getAllPastTermsByCustomerId(customerId){
        const headers = {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };
        return axios.get(URL + '/pastCustomerReservation/' + customerId, {headers});
    }

    cancelReservation(adventure){
        const headers = {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };
        let data = {};
        data.reservationId = adventure.id;
        data.userId = LogInService.userId;

        return axios.put(URL + '/cancelTerm/', JSON.stringify(data), {headers});
    }

    async makeCustomReserve(reserveDto){
        const headers = {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };
        let data = JSON.stringify(reserveDto);
        return axios.post(URL + '/customReserve', data, {headers});
    }

    deleteReservation(id){
        const headers = {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };

        return axios.delete(URL + '/' + id, {headers});
    }

    getAllNextActionsOfInstructor(id){
        const headers = {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };
        return axios.get(URL + '/nextActions/' + id, {headers});
    }

    getAllReservationForGraph(data){
        const headers = {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };
        return axios.post(URL + '/graph',JSON.stringify(data), {headers});
    }
}
export default new AdventureReservationService()
