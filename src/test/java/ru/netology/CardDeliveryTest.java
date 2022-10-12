package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {

    String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @Test
    void shouldCardApplication() {
        Configuration.holdBrowserOpen = true;
        String date = generateDate(4);
        open("http://localhost:9999");
        $("input[placeholder=\"Город\"]").val("Москва");
        $("[data-test-id=\"date\"] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=\"date\"] input").val(date);
        $("[name='name']").val("Иванов Иван");
        $("[name='phone']").val("+79001234567");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $(byText("Успешно!")).should(visible, Duration.ofSeconds(15));
        $(".notification__content").shouldHave(Condition.text("Встреча успешно забронирована на " + date), Duration.ofSeconds(15));
    }
}
