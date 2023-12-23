package mt.wayne.joomtest.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CartPage extends BasePage{
    public CartPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public boolean isCartEmpty() {

        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div[class^=empty___]")));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isCartNotEmpty() {

        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div[class^=item___]")));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void clearCart() {
        WebElement removeAllElement = driver.findElement(By.xpath("//button[contains(.//text(), 'Remove all selected')]"));
        removeAllElement.click();

        WebElement confirm = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[.//text()= 'Remove']")));
        confirm.click();

        try{
            Thread.sleep(10000);
            //wait to ensure that cart is cleared
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
