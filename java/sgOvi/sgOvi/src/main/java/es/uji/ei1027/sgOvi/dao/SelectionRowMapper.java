package es.uji.ei1027.sgOvi.dao;

import es.uji.ei1027.sgOvi.model.Selection;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class SelectionRowMapper implements RowMapper<Selection> {
    public Selection mapRow(ResultSet rs, int rowNum) throws SQLException {
        Selection selection = new Selection();
        selection.setIdSelection(rs.getString("idSelection"));
        selection.setDate(rs.getObject("date", java.time.LocalDate.class));
        selection.setState(rs.getString("state"));
        selection.setIdCommunication(rs.getString("idCommunication"));
        selection.setIdPap(rs.getString("idPap"));
        selection.setIdAsReq(rs.getString("idAsReq"));
        return selection;
    }
}