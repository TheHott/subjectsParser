package evgen.models;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class Subject {
	
//	private final static int MONDAY = 1; // понедельник
//	private final static int TUESDAY = 2; // вторник
//	private final static int WEDNESDAY = 3; // среда
//	private final static int THURSDAY = 4; // четверг
//	private final static int FRIDAY = 5; // пятница
//	private final static int SATURDAY = 6; // суббота
//	private final static int SUNDAY = 7; // воскресенье, не используется
	
	@Min(value = 1, message = "Id cannot be less than 1")
	private int id;
	
	@Size(max = 150, message = "Name can't be over 150 characters")
	private String name;
	
	private boolean weekOdd = true;
	
	@NotEmpty
//	@Size(min = 1, max = 7, message = "Week day must be between 1 and 7")
	private String weekDay;
	
	@NotEmpty
	@Size(min = 1, max = 7, message = "Subject's index must be between 1 and 7")
	private int index;

	private String time;
	
	
	public Subject() {}

	public Subject(@Min(value = 1, message = "Id cannot be less than 1") int id,
			@Size(max = 150, message = "Name can't be over 150 characters") String name, boolean weekOdd,
			@NotEmpty String weekDay,
			@NotEmpty @Size(min = 1, max = 7, message = "Subject's index must be between 1 and 7") int index,
			String time) {
		super();
		this.id = id;
		this.name = name;
		this.weekOdd = weekOdd;
		this.weekDay = weekDay;
		this.index = index;
		this.time = time;
	}
	

	public boolean isWeekOdd() {
		return weekOdd;
	}

	public void setWeekOdd(boolean weekOdd) {
		this.weekOdd = weekOdd;
	}

	public String getWeekDay() {
		return weekDay;
	}
	
	public int getWeekDayID() {
		switch(weekDay) {
		case "Понедельник":
			return 1;
		case "Вторник":
			return 2;
		case "Среда":
			return 3;
		case "Четверг":
			return 4;
		case "Пятница":
			return 5;
		case "Суббота":
			return 6;
		case "Воскресенье":
			return 7;
		default:
			return 0;
		}
	}


	public void setWeekDay(String weekDay) {
		this.weekDay = weekDay;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
