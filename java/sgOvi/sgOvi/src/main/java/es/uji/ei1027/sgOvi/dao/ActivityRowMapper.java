package es.uji.ei1027.sgOvi.dao;

import es.uji.ei1027.sgOvi.model.Activity;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;

public final class ActivityRowMapper implements RowMapper<Activity> {

    @Override
    public Activity mapRow(ResultSet rs, int rowNum) throws SQLException {
        Activity activity = new Activity();

        activity.setIdActivity(rs.getString("idActivity"));
        activity.setActivityType(rs.getString("activityType"));
        activity.setTittle(rs.getString("tittle"));
        activity.setDescription(rs.getString("description"));
        activity.setDate(rs.getObject("date", LocalDate.class));
        activity.setAddress(rs.getString("address"));
        activity.setCapacity(rs.getObject("capacity", Integer.class));
        activity.setSponsor(rs.getString("sponsor"));
        activity.setIdInstructor(rs.getString("idInstructor"));

        return activity;
    }
}