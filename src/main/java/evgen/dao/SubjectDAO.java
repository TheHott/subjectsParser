package evgen.dao;

import java.sql.PreparedStatement;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import evgen.models.Subject;

@Component
public class SubjectDAO {
	private final JdbcTemplate jdbcTemplate;
	
	@Autowired
	public SubjectDAO (JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public List<Subject> index() {
		return jdbcTemplate.query("SELECT Subject.ID, Subject_Name.Name, Subject.ID_index, Subject_Index.Lesson_Time, \r\n"
				+ "Subject.Odd_week, Week_Day.Day FROM Subject \r\n"
				+ "LEFT JOIN Subject_Name ON Subject.ID_name = Subject_Name.ID \r\n"
				+ "LEFT JOIN Subject_Index ON Subject.ID_index = Subject_Index.ID\r\n"
				+ "LEFT JOIN Week_Day ON Subject.ID_week_day = Week_Day.ID;", new SubjectMapper());
	}
	
//	public Subject show(int id) {
//		return jdbcTemplate.query("SELECT Subject.ID, Subject_Name.Name, Subject.ID_index, \"\r\n"
//				+ "Subject_Index.Lesson_Time, "
//				+ "Subject.Odd_week, Subject.ID_week_day FROM Subject "
//				+ "LEFT JOIN Subject_Name ON Subject.ID_name = Subject_Name.ID "
//				+ "LEFT JOIN Subject_Index ON Subject.ID_index = Subject_Index.ID "
//				+ "WHERE Subject.ID=?", new Object[] {id}, 
//				new SubjectMapper()).stream().findAny().orElse(null);
//	}
	
	public void add(Subject subject) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection
					.prepareStatement("INSERT INTO Subject_Name (Name) VALUES(?)", new String[] {"ID"});
			ps.setString(1, subject.getName()); 
			return ps; }, keyHolder);
		
		jdbcTemplate.update("INSERT INTO Subject (Odd_week, ID_week_day, ID_name, ID_index) "
				+ "VALUES(?, ?, ?, ?)", subject.isWeekOdd(), subject.getWeekDayID(), 
				keyHolder.getKey().intValue(), subject.getIndex());
	}
	
//	public void update(int id, Subject updatedSubject) {
//		jdbcTemplate.update("UPDATE Subject SET name=?, age=?, mail=? WHERE id=?", 
//				updatedSubject.getName(), updatedSubject.getAge(), updatedSubject.getEmail(), id);
//	}
	
	public void delete_one(int id) {
		jdbcTemplate.update("DELETE FROM Subject_Name WHERE id=?", id);
	}
	
	public void delete_all() {
		jdbcTemplate.update("DELETE FROM Subject_Name");
	}
}
