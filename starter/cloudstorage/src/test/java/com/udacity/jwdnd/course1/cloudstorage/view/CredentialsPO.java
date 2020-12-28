package com.udacity.jwdnd.course1.cloudstorage.view;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CredentialsPO {
    private final JavascriptExecutor js;
    @FindBy(id="nav-credentials-tab")
    private WebElement credentialstab;
    @FindBy(id="new-credential")
    private WebElement newcredential;
    @FindBy(id="credential-url")
    private WebElement credentialurl;
    @FindBy(id="credential-username")
    private WebElement credentialusername;
    @FindBy(id="credential-password")
    private WebElement credentialpassword;
    @FindBy(id="save-credential")
    private WebElement savecredential;
    @FindBy(id="edit-credential")
    private WebElement editcredential;
    @FindBy(id="delete-credential")
    private WebElement deletecredential;



    public CredentialsPO(WebDriver driver){
        PageFactory.initElements(driver, this);
        js = (JavascriptExecutor) driver;
    }

    public void createCredential(String url, String username, String password) {
        js.executeScript("arguments[0].click();", credentialstab);
        js.executeScript("arguments[0].click();", newcredential);
        js.executeScript("arguments[0].value='" + url + "';", credentialurl);
        js.executeScript("arguments[0].value='" + username + "';", credentialusername);
        js.executeScript("arguments[0].value='" + password + "';", credentialpassword);
        js.executeScript("arguments[0].click();", savecredential);
    }

    public void showCredentialModal() {
        js.executeScript("arguments[0].click();", editcredential);
    }

    public void editCredential(String url, String username, String password){
        js.executeScript("arguments[0].value='" + url + "';", credentialurl);
        js.executeScript("arguments[0].value='" + username + "';", credentialusername);
        js.executeScript("arguments[0].value='" + password + "';", credentialpassword);
        js.executeScript("arguments[0].click();", savecredential);
    }

    public void deleteCredential() {
        js.executeScript("arguments[0].click();", deletecredential);
    }
}
