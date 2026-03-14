package es.uji.ei1027.sgOvi.dao;
import es.uji.ei1027.sgOvi.model.Ovi_User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;


@Repository // En Spring els DAOs van anotats amb @Repository
public class OviUserDao {

    private JdbcTemplate jdbcTemplate;

    // Obté el jdbcTemplate a partir del Data Source
    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }


    public void addOviUser( Ovi_User oviUser) {
        String legalGuardian = oviUser.getLegalGuardian();
        if (legalGuardian.equals(""))
            legalGuardian = null;


        jdbcTemplate.update("INSERT INTO Ovi_User VALUES(?, ?, ?, ?, ?, ?, ?, ?)",
                oviUser.getDni(),
                oviUser.getBirthdayDate(),
                oviUser.getPassword(),
                oviUser.getAddress(),
                legalGuardian,
                false,
                "pendiente",
                oviUser.getUserPreferences()
        );
    }


    public void deleteOviUser(String dni) {
        jdbcTemplate.update("DELETE FROM Ovi_User WHERE dni LIKE " + dni + "'");
    }

    public void updateOviUser(Ovi_User oviUser) {
        jdbcTemplate.update("UPDATE Ovi_User SET birthdayDate ="+ oviUser.getBirthdayDate() +", password ="+oviUser.getPassword()+", address="+oviUser.getAddress()+", legalGuardian ="+ oviUser.getLegalGuardian() +", accepted"+ oviUser.getAccepted()+ ", reason ="+oviUser.getReason()+", userPreferences ="+oviUser.getUserPreferences()+ "WHERE dni LIKE " + oviUser.getDni() + "'");
    }

    public Ovi_User getOviUser(String dni) {
        try {
            Ovi_User n = jdbcTemplate.queryForObject(
                    "SELECT * FROM Ovi_User WHERE dni = '" + dni + "'",
                    new OviUserRowMapper());
            return n;

        } catch (DataAccessException e) {
            return new Ovi_User();
        }
    }

    public List<Ovi_User> getOviUsers() {
        try {
            return jdbcTemplate.query("SELECT * FROM Ovi_User", new OviUserRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<Ovi_User>();
        }
    }
}
