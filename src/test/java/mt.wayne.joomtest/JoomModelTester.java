package mt.wayne.joomtest;

import mt.wayne.joomtest.model.JoomStates;
import mt.wayne.joomtest.pages.CartPage;
import mt.wayne.joomtest.pages.CataloguePage;
import mt.wayne.joomtest.pages.DetailsPage;
import mt.wayne.joomtest.pages.HomePage;
import nz.ac.waikato.modeljunit.*;
import nz.ac.waikato.modeljunit.coverage.ActionCoverage;
import nz.ac.waikato.modeljunit.coverage.StateCoverage;
import nz.ac.waikato.modeljunit.coverage.TransitionCoverage;
import nz.ac.waikato.modeljunit.coverage.TransitionPairCoverage;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class JoomModelTester implements FsmModel {

    //system under test
    WebDriver driver;
    WebDriverWait wait;
    HomePage homePage;
    CataloguePage cataloguePage;
    DetailsPage detailsPage;
    CartPage cartPage;

    JoomStates state = JoomStates.HOME;

    boolean loggedIn = false;
    boolean itemsInCart = false;

    @Override
    public Object getState() {
        System.out.println(itemsInCart);
        return state;
    }

    @Override
    public void reset(boolean testing) {
        state = JoomStates.HOME;
        loggedIn = false;
        itemsInCart = false;


        if (testing){
            if (driver != null)
                driver.quit();
            ChromeOptions options = new ChromeOptions();
            options.setBinary("D:\\selenium\\chrome-win64-118.0.5993.70\\chrome.exe");
            driver = new ChromeDriver(options);
            driver.manage().window().maximize();
            wait = new WebDriverWait(driver, Duration.ofSeconds(3));

            homePage = new HomePage(driver,wait);
            cataloguePage = new CataloguePage(driver,wait);
            detailsPage = new DetailsPage(driver,wait);
            cartPage = new CartPage(driver,wait);

            homePage.load();

            cartPage.goToCart();
            if(cartPage.isCartNotEmpty())
                cartPage.clearCart();

            homePage.goHome();
        }
    }

    public boolean chooseCategoryGuard(){
        return state == JoomStates.HOME;
    }

    @Action
    public void chooseCategory(){
        homePage.clickOnCategoryByName("Kids");
        state = JoomStates.CATALOGUE;
        cataloguePage.verifyCategoryHeader("Kids");
        cataloguePage.verifyProductCount(10);
    }

    public boolean searchGuard(){
        return state == JoomStates.HOME;
    }

    @Action
    public void search(){
        homePage.search("phone");
        state = JoomStates.CATALOGUE;
        cataloguePage.verifyProductCount(10);
        cataloguePage.verifySearchQuery("phone");
    }

    public boolean goHomeGuard(){
        return true;
    }

    @Action
    public void goHome(){
        homePage.goHome();
        state = JoomStates.HOME;
    }

    public boolean clickProductGuard(){
        return state == JoomStates.CATALOGUE;
    }

    @Action
    public void clickProduct(){
        cataloguePage.clickFirstProductAndSwitchTab();
        state = JoomStates.PRODUCT;
        detailsPage.verifyRequiredElements();
    }

    public boolean addToCartGuard(){
        return state == JoomStates.PRODUCT;
    }

    @Action
    public void addToCart(){
        detailsPage.addToCart();
        itemsInCart = true;
    }

    public boolean goToCartGuard(){
        return true;
    }

    @Action
    public void goToCart(){
        cartPage.goToCart();
        state = JoomStates.CART;
        if (itemsInCart) {
            assertTrue(cartPage.isCartNotEmpty());
            assertFalse(cartPage.isCartEmpty());
        } else {
            assertFalse(cartPage.isCartNotEmpty());
            assertTrue(cartPage.isCartEmpty());
        }
    }

    public boolean clearCartGuard(){
        return state == JoomStates.CART && itemsInCart;
    }

    @Action
    public void clearCart(){
        cartPage.clearCart();
        itemsInCart = false;
    }

    @Test
    public void JoomModelRunnerGreedy(){
        final GreedyTester tester = new GreedyTester(new JoomModelTester());
        tester.setRandom(new Random(42));

        tester.buildGraph();
        tester.addListener(new StopOnFailureListener());
        tester.addListener("verbose");
        tester.addListener(new AbstractListener() {
            @Override
            public String getName() {
                return "wait listener";
            }

            @Override
            public void doneTransition(int action, Transition tr) {
                try {
                    Thread.sleep(1000);
                    System.out.println("waited 1 second");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        tester.addCoverageMetric(new TransitionPairCoverage());
        tester.addCoverageMetric(new StateCoverage());
        tester.addCoverageMetric(new ActionCoverage());

        tester.generate(500);

        tester.printCoverage();
    }

    @Test
    public void JoomModelRunnerRandom(){
        final RandomTester tester = new RandomTester(new JoomModelTester());
        tester.setRandom(new Random(42));

        tester.buildGraph();
        tester.addListener(new StopOnFailureListener());
        tester.addListener("verbose");
        tester.addListener(new AbstractListener() {
            @Override
            public String getName() {
                return "wait listener";
            }

            @Override
            public void doneTransition(int action, Transition tr) {
                try {
                    Thread.sleep(1000);
                    System.out.println("waited 1 second");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        tester.addCoverageMetric(new TransitionPairCoverage());
        tester.addCoverageMetric(new StateCoverage());
        tester.addCoverageMetric(new ActionCoverage());

        tester.generate(500);

        tester.printCoverage();
    }


}
