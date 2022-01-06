package com.example.backend.Services;

import com.example.backend.Beans.CottageReservation;
import com.example.backend.Beans.Customer;
import com.example.backend.Beans.FishingInstructor;
import com.example.backend.Beans.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private Environment env;

    @Async
    public void sendNotificaitionAsync(User user) throws MailException, InterruptedException {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(user.getEmail());
        mail.setFrom(env.getProperty("spring.mail.username"));
        mail.setSubject("Helloooo!");
        mail.setText("Pozdrav " + user.getFirstname() + ",\n\novo je link za potvrdu vaseg naloga. \n\n"
        + "http://localhost:8080/auth/" + user.getEmail());
        javaMailSender.send(mail);
    }

    @Async
    public void sendAdventureReservationConfirm(Customer user) throws MailException, InterruptedException {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(user.getEmail());
        mail.setFrom(env.getProperty("spring.mail.username"));
        mail.setSubject("Potvrda o zakazanom terminu avanture");
        mail.setText("Uspesno ste zakazali termin avanture!");
        javaMailSender.send(mail);
    }

    @Async
    public void sendCottageReservationConfirm(Customer user, CottageReservation cottageReservation) throws MailException, InterruptedException {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(user.getEmail());
        mail.setFrom(env.getProperty("spring.mail.username"));
        mail.setSubject("Potvrda o izdavanju vikendice");
        mail.setText("Uspesno ste zakupili vikendicu!" + System.lineSeparator() +
                "Vas termin traje od" + System.lineSeparator() + cottageReservation.getReservationStart() +
                "do " + cottageReservation.getReservationEnd() + System.lineSeparator() +
                "Adresa vikendice: " + cottageReservation.getCottage().getAddress().toString());
        javaMailSender.send(mail);
    }

    @Async
    public void sendConfirmMailToUser(User user) throws MailException, InterruptedException{
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(user.getEmail());
        mail.setFrom(env.getProperty("spring.mail.username"));
        mail.setSubject("Odgovor na podnošenje zahteva za registraciju.");
        mail.setText("Pozdrav " + user.getFirstname() + ", \n\nobaveštavamo vas da je vaš nalog upravo " +
                "aktiviran i od sada možete koristiti sve beneficije sistema." +
                "\n\nS poštovanjem admin tim.");
        javaMailSender.send(mail);
    }

    @Async
    public void sendNegativeMailToUser(User user,String reason) throws MailException, InterruptedException{
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(user.getEmail());
        mail.setFrom(env.getProperty("spring.mail.username"));
        mail.setSubject("Odgovor na podnošenje zahteva za registraciju.");
        mail.setText("Pozdrav " + user.getFirstname() + ", \n\nobaveštavamo vas da vaš nalog nažalost" +
                " nije prihvaćen zbog.\n" + reason +
                "\n\nS poštovanjem admin tim.");
        javaMailSender.send(mail);
    }

    @Async
    public void sendNotificationForCreatingNewAdventureReservation(Customer user)
            throws MailException, InterruptedException{
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(user.getEmail());
        mail.setFrom(env.getProperty("spring.mail.username"));
        mail.setSubject("Obaveštenje o kreiranju nove brze rezervacije.");
        mail.setText("Pozdrav " + user.getFirstname() + ", \n\nobaveštavamo vas da" +
                " je upravo kreirana nova brza rezervacija u okviru avanture/časova precanja na kojoj/kojima" +
                "ste pretplaćeni." +
                "\n\nS poštovanjem admin tim.");
        javaMailSender.send(mail);
    }

    @Async
    public void sendNotificationForEnablingRevision(String email, String firstname) throws MailException,
            InterruptedException{
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(email);
        mail.setFrom(env.getProperty("spring.mail.username"));
        mail.setSubject("Obaveštenje o odobravanju revizije.");
        mail.setText("Pozdrav " + firstname + ", \n\nobaveštavamo vas da" +
                " vam je upravo odobrena nova revizija" +
                "\n\nS poštovanjem admin tim.");
        javaMailSender.send(mail);
    }

    @Async
    public void sendNotificationMailForCreatingCustomReservationToCustomer(Customer customer) throws MailException,
            InterruptedException{
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(customer.getEmail());
        mail.setFrom(env.getProperty("spring.mail.username"));
        mail.setSubject("Obaveštenje o kreiranju nove rezervacije.");
        mail.setText("Pozdrav " + customer.getFirstname() + ", \n\nobaveštavamo vas da" +
                " je upravo kreirana nova rezervacija u okviru avanture/časova precanja." +
                "\n\nS poštovanjem admin tim.");
        javaMailSender.send(mail);
    }

    @Async
    public void sendAnswerOnRequestForDeletingProfile(User user, String answer) throws MailException,
            InterruptedException{
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(user.getEmail());
        mail.setFrom(env.getProperty("spring.mail.username"));
        mail.setSubject("Obaveštenje o zahtevu za brisanje profila.");
        mail.setText("Pozdrav " + user.getFirstname() + ", \n\nobaveštavamo vas da " +
                answer +
                "\n\nS poštovanjem admin tim.");
        javaMailSender.send(mail);
    }

    @Async
    public void sendAnswerOnComplaint(User user, String answer) throws MailException,
            InterruptedException{
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(user.getEmail());
        mail.setFrom(env.getProperty("spring.mail.username"));
        mail.setSubject("Obaveštenje o žalbi.");
        mail.setText("Pozdrav " + user.getFirstname() + ", \n\n" +
                "ovom prilikom želimo da vas obavestimo sledeće\n" +
                answer +
                "\n\nS poštovanjem admin tim.");
        javaMailSender.send(mail);
    }

    @Async
    public void sendMailForDisabling(User user) throws MailException,
            InterruptedException{
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(user.getEmail());
        mail.setFrom(env.getProperty("spring.mail.username"));
        mail.setSubject("Obaveštenje o blokiranju profila.");
        mail.setText("Pozdrav " + user.getFirstname() + ", \n\n" +
                "ovom prilikom želimo da vas obavestimo da je vas nalog blokiran." +
                "\n\nS poštovanjem admin tim.");
        javaMailSender.send(mail);
    }

    @Async
    public void sendMailToInstructorAboutIncome(User user, int income, int reservationNumber) throws MailException,
            InterruptedException{
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(user.getEmail());
        mail.setFrom(env.getProperty("spring.mail.username"));
        mail.setSubject("Obaveštenje o prihodu za selektovani period.");
        mail.setText("Pozdrav " + user.getFirstname() + ", \n\n" +
                "ovom prilikom želimo da vas obavestimo da je Vaš prihod za selektovani period bez " +
                "plaćanja sistemu " + income + "din, a ukupan broj rezervacija je: " + reservationNumber +
                "\n\nS poštovanjem admin tim.");
        javaMailSender.send(mail);
    }
}
