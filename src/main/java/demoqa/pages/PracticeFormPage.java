package demoqa.pages;

import demoqa.core.BasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class PracticeFormPage extends BasePage {
    public PracticeFormPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    @FindBy(id = "firstName")
    WebElement firstName;
    @FindBy(id = "lastName")
    WebElement lastName;
    @FindBy(id = "userEmail")
    WebElement userEmail;
    @FindBy(id = "userNumber")
    WebElement userNumber;

    public PracticeFormPage enterPersonalData(String name, String surName, String email, String number) {

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("⛔ First name cannot be null or blank");
        }
        if (surName == null || surName.isBlank()) {
            throw new IllegalArgumentException("⛔ Last name cannot be null or blank");
        }
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("⛔ Email cannot be null or blank");
        }
        if (number == null || number.isBlank()) {
            throw new IllegalArgumentException("⛔ Number cannot be null or blank");
        }

        try {
            try {
                type(firstName, name);
                type(lastName, surName);
                type(userEmail, email);
                type(userNumber, number);
                System.out.printf("✅ Personal data: [%s], [%s], [%s], [%s]%n",name,surName,email,number);
            } catch (NoSuchElementException e) {
                throw new NoSuchElementException("⛔ Personal data field not found. " + e.getMessage());
            }
        } catch (Exception e) {
            throw new RuntimeException("❌ Error entering personal data: " + e);
        }
        return this;
    }


    public PracticeFormPage selectGender(String gender) {
        if (gender == null || gender.isBlank()) {
            throw new IllegalArgumentException("⛔ Gender cannot be null or blank");
        }
        List<String> validGenders = Arrays.asList("Male", "Female", "Other");
        if (!validGenders.contains(gender)) {
            throw new IllegalArgumentException("⛔ Invalid gender: [" + gender + "]. Allowed values: " + validGenders);
        }
        try {
            String xpathGender = "//label[.='" + gender + "']";
            WebElement genderLocator = driver.findElement(By.xpath(xpathGender));
            click(genderLocator);
            System.out.printf("✅ Gender: [%s]%n", gender);
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("⛔ Gender element not found: [" + gender + "]. " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("❌ Error selecting gender: [" + gender + "]. " + e);
        }
        return this;
    }



    @FindBy(id = "dateOfBirthInput")
    WebElement dateOfBirthInput;

    public PracticeFormPage chooseDateAsString(String date) {

        if (date == null || date.isBlank()) {
            throw new IllegalArgumentException("⛔ Date cannot be null or blank");
        }
        try {
        //type(dateOfBirthInput, date);
        click(dateOfBirthInput);

            if (System.getProperty("os.name").contains("Mac")) {
                dateOfBirthInput.sendKeys(Keys.COMMAND, "a");
            } else {
                dateOfBirthInput.sendKeys(Keys.CONTROL, "a");
            }
            dateOfBirthInput.sendKeys(date);
            dateOfBirthInput.sendKeys(Keys.ENTER);
            System.out.printf("✅ Date: [%s]%n", date);
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("⛔ Date of Birth input not found. " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("❌ Error choosing date as string: " + e);
        }
        return this;
    }

    public PracticeFormPage chooseDate(String day, String month, String year) {

        if (day == null || day.isBlank()) {
            throw new IllegalArgumentException("⛔ Day cannot be null or blank");
        }
        if (month == null || month.isBlank()) {
            throw new IllegalArgumentException("⛔ Month cannot be null or blank");
        }
        if (year == null || year.isBlank()) {
            throw new IllegalArgumentException("⛔ Year cannot be null or blank");
        }
        try {

        // 1. Открываем календарь
        driver.findElement(By.id("dateOfBirthInput")).click();

        // 2. Выбор месяца :"react-datepicker__month-select"
        Select monthSelect = new Select(driver.findElement(By.className("react-datepicker__month-select")));
        monthSelect.selectByVisibleText(month);

        // 3. Выбор года: "react-datepicker__year-select"
        Select yearSelect = new Select(driver.findElement(By.className("react-datepicker__year-select")));
        yearSelect.selectByVisibleText(year);

        // 4. Выбор дня: "react-datepicker__day"
        // Если день начинается с нуля, убираем его
        String normalizedDay = day.startsWith("0") ? day.substring(1) : day;

        String dayLocator = String.format(
                "//div[contains(@class, 'react-datepicker__day') and text()='%s' and not(contains(@class, 'react-datepicker__day--outside-month'))]",
                normalizedDay
        );
            WebElement dayElement = driver.findElement(By.xpath(dayLocator));
            if (dayElement == null) {
                throw new NoSuchElementException("⛔ Day element not found for day: " + day);
            }
            dayElement.click();
            System.out.printf("✅ Date selected: %s %s %s%n", day, month, year);
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("⛔ Date element not found: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("❌ Error selecting date: " + e);
        }
        return this;
    }

    @FindBy(id = "subjectsInput")
    WebElement subjectsInput;

    public PracticeFormPage enterSubjects(String[] subjects) {
        if (subjects == null || subjects.length == 0) {
            throw new IllegalArgumentException("⛔ Subjects cannot be null or empty");
        }
        try {
            for (String subject : subjects) {
                if (subject == null || subject.isBlank()) {
                    throw new IllegalArgumentException("⛔ Subject value cannot be null or blank");
                }
                type(subjectsInput, subject);
                subjectsInput.sendKeys(Keys.ENTER);
                System.out.printf("✅ Subject: [%s]%n", subject);
            }
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("⛔ Subjects input element not found. " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("❌ Error entering subjects: " + e);
        }
        return this;
    }

    public PracticeFormPage chooseHobbies(String[] hobbies) {
        if (hobbies == null || hobbies.length == 0) {
            throw new IllegalArgumentException("⛔ Hobbies cannot be null or empty");
        }
        try {
            for (String hobby : hobbies) {
                if (hobby == null || hobby.isBlank()) {
                    throw new IllegalArgumentException("⛔ Hobby value cannot be null or blank");
                }
                By hobbyLocator = By.xpath("//label[.='" + hobby + "']");
                WebElement element = driver.findElement(hobbyLocator);
                if (element == null) {
                    throw new NoSuchElementException("⛔ Hobby element not found for hobby: " + hobby);
                }
                click(element);
                System.out.printf("✅ Hobby: [%s]%n", hobby);
            }
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("⛔ Hobby element not found: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("❌ Error choosing hobbies: " + e);
        }
        return this;
    }

    @FindBy(id = "uploadPicture")
    WebElement uploadPicture;

    public PracticeFormPage uploadPicture(String imgPath) {
        if (imgPath == null || imgPath.isBlank()) {
            throw new IllegalArgumentException("⛔ Image path cannot be null or blank");
        }
        // Проверка существования файла по указанному пути
        File file = new File(imgPath);
        if (!file.exists()) {
            throw new IllegalArgumentException("⛔ File does not exist: " + imgPath);
        }
        try {
            // Загружаем файл
            uploadPicture.sendKeys(imgPath);
            System.out.printf("✅ Image path: [%s]%n", imgPath);

            // Извлекаем ожидаемое имя файла из переданного пути
            String expectedFileName = file.getName();

            // Получаем значение атрибута value, где содержится путь с именем файла
            String uploadedFileNameWithPath = uploadPicture.getAttribute("value");

            // Используем Paths для получения только имени файла
            String uploadedFileName = Paths.get(uploadedFileNameWithPath).getFileName().toString();
            System.out.printf("✅ Uploaded file name: [%s]%n", uploadedFileName);

            // Проверяем, что загруженный файл отображается корректно (с учётом обработки input-элемента в shouldHaveText())
            shouldHaveText(uploadPicture, expectedFileName, 5000);
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("⛔ Upload element not found: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("⛔ Invalid image path: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("❌ Error uploading picture: " + e);
        }
        return this;
    }

    @FindBy(id = "currentAddress")
    WebElement currentAddress;

    public PracticeFormPage enterCurrentAddress(String address) {
        type(currentAddress, address);
        System.out.printf("✅ Address: [%s]%n", address);
        return this;
    }

    @FindBy(id = "react-select-3-input")
    WebElement stateInput;

    public PracticeFormPage enterState(String state) {
        // type(stateContainer, state);
        stateInput.sendKeys(state);
        stateInput.sendKeys(Keys.ENTER);
        System.out.printf("✅ Address state: [%s]%n", state);
        return this;
    }

    @FindBy(id = "react-select-4-input")
    WebElement cityInput;
    public PracticeFormPage enterCity(String city) {
        cityInput.sendKeys(city);
        cityInput.sendKeys(Keys.ENTER);
        System.out.printf("✅ Address city: [%s]%n", city);
        return this;
    }

    @FindBy(id = "submit")
    WebElement submitButton;

    public PracticeFormPage submitForm() {
        click(submitButton);
        return this;
    }

    @FindBy(id = "example-modal-sizes-title-lg")
    WebElement registrationModal;

    public PracticeFormPage verifySuccessRegistration(String textToCheck) {
        shouldHaveText(registrationModal, textToCheck, 5000);
        System.out.printf("✅ Registration success: [%s]%n", textToCheck);
        return this;
    }
}
