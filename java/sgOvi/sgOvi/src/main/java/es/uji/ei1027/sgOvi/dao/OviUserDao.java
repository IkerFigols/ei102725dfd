package es.uji.ei1027.sgOvi.dao;
import es.uji.ei1027.sgOvi.model.OviUser;
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


    public void addOviUser( OviUser oviUser) {
        String legalGuardian = oviUser.getLegalGuardian();
        if (legalGuardian.equals(""))
            legalGuardian = null;


        jdbcTemplate.update("INSERT INTO Ovi_User VALUES(?, ?, ?, ?, ?, ?, ?)",
                oviUser.getDni(),
                oviUser.getBirthdayDate(),
                oviUser.getAddress(),
                legalGuardian,
                oviUser.getState(),
                oviUser.getReason(),
                oviUser.getUserPreferences()
        );
    }


    public void deleteOviUser(String dni) {
        jdbcTemplate.update("DELETE FROM Ovi_User WHERE dni LIKE '" + dni + "'");
    }

    public void updateOviUser(OviUser user) {
        String sql = "UPDATE Ovi_User SET birthdayDate=?, address=?, legalGuardian=?, state=?, reason=?, userPreferences=? WHERE dni=?";

        jdbcTemplate.update(sql,
                user.getBirthdayDate(),
                user.getAddress(),
                user.getLegalGuardian(),
                user.getState(),
                user.getReason(),
                user.getUserPreferences(),
                user.getDni()
        );
    }

    public OviUser getOviUser(String dni) {
        try {
            OviUser n = jdbcTemplate.queryForObject(
                    "SELECT * FROM Ovi_User WHERE dni = '" + dni + "'",
                    new OviUserRowMapper());
            return n;

        } catch (DataAccessException e) {
            return null;
        }
    }

    public List<OviUser> getOviUsers() {
        try {
            return jdbcTemplate.query("SELECT * FROM Ovi_User", new OviUserRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<OviUser>();
        }
    }
}
