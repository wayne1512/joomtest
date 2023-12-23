package mt.wayne.joomtest.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage extends BasePage {

    public HomePage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public void load(){
        driver.get("https://joom.com");


        closePopupIfNeeded();
    }

    public void clickOnCategoryByName(String categoryName){
        WebElement allCategoriesButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[class^='allCategoriesButton___']")));
        allCategoriesButton.click();
        WebElement categoryButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a//span[text()='"+categoryName+"']")));
        categoryButton.click();
        closePopupIfNeeded();
    }


    public void search(String query){
        WebElement searchBox = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[placeholder='What are you looking for?'], input[placeholder='Ho ho ho, merry searching']")));
        searchBox.sendKeys(query);
        searchBox.sendKeys(Keys.ENTER);
        closePopupIfNeeded();
    }

    //this can be called from any page
    public void goHome() {
        WebElement homeButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("a[class^='logo___']")));
        homeButton.click();
        closePopupIfNeeded();
    }
}
