package es.uji.ei1027.sgOvi.dao;

import es.uji.ei1027.sgOvi.model.PapPati;
import es.uji.ei1027.sgOvi.model.enums.ShiftType;
import es.uji.ei1027.sgOvi.service.PersonPapPatiDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PapPatiDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addPapPati(PapPati papPati){
        jdbcTemplate.update("INSERT INTO Pap_Pati  VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                papPati.getDni(),
                papPati.getAddress(),
                papPati.getType().name(),
                papPati.getAvailable(),
                papPati.getTraining(),
                papPati.getExperience(),
                papPati.getDocument(),
                papPati.getReason(),
                papPati.getState().name(),
                papPati.getDrivingLicense(),
                papPati.getShift().name()
        );
    }

    public void updatePapPati(PapPati papPati) {
        jdbcTemplate.update("UPDATE Pap_Pati SET address=?, type=?, available=?, training=?, document=?, reason=?, state=?, experience=?, drivingLicense=?, shift=? WHERE dni=?",
                papPati.getAddress(),
                papPati.getType().name(),
                papPati.getAvailable(),
                papPati.getTraining(),
                papPati.getDocument(),
                papPati.getReason(),
                papPati.getState().name(),
                papPati.getExperience(),
                papPati.getDrivingLicense(),
                papPati.getShift().name(),
                papPati.getDni() // El dni va al final porque es el '?' del WHERE
        );
    }

    public void deletePapPati(String dni) {
        jdbcTemplate.update("DELETE FROM Pap_Pati WHERE dni=?", dni);
    }

    public PapPati getPapPati(String dni){
        try{
            PapPati papPati = jdbcTemplate.queryForObject(
                    "SELECT * FROM Pap_Pati WHERE dni=?",
                    new PapPatiRowMapper(),
                    dni
            );
            return papPati;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<PapPati> getPapPatis(){
        try {
            List<PapPati> papPatiList = jdbcTemplate.query(
                    "SELECT * FROM Pap_Pati",
                    new PapPatiRowMapper()
            );
            return papPatiList;
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

    public void updatePreferences(PapPati papPati) {
        jdbcTemplate.update(
                "UPDATE Pap_Pati SET drivingLicense=?, shift=? WHERE dni=?",
                papPati.getDrivingLicense(),
                papPati.getShift().name(),
                papPati.getDni()
        );
    }

    public List<PapPati> getCandidatesPapPati(String idAsReq){
        try {
            List<PapPati> papPatiList = jdbcTemplate.query(
                    "SELECT pap.* FROM Pap_Pati as pap JOIN Selection as s ON pap.dni = s.idPapPati JOIN Assistance_Request as ap USING(idAsReq) WHERE ap.idAsReq = ?;  "
                    ,new PapPatiRowMapper()
                    ,idAsReq);
            return papPatiList;


        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

    public ArrayList<PapPati> findCompatiblePapPatis(//String type,
                                                     Boolean drivingLicense,
                                                     ShiftType shiftType,
                                                     String province,
                                                     int minAge,
                                                     int minExperience
    ) {
        LocalDate minBirthdayDate = LocalDate.now().minusYears(minAge);
        String stringShiftType = "";
        if(shiftType == null || shiftType.name().equals("ANY")){
            stringShiftType = "%";
        }else{
            stringShiftType = shiftType.name();
        }

        try{
            List<PapPati> papPatiList = jdbcTemplate.query(
                    "SELECT pap.* " +
                            "FROM Pap_Pati AS pap " +
                            "JOIN Person AS per USING (dni) " +
                            "WHERE state = 'APPROVED' " +
                            "AND available = TRUE " +
                            //"AND pap.type = ? " +
                            "AND (? = FALSE OR pap.drivingLicense = TRUE) " +
                            "AND (pap.shift LIKE ? OR pap.shift LIKE 'ANY') " +
                            "AND pap.experience >= ? " +
                            "AND per.birthdayDate <= ? " +
                            "AND LOWER(per.province) = LOWER(?)",
                    new PapPatiRowMapper(),
                    //type,
                    drivingLicense,
                    stringShiftType,
                    minExperience,
                    minBirthdayDate,
                    province
            );
            return new ArrayList<>(papPatiList);

        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

    public List<PersonPapPatiDTO> getPapPatiTrainingActivities(String idActivity){
        try{
            return jdbcTemplate.query("SELECT p.*, pap.* FROM Person AS p JOIN Pap_Pati AS pap USING(dni) JOIN Attendance AS at ON (pap.dni = at.idPapPati) WHERE at.idActivity = ?",new PersonPapPatiDTORowMapper(),idActivity);
        }
        catch (EmptyResultDataAccessException e){
            return new ArrayList<>();
        }
    }
    public List<PapPati> getAvailablePapPatis(){
        try {
            List<PapPati> papPatiList = jdbcTemplate.query(
                    "SELECT * FROM Pap_Pati WHERE available=TRUE AND state='APPROVED'",
                    new PapPatiRowMapper()
            );
            return papPatiList;
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

}