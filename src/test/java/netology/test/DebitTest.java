package netology.test;


import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import netology.data.DataHelper;
import netology.data.SQLHelper;
import netology.page.DebitPage;
import org.junit.jupiter.api.*;

import static com.codeborne.selenide.Selenide.open;
import static netology.data.DataHelper.getOneDigit;
import static netology.data.SQLHelper.getDebitStatusFromDatabase;
import static org.junit.jupiter.api.Assertions.*;

public class DebitTest {

    String url = System.getProperty("sut.url");
    private DebitPage buy;

    @BeforeAll
    static void setAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDown() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    public void openPage() {
        open(url);
        buy = new DebitPage();
        buy.buyDebit();
    }

    @AfterEach
    public void cleanDataBase() {
        SQLHelper.cleanDatabase();
    }

    @Test
    @DisplayName(" Успешная покупка дебетовой картой при вводе валидных данных")
    public void shouldSuccessfulDebitPurchase() {
        buy.fieldCardNumberInsert(DataHelper.CARD_NUMBER_APPROVED);
        buy.fieldCardMonthInsert(DataHelper.getMonthValid());
        buy.fieldCardYearInsert(DataHelper.getYearValid());
        buy.fieldCardholderInsert(DataHelper.getCardholderFullNameEn());
        buy.fieldCardCvcInsert(DataHelper.getCvc());
        buy.buttonContinueClick();
        buy.shouldHaveMsgSuccess();
        assertEquals("APPROVED", getDebitStatusFromDatabase());
    }

    @Test
    @DisplayName("Отказ в покупке при вводе невалидных данных дебетовой карты")
    public void shouldDeclinedDebitPurchase() {
        buy.fieldCardNumberInsert(DataHelper.CARD_NUMBER_DECLINED);
        buy.fieldCardMonthInsert(DataHelper.getMonthValid());
        buy.fieldCardYearInsert(DataHelper.getYearValid());
        buy.fieldCardholderInsert(DataHelper.getCardholderFullNameEn());
        buy.fieldCardCvcInsert(DataHelper.getCvc());
        buy.buttonContinueClick();
        buy.shouldHaveMsgCardInvalid();
        assertEquals("DECLINED", SQLHelper.getDebitStatusFromDatabase());
    }

    // Заполнение карты поле "Номер карты"

    @Test
    @DisplayName("Неверно заполнено поле Номер карты, пустое значение")
    public void shouldIncorrectCardNumberEmpty() {
        buy.fieldCardMonthInsert(DataHelper.getMonthValid());
        buy.fieldCardYearInsert(DataHelper.getYearValid());
        buy.fieldCardholderInsert(DataHelper.getCardholderFullNameEn());
        buy.fieldCardCvcInsert(DataHelper.getCvc());
        buy.buttonContinueClick();
        buy.shouldHaveMsgRequiredField();
        assertEquals(0, SQLHelper.getOrdersFromDatabase());
    }

    @Test
    @DisplayName("Неверно заполнено поле Номер карты,при вводе латинских букв")
    public void shouldIncorrectCardNumberLetter() {
        buy.fieldCardNumberInsert(DataHelper.getCardNumberLetters());
        buy.fieldCardMonthInsert(DataHelper.getMonthValid());
        buy.fieldCardYearInsert(DataHelper.getYearValid());
        buy.fieldCardholderInsert(DataHelper.getCardholderFullNameEn());
        buy.fieldCardCvcInsert(DataHelper.getCvc());
        buy.buttonContinueClick();
        buy.shouldHaveMsgIncorrectFormat();
        assertEquals(0, SQLHelper.getOrdersFromDatabase());
    }

    @Test
    @DisplayName("Неверно заполнено поле Номер карты, при вводе спецсимволов")
    public void shouldIncorrectCardNumberSymbols() {
        buy.fieldCardNumberInsert(DataHelper.getSymbol());
        buy.fieldCardMonthInsert(DataHelper.getMonthValid());
        buy.fieldCardYearInsert(DataHelper.getYearValid());
        buy.fieldCardholderInsert(DataHelper.getCardholderFullNameEn());
        buy.fieldCardCvcInsert(DataHelper.getCvc());
        buy.buttonContinueClick();
        buy.shouldHaveMsgIncorrectFormat();
        assertEquals(0, SQLHelper.getOrdersFromDatabase());
    }


