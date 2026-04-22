package es.uji.ei1027.sgOvi.dao;

import es.uji.ei1027.sgOvi.model.PapPati;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;
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
        jdbcTemplate.update("INSERT INTO Pap_Pati (dni, password, address, type, available, training, document, reason, state, papPatiPreferences) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)",
                papPati.getDni(),
                papPati.getAddress(),
                papPati.getType(),
                papPati.isAvailable(),
                papPati.getTraining(),
                papPati.getDocument(),
                papPati.getReason(),
                papPati.getState(),
                papPati.getPapPatiPreferences()
        );
    }

    public void updatePapPati(PapPati papPati) {
        jdbcTemplate.update("UPDATE Pap_Pati SET address=?, type=?, available=?, training=?, document=?, reason=?, state=?, papPatiPreferences=? WHERE dni=?",
                papPati.getAddress(),
                papPati.getType(),
                papPati.isAvailable(),
                papPati.getTraining(),
                papPati.getDocument(),
                papPati.getReason(),
                papPati.getState(),
                papPati.getPapPatiPreferences(),
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
}