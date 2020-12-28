package com.udacity.jwdnd.course1.cloudstorage.view;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePO {
    @FindBy(id="idLogoutButton")
    private WebElement logout;

    public HomePO(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void logoutWeb() {
        logout.click();
    }
}