    @Test
    @DisplayName("Неверно заполнено поле Номер карты, при вводе 16 нулей")
    public void shouldIncorrectCardNumberZeros() {
        buy.fieldCardNumberInsert(DataHelper.CARD_NUMBER_ZEROS);
        buy.fieldCardMonthInsert(DataHelper.getMonthValid());
        buy.fieldCardYearInsert(DataHelper.getYearValid());
        buy.fieldCardholderInsert(DataHelper.getCardholderFullNameEn());
        buy.fieldCardCvcInsert(DataHelper.getCvc());
        buy.buttonContinueClick();
        buy.shouldHaveMsgIncorrectFormat();
        assertEquals(0, SQLHelper.getOrdersFromDatabase());
    }

    @Test
    @DisplayName(" Неверно заполнено поле Номер карты, слишком короткое значение, 15 цифр")
    public void shouldIncorrectCardNumberShort() {
        buy.fieldCardNumberInsert(DataHelper.getCardNumberShort());
        buy.fieldCardMonthInsert(DataHelper.getMonthValid());
        buy.fieldCardYearInsert(DataHelper.getYearValid());
        buy.fieldCardholderInsert(DataHelper.getCardholderFullNameEn());
        buy.fieldCardCvcInsert(DataHelper.getCvc());
        buy.buttonContinueClick();
        buy.shouldHaveMsgIncorrectFormat();
        assertEquals(0, SQLHelper.getOrdersFromDatabase());
    }


    // Заполнение карты поле "Месяц"

    @Test
    @DisplayName(" Неверно заполнено поле Месяц, пустое значение")
    public void shouldIncorrectMonthEmpty() {
        buy.fieldCardNumberInsert(DataHelper.CARD_NUMBER_APPROVED);
        buy.fieldCardYearInsert(DataHelper.getYearValid());
        buy.fieldCardholderInsert(DataHelper.getCardholderFullNameEn());
        buy.fieldCardCvcInsert(DataHelper.getCvc());
        buy.buttonContinueClick();
        buy.shouldHaveMsgRequiredField();
        assertEquals(0, SQLHelper.getOrdersFromDatabase());
    }

    @Test
    @DisplayName("Неверно заполнено поле Месяц, ввод латинских букв")
    public void shouldIncorrectMonthLetters() {
        buy.fieldCardNumberInsert(DataHelper.CARD_NUMBER_APPROVED);
        buy.fieldCardMonthInsert(DataHelper.getLetters());
        buy.fieldCardYearInsert(DataHelper.getYearValid());
        buy.fieldCardholderInsert(DataHelper.getCardholderFullNameEn());
        buy.fieldCardCvcInsert(DataHelper.getCvc());
        buy.buttonContinueClick();
        buy.shouldHaveMsgRequiredField();
        assertEquals(0, SQLHelper.getOrdersFromDatabase());
    }

    @Test
    @DisplayName("Неверно заполнено поле Месяц, ввод спецсимволов")
    public void shouldIncorrectMonthSymbols() {
        buy.fieldCardNumberInsert(DataHelper.CARD_NUMBER_APPROVED);
        buy.fieldCardMonthInsert(DataHelper.getSymbol());
        buy.fieldCardYearInsert(DataHelper.getYearValid());
        buy.fieldCardholderInsert(DataHelper.getCardholderFullNameEn());
        buy.fieldCardCvcInsert(DataHelper.getCvc());
        buy.buttonContinueClick();
        buy.shouldHaveMsgRequiredField();
        assertEquals(0, SQLHelper.getOrdersFromDatabase());
    }


