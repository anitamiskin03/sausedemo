package Tests;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


public class ExcelLoginTestCase {


    static WebDriver driver;

    @BeforeClass
    public void setup() {
        //System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        System.out.println("Test execution started.");
    }

    public void log(String message) {
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        System.out.println(timeStamp + " - " + message);
    }

    public void openLoginPage() {
        driver.get("https://www.saucedemo.com/");
        log("Opened SauceDemo login page.");
    }

    public void login(String username, String password) {
        try {
            WebElement userField = driver.findElement(By.id("user-name"));
            WebElement passField = driver.findElement(By.id("password"));
            WebElement loginButton = driver.findElement(By.id("login-button"));

            userField.sendKeys(username);
            passField.sendKeys(password);
            loginButton.click();
            log("Attempted login with username: " + username);
        } catch (NoSuchElementException e) {
            log("Error during login: " + e.getMessage());
        }
    }

    public void verifyLogin(String testName) throws IOException {
        try {
            Thread.sleep(2000);
            if (driver.getCurrentUrl().contains("inventory.html")) {
                log(testName + " - Login Successful.");
            } else {
                takeScreenshot(testName);
                log(testName + " - Login Failed.");
            }
        } catch (InterruptedException e) {
            log("Interrupted Exception: " + e.getMessage());
        }
    }

    public void takeScreenshot(String testName) throws IOException {
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

        String filePath = "screenshots/" + testName + "_" + timeStamp + ".png";
        Files.createDirectories(Paths.get("screenshots"));
        Files.copy(srcFile.toPath(), Paths.get(filePath));
        log("Screenshot saved: " + filePath);
    }

    public List<String[]> readExcelData(String filePath) throws IOException {
        List<String[]> records = new ArrayList<>();
        FileInputStream fis = new FileInputStream(new File(filePath));
        Workbook workbook = new XSSFWorkbook(fis);
        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rowIterator = sheet.iterator();
        rowIterator.next(); // Skip header row

        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            String username = row.getCell(0).getStringCellValue();
            String password = row.getCell(1).getStringCellValue();
            boolean isSuccessExpected = row.getCell(2).getBooleanCellValue();
            records.add(new String[]{username, password, String.valueOf(isSuccessExpected)});
        }
        workbook.close();
        fis.close();
        return records;
    }

    @Test
    public void testLoginFromExcel() throws IOException {
        List<String[]> testData = readExcelData("C:\\A M\\testdata.xlsx");
        for (String[] data : testData) {
            String username = data[0];
            String password = data[1];
            boolean isSuccessExpected = Boolean.parseBoolean(data[2]);

            String testName = "testLogin_" + username.replaceAll("[\\s\\W]+", "_");
            openLoginPage();
            login(username, password);
            if (isSuccessExpected) {
                verifyLogin(testName);
            } else {
                takeScreenshot(testName);
                log(testName + " - Login expected to fail.");
            }
        }
    }

    @AfterClass
    public void tearDown() {
        log("Test execution completed.");
        driver.quit();
    }
}




