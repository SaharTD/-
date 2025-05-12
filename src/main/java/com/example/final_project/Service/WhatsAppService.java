package com.example.final_project.Service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor

@Service
public class WhatsAppService {

    private static final String ACCOUNT_SID = "ACa388ab380d41adc951177a7a3331b53f";
    private static final String AUTH_TOKEN = "4f438842df56281a1e1bb6df6ab31fdb";
    private static final String FROM_WHATSAPP_NUMBER = "+17759426544";


    public void sendAccountantActivationMessage(String username, String password, String employeeId, String phoneNumber, LocalDate createdDate) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        String formattedDate = createdDate.format(DateTimeFormatter.ofPattern("dd MMMM yyyy"));

        String message = " Your accountant account has been activated successfully!\n\n"
                + "Date: " + formattedDate + "\n"
                + "Username: " + username + "\n"
                + "Password: " + password + "\n"
                + "employee ID: " + employeeId + "\n\n"
                + "Please keep this information safe.\n\n"
                + "For support, contact the Mohasil Team.";

        Message.creator(
                new PhoneNumber("whatsapp:" + phoneNumber),
                new PhoneNumber(FROM_WHATSAPP_NUMBER),
                message
        ).create();
    }
}
