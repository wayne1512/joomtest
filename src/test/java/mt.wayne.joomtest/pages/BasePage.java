package mt.wayne.joomtest.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public abstract class BasePage{
    WebDriver driver;
    WebDriverWait wait;

    public BasePage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public void closePopupIfNeeded() {

        //close cookies popup
        try{
            WebElement acceptButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[descendant::span[text()='Accept all']]")));
            if (acceptButton != null)
                acceptButton.click();
        } catch (Exception e) {
            //ignore since no popup was found
        }

        try {
            WebElement closeButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("button[class^='close___']")));
            if (closeButton != null)
                closeButton.click();
        } catch (Exception e) {
            //ignore since no popup was found
        }
    }

    public void goToCart() {
        WebElement cartButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[contains(., 'Shopping cart')]")));
        cartButton.click();
        closePopupIfNeeded();
    }
}
