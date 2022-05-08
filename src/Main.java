package Main;

import java.io.IOException;
import java.util.Iterator;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Main {

	public static void main(String[] args) throws IOException, InterruptedException {
		Data csvdata = new Data();
		Crawling dataCrawling = new Crawling();
		csvdata.ReadData();
		csvdata.WriteMergeData(csvdata.fullData);
		csvdata.WriteFinalData(dataCrawling.CrawlingData(csvdata));
	}

}