    @Test
    @DisplayName("Неверно заполнено поле Месяц, при вводе одной цифры")
    public void shouldIncorrectMonthShort() {
        buy.fieldCardNumberInsert(DataHelper.CARD_NUMBER_APPROVED);
        buy.fieldCardYearInsert(DataHelper.getYearValid());
        buy.fieldCardMonthInsert(getOneDigit());
        buy.fieldCardholderInsert(DataHelper.getCardholderFullNameEn());
        buy.fieldCardCvcInsert(DataHelper.getCvc());
        buy.buttonContinueClick();
        buy.shouldHaveMsgIncorrectFormat();
        assertEquals(0, SQLHelper.getOrdersFromDatabase());
    }

    @Test
    @DisplayName(" Неверно заполнено поле Месяц, значения больше 12")
    public void shouldIncorrectMonth13() {
        buy.fieldCardNumberInsert(DataHelper.CARD_NUMBER_APPROVED);
        buy.fieldCardMonthInsert(DataHelper.getMonthInvalid13());
        buy.fieldCardYearInsert(DataHelper.getYearValid());
        buy.fieldCardholderInsert(DataHelper.getCardholderFullNameEn());
        buy.fieldCardCvcInsert(DataHelper.getCvc());
        buy.buttonContinueClick();
        buy.shouldHaveMsgIncorrectDate();
        assertEquals(0, SQLHelper.getOrdersFromDatabase());
    }

    @Test
    @DisplayName(" Неверно заполнено поле Месяц, введены нули")
    public void shouldIncorrectMonthZeros() {
        buy.fieldCardNumberInsert(DataHelper.CARD_NUMBER_APPROVED);
        buy.fieldCardMonthInsert(DataHelper.DATE_ZEROS);
        buy.fieldCardYearInsert(DataHelper.getYearValid());
        buy.fieldCardholderInsert(DataHelper.getCardholderFullNameEn());
        buy.fieldCardCvcInsert(DataHelper.getCvc());
        buy.buttonContinueClick();
        buy.shouldHaveMsgIncorrectDate();
        assertEquals(0, SQLHelper.getOrdersFromDatabase());
    }


    // Заполнение карты поле "Год"


    @Test
    @DisplayName(" Неверно заполнено поле Год, пустое значение")
    public void shouldIncorrectYearEmpty() {
        buy.fieldCardNumberInsert(DataHelper.CARD_NUMBER_APPROVED);
        buy.fieldCardMonthInsert(DataHelper.getMonthValid());
        buy.fieldCardholderInsert(DataHelper.getCardholderFullNameEn());
        buy.fieldCardCvcInsert(DataHelper.getCvc());
        buy.buttonContinueClick();
        buy.shouldHaveMsgRequiredField();
        assertEquals(0, SQLHelper.getOrdersFromDatabase());
    }

    @Test
    @DisplayName("Неверно заполнено поле Год, ввод букв")
    public void shouldIncorrectYearLetters() {
        buy.fieldCardNumberInsert(DataHelper.CARD_NUMBER_APPROVED);
        buy.fieldCardMonthInsert(DataHelper.getMonthValid());
        buy.fieldCardYearInsert(DataHelper.getLetters());
        buy.fieldCardholderInsert(DataHelper.getCardholderFullNameEn());
        buy.fieldCardCvcInsert(DataHelper.getCvc());
        buy.buttonContinueClick();
        buy.shouldHaveMsgIncorrectFormat();
        assertEquals(0, SQLHelper.getOrdersFromDatabase());
    }

    @Test
    @DisplayName("Неверно заполнено поле Год, ввод спецсимволов")
    public void shouldIncorrectYearSymbols() {
        buy.fieldCardNumberInsert(DataHelper.CARD_NUMBER_APPROVED);
        buy.fieldCardMonthInsert(DataHelper.getMonthValid());
        buy.fieldCardYearInsert(DataHelper.getSymbol());
        buy.fieldCardholderInsert(DataHelper.getCardholderFullNameEn());
        buy.fieldCardCvcInsert(DataHelper.getCvc());
        buy.buttonContinueClick();
        buy.shouldHaveMsgIncorrectFormat();
        assertEquals(0, SQLHelper.getOrdersFromDatabase());
    }

