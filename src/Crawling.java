package Main;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Crawling {

	public static final String WEB_DRIVER_ID = "webdriver.chrome.driver";
	public static final String WEB_DRIVER_PATH = "C:\\Users\\user\\eclipse-workspace\\StarBucksProject\\chromedriver\\chromedriver.exe";
	public int time = 0;
	public String distance = "";
	public List<String> mData = new ArrayList<>();

	public List<String> CrawlingData(Data a) throws InterruptedException {
		System.out.println("Check the travel time and distance to nearby Starbucks.");
		String[] tempArray = a.fullData.toArray(new String[a.fullData.size()]);
		String checkdata = "";
		System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);
		ChromeOptions chromeOptions = new ChromeOptions();
	//	chromeOptions.addArguments("--headless");
	//	chromeOptions.addArguments("--no-sandbox");
		WebDriver driver = new ChromeDriver(chromeOptions);
		driver.get("https://map.naver.com/v5/directions/-/-/-/walk?c=14149994.2444581,4493108.5003507,14,0,0,0,dh");
		sleep(2000);
		for (int i = 0; i < a.listAddress.size(); i++) {
			System.out.println("data crawling ...( " + (i + 1) + "/" + a.listAddress.size() + " )");
			if (i > 0 && a.listAddress.get(i).equals(a.listAddress.get(i - 1))) {
				tempArray[i] = tempArray[i] + "," + Integer.toString(time) + "," + distance;
				System.out.println(tempArray[i]);
				mData.add(tempArray[i]);
			} else {
					driver.findElement(By.xpath(
							"/html/body/app/layout/div[3]/div[2]/shrinkable-layout/div/directions-layout/directions-result/div[1]/div[1]/directions-search/div[1]/directions-search-box[1]/div/div/div[1]/input"))
							.click();
					driver.findElement(By.xpath(
							"/html/body/app/layout/div[3]/div[2]/shrinkable-layout/div/directions-layout/directions-result/div[1]/div[1]/directions-search/div[1]/directions-search-box[1]/div/div/div[1]/input"))
							.sendKeys(a.listAddress.get(i) + "\n");
					sleep(1500);
					
					driver.findElement(By.xpath(
							"/html/body/app/layout/div[3]/div[2]/shrinkable-layout/div/directions-layout/directions-result/div[1]/div[1]/directions-search/div[1]/directions-search-box[2]/div/div/div[1]/input"))
							.click();
					driver.findElement(By.xpath(
							"/html/body/app/layout/div[3]/div[2]/shrinkable-layout/div/directions-layout/directions-result/div[1]/div[1]/directions-search/div[1]/directions-search-box[2]/div/div/div[1]/input"))
							.sendKeys("스타벅스\n");
					sleep(1500);

				driver.findElement(By.xpath(
						"/html/body/app/layout/div[3]/div[2]/shrinkable-layout/div/directions-layout/directions-result/div[1]/div/directions-search/div[2]/button[3]"))
						.click();
				sleep(1500);
				try {
					String time1 = driver.findElement(By.xpath(
							"/html/body/app/layout/div[3]/div[2]/shrinkable-layout/div/directions-layout/directions-result/div[1]/directions-summary-list/directions-hover-scroll/div/ul/li/directions-summary-item-walking/div[1]/strong/readable-duration/span[1]"))
							.getText();
					String unit1 = driver.findElement(By.xpath(
							"/html/body/app/layout/div[3]/div[2]/shrinkable-layout/div/directions-layout/directions-result/div[1]/directions-summary-list/directions-hover-scroll/div/ul/li/directions-summary-item-walking/div[1]/strong/readable-duration/span[2]"))
							.getText();
					if (unit1.equals("시간")) {
						String time2 = driver.findElement(By.xpath(
								"/html/body/app/layout/div[3]/div[2]/shrinkable-layout/div/directions-layout/directions-result/div[1]/directions-summary-list/directions-hover-scroll/div/ul/li/directions-summary-item-walking/div[1]/strong/readable-duration/span[3]"))
								.getText();
						String unit2 = driver.findElement(By.xpath(
								"/html/body/app/layout/div[3]/div[2]/shrinkable-layout/div/directions-layout/directions-result/div[1]/directions-summary-list/directions-hover-scroll/div/ul/li/directions-summary-item-walking/div[1]/strong/readable-duration/span[4]"))
								.getText();
						time = Integer.parseInt(time1) * 60 + Integer.parseInt(time2);
					} else {
						time = Integer.parseInt(time1);
					}
					distance = driver.findElement(By.xpath(
							"/html/body/app/layout/div[3]/div[2]/shrinkable-layout/div/directions-layout/directions-result/div[1]/directions-summary-list/directions-hover-scroll/div/ul/li/directions-summary-item-walking/div[1]/span[2]/readable-distance"))
							.getText();
					if (distance.contains("km")) {
						distance = distance.substring(0, distance.length() - 2);
						int tempDistance = (int) (Double.parseDouble(distance) * 1000);
						distance = Integer.toString(tempDistance);
					} else {
						distance = distance.substring(0, distance.length() - 1);
					}
					tempArray[i] = tempArray[i] + "," + Integer.toString(time) + "," + distance;
					System.out.println(tempArray[i]);
					mData.add(tempArray[i]);
					sleep(1500);	
				} catch(org.openqa.selenium.NoSuchElementException e) {
					System.out.println("시간, 거리 정보 없음.");
					continue;
				}
			}

		}
		System.out.println("Travel distance and time have been checked.");
		return mData;
	}

	public void sleep(long num) {
		try {
			Thread.sleep(num);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
