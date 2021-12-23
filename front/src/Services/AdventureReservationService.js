import axios from "axios";
import LogInService from "@/Services/LogInService";

const URL = 'http://localhost:8080/adventureReservation';

class AdventureReservationService {

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

    getAllAvailableAdventureTerms(dateFromPar, dateToPar, persons){

            let headers = {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + LogInService.accessToken
            };
            let body = {
                dateFrom: dateFromPar.replaceAll('T', ' '),
                dateTo: dateToPar.replaceAll('T', ' '),
                persons: persons
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

        return axios.post(URL + '/makeFastReservation', JSON.stringify(data), {headers});
    }

    reserveAdventure(reservationId){
        const headers = {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + LogInService.accessToken
        };
        let data = {};
        data.reservationId = reservationId;
        data.userId = LogInService.userId;

        return axios.put(URL + '/reserveTerm/', JSON.stringify(data), {headers});
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
}
export default new AdventureReservationService()
