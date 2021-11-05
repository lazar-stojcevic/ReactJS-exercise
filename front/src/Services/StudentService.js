import axios from "axios";

const STUDENT_URL = 'http://localhost:9000/rest/students';

class StudentService{
    getStudents(){
        return axios.get(STUDENT_URL);
    }
}

export default new StudentService()