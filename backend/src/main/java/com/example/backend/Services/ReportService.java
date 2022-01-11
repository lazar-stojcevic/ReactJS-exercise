package com.example.backend.Services;

import com.example.backend.Beans.AdventureReservation;
import com.example.backend.Beans.BoatReservation;
import com.example.backend.Beans.CottageReservation;
import com.example.backend.Beans.Report;
import com.example.backend.Dtos.MakeReportDto;
import com.example.backend.Repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Objects;

@Service
public class ReportService {
    @Autowired
    private final ReportRepository reportRepository;
    @Autowired
    private AdventureReservationService adventureReservationService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private CottageReservationService cottageReservationService;
    @Autowired
    private BoatReservationService boatReservationService;

    public ReportService(ReportRepository reportRepository){
        this.reportRepository = reportRepository;
    }

    public Collection<Report> getAllNotProcessedReports(){
        return reportRepository.getAllNotProcessedReports();
    }

    public Report findReportById(long reportId){
        return reportRepository.findById(reportId).orElse(null);
    }

    public Report penalizeCustomer(long reportId){
        Report report = findReportById(reportId);
        if(customerService.addPenaltyPointToCustomer(report.getCustomer().getId()) == null)
            return null;
        report.setProcessed(true);
        return save(report);
    }

    public Report haveMercyOnCustomer(long reportId){
        Report report = findReportById(reportId);
        report.setProcessed(true);
        return save(report);
    }

    public Report makeReportForSaving(MakeReportDto dto){
        if(Objects.equals(dto.getReservationType(), "AR"))
            return makeAdventureReservationReport(dto);
        else if(Objects.equals(dto.getReservationType(), "BR"))
            return makeBoatReservationReport(dto);
        else return makeCottageReservationReport(dto);
    }

    private Report makeCottageReservationReport(MakeReportDto dto) {
        CottageReservation reservation = cottageReservationService.markReservationAsReported(dto.getReservationId());
        if(dto.isCustomerAppear())
            return makeCottageReservationReportForCustomerAppeared(dto, reservation);
        else
            return makeCottageReservationReportForCustomerNotAppeared(dto, reservation);
    }

    private Report makeBoatReservationReport(MakeReportDto dto) {
        BoatReservation reservation = boatReservationService.markReservationAsReported(dto.getReservationId());
        if(dto.isCustomerAppear())
            return makeBoatReservationReportForCustomerAppeared(dto, reservation);
        else
            return makeBoatReservationReportForCustomerNotAppeared(dto, reservation);
    }

    private Report makeAdventureReservationReport(MakeReportDto dto) {
        AdventureReservation reservation = adventureReservationService.markReservationAsReported(dto.getReservationId());
        if(dto.isCustomerAppear())
            return makeAdventureReservationReportForCustomerAppeared(dto, reservation);
        else
            return makeAdventureReservationReportForCustomerNotAppeared(dto, reservation);
    }

    private Report makeAdventureReservationReportForCustomerNotAppeared(MakeReportDto dto, AdventureReservation reservation) {
        Report report = new Report();
        report.setCustomer(reservation.getCustomer());
        report.setBadReport(true);
        report.setCustomerAppear(false);
        report.setProcessed(true);
        report.setComment(dto.getComment());
        if(customerService.addPenaltyPointToCustomer(report.getCustomer().getId()) == null)
            return null;

        return save(report);
    }


    private Report makeAdventureReservationReportForCustomerAppeared(MakeReportDto dto, AdventureReservation reservation) {
        Report report = new Report();
        report.setCustomer(reservation.getCustomer());
        report.setComment(dto.getComment());
        if(dto.isBadReport()){
            report.setBadReport(true);
        }else {
            report.setBadReport(false);
            report.setProcessed(true);
        }
        return save(report);
    }

    private Report makeCottageReservationReportForCustomerNotAppeared(MakeReportDto dto, CottageReservation reservation) {
        Report report = new Report();
        report.setCustomer(reservation.getCustomer());
        report.setBadReport(true);
        report.setCustomerAppear(false);
        report.setProcessed(true);
        report.setComment(dto.getComment());
        if(customerService.addPenaltyPointToCustomer(report.getCustomer().getId()) == null)
            return null;

        return save(report);
    }


    private Report makeCottageReservationReportForCustomerAppeared(MakeReportDto dto, CottageReservation reservation) {
        Report report = new Report();
        report.setCustomer(reservation.getCustomer());
        report.setComment(dto.getComment());
        if(dto.isBadReport()){
            report.setBadReport(true);
        }else {
            report.setBadReport(false);
            report.setProcessed(true);
        }
        return save(report);
    }

    private Report makeBoatReservationReportForCustomerNotAppeared(MakeReportDto dto, BoatReservation reservation) {
        Report report = new Report();
        report.setCustomer(reservation.getCustomer());
        report.setBadReport(true);
        report.setCustomerAppear(false);
        report.setProcessed(true);
        report.setComment(dto.getComment());
        if(customerService.addPenaltyPointToCustomer(report.getCustomer().getId()) == null)
            return null;

        return save(report);
    }


    private Report makeBoatReservationReportForCustomerAppeared(MakeReportDto dto, BoatReservation reservation) {
        Report report = new Report();
        report.setCustomer(reservation.getCustomer());
        report.setComment(dto.getComment());
        if(dto.isBadReport()){
            report.setBadReport(true);
        }else {
            report.setBadReport(false);
            report.setProcessed(true);
        }
        return save(report);
    }

    private Report save(Report report){
        return reportRepository.save(report);
    }
}
