package mt.wayne.joomtest.pages;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static org.junit.Assert.assertNotNull;

public class DetailsPage extends BasePage{

    public DetailsPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
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

    public void addToCart() {

        List<WebElement> attributeSelectors = driver.findElements(By.cssSelector("div[class^='attributeSelector___']"));

        //select size, color, etc, if needed
        for (WebElement attributeSelector : attributeSelectors) {
            List<WebElement> options = attributeSelector.findElements(By.cssSelector("label[class^='button___']:not([class*='disabled'])"));
            options.getFirst().click();
        }

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[descendant::span[text()='Add to cart']]")));
        List<WebElement> addToCartButtons = driver.findElements(By.xpath("//button[descendant::span[text()='Add to cart']]"));


        for (WebElement addToCartButton : addToCartButtons) {
            try {
                if (addToCartButton.isDisplayed()) {
                    addToCartButton.click();
                    break;
                }
            } catch (Exception e) {
                //ignore
            }
        }

        try {
            Thread.sleep(10000);
            //wait for add to finish
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

}
