package com.example.backend.Services;

import com.example.backend.Beans.Grade;
import com.example.backend.Repository.GradeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class GradeService {
    @Autowired
    private final GradeRepository gradeRepository;

    @Autowired
    private EmailService emailService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public GradeService(GradeRepository gradeRepository){
        this.gradeRepository = gradeRepository;
    }

    public Collection<Grade> getAllGrades(){
        return gradeRepository.findAll();
    }

    public Grade saveGrade(Grade grade){
        return gradeRepository.save(grade);
    }

    public void deleteGrade(long gradeId){
        gradeRepository.deleteById(gradeId);
    }

    public Grade findGradeById(long id){
        return gradeRepository.findById(id).orElse(null);
    }

    public Collection<Grade> getAllGradesForRevision(){
        return gradeRepository.findAllGradesForRevision();
    }

    public void enableGrade(long gradeId) {
        Grade grade = findGradeById(gradeId);
        grade.setEnabled(true);
        saveGrade(grade);
        findUserToSandMail(grade);
    }

    //TODO: DODATI ZA OSTALE ULOGE
    private void findUserToSandMail(Grade grade){
        if(grade.getInstructor() != null)
            sendMailForEnablingGrade(grade.getInstructor().getEmail(), grade.getInstructor().getFirstname());
    }

    private void sendMailForEnablingGrade(String email, String firstname){
        try{
            logger.info("< Sending mail");
            emailService.sendNotificationForEnablingRevision(email, firstname);
        }catch (Exception e) {
            logger.info(e.toString());
        }
    }
}
