import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;
import static org.openqa.selenium.Keys.*;

public class CardDeliveryTest {


    @Test
    public void shouldOrderCardWithDelivery() {
        Configuration.holdBrowserOpen = true;
        //    Configuration.browser = "firefox";
        open("http://localhost:9999/");
        $("[placeholder='Город']").setValue("Санкт-Петербург");
        $("[data-test-id=date] input").sendKeys(chord(COMMAND, "a"), DELETE);

        LocalDate date = LocalDate.now().plusDays(4);
        //%02d - добавить ноль, если меньше 2х символов
        String myDate = String.format("%02d.%02d.%d", date.getDayOfMonth(), date.getMonthValue(), date.getYear());

       // $("[data-test-id=date] input").sendKeys(chord(COMMAND, "a"), DELETE);
        $("[placeholder='Дата встречи']").setValue(myDate);
        $("[name='name']").setValue("Чайковский Петр");
        $("[name='phone']").val("+79998887766");
        $("[data-test-id='agreement']").click();
        $(byText("Забронировать")).click();
        $("[data-test-id=notification]").shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(exactText("Успешно! Встреча успешно забронирована на " + myDate));

    }

    @Test
    public void shouldUseAutocompleteAndCalendar() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        $("[placeholder='Город']").setValue("Тв");
        $(".input__menu").find(withText("Тверь")).click();
        $("[data-test-id=date] [value]").click();

        LocalDate dateOfCreationOfApplication = LocalDate.now();
        LocalDate dateOfMeeting = LocalDate.now().plusDays(7);

        if (dateOfMeeting.getMonthValue() > dateOfCreationOfApplication.getMonthValue()
                | dateOfMeeting.getYear() > dateOfCreationOfApplication.getYear()) {
            $(".calendar__arrow_direction_right[data-step='1']").click();
        }
        String dayToSearch = String.valueOf(dateOfMeeting.getDayOfMonth());
        $$("td.calendar__day").find(exactText(dayToSearch)).click();

        $("[name='name']").setValue("Чайковский Петр");
        $("[name='phone']").val("+79998887766");
        $("[data-test-id='agreement']").click();
        $(byText("Забронировать")).click();
        String stringDateInSuccessPopUp = dateOfMeeting.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=notification]").shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(exactText("Успешно! Встреча успешно забронирована на " + stringDateInSuccessPopUp));
    }
}
