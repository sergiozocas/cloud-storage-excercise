package com.udacity.jwdnd.course1.cloudstorage.view;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class NotesPO {
    // I use JavascriptExecutor in this page because i had problem with some element's state
    private final JavascriptExecutor js;

    @FindBy(id="note-title")
    private WebElement notetitle;
    @FindBy(id="note-description")
    private WebElement notedescription;
    @FindBy(id="new-note")
    private WebElement newnote;
    @FindBy(id="nav-notes-tab")
    private WebElement notestab;
    @FindBy(id="saveNote")
    private WebElement savenote;
    @FindBy(id="edit-note")
    private WebElement editnote;
    @FindBy(id="delete-note")
    private WebElement deletenote;

    public NotesPO(WebDriver driver){
        PageFactory.initElements(driver, this);
        js = (JavascriptExecutor) driver;
    }

    public void createNote(String noteTitle, String noteDescription){
        js.executeScript("arguments[0].click();", notestab);
        js.executeScript("arguments[0].click();", newnote);
        js.executeScript("arguments[0].value='" + noteTitle + "';", notetitle);
        js.executeScript("arguments[0].value='" + noteDescription + "';", notedescription);
        js.executeScript("arguments[0].click();", savenote);
    }

    public void editNote(String noteTitle, String noteDescription) {
        js.executeScript("arguments[0].click();", notestab);
        js.executeScript("arguments[0].click();", editnote);
        js.executeScript("arguments[0].value='" + noteTitle + "';", notetitle);
        js.executeScript("arguments[0].value='" + noteDescription + "';", notedescription);
        js.executeScript("arguments[0].click();", savenote);
    }

    public void deleteNote() {
        js.executeScript("arguments[0].click();", notestab);
        js.executeScript("arguments[0].click();", deletenote);
    }

}
