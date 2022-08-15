package ru.netology.testmode.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static ru.netology.testmode.data.DataGenerator.Registration.getRegisteredUser;
import static ru.netology.testmode.data.DataGenerator.Registration.getUser;
import static ru.netology.testmode.data.DataGenerator.getRandomLogin;
import static ru.netology.testmode.data.DataGenerator.getRandomPassword;

public class AuthTest {
    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successfully login with active registered user")
    void shouldSuccessfulLoginIfRegisteredActiveUser(){
        Configuration.holdBrowserOpen = true;
        var registeredUser = getRegisteredUser("active");
        $("[name='login'].input__control").val(registeredUser.getLogin());
        $("[name='password'].input__control").val(registeredUser.getPassword());
        $("[data-test-id='action-login'].button").click();
        $("[id='root']").shouldHave(Condition.exactText("  Личный кабинет"));

        // TODO: добавить логику теста, в рамках которого будет выполнена попытка входа в личный кабинет с учётными
        //  данными зарегистрированного активного пользователя, для заполнения полей формы используйте
        //  пользователя registeredUser
    }

    @Test
    @DisplayName("Should get error message if login with not registered user")
    void shouldGetErrorIfNotRegisteredUser() {
        Configuration.holdBrowserOpen = true;
        var notRegisteredUser = getUser("active");
        $("[name='login'].input__control").val(notRegisteredUser.getLogin());
        $("[name='password'].input__control").val(notRegisteredUser.getPassword());
        $("[data-test-id='action-login'].button").click();
        $("[data-test-id='error-notification']").shouldBe(visible);

        // TODO: добавить логику теста в рамках которого будет выполнена попытка входа в личный кабинет
        //  незарегистрированного пользователя, для заполнения полей формы используйте пользователя notRegisteredUser
    }

    @Test
    @DisplayName("Should get error message if login with blocked registered user")
    void shouldGetErrorIfBlockedUser() {
        var blockedUser = getRegisteredUser("blocked");
        $("[name='login'].input__control").val(blockedUser.getLogin());
        $("[name='password'].input__control").val(blockedUser.getPassword());
        $("[data-test-id='action-login'].button").click();
        $("[data-test-id='error-notification']").shouldBe(visible);

        // TODO: добавить логику теста в рамках которого будет выполнена попытка входа в личный кабинет,
        //  заблокированного пользователя, для заполнения полей формы используйте пользователя blockedUser
    }

    @Test
    @DisplayName("Should get error message if login with wrong login")
    void shouldGetErrorIfWrongLogin() {
        var registeredUser = getRegisteredUser("active");
        var wrongLogin = getRandomLogin();
        $("[name='login'].input__control").val(wrongLogin);
        $("[name='password'].input__control").val(registeredUser.getPassword());
        $("[data-test-id='action-login'].button").click();
        $("[data-test-id='error-notification']").shouldBe(visible);

        // TODO: добавить логику теста в рамках которого будет выполнена попытка входа в личный кабинет с неверным
        //  логином, для заполнения поля формы "Логин" используйте переменную wrongLogin,
        //  "Пароль" - пользователя registeredUser
    }

    @Test
    @DisplayName("Should get error message if login with wrong password")
    void shouldGetErrorIfWrongPassword() {
        var registeredUser = getRegisteredUser("active");
        var wrongPassword = getRandomPassword();
        $("[name='login'].input__control").val(registeredUser.getLogin());
        $("[name='password'].input__control").val(wrongPassword);
        $("[data-test-id='action-login'].button").click();
        $("[data-test-id='error-notification']").shouldBe(visible);

        // TODO: добавить логику теста в рамках которого будет выполнена попытка входа в личный кабинет с неверным
        //  паролем, для заполнения поля формы "Логин" используйте пользователя registeredUser,
        //  "Пароль" - переменную wrongPassword
    }
}
