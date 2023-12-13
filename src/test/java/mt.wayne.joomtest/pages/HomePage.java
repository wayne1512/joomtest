package mt.wayne.joomtest.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {
    WebDriver driver;
    WebDriverWait wait;

    public HomePage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public void load(){
        driver.get("https://joom.com");

        //close cookies popup
        WebElement acceptButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[descendant::span[text()='Accept all']]")));
        if (acceptButton != null)
            acceptButton.click();


        WebElement closeButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("button[class^='close___']")));
        if (closeButton != null)
            closeButton.click();
    }

    public void clickOnCategoryByName(String categoryName){
        WebElement allCategoriesButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[class^='allCategoriesButton___']")));
        allCategoriesButton.click();
        WebElement categoryButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a//span[text()='"+categoryName+"']")));
        categoryButton.click();
    }


    public void search(String query){
        WebElement searchBox = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[placeholder='What are you looking for?']")));
        searchBox.sendKeys(query);
        searchBox.sendKeys(Keys.ENTER);
    }


}
