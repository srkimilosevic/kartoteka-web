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
import java.util.Map;


@Controller
@RequestMapping("/pacijenti")
public class PacijentController {
    private List<Pacijent> pacijenti;
    private File file = new File("E:\\Spring kurs\\kartoteka.xml");
    public List<Pacijent> result;
    public int updateInd=-1;
    public Pacijent selectedPacijent;
    public List<Bolest> bolestiSelektovanogPacijenta;
    //za povratak na selektovanog pacijenta nakon dodavanja bolesti, samo u /prikaz
    public int prikazPacijentaId;


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
        result=null;
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
        prikazPacijentaId = pacijenti.indexOf(pacijent);
        selectedPacijent=pacijent;

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
        updateInd=-1;
        Pacijent pacijent = new Pacijent();
        model.addAttribute("pacijent", pacijent);
        return "podaci-pacijent";
    }
    @PostMapping("/save")
    public String savePacijent(@ModelAttribute("pacijent") Pacijent pacijent){

        if(updateInd>=0){
            pacijenti.set(updateInd, pacijent);
            pacijent.setBolesti(bolestiSelektovanogPacijenta);
        }else {
            pacijenti.add(pacijent);
        }
        bolestiSelektovanogPacijenta=null;
        savePacijentiDatabase(pacijenti);
        result=pacijenti;
        updateInd=-1;

        return "redirect:/pacijenti/list";
    }
    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("tableId") int id, Model model){
        Pacijent pacijent;

        if(result!=null){
            pacijent = result.get(id);
            for(int i=0; i<pacijenti.size(); i++){
                if(pacijenti.get(i).equals(pacijent)){
                    updateInd=i;
                }
            }
        }else {
            pacijent = pacijenti.get(id);
            updateInd=id;
        }

        bolestiSelektovanogPacijenta=pacijent.getBolesti();
        model.addAttribute("pacijent", pacijent);
        return "podaci-pacijent";
    }
    @GetMapping("/delete")
    public String delete(@RequestParam("tableId") int id){
        Pacijent pacijent;

        if(result!=null){
            pacijent = result.get(id);
            for(int i=0; i<pacijenti.size(); i++){
                if(pacijenti.get(i).equals(pacijent)){
                    updateInd=i;
                }
            }
        }else {
            updateInd=id;
        }
        pacijenti.remove(pacijenti.get(updateInd));
        savePacijentiDatabase(pacijenti);
        result=pacijenti;
        updateInd=-1;

        return "redirect:/pacijenti/list";
    }
    public void savePacijentiDatabase(List<Pacijent> lista){

        try {
            JAXBContext context = JAXBContext
                    .newInstance(PersonListWrapper.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            PersonListWrapper wrapper = new PersonListWrapper();
            wrapper.setPacijenti(lista);

            m.marshal(wrapper, file);

        } catch (Exception e){
            e.printStackTrace();
        }
    }
    @GetMapping("/pacijent/pregled")
    public String getBolest(@RequestParam("tableId") int id, Model model){
        Bolest bolest = selectedPacijent.getBolesti().get(id);

        bolest.setPacijent(selectedPacijent);

        model.addAttribute("bolest", bolest);

        if(bolest.getVrstaPregledaBolest().equals("OP")) {
            return "show-bolest-OP";
        }else if(bolest.getVrstaPregledaBolest().equals("EUZ1")){
            return "show-bolest-EUZ1";
        }else{
            return "show-bolest-EUZ2";
        }
    }
    //dodavanje pregleda
    @GetMapping("/pacijent/showFormForAddBolestOp")
    public String showFormForAddBolestOp(Model model){
        Bolest bolest = new Bolest();
        model.addAttribute("bolest", bolest);
        System.out.println(bolest);
        return "add-OP";
    }
    @PostMapping("/pacijent/savebolest")
    public String savebolest(@ModelAttribute("bolest") Bolest bolest, Model model){
        bolest.setVrstaPregledaBolest("OP");

        pacijenti.get(prikazPacijentaId).add(bolest);


        savePacijentiDatabase(pacijenti);
        model.addAttribute("pacijent", pacijenti.get(prikazPacijentaId));

        return "show-pacijent";
    }

}
