package es.uji.ei1027.sgOvi.dao;

import es.uji.ei1027.sgOvi.model.PapPati;
import es.uji.ei1027.sgOvi.model.Person;
import es.uji.ei1027.sgOvi.model.Selection;
import es.uji.ei1027.sgOvi.service.PapPatiSelectionDTO;
import es.uji.ei1027.sgOvi.service.PersonPapPatiDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class PersonPapPatiSelDTORowMapper  implements RowMapper<PapPatiSelectionDTO> {

    @Override
    public PapPatiSelectionDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        Person person = new Person();
        person.setDni(rs.getString("dni"));
        person.setName(rs.getString("name"));
        person.setSurname(rs.getString("surname"));
        person.setPhoneNumber(rs.getString("phoneNumber"));
        person.setEmail(rs.getString("email"));
        person.setGender(rs.getString("gender"));
        person.setPassword(rs.getString("password"));
        person.setCity(rs.getString("city"));
        person.setProvince(rs.getString("province"));
        person.setBirthdayDate(rs.getObject("birthdayDate", LocalDate.class));

        PapPati papPati = new PapPati();
        papPati.setDni(rs.getString("dni"));
        papPati.setAddress(rs.getString("address"));
        papPati.setType(rs.getString("type"));
        papPati.setAvailable(rs.getBoolean("available"));
        papPati.setTraining(rs.getString("training"));
        papPati.setDocument(rs.getString("document"));
        papPati.setReason(rs.getString("reason"));
        papPati.setState(rs.getString("state"));
        papPati.setExperience(rs.getInt("experience"));
        papPati.setDrivingLicense(rs.getBoolean("drivingLicense"));
        papPati.setShift(rs.getString("shift"));


        Selection selection = new Selection();
        selection.setIdSelection(rs.getString("idSelection"));
        selection.setDate(rs.getObject("date", java.time.LocalDate.class));
        selection.setState(rs.getString("state"));
        selection.setIdPapPati(rs.getString("idPap"));
        selection.setIdAsReq(rs.getString("idAsReq"));


        PapPatiSelectionDTO  papPatiSelectionDTO= new PapPatiSelectionDTO();
        papPatiSelectionDTO.setPapPati(papPati);
        papPatiSelectionDTO.setPerson(person);
        papPatiSelectionDTO.setSelection(selection);
        return papPatiSelectionDTO;
    }
}
