package es.uji.ei1027.sgOvi.service.Services;

import es.uji.ei1027.sgOvi.dao.OviUserDao;
import es.uji.ei1027.sgOvi.dao.PersonDao;
import es.uji.ei1027.sgOvi.model.OviUser;
import es.uji.ei1027.sgOvi.model.Person;
import es.uji.ei1027.sgOvi.service.DTOs.PersonOviUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class OviUserServiceImp implements OviUserService {


    @Autowired
    OviUserDao oviUserDao;

    @Autowired
    PersonDao personDao;
    @Override
    public OviUser getOviUser(String dni) {
        return oviUserDao.getOviUser(dni) ;
    }

    @Override
    public Person getPerson(String dni) {
        return personDao.getPerson(dni);
    }

    @Override
    public void updateOviUser(OviUser oviUser) {
        oviUserDao.updateOviUser(oviUser);
    }

    @Override
    public void deleteOviUser(String dni) {
        oviUserDao.deleteOviUser(dni);
    }

    @Override
    public void addOviUser(OviUser oviUser) {
        oviUserDao.addOviUser(oviUser);
    }

    @Override
    public void addPerson(Person person) {
        personDao.addPerson(person);
    }

    public List<PersonOviUserDTO> getPersonUserList(String stateSel, String sortSel) {

        List<PersonOviUserDTO> listaDTOs = new ArrayList<>();
        List<OviUser> listaUser = oviUserDao.getOviUsers();
        for(OviUser user : listaUser){
            PersonOviUserDTO personOviUserDTO = new PersonOviUserDTO();
            personOviUserDTO.setPerson(personDao.getPerson(user.getDni()));
            personOviUserDTO.setOviUser(user);
            listaDTOs.add(personOviUserDTO);
        }

        //filtrado
        if (stateSel != null && !stateSel.equals("ALL")) {
            listaDTOs.removeIf(dto -> !dto.getOviUser().getState().name().equals(stateSel));
        }

        if (sortSel != null) {
            switch (sortSel) {
                case "nameAsc":
                    listaDTOs.sort(Comparator.comparing(personOviUserDTO -> { return personOviUserDTO.getPerson().getName();},String.CASE_INSENSITIVE_ORDER));
                    break;
                case "nameDesc":
                    listaDTOs.sort(Comparator.comparing(personOviUserDTO -> { return personOviUserDTO.getPerson().getName();},String.CASE_INSENSITIVE_ORDER));
                    listaDTOs = listaDTOs.reversed();
                    break;
                case "dni":
                    listaDTOs.sort(Comparator.comparing(personOviUserDTO -> { return personOviUserDTO.getPerson().getDni();},String.CASE_INSENSITIVE_ORDER));
                    break;
            }
        }


        return listaDTOs;
    }

}
