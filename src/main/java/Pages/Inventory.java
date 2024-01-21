package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Inventory {

    @FindBy(xpath = "//button[text()='Open Menu']")
    WebElement hamburgerMenu;

    @FindBy(id = "shopping_cart_container")
    WebElement miniCart;

    public Inventory(WebDriver driver){
        PageFactory.initElements(driver, this);
    }
    public void openMenu(){
        hamburgerMenu.click();
    }
    public void cartClick(){
        miniCart.click();
    }


}
