package com.udacity.jwdnd.course1.cloudstorage.view;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignupPO {
    @FindBy(id="inputFirstName")
    private WebElement firstname;
    @FindBy(id="inputLastName")
    private WebElement lastname;
    @FindBy(id="inputUsername")
    private WebElement username;
    @FindBy(id="inputPassword")
    private WebElement password;
    @FindBy(id="inputSubmitSignup")
    private WebElement submit;

    public SignupPO(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void signup(String firstname, String lastname, String username, String password){
        this.firstname.sendKeys(firstname);
        this.lastname.sendKeys(lastname);
        this.username.sendKeys(username);
        this.password.sendKeys(password);
        this.submit.click();
    }

}
