package es.uji.ei1027.sgOvi.service.Services;

import es.uji.ei1027.sgOvi.dao.PapPatiDao;
import es.uji.ei1027.sgOvi.dao.PersonDao;
import es.uji.ei1027.sgOvi.model.PapPati;
import es.uji.ei1027.sgOvi.model.Person;
import es.uji.ei1027.sgOvi.service.DTOs.PersonPapPatiDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class PapPatiServiceImp implements PapPatiService{

    @Autowired
    private PapPatiDao papPatiDao;
    @Autowired
    private PersonDao personDao;

    @Override
    public void addPapPati(PapPati papPati) {
        papPatiDao.addPapPati(papPati);
    }

    @Override
    public void addPerson(Person person) {
        personDao.addPerson(person);
    }

    @Override
    public Person getPerson(PapPati papPati) {
        return personDao.getPerson(papPati.getDni());
    }

    @Override
    public PapPati getPapPati(String dni) {
        return papPatiDao.getPapPati(dni);
    }

    @Override
    public List<PersonPapPatiDTO> listByName(String stateSel, String sortSel) {
        List<PersonPapPatiDTO> listaDTOs = new ArrayList<>();
        List<PapPati> listaPapPati = papPatiDao.getPapPatis();
        for(PapPati papPati : listaPapPati){
            PersonPapPatiDTO personPapPatiDTO = new PersonPapPatiDTO();
            personPapPatiDTO.setPapPati(papPati);
            personPapPatiDTO.setPerson(personDao.getPerson(papPati.getDni()));
            listaDTOs.add(personPapPatiDTO);
        }
        //filtrado
        if (stateSel != null && !stateSel.equals("ALL")) {
            listaDTOs.removeIf(dto -> !dto.getPapPati().getState().name().equals(stateSel));
        }

        if (sortSel != null) {
            switch (sortSel) {
                case "nameAsc":
                    listaDTOs.sort(Comparator.comparing(personPapPatiDTO -> { return personPapPatiDTO.getPerson().getName();},String.CASE_INSENSITIVE_ORDER));
                    break;
                case "nameDesc":
                    listaDTOs.sort(Comparator.comparing(personPapPatiDTO -> { return personPapPatiDTO.getPerson().getName();},String.CASE_INSENSITIVE_ORDER));
                    listaDTOs.reversed();
                    break;
                case "dni":
                    listaDTOs.sort(Comparator.comparing(personPapPatiDTO -> { return personPapPatiDTO.getPerson().getDni();},String.CASE_INSENSITIVE_ORDER));
                    break;
            }
        }
        return listaDTOs;
    }

    @Override
    public List<PersonPapPatiDTO> orderList(List<PersonPapPatiDTO> listaDTOs, String sortSel){
        if (sortSel != null) {
            switch (sortSel) {
                case "nameAsc":
                    listaDTOs.sort(Comparator.comparing(personPapPatiDTO -> { return personPapPatiDTO.getPerson().getName();},String.CASE_INSENSITIVE_ORDER));
                    break;
                case "nameDesc":
                    listaDTOs.sort(Comparator.comparing(personPapPatiDTO -> { return personPapPatiDTO.getPerson().getName();},String.CASE_INSENSITIVE_ORDER));
                    listaDTOs.reversed();
                    break;
                case "dni":
                    listaDTOs.sort(Comparator.comparing(personPapPatiDTO -> { return personPapPatiDTO.getPerson().getDni();},String.CASE_INSENSITIVE_ORDER));
                    break;
            }
        }
        return listaDTOs;
    }


    @Override
    public void updatePapPati(PapPati papPati) {
        papPatiDao.updatePapPati(papPati);
    }

    @Override
    public List<PersonPapPatiDTO> getPapPatiTrainingActivities(String idActivity) {
        return papPatiDao.getPapPatiTrainingActivities(idActivity);
    }
}
