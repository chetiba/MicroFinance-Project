package tn.esprit.gnbapp.controllers;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import tn.esprit.gnbapp.entities.project;
import tn.esprit.gnbapp.services.ProjectServiceImpl;
import tn.esprit.gnbapp.repositories.ProjectRepository;
import tn.esprit.gnbapp.services.ProjectServices;

import javax.imageio.ImageIO;
import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;
import java.util.List;
import java.util.Properties;

@RestController
@AllArgsConstructor
@RequestMapping("/Project")
public class ProjectRestController {
    private ProjectServices projectService;
    private ProjectRepository projectRepository;


  /*  @PostMapping("/ajouterProject")
    public ResponseEntity<String> add(@RequestBody project project) {

        projectService.add(project);
        return ResponseEntity.ok("Added successfully !");


    }*/

    @PostMapping("/ajouterProject")
    public ResponseEntity<String> add(@RequestBody project project) throws IOException, MessagingException {
        projectService.add(project);
        File file = ResourceUtils.getFile("src/main/java/tn/esprit/gnbapp/utils/ProjectAdded.html");
        System.out.println("File Found : " + file.exists());
        String content = new String(Files.readAllBytes(file.toPath()));
              content = content.replace("${projectname}", projectRepository.findName());
       // String emailUser= consultationRepository.findEmail(id_cons);
        projectService.sendmail("sara.wahada@esprit.tn", "GoliathBank", content);

        /*
        // Envoyer un e-mail pour informer de l'ajout du projet
        final String username = "souhailamri90@gmail.com"; // Adresse e-mail source
        final String password = "fekiotqmbdxytssp"; // Mot de passe e-mail source

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse("mohamedsouhail.amri@esprit.tn") // Adresse e-mail de destination
            );
            message.setSubject("New project added!");
            message.setText(html,true);

            Transport.send(message);

            System.out.println("E-mail envoyé avec succès !");

        } catch (MessagingException e) {
            System.out.println("Erreur lors de l'envoi de l'e-mail : " + e.getMessage());
        }*/

        return ResponseEntity.ok("Added successfully !");
    }

    @PostMapping("ajouterAllProject")
    public ResponseEntity<String> addAll(@RequestBody List<project> list){

        projectService.addAll(list);
        return ResponseEntity.ok("AddedAll successfully !");

    }
    @PutMapping("/ModifierProject")
    public ResponseEntity<String> edit(@RequestBody project project){

        projectService.edit(project);
        return ResponseEntity.ok("Updated successfully !");

    }
    @DeleteMapping("SupprimerProject")
    public ResponseEntity<String> delete(@RequestBody project project){
        projectService.delete(project);
        return ResponseEntity.ok("Deleted successfully !");


    }
    @GetMapping("/afficherProject")
    public List<project> afficher(){return projectService.selectAll();}

    @DeleteMapping("/deleteProjectById")
    public ResponseEntity<String> deleteProjectById(@RequestBody int id)
    {
        projectService.deleteById(id);
        return ResponseEntity.ok("Deleted successfully !");
    }

    @GetMapping("/getTabInvest")
    public ResponseEntity<String> getAllProjects() {
        List<project> projects = projectService.retrieveAllProject();

        // Générer le tableau HTML
        String table = "<table><tr><th>Project ID</th><th>Project Name</th><th>Investment Amount</th></tr>";
        for (project project : projects) {
            table += "<tr><td>" + project.getId() + "</td><td>" + project.getName() + "</td><td>" + project.getInvestable_amout() + "</td></tr>";
        }
        table += "</table>";

        // Générer le graphique
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (project project : projects) {
            dataset.addValue(project.getInvestable_amout(), "Investment Amount", project.getName());
        }
        JFreeChart chart = ChartFactory.createBarChart("Investment Amounts by Project", "Project Name", "Investment Amount", dataset, PlotOrientation.VERTICAL, false, true, false);
        BufferedImage image = chart.createBufferedImage(600, 400);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "png", baos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] bytes = baos.toByteArray();
        String base64Image = Base64.getEncoder().encodeToString(bytes);
        String chartImg = "<img src='data:image/png;base64," + base64Image + "'/>";

        // Combiner le tableau HTML et le graphique
        String report = "<h2>Investment Amounts by Project</h2>" + table + chartImg;

        return new ResponseEntity<>(report, HttpStatus.OK);
    }

}
