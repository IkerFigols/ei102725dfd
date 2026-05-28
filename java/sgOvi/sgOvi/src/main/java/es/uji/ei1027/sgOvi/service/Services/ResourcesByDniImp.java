package es.uji.ei1027.sgOvi.service.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@Service
public class ResourcesByDniImp implements ResourcesByDni {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Map<String, Object>> getContractsByDni(String dni) {
        // Query que une CONTRACT -> SELECTION -> ASSISTANCE_REQUEST
        // Filtra si el DNI es el del OviUser (dueño de la solicitud)
        // o el del PapPati (asignado en la selección)
        String sql = "SELECT c.idContract, c.startDate, c.endDate, c.salary, c.document, s.idSelection " +
                "FROM Contract c " +
                "JOIN Selection s ON c.idSelection = s.idSelection " +
                "LEFT JOIN Assistance_Request ar ON s.idAsReq = ar.idAsReq " +
                "WHERE ar.idOviUser = ? OR s.idPap = ?";

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, dni, dni);
        return rows;
    }
}