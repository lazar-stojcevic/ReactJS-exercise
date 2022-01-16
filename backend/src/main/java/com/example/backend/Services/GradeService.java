package com.example.backend.Services;

import com.example.backend.Beans.*;
import com.example.backend.Dtos.GradeToSaveDto;
import com.example.backend.Dtos.GradeToShowDto;
import com.example.backend.Repository.BoatRepository;
import com.example.backend.Repository.CottageRepository;
import com.example.backend.Repository.FishingInstructorRepository;
import com.example.backend.Repository.GradeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
    private AdventureReservationService reservationService;

    @Autowired
    private CottageReservationService cottageReservationService;

    @Autowired
    private BoatReservationService boatReservationService;

    @Autowired
    private FishingInstructorRepository fishingInstructorRepository;

    @Autowired
    private CottageRepository cottageRepository;

    @Autowired
    private BoatRepository boatRepository;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public GradeService(GradeRepository gradeRepository){
        this.gradeRepository = gradeRepository;
    }

    public Collection<Grade> getAllGrades(){
        return gradeRepository.findAll();
    }

    @Transactional
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

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void enableOrDisableGrade(long gradeId, boolean forEnabling) {
        Grade grade = gradeRepository.findOneById(gradeId);

        if(grade.isReviewed()) return;

        if(forEnabling) grade.setEnabled(true);

        grade.setReviewed(true);
        saveGrade(grade);

        if(forEnabling) {
            User user = null;
            if (grade.getInstructor() != null) {
                grade.getInstructor().setRating(CalculateNewAverageGradeForInstructor(gradeRepository.findAllGradesOfInstructor(grade.getInstructor().getId())));
                user = fishingInstructorRepository.save(grade.getInstructor());
            } else if (grade.getCottage() != null) {
                grade.getCottage().setRating(CalculateNewAverageGradeForCottage(gradeRepository.findAllGradesOfCottage(grade.getCottage().getId())));
                user = cottageRepository.save(grade.getCottage()).getCottageOwner();
            } else if (grade.getBoat() != null) {
                grade.getBoat().setRating(CalculateNewAverageGradeForBoat(gradeRepository.findAllGradesOfBoat(grade.getBoat().getId())));
                user = boatRepository.save(grade.getBoat()).getBoatOwner();
            }
            sendMailForEnablingGrade(user);
        }
    }

    private double CalculateNewAverageGradeForInstructor(Collection<Grade> allGradesOfInstructor) {
        double sum = 0;
        int amount = 0;
        for (Grade grade : allGradesOfInstructor){
            sum += grade.getRating();
            ++amount;
        }
        if (amount!=0)
            return sum/amount;
        return 0;
    }

    private double CalculateNewAverageGradeForCottage(Collection<Grade> allGradesOfCottage) {
        double sum = 0;
        int amount = 0;
        for (Grade grade : allGradesOfCottage){
            sum += grade.getRating();
            ++amount;
        }
        if (amount!=0)
            return sum/amount;
        return 0;
    }

    private double CalculateNewAverageGradeForBoat(Collection<Grade> allGradesOfBoat) {
        double sum = 0;
        int amount = 0;
        for (Grade grade : allGradesOfBoat){
            sum += grade.getRating();
            ++amount;
        }
        if (amount!=0)
            return sum/amount;
        return 0;
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

    private void sendMailForEnablingGrade(User user){
        try{
            logger.info("< Sending mail");
            emailService.sendNotificationForEnablingRevision(user);
            logger.info("< Mail sent");
        }catch (Exception e) {
            logger.info(e.toString());
        }
    }

    public Grade makeGradeForSaving(GradeToSaveDto dto) {
        if(Objects.equals(dto.getEntityType(), "I"))
            return makeGradeForInstructor(dto);
        else if(Objects.equals(dto.getEntityType(), "CO"))
            return makeGradeForCottageOwner(dto);
        else if(Objects.equals(dto.getEntityType(), "BO"))
            return makeGradeForBoatOwner(dto);
        else if(Objects.equals(dto.getEntityType(), "C"))
            return makeGradeForCottage(dto);
        else return makeGradeForBoat(dto);
    }

    private Grade makeGradeForBoat(GradeToSaveDto dto) {
        Grade grade = new Grade();
        makeBaseGrade(grade, dto);
        BoatReservation reservation = boatReservationService.markReservationAsEvaluated(dto.getEntityId());
        grade.setBoat(reservation.getBoat());
        return saveGrade(grade);
    }

    private Grade makeGradeForCottage(GradeToSaveDto dto) {
        Grade grade = new Grade();
        makeBaseGrade(grade, dto);
        CottageReservation reservation = cottageReservationService.markReservationAsEvaluated(dto.getEntityId());
        grade.setCottage(reservation.getCottage());
        return saveGrade(grade);
    }

    private Grade makeGradeForBoatOwner(GradeToSaveDto dto) {
        return new Grade();
    }

    private Grade makeGradeForCottageOwner(GradeToSaveDto dto) {
        return new Grade();
    }

    private Grade makeGradeForInstructor(GradeToSaveDto dto){
        Grade grade = new Grade();
        makeBaseGrade(grade, dto);
        AdventureReservation reservation = reservationService.markReservationAsEvaluated(dto.getEntityId());
        grade.setInstructor(reservation.getAdventure().getInstructor());
        return saveGrade(grade);
    }

    private void makeBaseGrade(Grade grade, GradeToSaveDto dto){
        grade.setRevision(dto.getRevision());
        grade.setRating(dto.getRating());
    }
}
