package com.udacity.jwdnd.course1.cloudstorage.view;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPO {
    @FindBy(id="inputUsernameLogin")
    private WebElement username;
    @FindBy(id="inputPasswordLogin")
    private WebElement password;
    @FindBy(id="inputSubmitLogin")
    private WebElement submit;

    public LoginPO(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void login(String username, String password) {
        this.username.sendKeys(username);
        this.password.sendKeys(password);
        this.submit.click();
    }
}
