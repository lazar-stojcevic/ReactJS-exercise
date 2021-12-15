package com.example.backend.Services;

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
        mail.setText("Pozdrav " + user.getFirstname() + ",\n\nhvala što pratiš ISA. \n\n"
        + "http://localhost:8080/auth/" + user.getEmail());
        javaMailSender.send(mail);
    }

    @Async
    public void sendConfirmMailToInstructor(User user) throws MailException, InterruptedException{
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
    public void sendNegativeMailToInstructor(User user) throws MailException, InterruptedException{
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(user.getEmail());
        mail.setFrom(env.getProperty("spring.mail.username"));
        mail.setSubject("Odgovor na podnošenje zahteva za registraciju.");
        mail.setText("Pozdrav " + user.getFirstname() + ", \n\nobaveštavamo vas da vaš nalog nažalost" +
                " nije prihvaćen." +
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
}
