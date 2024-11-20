package tn.esprit.gnbapp.services;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.gnbapp.entities.consultation;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import tn.esprit.gnbapp.entities.user;
import tn.esprit.gnbapp.repositories.IconsultationRepository;
import tn.esprit.gnbapp.repositories.IuserRepository;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.Random;

@Service
@AllArgsConstructor
public class ConsultationServicesImpl implements IConsultationServices{
    private final IconsultationRepository consultationRepository;
    private final IuserRepository userRepository;
    private JavaMailSender mailSender;
    static int code=generateRandomIntIntRange(100001,200001);

    @Override
    public List<consultation> retrieveAllConsultations() {
        return consultationRepository.findAll();
    }

    @Override
    public consultation addOrUpdateConsultation(consultation c) {
        return consultationRepository.save(c);
    }

    @Override
    public consultation retrieveConsultation(Integer id_consult) {
        return consultationRepository.findById(id_consult).orElse(null);
    }
    @Override
    public void removeConsultation(Integer id_consult) {
        consultationRepository.deleteById(id_consult);

    }
    @Override
    public void assignConsultToUser(Integer id_cons,Integer id_user) {
        consultation c = consultationRepository.findById(id_cons).orElse(null);
        user u = userRepository.findById(id_user).orElse(null);
        c.setUser(u);
        c.setStatus_cons(true);
        c.setCode_cons(code);
        consultationRepository.save(c);
    }
    @Override
    public void sendmail(String toEmail, String subject, String htmlBody) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHealper = new MimeMessageHelper(mimeMessage,true);
        mimeMessageHealper.setFrom("sara.wahada@esprit.tn");
        mimeMessageHealper.setTo(toEmail);
        mimeMessageHealper.setText(htmlBody,true);
        mimeMessageHealper.setSubject(subject);
        mailSender.send(mimeMessage);
        System.out.println("Mail sent successfully!");

    }
    @Override
    public  int generateQRCode(consultation c) throws WriterException, IOException {
        String qrCodePath = "/Users/sarawahada/app/gnbApp/src/main/java/tn/esprit/gnbapp/utils";
        String qrCodeName = qrCodePath+c.getCreationdate_cons()+"-QRCODE.png";
        var qrCodeWriter = new QRCodeWriter();

        if (c.isStatus_cons() && Objects.equals(c.getTypecons().toString(), "online")){
            BitMatrix bitMatrix = qrCodeWriter.encode(
                    "Creation Date: "+c.getCreationdate_cons()+ "\n"+
                            "Type: "+c.getTypecons()+ "\n"+
                            "Status: "+"Confirmed "+ "\n"+
                            "To access your consultation type in this code : "+code+"\n"
                    , BarcodeFormat.QR_CODE, 400, 400);
            Path path = FileSystems.getDefault().getPath(qrCodeName);
            MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
            System.out.println(code);
            return code;
        }
        else if (c.isStatus_cons() && Objects.equals(c.getTypecons().toString(), "facetoface")){
            BitMatrix bitMatrix = qrCodeWriter.encode(
                    "Creation Date: "+c.getCreationdate_cons()+ "\n"+
                            "Type: "+c.getTypecons()+ "\n"+
                            "Status: "+"Confirmed "+ "\n"+
                            "Address: "+"Centre ville"+"\n"+
                            "To access your consultation use this code at the door : "+code+"\n"
                    , BarcodeFormat.QR_CODE, 400, 400);
            Path path = FileSystems.getDefault().getPath(qrCodeName);
            MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
            System.out.println(code);
            return code;
        }
        else {
            BitMatrix bitMatrix = qrCodeWriter.encode(
                    "Creation Date: "+c.getCreationdate_cons()+ "\n"+
                            "Type: "+c.getTypecons()+ "\n"+
                            "Status: "+" pending..."+ "\n"
                    , BarcodeFormat.QR_CODE, 400, 400);
            Path path = FileSystems.getDefault().getPath(qrCodeName);
            MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
            return -1;
        }
    }
    public static int generateRandomIntIntRange(int min, int max) {
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

}