    @Test
    @DisplayName("Неверно заполнено поле Год, при вводе одной цифры")
    public void shouldIncorrectYearShort() {
        buy.fieldCardNumberInsert(DataHelper.CARD_NUMBER_APPROVED);
        buy.fieldCardMonthInsert(DataHelper.getMonthValid());
        buy.fieldCardYearInsert(DataHelper.getOneDigit());
        buy.fieldCardholderInsert(DataHelper.getCardholderFullNameEn());
        buy.fieldCardCvcInsert(DataHelper.getCvc());
        buy.buttonContinueClick();
        buy.shouldHaveMsgIncorrectFormat();
        assertEquals(0, SQLHelper.getOrdersFromDatabase());
    }


    @Test
    @DisplayName("Неверно заполнено поле Год, нули")
    public void shouldIncorrectYearZeros() {
        buy.fieldCardNumberInsert(DataHelper.CARD_NUMBER_APPROVED);
        buy.fieldCardMonthInsert(DataHelper.getMonthValid());
        buy.fieldCardYearInsert(DataHelper.DATE_ZEROS);
        buy.fieldCardholderInsert(DataHelper.getCardholderFullNameEn());
        buy.fieldCardCvcInsert(DataHelper.getCvc());
        buy.buttonContinueClick();
        buy.shouldHaveMsgExpiredDate();
        assertEquals(0, SQLHelper.getOrdersFromDatabase());
    }

    // Заполнение карты поле "Владелец"

    @Test
    @DisplayName("Неверно заполнено поле Владелец, пустое значение")
    public void shouldIncorrectCardholderEmpty() {
        buy.fieldCardNumberInsert(DataHelper.CARD_NUMBER_APPROVED);
        buy.fieldCardMonthInsert(DataHelper.getMonthValid());
        buy.fieldCardYearInsert(DataHelper.getYearValid());
        buy.fieldCardCvcInsert(DataHelper.getCvc());
        buy.buttonContinueClick();
        buy.shouldHaveMsgRequiredField();
        assertEquals(0, SQLHelper.getOrdersFromDatabase());
    }

    @Test
    @DisplayName(" Неверно заполнено поле Владелец, ввод спецсимволов")
    public void shouldIncorrectCardholderSymbols() {
        buy.fieldCardNumberInsert(DataHelper.CARD_NUMBER_APPROVED);
        buy.fieldCardMonthInsert(DataHelper.getMonthValid());
        buy.fieldCardYearInsert(DataHelper.getYearValid());
        buy.fieldCardholderInsert(DataHelper.getSymbol());
        buy.fieldCardCvcInsert(DataHelper.getCvc());
        buy.buttonContinueClick();
        buy.shouldHaveMsgIncorrectFormat();
        assertEquals(0, SQLHelper.getOrdersFromDatabase());
    }

    @Test
    @DisplayName(" Неверно заполнено поле Владелец, ввод цифр")
    public void shouldIncorrectCardholderDigits() {
        buy.fieldCardNumberInsert(DataHelper.CARD_NUMBER_APPROVED);
        buy.fieldCardMonthInsert(DataHelper.getMonthValid());
        buy.fieldCardYearInsert(DataHelper.getYearValid());
        buy.fieldCardholderInsert(getOneDigit());
        buy.fieldCardCvcInsert(DataHelper.getCvc());
        buy.buttonContinueClick();
        buy.shouldHaveMsgRequiredField();
        assertEquals(0, SQLHelper.getOrdersFromDatabase());
    }

