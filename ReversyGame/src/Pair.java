/**
 * Публичный класс пара, необходим для удобного хранения и возвращения некоторых данных
 * Приватные поля:
 * value1 - первое значение
 * value2 - второе значение
 * @param <T> тип первого значения
 * @param <S> тип второго значения
 */
public class Pair<T, S> {
    private T value1;
    private S value2;

    /**
     * Публичный конструктор для создания пары двух элементов
     * @param val1 первое записываемое значение
     * @param val2 второе записываемое значение
     */
    public Pair(T val1, S val2) {
        value1 = val1;
        value2 = val2;
    }

    /**
     * Публичный геттер для получения первого значения
     * @return первое значение
     */
    public T getFirst() {
        return value1;
    }
    /**
     * Публичный геттер для получения второго значения
     * @return второе значение
     */
    public S getSecond() {
        return value2;
    }
    /**
     * Публичный сеттер для записи первого значения
     * @param value значение, которое хотим присвоить
     */
    public void setFirst(T value) {
        value1 = value;
    }
    /**
     * Публичный сеттер для записи второго значения
     * @param value значение, которое хотим присвоить
     */
    public void setSecond(S value) {
        value2 = value;
    }
}
