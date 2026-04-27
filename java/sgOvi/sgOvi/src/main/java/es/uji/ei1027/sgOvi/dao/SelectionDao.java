package es.uji.ei1027.sgOvi.dao;

import es.uji.ei1027.sgOvi.model.Selection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SelectionDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /* añade la seleccion */
    public void addSelection(Selection selection) {
        jdbcTemplate.update("INSERT INTO Selection VALUES(?, ?, ?, ?, ?, ?)",
                selection.getIdSelection(),
                selection.getDate(),
                selection.getState(),
                selection.getIdCommunication(),
                selection.getIdPap(),
                selection.getIdAsReq()
        );
    }

    /* Borra la selección */
    public void deleteSelection(String idSelection) {
        jdbcTemplate.update("DELETE FROM Selection WHERE idSelection = '" + idSelection + "'");
    }

    /* Actualzia la seleccion */
    public void updateSelection(Selection selection) {
        jdbcTemplate.update("UPDATE Selection SET date = '" + selection.getDate()
                + "', state = '" + selection.getState()
                + "', idCommunication = '" + selection.getIdCommunication()
                + "', idPap = '" + selection.getIdPap()
                + "', idAsReq = '" + selection.getIdAsReq()
                + "' WHERE idSelection = '" + selection.getIdSelection() + "'");
    }

    /*Obtiene la seleccion especificada */
    public Selection getSelection(String idSelection) {
        try {
            Selection s = jdbcTemplate.queryForObject(
                    "SELECT * FROM Selection WHERE idSelection = '" + idSelection + "'",
                    new SelectionRowMapper());
            return s;

        } catch (DataAccessException e) {
            return new Selection();
        }
    }

    /* Obtiene todas las selecciones */
    public List<Selection> getSelections() {
        try {
            return jdbcTemplate.query("SELECT * FROM Selection", new SelectionRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<Selection>();
        }
    }
}