package es.uji.ei1027.sgOvi.dao;

import es.uji.ei1027.sgOvi.model.Activity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ActivityDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /* Afegeix una nova activitat a la base de dades */
    public void addActivity(Activity activity) {
        jdbcTemplate.update(
                "INSERT INTO Activity (idActivity, activityType, tittle, description, date, address, capacity, sponsor, idInstructor) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)",
                activity.getIdActivity(),
                activity.getActivityType(),
                activity.getTittle(),
                activity.getDescription(),
                activity.getDate(),
                activity.getAddress(),
                activity.getCapacity(),
                activity.getSponsor(),
                activity.getIdInstructor()
        );
    }

    /* Actualitza els atributs d'una activitat existent */
    public void updateActivity(Activity activity) {
        jdbcTemplate.update(
                "UPDATE Activity SET activityType=?, tittle=?, description=?, date=?, address=?, capacity=?, sponsor=?, idInstructor=? WHERE idActivity=?",
                activity.getActivityType(),
                activity.getTittle(),
                activity.getDescription(),
                activity.getDate(),
                activity.getAddress(),
                activity.getCapacity(),
                activity.getSponsor(),
                activity.getIdInstructor(),
                activity.getIdActivity() // El ID va al final para coincidir con el WHERE
        );
    }

    /* Esborra una activitat de la base de dades */
    public void deleteActivity(String idActivity) {
        jdbcTemplate.update("DELETE FROM Activity WHERE idActivity=?", idActivity);
    }

    /* Obté l'activitat amb l'ID donat. Torna null si no existeix. */
    public Activity getActivity(String idActivity) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM Activity WHERE idActivity=?",
                    new ActivityRowMapper(),
                    idActivity
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    /* Obté totes les activitats. Torna una llista buida si no n'hi ha cap. */
    public List<Activity> getActivities() {
        try {
            return jdbcTemplate.query(
                    "SELECT * FROM Activity",
                    new ActivityRowMapper()
            );
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }
}
