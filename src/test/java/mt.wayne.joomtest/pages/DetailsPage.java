package mt.wayne.joomtest.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.Assert.assertNotNull;

public class DetailsPage {
    WebDriver driver;
    WebDriverWait wait;

    public DetailsPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public void verifyRequiredElements(){
        //verify that some of these required elements are present on the details page

        WebElement name = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div[class^='nameRow___']")));
        assertNotNull(name);
        WebElement price = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div[class^='price___']")));
        assertNotNull(price);
        WebElement buyNow = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[descendant::span[text()='Buy now']]")));
        assertNotNull(buyNow);
        WebElement addToCart = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[descendant::span[text()='Add to cart']]")));
        assertNotNull(addToCart);
    }
}
