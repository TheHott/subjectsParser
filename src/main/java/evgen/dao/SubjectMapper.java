package evgen.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import evgen.models.Subject;

public class SubjectMapper implements RowMapper<Subject>{
	@Override
	public Subject mapRow(ResultSet rs, int i) throws SQLException {
		Subject subject = new Subject();
		
		subject.setId(rs.getInt("ID"));
		subject.setName(rs.getString("Name"));
		subject.setWeekOdd(rs.getBoolean("Odd_week"));
		subject.setWeekDay(rs.getString("Day"));
		subject.setIndex(rs.getInt("ID_index"));
		subject.setTime(rs.getString("Lesson_Time"));
		
		return subject;
	}

}
