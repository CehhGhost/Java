import org.junit.jupiter.api.*;

import org.example.Farm;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class FarmTest {
    private static Farm farm = null;

    @BeforeAll
    static void setup() {
        System.out.println("Начинаем наши проверки!");
        farm = new Farm();
    }

    @Test
    @DisplayName("Посчитаем овец")
    void testCalcAddOne() {
        var expected = 5;
        var result = farm.CountSheep(5);
        Assertions.assertEquals(expected, result);
    }

    @DisplayName("Посмотрим чем занимается фермер")
    @ParameterizedTest
    @ValueSource(strings = {"0:00", "0:15", "12:12", "5:40", "8:54"})
    void testCalcIsOdd(String time) {
        Assertions.assertEquals(farm.EverydayRoutine(time), "Опять работать");
    }

    @Test
    @DisplayName("Проверим коров")
    void testCalcSubOne() {
        var expected = "Мууу";
        var result = farm.CheckCowsVoices(5);
        Assertions.assertEquals(expected, result);
    }

    @AfterAll
    static void tear() {
        System.out.println("Вот и конец всех проверок");
    }
}