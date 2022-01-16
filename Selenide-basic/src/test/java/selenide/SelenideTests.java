package selenide;
import com.codeborne.selenide.*;
import com.codeborne.selenide.collections.ExactTexts;
import com.codeborne.selenide.conditions.CssValue;
import com.codeborne.selenide.conditions.Value;
import com.codeborne.selenide.ex.SoftAssertionError;
import com.codeborne.selenide.junit5.ScreenShooterExtension;
import com.codeborne.selenide.testng.ScreenShooter;
import com.codeborne.selenide.testng.SoftAsserts;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.FileNotFoundException;
import java.time.Duration;
import java.util.List;

import static com.codeborne.selenide.CollectionCondition.*;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Configuration.*;

import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

@Listeners({ SoftAsserts.class})
public class SelenideTests {

    @Test(priority = 1)
    public void SelenideTests(){
        startMaximized=true;
        //ბროუზერს არ ვთიშავ ტესტის ბოლოს
        holdBrowserOpen=true;
        open("http://the-internet.herokuapp.com/checkboxes");

    }

    @Test(priority = 2)
    public void Checkboxes() {
        //region Checkboxes
        //ვიღებ ყველა ჩეკბოქსს ფორმიდან
        ElementsCollection Checkbox=$$(By.cssSelector("form#checkboxes input"));

        //პირველი ჩეკბოქსი რაც დამხვდება ვაჭერ ღილაკზე
        Checkbox.get(0).click();

        //ვაკეთებ ქოუნთერს რომ შემდეგ შევადარო
        int CheckboxCounter=0;

        //სათითაოდ ვიღებ ყველა ელემენტის ტიპს და თუ ჩეკბოქს ემთხვევა ქოუნთერს ვამატებ ერთს
        for(int i=0;i<Checkbox.size();i++) {

            if(Checkbox.get(i).getAttribute("type").equals("checkbox")){
                CheckboxCounter++;

            }
            System.out.println(Checkbox.get(i).getAttribute("type"));

        }

        //და ბოლოს თუ ქაუნტერის რაოდენობა დაემთხვევა ელემენტების რაოდენობას მაშინ ყველა ელემენტი
        //ჩეკბოქს ტიპისაა თუ არ დაემთხვა მაშინ არ არის
        if(CheckboxCounter==Checkbox.size()){
            System.out.println("ყველა ელემენტი ჩეკბოქს ტიპის არის :" );
        }else {
            System.out.println("ელემენტებს აქვთ განსხვავებული ტიპები ");
        }
        //endregion

    }

    @Test(priority = 3)
    public void Dropdowns(){
        open("http://the-internet.herokuapp.com/dropdown");

        //region DropDown
        //dropdown ელემენტში ვირჩევ არჩეულ პარამეტრს და ვბეჭდავ შემდეგ კი ვუთითებ ახალს და მასაც ვბეჭდავ გადასამოწმებლად
        Select select=new Select($(By.cssSelector("#dropdown")));
        System.out.println("Selected option is \""+select.getFirstSelectedOption().getText()+"\"");
        select.selectByVisibleText("Option 2");
        System.out.println("Selected option is \""+select.getFirstSelectedOption().getText()+"\"");
        //endregion

    }

    @Test(priority = 4)
    public void TextBoxes(){
        //გადავდივარ ახალ საიტზე
        open("https://demoqa.com/text-box ");


        //region Write Information
        //ვიღებ პირველ ელემენტს იუზერის ჩასაწერად აიდით
        WebElement FullName =$(By.id("userName"));
        FullName.sendKeys("ლევან ჭიჭაძე");

        //შემდეგ ცსს სელექტორით
        WebElement Email =$(By.cssSelector("input#userEmail"));
        Email.sendKeys("Levan.Tchitchadze@Gau.Edu.Ge");

        //შემდეგს xpath-ით
        WebElement CurrentAddress=$(By.xpath("//Textarea[@placeholder='Current Address']"));
        CurrentAddress.sendKeys("Tbilisi");

        //და ბოლო ველს კლასის ნომრით და ინდექსით
        WebElement PermanentAddress=$$(By.className("form-control")).get(3);
        PermanentAddress.sendKeys("Tbilisi");
        //endregion


        //region Check Information

        //ვქმნი ორ მასივს სადაც შემყავს ინფუთებში ჩაწერილი ტექსტები რომ შემდეგ გადავამოწმო თუ აისახა
        String[] ValuesForInputs ={"ლევან ჭიჭაძე","Levan.Tchitchadze@Gau.Edu.Ge"};
        String[] ValuesForAreas ={"tbilisi","tbilisi"};

        System.out.println("\n \n ინფუთებში ჩაწერილი ტექსტები : ");

        //შემდეგ ვამოწმებ თუ არსებობს მასივებში არსებული მნიშვნელობებით ელემენტები თუ არ არსებობს
        //ტრაიში ზის რომ ტესტი გაგრძელდეს და დაბრუნებს პასუხს რომ კონკრეტული მნიშვნელობა არ ჩაიწერა
        for(int i=0;i<ValuesForInputs.length;i++) {
            try {
                WebElement InputCheck = $$("form#userForm input").findBy(value(ValuesForInputs[i]));
                System.out.println(InputCheck.getAttribute("value"));
            }catch (SoftAssertionError SAE){
                System.out.println("შეცდომა "+SAE.getClass().getSimpleName());
                System.out.println("ველი მნიშვნელობით "+i+" არ მოიძებნა");
            }
        }

        for (int i=0;i<ValuesForAreas.length;i++){
            try {
            WebElement AreaCheck = $$("form#userForm textarea").findBy(value(ValuesForAreas[i]));
            System.out.println(AreaCheck.getAttribute("value"));
            }catch (SoftAssertionError SAE){
                System.out.println("შეცდომა "+SAE.getClass().getSimpleName());
                System.out.println("ველი მნიშვნელობით "+i+" არ მოიძებნა");
            }
        }
        //endregion
    }

}
