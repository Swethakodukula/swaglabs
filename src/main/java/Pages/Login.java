package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.w3c.dom.html.HTMLInputElement;

public class Login {
    @FindBy(name="user-name")
    WebElement username;

    @FindBy(id="password")
    WebElement password ;

    @FindBy(css=".btn_action")
    WebElement loginBtn;


    public Login(WebDriver driver){
        PageFactory.initElements(driver,this);

    }



    public void enterUsername(String usn) {
        username.clear();
        username.sendKeys(usn);
    }

    public void enterPassword(String pwd) {
        password.clear();
        password.sendKeys(pwd);
    }

    public void clickLogin() {
        loginBtn.click();
    }
}
