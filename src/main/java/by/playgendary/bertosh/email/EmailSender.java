package by.playgendary.bertosh.email;

import by.playgendary.bertosh.entities.Booking;
import by.playgendary.bertosh.entities.User;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Component
public class EmailSender {

    private String subject = "Room booking";
    private StringBuilder text = new StringBuilder();
    private String username = "playgendaryroombookingservice@gmail.com";
    private String password = "roomservice123";
    private Properties props;

    private final static Logger logger = LogManager.getLogger(EmailSender.class);

    public EmailSender() {
        props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
    }

    public void send(User user, Booking booking){
        Session session = Session.getDefaultInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        createText(user, booking);

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(user.getEmail()));
            message.setSubject(subject);
            message.setText(text.toString());

            Transport.send(message);
            logger.info("Sent email to " + user.getEmail());
        } catch (MessagingException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private void createText(User user, Booking booking) {
        text.append("Dear " + user.getFirstName() + ".");
        text.append("You have been successful booked room #" + booking.getRoom().getRoomNumber());
        text.append(" at " + booking.getBookingDate().toString());
        text.append(" from " + booking.getStartTime().toString() + " to " + booking.getEndTime().toString());
    }
}
