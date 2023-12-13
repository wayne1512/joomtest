package mt.wayne.joomtest;

import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import mt.wayne.joomtest.pages.CataloguePage;
import mt.wayne.joomtest.pages.DetailsPage;
import mt.wayne.joomtest.pages.HomePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class JoomSteps {

    WebDriver driver;
    WebDriverWait wait;

    HomePage homePage;
    CataloguePage cataloguePage;
    DetailsPage detailsPage;


    @Given("I am a user of the website")
    public void iAmAUserOfTheWebsite() {
        System.setProperty("webdriver.chrome.driver","D:\\selenium\\chromedriver-win64-118.0.5993.70\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.setBinary("D:\\selenium\\chrome-win64-118.0.5993.70\\chrome.exe");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        homePage = new HomePage(driver,wait);
        cataloguePage = new CataloguePage(driver,wait);
        detailsPage = new DetailsPage(driver,wait);
    }

    @When("I visit the shopping website")
    public void iVisitTheShoppingWebsite() {
        homePage.load();
    }

    @And("I click on the {string} category")
    public void iClickOnTheCategoryNameCategory(String categoryName) {
        homePage.clickOnCategoryByName(categoryName);
    }

    @When("I search for a product using the term {string}")
    public void iSearchForAProductUsingTheTerm(String search) {
        homePage.search(search);
    }

    @When("I click on the first product in the results")
    public void iClickOnTheFirstProductInTheResults() {
        cataloguePage.clickFirstProductAndSwitchTab();
    }

    @Then("I should be taken to the details page for that product")
    public void iShouldBeTakenToTheDetailsPageForThatProduct() {
        detailsPage.verifyRequiredElements();
    }

    @Then("I should be taken to {string} category")
    public void iShouldBeTakenToCategoryNameCategory(String categoryName) {
        cataloguePage.verifyCategoryHeader(categoryName);
    }

    @And("the category should show at least {int} products")
    public void theCategoryShouldShowAtLeastNumProductsProducts(int count) {
        cataloguePage.verifyProductCount(count);
    }


    @Then("I should see the search results for {string}")
    public void iShouldSeeTheSearchResultsFor(String search) {
        cataloguePage.verifySearchQuery(search);
    }

    @And("there should be at least {int} products in the search results")
    public void thereShouldBeAtLeastProductsInTheSearchResults(int count) {
        cataloguePage.verifyProductCount(count);
    }

    @After
    public void closeDriver(){
        driver.quit();
    }
}
