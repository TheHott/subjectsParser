package twentytwentyone.ParserConsole;

import java.io.IOException;
import java.net.MalformedURLException;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class App {
    public static void main( String[] args ) throws MalformedURLException, IOException {
    	Parser parser = new Parser();
    	String url = "https://www.samgups.ru/raspisanie/2020-2021/vtoroy-semestr/HTML/73.html";
    	
    	Document page = parser.getPage(url);
    	
    	Elements tables = parser.getTables(page);
    	Elements tableCols = parser.getColumns(tables);
    	Elements tableRows = parser.getRows(tableCols);    
    	Elements subjects = parser.getSubjects(tableRows);
    	
    	// parser.readSubjects(subjects);
    	parser.writeSubjsIntoFile(subjects);
    }
}
