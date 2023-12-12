package mt.wayne.joomtest;

import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class JoomSteps {

    WebDriver driver;
    WebDriverWait wait;


    @Given("I am a user of the website")
    public void iAmAUserOfTheWebsite() {
        System.setProperty("webdriver.chrome.driver","D:\\selenium\\chromedriver-win64-118.0.5993.70\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.setBinary("D:\\selenium\\chrome-win64-118.0.5993.70\\chrome.exe");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    }

    @When("I visit the shopping website")
    public void iVisitTheShoppingWebsite() {
        driver.get("https://joom.com");

        //close cookies popup
        WebElement acceptButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[descendant::span[text()='Accept all']]")));
        if (acceptButton != null)
            acceptButton.click();


        WebElement closeButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("button[class^='close___']")));
        if (closeButton != null)
            closeButton.click();


    }


    @And("I click on the {string} category")
    public void iClickOnTheCategoryNameCategory(String categoryName) {
        WebElement allCategoriesButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[class^='allCategoriesButton___']")));
        allCategoriesButton.click();
        WebElement categoryButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a//span[text()='"+categoryName+"']")));
        categoryButton.click();
    }

    @When("I click on the first product in the results")
    public void iClickOnTheFirstProductInTheResults() {
        List<WebElement> products = driver.findElements(By.cssSelector("div[class^='product___']"));
        products.getFirst().click();
        Set<String> allWindowHandles = driver.getWindowHandles();

        // Loop through each handle
        for (String handle : allWindowHandles) {
            // Switch to the new tab
            if (!handle.equals(driver.getWindowHandle())) {
                driver.switchTo().window(handle);
            }
        }
    }

    @Then("I should be taken to {string} category")
    public void iShouldBeTakenToCategoryNameCategory(String categoryName) {
        WebElement categoryHeader = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("h1[class^='header___']")));
        String text = categoryHeader.getText();
        assertEquals(categoryName,text);
    }

    @And("the category should show at least {int} products")
    public void theCategoryShouldShowAtLeastNumProductsProducts(int count) {
        List<WebElement> products = driver.findElements(By.cssSelector("div[class^='product___']"));
        assertTrue(products.size() >= count);
    }

    @Then("I should be taken to the details page for that product")
    public void iShouldBeTakenToTheDetailsPageForThatProduct() {
        //name
        WebElement name = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div[class^='nameRow___']")));
        assertNotNull(name);
        WebElement price = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div[class^='price___']")));
        assertNotNull(price);
        WebElement buyNow = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[descendant::span[text()='Buy now']]")));
        assertNotNull(buyNow);
        WebElement addToCart = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[descendant::span[text()='Add to cart']]")));
        assertNotNull(addToCart);



    }

    @After
    public void closeDriver(){
        driver.quit();
    }

    @When("I search for a product using the term {string}")
    public void iSearchForAProductUsingTheTerm(String search) {
        WebElement searchBox = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[placeholder='What are you looking for?']")));
        searchBox.sendKeys(search);
        searchBox.sendKeys(Keys.ENTER);
    }

    @Then("I should see the search results for {string}")
    public void iShouldSeeTheSearchResultsFor(String search) {
        WebElement searchBox = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[placeholder='What are you looking for?']")));
        String text = searchBox.getAttribute("value");
        assertEquals(search, text);
    }

    @And("there should be at least {int} products in the search results")
    public void thereShouldBeAtLeastProductsInTheSearchResults(int count) {
        List<WebElement> products = driver.findElements(By.cssSelector("div[class^='product___']"));
        assertTrue(products.size() >= count);
    }
}
