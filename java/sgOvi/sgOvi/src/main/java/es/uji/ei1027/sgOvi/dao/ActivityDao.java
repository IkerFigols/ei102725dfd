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
                "INSERT INTO Activity VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)",
                activity.getIdActivity(),
                activity.getType().name(),
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
                activity.getType().name(),
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

    //Obtiene una lista de las proximas actividades
    public List<Activity> getRecentActivities(){
        try{
            return jdbcTemplate.query("SELECT * FROM Activity ORDER BY date DESC FETCH NEXT 10 ROWS ONLY", new ActivityRowMapper());
        }
        catch (EmptyResultDataAccessException e){
            return new ArrayList<>();
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

    //Este bloque en principio solo se usa para generar los códigos mas eficientemente
    public String getMaxId(){
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT MAX(idActivity) FROM Activity",
                    String.class
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
    public List<Activity> getUserActivities(String dni) {
        try {
            return jdbcTemplate.query(
                    "SELECT a.* FROM Activity a " +
                            "JOIN Attendance att ON a.idActivity = att.idActivity " +
                            "WHERE att.idOviUser = ? OR att.idPapPati = ? " +
                            "ORDER BY a.date DESC",
                    new ActivityRowMapper(),
                    dni, dni
            );
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }
}