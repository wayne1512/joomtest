package mt.wayne.joomtest.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CataloguePage extends BasePage{

    public CataloguePage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public void clickFirstProductAndSwitchTab(){
        List<WebElement> products = driver.findElements(By.cssSelector("div[class^='product___']"));
        products.getFirst().click();


        String currentTab = driver.getWindowHandle();

        //close current tab
        driver.close();

        Set<String> allWindowHandles = driver.getWindowHandles();


        // Loop through each handle
        for (String handle : allWindowHandles) {
            // Switch to the new tab
            if (!handle.equals(currentTab)) {
                driver.switchTo().window(handle);
            }
        }
    }

    public void verifyCategoryHeader(String categoryName) {
        WebElement categoryHeader = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("h1[class^='header___']")));
        String text = categoryHeader.getText();
        assertEquals(categoryName, text);
    }

    public void verifyProductCount(int count) {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div[class^='product___']")));
        List<WebElement> products = driver.findElements(By.cssSelector("div[class^='product___']"));
        assertTrue(products.size() >= count);
    }

    public void verifySearchQuery(String search) {
        WebElement searchBox = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[placeholder='What are you looking for?'], input[placeholder='Ho ho ho, merry searching']")));
        String text = searchBox.getAttribute("value");
        assertEquals(search, text);
    }

}
