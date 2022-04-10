import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

public class CardDeliveryTest {


    @Test
    public void shouldOrderCardWithDelivery() {
        Configuration.holdBrowserOpen = true;
    //    Configuration.browser = "firefox";
        open("http://localhost:9999/");
        $("[placeholder='Город']").setValue("Санкт-Петербург");
        LocalDate currentDate = LocalDate.now().plusDays(3);
        $("[placeholder='Дата встречи']").setValue("currentDate");
        $("[name='name']").setValue("Чайковский Петр");
        $("[name='phone']").val("+79998887766");
        $("[data-test-id='agreement']").click();
        $(byText("Забронировать")).click();
        $(withText("Успешно")).shouldBe(appear, Duration.ofSeconds(15));
    }

    @Test
    public void shouldUseAutocompleteAndCalendar() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        $("[placeholder='Город']").setValue("Тв");
        $$(".menu-item").filter(visible).last().click();
        $("[data-test-id=date] [value]").click();

        LocalDate dateOfCreationOfApplication = LocalDate.now();
        LocalDate dateOfMeeting = LocalDate.now().plusDays(7);

        if (dateOfMeeting.getMonthValue()>dateOfCreationOfApplication.getMonthValue()
                |dateOfMeeting.getYear()>dateOfCreationOfApplication.getYear()){
            $(".calendar__arrow_direction_right[data-step='1']").click();
        }
        String dayToSearch = String.valueOf(dateOfMeeting.getDayOfMonth());
        $$("td.calendar__day").find(exactText(dayToSearch)).click();

        $("[name='name']").setValue("Чайковский Петр");
        $("[name='phone']").val("+79998887766");
        $("[data-test-id='agreement']").click();
        $(byText("Забронировать")).click();
        String stringDateInSuccessPopUp = dateOfMeeting.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
     //   $(withText("Успешно! Встреча успешно забронирована на " + stringDateInSuccessPopUp)).shouldBe(visible, Duration.ofSeconds(20));
        $("[data-test-id=notification]").shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(exactText("Успешно! Встреча успешно забронирована на " + stringDateInSuccessPopUp));
    }

    @Test
    public void shouldFlipTheCalendarToTheNextMonth() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        $("[placeholder='Город']").setValue("Тв");
        $$(".menu-item").filter(visible).last().click();
        $("[data-test-id=date] [value]").click();

        LocalDate dateOfCreationOfApplication = LocalDate.now();
        LocalDate dateOfMeeting = LocalDate.now().plusDays(30);

        if (dateOfMeeting.getMonthValue()>dateOfCreationOfApplication.getMonthValue()
                |dateOfMeeting.getYear()>dateOfCreationOfApplication.getYear()){
            $(".calendar__arrow_direction_right[data-step='1']").click();
        }
        String dayToSearch = String.valueOf(dateOfMeeting.getDayOfMonth());
        $$("td.calendar__day").find(exactText(dayToSearch)).click();

        $("[name='name']").setValue("Чайковский Петр");
        $("[name='phone']").val("+79998887766");
        $("[data-test-id='agreement']").click();
        $(byText("Забронировать")).click();
        String stringDateInSuccessPopUp = dateOfMeeting.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        //   $(withText("Успешно! Встреча успешно забронирована на " + stringDateInSuccessPopUp)).shouldBe(visible, Duration.ofSeconds(20));
        $("[data-test-id=notification]").shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(exactText("Успешно! Встреча успешно забронирована на " + stringDateInSuccessPopUp));
    }

    @Test
    public void shouldFlipTheCalendarToTheNextYear() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        $("[placeholder='Город']").setValue("Тв");
        $$(".menu-item").filter(visible).last().click();
        $("[data-test-id=date] [value]").click();

        LocalDate dateOfCreationOfApplication = LocalDate.now();
        LocalDate dateOfMeeting = LocalDate.now().plusDays(365);

        if (dateOfMeeting.getMonthValue()>dateOfCreationOfApplication.getMonthValue()
                |dateOfMeeting.getYear()>dateOfCreationOfApplication.getYear()){
            $(".calendar__arrow_direction_right[data-step='12']").click();
        }
        String dayToSearch = String.valueOf(dateOfMeeting.getDayOfMonth());
        $$("td.calendar__day").find(exactText(dayToSearch)).click();

        $("[name='name']").setValue("Чайковский Петр");
        $("[name='phone']").val("+79998887766");
        $("[data-test-id='agreement']").click();
        $(byText("Забронировать")).click();
        String stringDateInSuccessPopUp = dateOfMeeting.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        //   $(withText("Успешно! Встреча успешно забронирована на " + stringDateInSuccessPopUp)).shouldBe(visible, Duration.ofSeconds(20));
        $("[data-test-id=notification]").shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(exactText("Успешно! Встреча успешно забронирована на " + stringDateInSuccessPopUp));
    }
}
