package com.luv2code.kartotekaweb.controller;

import com.luv2code.kartotekaweb.KartotekaWebApplication;
import com.luv2code.kartotekaweb.entity.Pacijent;
import com.luv2code.kartotekaweb.entity.PersonListWrapper;
import javafx.scene.control.Alert;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

@Controller
@RequestMapping("/pacijenti")
public class PacijentController {
    private List<Pacijent> pacijenti;
    private File file = new File("E:\\Spring kurs\\kartoteka.xml");

    /*
    public File getPersonFilePath() {
        Preferences prefs = Preferences.userNodeForPackage(KartotekaWebApplication.class);
        String filePath = prefs.get("filePath", null);
        if (filePath != null) {
            return new File(filePath);
        } else {
            return null;
        }
    }

    public void setPersonFilePath(File file) {
        Preferences prefs = Preferences.userNodeForPackage(KartotekaWebApplication.class);
        this.file=file;
        if (file != null) {
            prefs.put("filePath", file.getPath());

        } else {
            prefs.remove("filePath");

        }
    }
    */


    @PostConstruct
    public void loadPersonDataFromFile() {
        try {

            JAXBContext context = JAXBContext
                    .newInstance(PersonListWrapper.class);
            Unmarshaller um = context.createUnmarshaller();

            // Reading XML from the file and unmarshalling.
            PersonListWrapper wrapper = (PersonListWrapper) um.unmarshal(file);

            pacijenti = new ArrayList<>();
            pacijenti.addAll(wrapper.getPacijenti());


        } catch (Exception e) { // catches ANY exception
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not load data");
            alert.setContentText("Could not load data from file:\n" + file.getPath());

            alert.showAndWait();
        }
    }
    @GetMapping("/list")
    public String listAll(Model model){
        model.addAttribute("pacijenti", pacijenti);
        return "list-pacijenti";
    }
}
