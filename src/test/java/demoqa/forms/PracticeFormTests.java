package demoqa.forms;

import demoqa.core.TestBase;
import demoqa.pages.HomePage;
import demoqa.pages.PracticeFormPage;
import demoqa.pages.SidePanel;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class PracticeFormTests extends TestBase {
    @BeforeMethod
    public void precondition() {
        new HomePage(app.driver, app.wait).getForms().hideAds();
        new SidePanel(app.driver, app.wait).selectPracticeFormMenu().hideAds();
    }

    @Test
    public void practiceFormPositiveTest() {
        new PracticeFormPage(app.driver, app.wait)
                .enterPersonalData("Beth", "Gibbons", "portishead@gmail.com", "1234567890")
                .selectGender("Female")
                //.chooseDateAsString("04 May 1965")
                .chooseDate("04", "May", "1965")
                .enterSubjects(new String[]{"Maths", "English"})
                .chooseHobbies(new String[]{"Sports", "Music"})
                .uploadPicture("C:\\AIT_TR_QA\\HerokuApp\\Лось.png")

//                .enterCurrentAddress("Portishead, Bristol, UK")
//                .enterState("NCR")
//                .enterCity("Delhi")
//                .submitForm()
//                .verifySuccessRegistration("Thanks for submitting the form")
        ;
    }
}
