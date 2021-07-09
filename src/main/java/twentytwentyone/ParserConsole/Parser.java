package twentytwentyone.ParserConsole;

import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Parser {
	private final static int TIMEOUT = 7000;
	
	public Document getPage(String url) throws MalformedURLException, IOException {
		Document page = Jsoup.parse(new URL(url), TIMEOUT);
		
		return page;
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
