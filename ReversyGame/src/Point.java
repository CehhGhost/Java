/**
 * Публичный класс точка, из которых состоит доска
 * Приватные поля:
 * x - координата точки X
 * y - координата точки Y
 * value - значение точки(= -2/-1/0/1 = возможный ход/пустая клетка/точка первого игрока/точка второго игрока)
 */
public class Point {
    private int x;
    private int y;
    private int value;

    /**
     * Публичный конструктор, создающий точку по переданным параметрам
     * @param x координата точки X
     * @param y координата точки Y
     * @param value значение точки(= -2/-1/0/1 = возможный ход/пустая клетка/точка первого игрока/точка второго игрока)
     */
    Point(int x, int y, int value) {
        this.x = x;
        this.y = y;
        this.value = value;
    }

    /**
     * Публичный конструктор копирования, создает точку по глубокой копии другой точки
     * @param other другая точка для копирования
     */
    Point(Point other) {
        this.x = other.x;
        this.y = other.y;
        this.value = other.value;
    }

    /**
     * Публичный геттер, для получения координаты точки X
     * @return координата точки X
     */
    public int getX() {
        return x;
    }

    /**
     * Публичный геттер, для получения координаты точки Y
     * @return координата точки Y
     */
    public int getY() {
        return y;
    }

    /**
     * Публичный геттер, для получения значения точки
     * @return значение точки(= -2/-1/0/1 = возможный ход/пустая клетка/точка первого игрока/точка второго игрока)
     */
    public int getValue() {
        return value;
    }

    /**
     * Публичный сеттер, для записи значения точки
     * @param value значение, которое хотим записать
     */
    public void setValue(int value) {
        this.value = value;
    }
}
