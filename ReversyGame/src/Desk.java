import java.sql.Array;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Публичный класс игральная доска
 * Публичные поля класса:
 * length - поле для быстрого доступа к длине доски(8)
 * Приватные поля класса:
 * desk - поле для хранение всей доски ввиде двумерного массива точек
 */
public class Desk {
    public int length;
    private Point[][] desk;

    /**
     * Гетор-индексатор, быстрый доступ к значению нужной для нас точки
     * @param x координата точки X
     * @param y координата точки Y
     * @return значение интересующей нас точки
     */
    public int get(int x, int y) {
        return desk[x][y].getValue();
    }

    /**
     * Сетор-индексатор, быстрый доступ к записи значения нужной для нас точки
     * @param x координата точки X
     * @param y координата точки Y
     * @param value значение, которое хотим записать
     */
    public void set(int x, int y, int value) {
        desk[x][y].setValue(value);
    }

    /**
     * Быстрый доступ к интересующей нас точке
     * @param x координата точки X
     * @param y координата точки Y
     * @return интересующая нас точка
     */
    public Point getPoint(int x, int y) {
        return desk[x][y];
    }
    /**
     * Публичный конструктор, создает доску по массиву точек
     * @param array массив точек
     */
    public Desk(Point[][] array) {
        length = array.length;
        desk = array;
    }
    /**
     * Публичный конструктор копирования, создает доску по глубокой копии другой доски
     * @param other другая доски для копирования
     */
    public Desk(Desk other) {
        desk = new Point[other.desk.length][];
        for (int i = 0; i < other.desk.length; ++i) {
            desk[i] = new Point[other.desk[i].length];
            for (int j = 0; j < other.desk[i].length; ++j) {
                desk[i][j] = new Point(other.desk[i][j]);
            }
        }
        length = other.length;
    }
    /**
     * Метод для смены цвета точек, которые замкнули
     * @param changer замыкающая точка
     * @param order порядок хода (= 0/1)
     */
    public void changeColor(Point changer, int order) {
        this.set(changer.getX(), changer.getY(), order);
        int x = changer.getX();
        int y = changer.getY();
        for (int k = -1; k < 2; ++k) {
            for (int l = -1; l < 2; ++l) {
                ArrayList<Point> reversePoints = new ArrayList<>();
                if ( !(k == 0 && l == 0) && x + k >= 0 && x + k < desk.length && y + l >= 0 && y + l < desk.length && this.get(x + k, y + l) == ((order + 1) % 2)) {
                    int x1 = x + k;
                    int y1 = y + l;
                    while(x1 + k >= 0 && x1 + k < this.length && y1 + l >= 0 && y1 + l < this.length && this.get(x1, y1) == ((order + 1) % 2)) {
                        reversePoints.add(desk[x1][y1]);
                        x1 += k;
                        y1 += l;
                    }
                    if (this.get(x1, y1) == order) {
                        for (int i = 0; i < reversePoints.size(); ++i) {
                            this.set(reversePoints.get(i).getX(),reversePoints.get(i).getY(),order);
                        }
                    }
                }
            }
        }
    }

    /**
     * Метод  отрисовки доски
     * @param player1Color цвет точки первого игрока
     * @param player2Color цвет точки второго игрока
     * @param deskColor цвет доски
     * @param order порядок хода (= 0/1)
     * @return возвращает пару значений (количество точек первого игрока; количество точек второго игрока)
     */
    public Pair<Integer, Integer> drawDesk(int player1Color, int player2Color, int deskColor, int order) {
        System.out.print("Поле (");
        System.out.printf((char)27 + "[%d;49mцвет первого игрока", player1Color);
        System.out.print(", ");
        System.out.printf((char)27 + "[%d;49mцвет второго игрока", player2Color);
        System.out.print(", ");
        System.out.printf((char)27 + "[37;49mцвет вашего возможного хода");
        System.out.printf((char)27 + "[39;49m");
        System.out.print("):\n");
        System.out.print("   0  1  2  3  4  5  6  7\n");
        Integer firstAmount = 0;
        Integer secondAmount = 0;
        for (int j = 0; j < desk.length; ++j) {
            for (int i = 0; i < desk[j].length; ++i) {
                if (i == 0) {
                    System.out.printf("%d ", j);
                }
                if (desk[i][j].getValue() == -1) {
                    System.out.printf((char)27 + "[30;%dm   ", deskColor);
                } else if (desk[i][j].getValue() == -2) {
                    System.out.printf((char)27 + "[37;%dm * ", deskColor);
                } else if (desk[i][j].getValue() == 0) {
                    System.out.printf((char)27 + "[%d;%dm * ", player1Color, deskColor);
                    ++firstAmount;
                } else if (desk[i][j].getValue() == 1) {
                    System.out.printf((char)27 + "[%d;%dm * ", player2Color, deskColor);
                    ++secondAmount;
                } else {
                    System.out.printf((char)27 + "[35;%dm # ", deskColor);
                }
                if (i == desk.length - 1 && j == 3) {
                    System.out.printf((char)27 + "[39;49m");
                    System.out.print(" Ход: ");
                    System.out.printf((char)27 + "[%d;49m%s", (order == 0) ? player1Color : player2Color, (order == 0) ? "первого" : "второго");
                    System.out.printf((char)27 + "[39;49m");
                    System.out.print(" игрока");
                }
                System.out.printf((char)27 + "[39;49m");
            }
            System.out.printf((char)27 + "[39;49m\r\n");
        }
        System.out.printf((char)27 + "[%d;49mПервый игрок: %d", player1Color, firstAmount);
        System.out.printf((char)27 + "[39;49m, ");
        System.out.printf((char)27 + "[%d;49mВторой игрок: %d", player2Color, secondAmount);
        System.out.printf((char)27 + "[39;49m\r\n");
        return new Pair<>(firstAmount, secondAmount);
    }
}
