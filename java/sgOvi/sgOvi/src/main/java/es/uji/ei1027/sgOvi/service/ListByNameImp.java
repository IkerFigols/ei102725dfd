package es.uji.ei1027.sgOvi.service;

import es.uji.ei1027.sgOvi.dao.*;
import es.uji.ei1027.sgOvi.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ListByNameImp implements ListByName{
    @Autowired
    PersonDao personDao;

    @Autowired
    OviUserDao oviUserDao;

    @Autowired
    PapPatiDao papPatiDao;

    @Autowired
    AssistanceReqDao assistanceReqDao;

    @Autowired
    InstructorDao instructorDao;

    //El mapa es tal que: Map<dni, List<nombre, estado>
    @Override
    public Map<String, List<String>> personUserList() {
        Map<String, List<String>> mapa = new HashMap<>();
        List<OviUser> listaUser = oviUserDao.getOviUsers();
        for(OviUser user : listaUser){
            List<String> parametros = new ArrayList<>();
            parametros.add(personDao.getPerson(user.getDni()).getName());
            parametros.add(personDao.getPerson(user.getDni()).getSurname());
            parametros.add(user.getState());
            mapa.put(user.getDni(), parametros);
        }
        return mapa;
    }

    public Map<String, List<String>> personPapPatiList() {
        Map<String, List<String>> mapa = new HashMap<>();
        List<PapPati> listaPapPati = papPatiDao.getPapPatis();
        for(PapPati papPati : listaPapPati){
            List<String> parametros = new ArrayList<>();
            parametros.add(personDao.getPerson(papPati.getDni()).getName());
            parametros.add(personDao.getPerson(papPati.getDni()).getSurname());
            parametros.add(papPati.getType());
            String available = "No disponible";
            if(papPati.isAvailable())
                available = "Disponible";
            parametros.add(available);
            parametros.add(papPati.getState());
            mapa.put(papPati.getDni(), parametros);
        }
        return mapa;
    }

    @Override
    public Map<String, List<String>> personInstructorList() {
        Map<String, List<String>> mapa = new HashMap<>();
        List<Instructor> listaInstructor = instructorDao.getInstructors();
        for(Instructor instructor : listaInstructor){
            List<String> parametros = new ArrayList<>();
            parametros.add(personDao.getPerson(instructor.getDni()).getName());
            parametros.add(personDao.getPerson(instructor.getDni()).getSurname());
            String especialidad = instructor.getExpertise();
            if(instructor.getExpertise() == null) {
                especialidad = "N/A";
            }
            parametros.add(especialidad);
            mapa.put(instructor.getDni(), parametros);
        }
        return mapa;
    }
}
