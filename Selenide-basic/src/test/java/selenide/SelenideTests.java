package selenide;
import com.codeborne.selenide.*;
import com.codeborne.selenide.ex.SoftAssertionError;
import com.codeborne.selenide.testng.SoftAsserts;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Configuration.*;

import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

@Listeners({ SoftAsserts.class})
public class SelenideTests {

    @Test(priority = 0)
    public void SelenideTests_2(){
        startMaximized=true;
        //ბროუზერს არ ვთიშავ ტესტის ბოლოს
        holdBrowserOpen=true;
        open("https://demoqa.com/books");
        //დაფეილების შემთხვევაში სქეინს გადაიტანს აქ
        reportsFolder="src/main/resources/Pictures";
    }

    @Test(priority = 1)
    public void FindElements(){

        //region FindAll
        //აქ მაქვს გამოყენებული findAll რომ ყველა ლინკი ვიპოვნო რომელიც ცხრილშია მოცემული
        ElementsCollection TitleElements =$(By.cssSelector("div.rt-tbody")).findAll(".mr-2 a");

        //ორი ქოუნთერი მაქვს ერთი დამჭირდა რადგან
        //სტრიქონების ათვლა იწყებოდა ერთიდან
        int counter=1;
        //და მეორე დამთხვევების დასათვლელად
        int match=0;


        for(int i=0;i<TitleElements.size();i++){


            //მაქვს მეორე ელემენტების კოლექცია რომელშიც ყველა ველის მეოთხე დივს ვიღებ
            //ცოტა ჰარდად არის გაწერილი მაგრამ სხვანაირად არ გამოვიდა
            ElementsCollection PublisherElements=$(By.cssSelector("div.rt-tr-group:nth-child("+counter+")")).findAll(" div div:nth-child(4)");
            counter++;

            //აქ ვითვლი დამთხვევებს სათაურში შედის თუ არა JavaScript და გამომქვეყნებელი თუ არის "O'Reilly Media"
            if (TitleElements.get(i).getText().contains("JavaScript") && PublisherElements.get(0).getText().equals("O'Reilly Media")){
                    match++;
                    System.out.println(match+" Match");
                    System.out.println(TitleElements.get(i).getText()+"   |||   "+PublisherElements.get(0).getText());

            }
        }
        //endregion

        //region SoftAssert
        //მაქვს გამოყენებული SoftAssert-ი რომ შევადარო ლისტის ზომა 10-ს
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(TitleElements.size(),10);
        //endregion

        //region Stream

        //stream ის გამოყენებით ვაჭერ ყველა ლინკს მაგრამ უკან დაბრუნების გარეშე აბრუნებს შეცდომას
        //ასე დავტოვე რადგან სქრინი გადაეღო დაფეილების დროს
        TitleElements.stream().forEach(el -> el.click());

        //endregion
    }


    @Test(priority = 2)
    public void Checkboxes() {
        open("https://demoqa.com/books ");

        //region check images

        //ვიღებ ყველა სათაურს
        ElementsCollection AllTitle =$$(".mr-2 a");
        //ვიღებ ყველა ფაბლიშერს
        ElementsCollection AllPublisher =$$(By.xpath("//*[@class='rt-tbody']//div[text()=\"O'Reilly Media\"]"));
        //ვიღებ ყველა სურათს
        ElementsCollection AllImage =$$(By.xpath("//*[@class='rt-tbody']//img[@alt='image']"));

        //სათაურების სიგრძეზე ვამოწმებ სურათები თუ აქვს ყველას
        for (int i=0;i<AllTitle.size();i++) {
            System.out.println(AllImage.get(i).exists()+"  ||  "+AllTitle.get(i));
        }
        //endregion

    }

}
