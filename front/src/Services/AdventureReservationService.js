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

}
export default new AdventureReservationService()