package evgen.parser;

import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import evgen.models.Subject;

public class Parser {
	private final static int TIMEOUT = 14000;
	private final static Map<Integer, String> weekDays = new HashMap<Integer, String>();

	static {
		weekDays.put(1, "Понедельник");
		weekDays.put(2, "Вторник");
		weekDays.put(3, "Среда");
		weekDays.put(4, "Четверг");
		weekDays.put(5, "Пятница");
		weekDays.put(6, "Суббота");
		weekDays.put(7, "Воскресенье"); // не используется
	}
	
	public Document getPage(String url) throws MalformedURLException, IOException {
		Document page = Jsoup.parse(new URL(url), TIMEOUT);
		
		return page;
	}
	
	// TODO doesn't work
	public Element getGroupName(Document page) {
		// Pattern pattern = Pattern.compile("дИ", Pattern.UNICODE_CHARACTER_CLASS);
		
		Element name = page.select("font[size=6]").first();
		
		// Matcher matcher = pattern.matcher(name.text());
		
		// System.out.println(name.text());
		
		return name;
	}
	
	public Elements getTables(Document page) {
		Elements tables = page.select("table[width=801]");
		
		return tables;
	}
	
	public Elements getColumns(Elements tables) {
		Elements tableCols = tables.select("tr");
//		System.out.println("Очередная column: " + tableCols);
		return tableCols;
	}
	
	public Elements getRows(Elements tableCols) {
		Elements tableRows = tableCols.select("td");
//		System.out.println("Rows: " + tableRows);
		return tableRows;
	}
	
	public Elements getSubjects(Elements tableRows) {
		Elements subjs = tableRows.select("td>p>font[size=1]");
		
		return subjs;
	}
	
	public void readSubjects(Elements subjs) {
		int subjNum = 1;
		int day = 1;
		
		for (Element subj : subjs) {
			System.out.println("День недели №" + day + ", " + subjNum + "-я пара: " + subj.text());
			subjNum++;
			if(subjNum==8) {
				subjNum=1;
				day++;
				if(day==7)
					day=1;
			}
		}
	}
	
	public ArrayList<Subject> setSubjectsIntoClass(String url) throws MalformedURLException, IOException { // TODO
		Document page = getPage(url);
    	
    	Elements tables = getTables(page);
    	Elements tableCols = getColumns(tables);
    	Elements tableRows = getRows(tableCols);    
    	Elements subjs = getSubjects(tableRows);
    	
    	ArrayList<Subject> subjectList = new ArrayList<>();
		int subjNum = 1;
		int day = 1;
		boolean weekOdd = true; // неделя нечетная?
		
		Subject subject;
		
		for (Element subj : subjs) {
			subject = new Subject();
			
			subject.setIndex(subjNum);
			subject.setName(subj.text());
			subject.setWeekDay(weekDays.get(day));
			subject.setWeekOdd(weekOdd);
			
			subjectList.add(subject);
			
//			System.out.println("День недели №" + day + ", " + subjNum + "-я пара: " + subj.text());
			
			subjNum++;
			
			if(subjNum==8) {
				subjNum=1;
				day++;
				if(day==7){
					day=1;
					weekOdd=!weekOdd;
				}
			}
		}
		
		return subjectList;
	}
	
	public void writeSubjsIntoFile(Elements subjs) {
		int subjNum = 1;
		int day = 1;
		boolean weekIsOdd = true; // неделя нечетная?
		
		String fileName = "Subjects.txt";
		String filePath = System.getProperty("user.dir");
		
		try(FileWriter writer = new FileWriter(fileName, false)) {
			for (Element subj : subjs) {
				if(weekIsOdd)
					writer.write("Нечётная неделя, ");
				else
					writer.write("Чётная неделя, ");
				writer.write("День недели №" + day + ", " + subjNum + "-я пара: " + subj.text() + "\n");
				subjNum++;
				if(subjNum==8) {
					subjNum=1;
					day++;
					if(day==7) {
						day=1;
						weekIsOdd=!weekIsOdd;
					}
				}
			}
			System.out.println("Список записан в файл по пути " + filePath + "\\" + fileName );
		}
		catch(IOException ex){ // проверка ошибок
            System.out.println(ex.getMessage());
        } 
	}
	
	
}
