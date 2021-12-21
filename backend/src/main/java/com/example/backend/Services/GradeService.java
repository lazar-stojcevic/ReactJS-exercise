package com.example.backend.Services;

import com.example.backend.Beans.FishingInstructor;
import com.example.backend.Beans.Grade;
import com.example.backend.Dtos.GradeToSaveDto;
import com.example.backend.Dtos.GradeToShowDto;
import com.example.backend.Repository.GradeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Service
public class GradeService {
    @Autowired
    private final GradeRepository gradeRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private FishingInstructorService fishingInstructorService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

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

    public GradeToShowDto getAllGradesOfInstructor(long id){
        return makeGradeToShow((List<Grade>) gradeRepository.findAllGradesOfInstructor(id));
    }

    public void enableGrade(long gradeId) {
        Grade grade = findGradeById(gradeId);
        grade.setEnabled(true);
        saveGrade(grade);
        findUserToSandMail(grade);
    }

    //PROSLEDI SE LISTA SVIH OCENA ODREDJENOG ENTITETA I VRATI SE DTO OBJEKAT ZA PRIKAZ
    private GradeToShowDto makeGradeToShow(List<Grade> grades){
        GradeToShowDto dto = new GradeToShowDto();
        dto.setAvgRating(calculateRating(grades));
        dto.setComments(makeListOfComments(grades));
        return dto;
    }

    private List<String> makeListOfComments(List<Grade> grades) {
        List<String> comments = new ArrayList<>();
        for (Grade g : grades)
            comments.add(g.getRevision());
        return comments;
    }

    private double calculateRating(List<Grade> grades) {
        double sum = 0;
        for(Grade g : grades)
            sum+=g.getRating();
        return sum/grades.size();
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

    public Grade makeGradeForSaving(GradeToSaveDto dto) {
        if(Objects.equals(dto.getEntityName(), "I"))
            return makeGradeForInstructor(dto);
        else if(Objects.equals(dto.getEntityName(), "CO"))
            return mageGradeForCottageOwner(dto);
        else if(Objects.equals(dto.getEntityName(), "BO"))
            return mageGradeForBoatOwner(dto);
        else if(Objects.equals(dto.getEntityName(), "C"))
            return mageGradeForCottage(dto);
        else return mageGradeForBoat(dto);
    }

    private Grade mageGradeForBoat(GradeToSaveDto dto) {
        return new Grade();
    }

    private Grade mageGradeForCottage(GradeToSaveDto dto) {
        return new Grade();
    }

    private Grade mageGradeForBoatOwner(GradeToSaveDto dto) {
        return new Grade();
    }

    private Grade mageGradeForCottageOwner(GradeToSaveDto dto) {
        return new Grade();
    }

    private Grade makeGradeForInstructor(GradeToSaveDto dto){
        Grade grade = new Grade();
        makeBaseGrade(grade, dto);
        grade.setInstructor(fishingInstructorService.findFishingInstructorById(dto.getEntityId()));
        return saveGrade(grade);
    }

    private void makeBaseGrade(Grade grade, GradeToSaveDto dto){
        grade.setRevision(dto.getRevision());
        grade.setRating(dto.getRating());
    }
}
