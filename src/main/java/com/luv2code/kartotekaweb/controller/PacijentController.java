package com.luv2code.kartotekaweb.controller;

import com.luv2code.kartotekaweb.entity.Bolest;
import com.luv2code.kartotekaweb.entity.Pacijent;
import com.luv2code.kartotekaweb.entity.PersonListWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/pacijenti")
public class PacijentController {
    private List<Pacijent> pacijenti;
    private File file = new File("E:\\Spring kurs\\kartoteka.xml");
    public List<Pacijent> result;

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
            e.printStackTrace();
        }
    }
    @GetMapping("/list")
    public String listAll(Model model){

        model.addAttribute("pacijenti", pacijenti);
        return "list-pacijenti";
    }
    @GetMapping("/pacijent")
    public String getPacijent(@RequestParam("tableId") int id, Model model){
        Pacijent pacijent;
        if(result!=null){
            pacijent = result.get(id);
        }else {
            pacijent = pacijenti.get(id);
        }
        if(pacijent==null){
            throw new RuntimeException("Pacijent nije pronadjen");
        }
       pacijent.getBolesti();
        System.out.println(pacijent.getBolesti());
        model.addAttribute("pacijent", pacijent);
        return "show-pacijent";
    }
    @GetMapping("/search")
    public String search(@RequestParam("searchName") String searchName, Model model){

         result = new ArrayList<>();

        for(int i=0; i<pacijenti.size(); i++){
            if(pacijenti.get(i).getImePrezime().toLowerCase().contains(searchName.toLowerCase().trim())){
                result.add(pacijenti.get(i));
            }
        }
        if(result!=null){
            model.addAttribute("pacijenti", result);
        }else{
            model.addAttribute("pacijenti", pacijenti);
        }

        return "list-pacijenti";
    }
    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model model){
        Pacijent pacijent = new Pacijent();
        model.addAttribute("pacijent", pacijent);
        return "podaci-pacijent";
    }
    @PostMapping("/save")
    public String savePacijent(@ModelAttribute("pacijent") Pacijent pacijent, Model model){
        pacijenti.add(pacijent);
        try {
            JAXBContext context = JAXBContext
                    .newInstance(PersonListWrapper.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            PersonListWrapper wrapper = new PersonListWrapper();
            wrapper.setPacijenti(pacijenti);

            m.marshal(wrapper, file);

        } catch (Exception e){
            e.printStackTrace();
        }
        model.addAttribute("pacijenti", pacijenti);
        return "list-pacijenti";
    }
    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("tableId") int id, Model model){
        Pacijent pacijent;
        if(result!=null){
            pacijent = result.get(id);
        }else {
            pacijent = pacijenti.get(id);
        }
        model.addAttribute("pacijent", pacijent);
        return "podaci-pacijent";
    }

}
