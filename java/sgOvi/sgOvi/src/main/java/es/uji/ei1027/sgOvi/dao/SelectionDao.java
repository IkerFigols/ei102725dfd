package es.uji.ei1027.sgOvi.dao;

import es.uji.ei1027.sgOvi.model.Assistance_Request;
import es.uji.ei1027.sgOvi.model.Selection;
import es.uji.ei1027.sgOvi.service.DTOs.AssistanceRequestSelectionDTO;
import es.uji.ei1027.sgOvi.service.DTOs.PapPatiSelectionDTO;
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
        jdbcTemplate.update("INSERT INTO Selection VALUES(?, ?, ?, ?, ?)",
                selection.getIdSelection(),
                selection.getDate(),
                selection.getState().name(),
                selection.getIdPapPati(),
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
                + "', state = '" + selection.getState().name()
                + "', idPap = '" + selection.getIdPapPati()
                + "', idAsReq = '" + selection.getIdAsReq()
                + "' WHERE idSelection = '" + selection.getIdSelection() + "'");
    }
    public void updateState(String idSelection, String state) {
        jdbcTemplate.update("UPDATE Selection SET state = ? WHERE idSelection = ?", state, idSelection);
    }

    public void rejectSelections(String idAsReq, String idPapPati){
        jdbcTemplate.update("UPDATE Selection SET    state = 'REJECTED' WHERE  idAsReq = ? AND  idPap <> ?",idAsReq,idPapPati);
    }

    /*Obtiene la seleccion especificada */
    public Selection getSelection(String idSelection) {
        try {
            Selection s = jdbcTemplate.queryForObject(
                    "SELECT * FROM Selection WHERE idSelection = '" + idSelection + "'",
                    new SelectionRowMapper());
            return s;

        } catch (DataAccessException e) {
            return null;
        }
    }

    /* Obtiene todas las selecciones de una apRequest */
    public List<Selection> getSelectionsAPRequest(String idAsReq) {
        try {
            return jdbcTemplate.query("SELECT * FROM Selection WHERE idAsReq = ?", new SelectionRowMapper(), idAsReq);
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<Selection>();
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
    //Este bloque en principio solo se usa para generar los códigos mas eficientemente
    public String getMaxId(){
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT MAX(idSelection) FROM Selection",
                    String.class
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
    //Para obtener las selection de un PapPati
    public List<Selection> getSelectionsByPapPati(String dniPap) {
        try {
            return jdbcTemplate.query(
                    "SELECT * FROM Selection WHERE idPap = ?",
                    new SelectionRowMapper(),
                    dniPap
            );
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }
    public List<PapPatiSelectionDTO> getPapSelByAP(String idAsReq, String state){
        try {
            String sql ="SELECT * FROM Selection AS s JOIN Pap_Pati AS pa ON (s.idPap = pa.dni) JOIN Person AS p USING(dni) WHERE s.idAsReq = ? ";
            if (!state.equals("ALL"))
                sql += "AND s.state = '"+ state+"' ";

            return jdbcTemplate.query(
                    sql,
                    new PersonPapPatiSelDTORowMapper(), idAsReq
            );
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }
    public List<AssistanceRequestSelectionDTO> getRequestsByPapPatiFiltered(String idPapPati, String state) {
        String sql = "SELECT s.*, ar.tittle, ar.date as request_date, p.name as ovi_name " +
                "FROM Selection s " +
                "JOIN Assistance_Request ar ON s.idAsReq = ar.idAsReq " +
                "JOIN Person p ON ar.idOviUser = p.dni " +
                "WHERE s.idPap = ?";

        List<Object> params = new ArrayList<>();
        params.add(idPapPati);

        if (state != null && !state.equals("ALL")) {
            sql += " AND s.state = ?";
            params.add(state);
        }

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            AssistanceRequestSelectionDTO dto = new AssistanceRequestSelectionDTO();
            dto.setIdSelection(rs.getString("idSelection"));
            dto.setSelectionState(rs.getString("state"));
            dto.setSelectionDate(rs.getDate("date").toLocalDate());

            Assistance_Request ar = new Assistance_Request();
            ar.setIdAsReq(rs.getString("idAsReq"));
            ar.setTittle(rs.getString("tittle"));
            ar.setDate(rs.getDate("request_date").toLocalDate());
            dto.setAssistanceRequest(ar);

            dto.setOviUserName(rs.getString("ovi_name"));
            return dto;
        }, params.toArray());
    }
    public List<Selection> getSelectionApprovedFromAp(String idAsReq) {
        try {
            return jdbcTemplate.query("SELECT * FROM Selection WHERE idAsReq =? AND state= 'APPROVED'", new SelectionRowMapper(), idAsReq);
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }
}