    @Test
    @DisplayName(" Неверно заполнено поле Владелец, ввод кириллицы")
    public void shouldIncorrectCardholderCyrillic() {
        buy.fieldCardNumberInsert(DataHelper.CARD_NUMBER_APPROVED);
        buy.fieldCardMonthInsert(DataHelper.getMonthValid());
        buy.fieldCardYearInsert(DataHelper.getYearValid());
        buy.fieldCardholderInsert(DataHelper.getCardholderFullNameRu());
        buy.fieldCardCvcInsert(DataHelper.getCvc());
        buy.buttonContinueClick();
        buy.shouldHaveMsgIncorrectFormat();
        assertEquals(0, SQLHelper.getOrdersFromDatabase());
    }


    // Заполнение карты поле "CVC/CVV"


        @Test
        @DisplayName(" Неверно заполнено поле CVC/CVV, пустое значение")
        public void shouldIncorrectCvcEmpty() {
            buy.fieldCardNumberInsert(DataHelper.CARD_NUMBER_APPROVED);
            buy.fieldCardMonthInsert(DataHelper.getMonthValid());
            buy.fieldCardYearInsert(DataHelper.getYearValid());
            buy.fieldCardholderInsert(DataHelper.getCardholderFullNameEn());
            buy.buttonContinueClick();
            buy.shouldHaveMsgRequiredField();
            assertEquals(0, SQLHelper.getOrdersFromDatabase());
        }

        @Test
        @DisplayName(" Неверно заполнено поле CVC/CVV, ввод букв")
        public void shouldIncorrectCvcLetters() {
            buy.fieldCardNumberInsert(DataHelper.CARD_NUMBER_APPROVED);
            buy.fieldCardMonthInsert(DataHelper.getMonthValid());
            buy.fieldCardYearInsert(DataHelper.getYearValid());
            buy.fieldCardholderInsert(DataHelper.getCardholderFullNameEn());
            buy.fieldCardCvcInsert(DataHelper.getLetters());
            buy.buttonContinueClick();
            buy.shouldHaveMsgIncorrectFormat();
            assertEquals(0, SQLHelper.getOrdersFromDatabase());
        }

        @Test
        @DisplayName(" Неверно заполнено поле CVC/CVV, ввод спецсимволов")
        public void shouldIncorrectCvcSymbols() {
            buy.fieldCardNumberInsert(DataHelper.CARD_NUMBER_APPROVED);
            buy.fieldCardMonthInsert(DataHelper.getMonthValid());
            buy.fieldCardYearInsert(DataHelper.getYearValid());
            buy.fieldCardholderInsert(DataHelper.getCardholderFullNameEn());
            buy.fieldCardCvcInsert(DataHelper.getSymbol());
            buy.buttonContinueClick();
            buy.shouldHaveMsgIncorrectFormat();
            assertEquals(0, SQLHelper.getOrdersFromDatabase());
        }

        @Test
        @DisplayName(" Неверно заполнено поле CVC/CVV, слишком короткое значение")
        public void shouldIncorrectCvcShort() {
            buy.fieldCardNumberInsert(DataHelper.CARD_NUMBER_APPROVED);
            buy.fieldCardMonthInsert(DataHelper.getMonthValid());
            buy.fieldCardYearInsert(DataHelper.getYearValid());
            buy.fieldCardholderInsert(DataHelper.getCardholderFullNameEn());
            buy.fieldCardCvcInsert(DataHelper.getOneDigit());
            buy.buttonContinueClick();
            buy.shouldHaveMsgIncorrectFormat();
            assertEquals(0, SQLHelper.getOrdersFromDatabase());
        }

        @Test
        @DisplayName(" Неверно заполнено поле CVC/CVV, нули")
        public void shouldIncorrectCvcZeros() {
            buy.fieldCardNumberInsert(DataHelper.CARD_NUMBER_APPROVED);
            buy.fieldCardMonthInsert(DataHelper.getMonthValid());
            buy.fieldCardYearInsert(DataHelper.getYearValid());
            buy.fieldCardholderInsert(DataHelper.getCardholderFullNameEn());
            buy.fieldCardCvcInsert(DataHelper.CVC_ZEROS);
            buy.buttonContinueClick();
            buy.shouldHaveMsgIncorrectFormat();
            assertEquals(0, SQLHelper.getOrdersFromDatabase());
        }

    }