import axios from "axios";
import LogInService from "@/Services/LogInService";

const URL = 'http://localhost:8080/cottageReservation';

class CottageReservationService {

    getAllAvailableAdventureTerms(dateFromPar, dateToPar, persons){
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
}
export default new CottageReservationService()
