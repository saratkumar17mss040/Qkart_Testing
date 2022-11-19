package QKART_SANITY_LOGIN.Module1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Home {
    RemoteWebDriver driver;
    String url = "https://crio-qkart-frontend-qa.vercel.app";

    public Home(RemoteWebDriver driver) {
        this.driver = driver;
    }

    public void navigateToHome() {
        if (!this.driver.getCurrentUrl().equals(this.url)) {
            this.driver.get(this.url);
        }
    }

    public Boolean PerformLogout() throws InterruptedException {
        try {
            // Find and click on the Logout Button
            WebElement logout_button = driver.findElement(By.className("MuiButton-text"));
            logout_button.click();

            // Wait for Logout to Complete
            Thread.sleep(3000);

            return true;
        } catch (Exception e) {
            // Error while logout
            return false;
        }
    }

    /*
     * Returns Boolean if searching for the given product name occurs without any
     * errors
     */
    public Boolean searchForProduct(String product) {
        try {
            // TODO: CRIO_TASK_MODULE_TEST_AUTOMATION - TEST CASE 03: MILESTONE 1
            // Clear the contents of the search box and Enter the product name in the search
            // box
            //*[@id="root"]/div/div/div[2]/div/input
            WebElement searchBox = driver.findElement(By.xpath("//input[@name='search']"));
            searchBox.clear();
            searchBox.sendKeys(product);
            Thread.sleep(5);
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            // if(this.getSearchResults().size() > 0) {
            return true;
            // }
            // return false;
        } catch (Exception e) {
            System.out.println("Error while searching for a product: " + e.getMessage());
            return false;
        }
    }

    /*
     * Returns Array of Web Elements that are search results and return the same
     */
    public List<WebElement> getSearchResults() {
        List<WebElement> searchResults = new ArrayList<WebElement>() {
        };
        try {
            // TODO: CRIO_TASK_MODULE_TEST_AUTOMATION - TEST CASE 03: MILESTONE 1
            // Find all webelements corresponding to the card content section of each of
            // search results
            searchResults = driver.findElements(By.className("css-1qw96cp"));
            // System.out.println("home ===>");
            // System.out.println(searchResults);
            return searchResults;
        } catch (Exception e) {
            System.out.println("There were no search results: " + e.getMessage());
            return searchResults;
        }
    }

    /*
     * Returns Boolean based on if the "No products found" text is displayed
     */
    public Boolean isNoResultFound() {
        Boolean status = false;
        try {
            // TODO: CRIO_TASK_MODULE_TEST_AUTOMATION - TEST CASE 03: MILESTONE 1
            // Check the presence of "No products found" text in the web page. Assign status
            // = true if the element is *displayed* else set status = false
            //*[@id="root"]/div/div/div[3]/div/div[2]/div/h4
            WebElement noProdElement = driver.findElement(By.xpath("//*[@id='root']/div/div/div[3]/div[1]/div[2]/div/h4"));
            status = noProdElement.isDisplayed() && noProdElement.getText().equals("No products found");
            // status = driver.getPageSource().contains("No products found");
            return status;
        } catch (Exception e) {
            return status;
        }
    }

    /*
     * Return Boolean if add product to cart is successful
     */
    public Boolean addProductToCart(String productName) {
        try {
            // TODO: CRIO_TASK_MODULE_TEST_AUTOMATION - TEST CASE 05: MILESTONE 4
            /*
             * Iterate through each product on the page to find the WebElement corresponding
             * to the matching productName
             * 
             * Click on the "ADD TO CART" button for that element
             * 
             * Return true if these operations succeeds
             */
            List<WebElement> products = driver.findElements(By.xpath("//*[@id='root']/div/div/div[3]/div/div[2]/div/div"));
            for (WebElement webElement : products) {
                if(webElement.findElement(By.xpath("div/p")).getText().equals(productName)) {
                    if(webElement.findElement(By.xpath("div/button")).getText().equals("ADD TO CART")) {
                        webElement.findElement(By.xpath("div/button")).click();
                    }
                    return true;
                }
            }
            System.out.println("Unable to find the given product");
            return false;
        } catch (Exception e) {
            System.out.println("Exception while performing add to cart: " + e.getMessage());
            return false;
        }
    }

    /*
     * Return Boolean denoting the status of clicking on the checkout button
     */
    public Boolean clickCheckout() {
        Boolean status = false;
        try {
            // TODO: CRIO_TASK_MODULE_TEST_AUTOMATION - TEST CASE 05: MILESTONE 4
            // Find and click on the the Checkout button
            //*[@id="root"]/div/div/div[3]/div[2]/div/div[4]/button
            // WebElement checkoutWE = driver.findElement(By.xpath("//*[@id='root']/div/div/div[3]/div[2]/div/div[4]/button"));
            //button[text()='Checkout']
            WebElement checkoutWE = driver.findElement(By.xpath(" //button[text()='Checkout']"));
            if(checkoutWE.isDisplayed() && checkoutWE.isEnabled()) {
                checkoutWE.click();
                status = true;
            }
            return status;
        } catch (Exception e) {
            System.out.println("Exception while clicking on Checkout: " + e.getMessage());
            return status;
        }
    }

    /*
     * Return Boolean denoting the status of change quantity of product in cart
     * operation
     */
    public Boolean changeProductQuantityinCart(String productName, int quantity) {
        try {
            // TODO: CRIO_TASK_MODULE_TEST_AUTOMATION - TEST CASE 06: MILESTONE 5

            // Find the item on the cart with the matching productName

            // Increment or decrement the quantity of the matching product until the current
            // quantity is reached (Note: Keep a look out when then input quantity is 0,
            // here we need to remove the item completely from the cart)

            // iterate through the cart contents
            List<WebElement> cartDivs = driver.findElements(By.xpath("//*[@class='MuiBox-root css-zgtx0t']"));
            //*[@class="MuiBox-root css-zgtx0t"]/div/div[2]/div[1]/button[1] - minus btn
            //*[@class="MuiBox-root css-zgtx0t"]/div/div[2]/div[1]/button[2] + plus btn
            // //*[@class="MuiBox-root css-zgtx0t"]/div[2]/div[1] - product name
            // //*[@class="MuiBox-root css-zgtx0t"]/div[2]/div[2]/div/div - curr qty
            for (WebElement webElement : cartDivs) {
                String currProdName = webElement.findElement(By.xpath("div[2]/div[1]")).getText();
                if(currProdName.equals(productName)) {
                    // if curr qty === qiven qty
                    //  do nothing
                    // else if curr qty <
                    int currQty = Integer.parseInt(webElement.findElement(By.xpath("div[2]/div[2]/div/div")).getText());
                    while(currQty != quantity) {
                        if(currQty > quantity) {
                            WebElement decBtn = webElement.findElement(By.xpath("div/div[2]/div[1]/button[1]"));
                            decBtn.click();
                            Thread.sleep(2000);
                            currQty -= 1;
                        }
                        else if(currQty < quantity) {
                            WebElement incBtn = webElement.findElement(By.xpath("div/div[2]/div[1]/button[2]"));
                            incBtn.click();
                            Thread.sleep(2000);
                            currQty += 1;
                        }
                    }
                }
            }
            return true;
        } catch (Exception e) {
            if (quantity == 0)
                return true;
            System.out.println("exception occurred when updating cart: " + e.getMessage());
            return false;
        }
    }

    /*
     * Return Boolean denoting if the cart contains items as expected
     */
    public Boolean verifyCartContents(List<String> expectedCartContents) {
        try {
            // TODO: CRIO_TASK_MODULE_TEST_AUTOMATION - TEST CASE 07: MILESTONE 6

            // Get all the cart items as an array of webelements

            // Iterate through expectedCartContents and check if item with matching product
            // name is present in the cart
            List<WebElement> cartDivs = driver.findElements(By.xpath("//*[@class='MuiBox-root css-zgtx0t']"));
            // //*[@class="MuiBox-root css-zgtx0t"]/div[2]/div[1] - product name
            for (int i=0; i < cartDivs.size(); i++) {
                String currProdName = cartDivs.get(i).findElement(By.xpath("div[2]/div[1]")).getText();
                if(!currProdName.equals(expectedCartContents.get(i))) { 
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            System.out.println("Exception while verifying cart contents: " + e.getMessage());
            return false;
        }
    }
}
