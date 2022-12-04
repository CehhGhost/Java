import java.util.*;

/**
 * Публичный класс игра, реализующий всю основную логику работы игры Reversy
 * Приватные поля:
 * desk - доска текущей игры
 * backUp - стек досок для реализации отмены хода(откат к предыдущим доскам)
 * deskColor - цвет доски
 * player1Color - цвет точки первого игрока
 * player2Color - цвет точки второго игрока
 * player1 - первый игрок
 * player2 - второй игрок
 * order - порядок хода (= 0/1)
 * playing - флаг для определения идет ли еще матч, или пора его закончить
 * bestScore - лучший результат среди всех матчей
 */
public class Game {
    private Desk desk;
    private Stack<Desk> backUp = new Stack<>();
    private final int deskColor = 40;
    private final int player1Color = 31;
    private final int player2Color = 34;
    private Player player1 = new Human(1);
    private Player player2 = new Human(2);
    private int order = 0;
    private boolean playing = false;
    private static Integer bestScore = 0;

    /**
     * Публичный метод для открытия начального меню
     * @throws InterruptedException - ошибка, если вдрыг произойдет ошибка при работе игрока-компьютера(использован Thread.sleep)
     */
    public void openMenu() throws InterruptedException {
        boolean exit = false;
        while (!exit) {
            Scanner in = new Scanner(System.in);
            int n;
            System.out.printf((char)27 + "[%d;49m\tМеню\r\n", player2Color);
            System.out.printf((char)27 + "[39;49m");
            System.out.println("Выберите один из вариантов:");
            System.out.print("""
                1) Начать играть с выбранными настройками
                2) Настройки
                3) Выйти из игры
                """);
            System.out.print("Ваш вариант = ");
            try {
                n = in.nextInt();
            } catch (InputMismatchException e) {
                System.out.print("Невозможно прочитать символ X(вводите числа), попробуйте снова\r\n");
                continue;
            }
            if (n <= 0 || n > 3) {
                System.out.print("Такого варианта нет, попробуйте снова\r\n");
                continue;
            }
            switch (n) {
                case 1:
                    this.generateGame();
                    break;
                case 2:
                    openSettings();
                    break;
                case 3:
                    exit = true;
                    break;
            }
        }
    }

    /**
     * Приватный метод для открывания меню настроек
     */
    private void openSettings() {
        boolean exit = false;
        while (!exit) {
            Scanner in = new Scanner(System.in);
            int n;
            System.out.printf((char)27 + "[%d;49m\tНастройки\r\n", player2Color);
            System.out.printf((char)27 + "[39;49m");
            System.out.println("Выберите один из вариантов:");
            System.out.printf("""
                1) Настройки игроков(сейчас играют:%s vs %s)
                2) Посмотреть рекордный счет
                3) Вернуться в меню
                """, (player1 instanceof Human) ? "Человек" : "Компьютер", (player2 instanceof Human) ? "Человек" : "Компьютер");
            System.out.print("Ваш вариант = ");
            try {
                n = in.nextInt();
            } catch (InputMismatchException e) {
                System.out.print("Невозможно прочитать символ X(вводите числа), попробуйте снова\r\n");
                continue;
            }
            if (n <= 0 || n > 4) {
                System.out.print("Такого варианта нет, попробуйте снова\r\n");
                continue;
            }
            switch (n) {
                case 1 -> setPlayers();
                case 2 -> {System.out.printf((char)27 + "[%d;49mРекордный счет: %d\r\n", player1Color, bestScore); System.out.printf((char)27 + "[39;49m");}
                case 3 -> exit = true;
            }
        }
    }

    /**
     * Приватный метод, для открывания меню настроек игроков
     */
    private void setPlayers() {
        int players = 1;
        while(players <= 2) {
            Scanner in = new Scanner(System.in);
            int n;
            System.out.printf((char)27 + "[%d;49m\tНастройки %d-го игрока\r\n", player2Color, players);
            System.out.printf((char)27 + "[39;49m");
            System.out.println("Выберите один из вариантов:");
            System.out.print("""
                1) Человек
                2) Компьютер
                """);
            try {
                n = in.nextInt();
            } catch (InputMismatchException e) {
                System.out.print("Невозможно прочитать символ X(вводите числа), попробуйте снова\r\n");
                continue;
            }
            if (n <= 0 || n > 2) {
                System.out.print("Такого варианта нет, попробуйте снова\r\n");
                continue;
            }
            switch (n) {
                case 1 -> {
                    if (players == 1) {
                        player1 = new Human(players);
                    } else if (players == 2) {
                        player2 = new Human(players);
                    }
                    ++players;
                }
                case 2 -> {
                    setComputer(players);
                    ++players;
                }
            }
        }
    }

    /**
     * Приватный метод для настройки игрока-компьютера
     * @param number номер игрока
     */
    private void setComputer(int number) {
        System.out.printf((char)27 + "[%d;49m\tНастройки компьютера\r\n", player2Color);
        System.out.printf((char)27 + "[39;49m");
        boolean flag = false;
        int level = 0;
        int robotTime = 0;
        while(!flag) {
            Scanner in = new Scanner(System.in);
            System.out.println("Выберите уровень компьютера(от 0 до 2)");
            try {
                level = in.nextInt();
            } catch (InputMismatchException e) {
                System.out.print("Невозможно прочитать символ X(вводите числа), попробуйте снова\r\n");
                continue;
            }
            if (level < 0 || level > 2) {
                System.out.print("Данный уровень отсутвует, попробуйте снова\r\n");
                continue;
            }
            flag = true;
        }
        flag = false;
        while(!flag) {
            Scanner in = new Scanner(System.in);
            System.out.println("Выберите время отклика компьютера(за сколько милисекунд будет совершаться ход,\r\n чем больше, тем проще понимать следить за игрой)(от 0 до 5000)");
            try {
                robotTime = in.nextInt();
            } catch (InputMismatchException e) {
                System.out.print("Невозможно прочитать символ X(вводите числа), попробуйте снова\r\n");
                continue;
            }
            if (robotTime < 0 || robotTime > 5000) {
                System.out.print("Данный уровень отсутвует, попробуйте снова\r\n");
                continue;
            }
            flag = true;
        }
        if (number == 1) {
            player1 = new Computer(number, level, robotTime);
        } else if (number == 2) {
            player2 = new Computer(number, level, robotTime);
        }
    }

    /**
     * Приватный метод, для начала матча
     * @throws InterruptedException - ошибка, если вдрыг произойдет ошибка при работе игрока-компьютера(использован Thread.sleep)
     */
    private void generateGame() throws InterruptedException {
        Point[][] points = new Point[8][8];
        for (int i = 0; i < points.length; ++i) {
            for (int j = 0; j < points[i].length; ++j) {
                points[i][j] = new Point(i, j, -1);
            }
        }
        points[3][3].setValue(1);
        points[3][4].setValue(0);
        points[4][3].setValue(0);
        points[4][4].setValue(1);
        desk = new Desk(points);
        order = 0;
        backUp = new Stack<>();
        playingGame();
    }

    /**
     * Приватный метод, для запуска матча
     * @throws InterruptedException - ошибка, если вдрыг произойдет ошибка при работе игрока-компьютера(использован Thread.sleep)
     */
    private void playingGame() throws InterruptedException {
        playing = true;
        while(playing) {
            var amount = desk.drawDesk(player1Color, player2Color, deskColor, order);
            Point res;
            if (order == 0) {
                if (player1 instanceof Human) {
                    res = ((Human) player1).getPossibleActions();
                }
                else {
                    res = player1.makeATurn();
                }
            } else {
                if (player2 instanceof Human) {
                    res = ((Human) player2).getPossibleActions();
                }
                else {
                    res = player2.makeATurn();
                }
            }
            if (!playing) {
                System.out.printf("Игра закончилась, по причине сдачи игрока номер %d => ", order + 1);
                if (order == 0) {
                    System.out.print("Победил ");
                    System.out.printf((char)27 + "[%d;49mВторой игрок со счетом: %d", player2Color, amount.getSecond());
                    System.out.printf((char)27 + "[39;49m\r\n");
                    if (amount.getSecond() > Game.bestScore) {
                        System.out.println("Что является рекордом!");
                        Game.bestScore = amount.getSecond();
                    }
                } else {
                    System.out.print("Победил ");
                    System.out.printf((char)27 + "[%d;49mПервый игрок со счетом: %d", player1Color, amount.getFirst());
                    System.out.printf((char)27 + "[39;49m\r\n");
                    if (amount.getFirst() > Game.bestScore) {
                        System.out.println("Что является рекордом!");
                        Game.bestScore = amount.getFirst();
                    }
                }
                continue;
            }
            if (!player1.canMakeATurn && !player2.canMakeATurn) {
                playing = false;
                System.out.println("Игра закончилась, по причине невозможности хода обоих игроков!");
                if (amount.getFirst() > amount.getSecond()) {
                    System.out.print("Победил ");
                    System.out.printf((char)27 + "[%d;49mПервый игрок со счетом: %d", player1Color, amount.getFirst());
                    System.out.printf((char)27 + "[39;49m\r\n");
                    if (amount.getFirst() > Game.bestScore) {
                        System.out.println("Что является рекордом!");
                        Game.bestScore = amount.getFirst();
                    }
                } else if (amount.getFirst() < amount.getSecond()) {
                    System.out.print("Победил ");
                    System.out.printf((char)27 + "[%d;49mВторой игрок со счетом: %d", player2Color, amount.getSecond());
                    System.out.printf((char)27 + "[39;49m\r\n");
                    if (amount.getSecond() > Game.bestScore) {
                        System.out.println("Что является рекордом!");
                        Game.bestScore = amount.getSecond();
                    }
                } else {
                    System.out.print("Партия окончилась ничьей!");
                }
            } else if (!player1.canMakeATurn) {
                System.out.println("Первый игрок пропускает ход(некуда ходить)");
            } else if (!player2.canMakeATurn) {
                System.out.println("Второй игрок пропускает ход(некуда ходить)");
            }
            if (res == null) {
                order = (order + 1) % 2;
                continue;
            }
            backUp.push(new Desk(desk));
            desk.changeColor(res, order);
            order = (order + 1) % 2;
        }
    }

    /**
     * Вложенный приватный абстрактный класс игрок, от которого наследуются все игроки(человек/компьютер)
     * Публичные поля:
     * canMakeATurn - может ли игрок сделать ход или нет
     * Протектед поля:
     * number - номер игрока
     */
    private abstract class Player {
        public boolean canMakeATurn = false;
        protected int number;

        /**
         * Протектед конструктор, создающий игрока по его номеру
         * @param num номер игрока
         */
        protected Player(int num) {
            number = num;
        }

        /**
         * Публичный абстрактный метод, для выполнения хода игрока
         * @return Возвращает точку, которую выбрал игрок
         * @throws InterruptedException - ошибка, если вдрыг произойдет ошибка при работе игрока-компьютера(использован Thread.sleep)
         */
        protected abstract Point makeATurn() throws InterruptedException;

        /**
         * Протектед итоговый метод, для получения пары (доска с отмеченными возможными ходами; список пар возможных ходов (точка; вес данной точки))(первое значение использует человек, второе - компьютер)
         * @return пара (доска с отмеченными возможными ходами; список пар возможных ходов (точка; вес данной точки))
         */
        protected final Pair<Desk, ArrayList<Pair<Point, Double>>> getPossibleTurns() {
            ArrayList<Pair<Point, Double>> res = new ArrayList<>();
            Desk possibleDesk = new Desk(desk);
            int possibleCounter = 0;
            canMakeATurn = false;
            for (int i = 0; i < possibleDesk.length; ++i) {
                for (int j = 0; j < possibleDesk.length; ++j) {
                    if (possibleDesk.get(i, j) == number - 1) {
                        for (int k = -1; k < 2; ++k) {
                            for (int l = -1; l < 2; ++l) {
                                Double price = (double) 0;
                                if ( !(k == 0 && l == 0) && i + k >= 0 && i + k < possibleDesk.length && j + l >= 0 && j + l < possibleDesk.length && possibleDesk.get(i +k, j + l) == (number % 2)) {
                                    int i1 = i + k;
                                    int j1 = j + l;
                                    while(i1 + k >= 0 && i1 + k < possibleDesk.length && j1 + l >= 0 && j1 + l < possibleDesk.length && possibleDesk.get(i1, j1) == (number % 2)) {
                                        if (i1 == 0 || i1 == possibleDesk.length - 1 || j1 == 0 || j1 == possibleDesk.length - 1) {
                                            price += 2;
                                        } else {
                                            price += 1;
                                        }
                                        i1 += k;
                                        j1 += l;
                                    }
                                    if (possibleDesk.get(i1, j1) == -1) {
                                        if (i1 == 0 || i1 == possibleDesk.length - 1) {
                                            price += 0.4;
                                        }
                                        if (j1 == 0 || j1 == possibleDesk.length - 1) {
                                            price += 0.4;
                                        }
                                        boolean haveVal = false;
                                        for (int m = 0; m < res.size(); ++m) {
                                            if (res.get(m).getFirst().getX() == possibleDesk.getPoint(i1, j1).getX() && res.get(m).getFirst().getY() == possibleDesk.getPoint(i1, j1).getY()) {
                                                res.get(m).setSecond(res.get(m).getSecond() + price);
                                                haveVal = true;
                                            }
                                        }
                                        if (!haveVal) {
                                            res.add(new Pair<>(possibleDesk.getPoint(i1, j1), price));
                                        }
                                        ++possibleCounter;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (possibleCounter > 0) {
                canMakeATurn = true;
            }
            for (int i = 0; i < res.size(); ++i) {
                possibleDesk.set(res.get(i).getFirst().getX(), res.get(i).getFirst().getY(), -2);
            }
            return new Pair<>(possibleDesk, res);
        }
    }

    /**
     * Вложенный приватный класс человек, расширяющий класс игрок
     */
    private class Human extends Player {
        /**
         * Приватный конструктор человек, создающий игрока по номеру(использует родительский конструктор)
         * @param num номер игрока-человека
         */
        private Human(int num) {
            super(num);
        }

        /**
         * Приватный метод для открытия меню выбора действий игрока-человека
         * @return Возвращает точку, которую выбрал игрок, если решил сделать ход
         */
        private Point getPossibleActions() {
            boolean exit = false;
            while (!exit) {
                Scanner in = new Scanner(System.in);
                int actionsAmount = 2;
                if (backUp.size() > 1) {
                    actionsAmount = 3;
                }
                int action;
                System.out.printf((char) 27 + "[%d;49m\tДоступные действия\r\n", player2Color);
                System.out.printf((char) 27 + "[39;49m");
                System.out.println("Выберите один из вариантов:");
                System.out.print("""
                        1) Сделать ход
                        2) Сдаться(игра завершиться и вернется в меню, присудив сопернику победу)
                        """);
                if (actionsAmount >= 3) {
                    System.out.print("3) Вернуться к моему предудущему ходу\r\n");
                }
                System.out.print("Ваш вариант = ");
                try {
                    action = in.nextInt();
                } catch (InputMismatchException e) {
                    System.out.print("Невозможно прочитать символ X(вводите числа), попробуйте снова\r\n");
                    continue;
                }
                if (action <= 0 || action > actionsAmount) {
                    System.out.print("Такого варианта нет, попробуйте снова\r\n");
                    continue;
                }
                switch (action) {
                    case 1 -> {return this.makeATurn();}
                    case 2 -> playing = false;
                    case 3 -> {backUp.pop(); desk = backUp.pop(); order = (order + 1) % 2;}
                }
                exit = true;
            }
            return null;
        }

        /**
         * Перегруженный протектед метод для выполнения хода игрока
         * @return Возвращает точку, которую выбрал игрок
         */
        @Override
        protected Point makeATurn() {
            Point res;
            Desk possibleDesk = new Desk(getPossibleTurns().getFirst());
            if (!this.canMakeATurn) {
                return null;
            }
            possibleDesk.drawDesk(player1Color, player2Color, deskColor, order);
            while (true) {
                Scanner in = new Scanner(System.in);
                int x;
                int y;
                System.out.println("Выберите точку и введите ее позицию (одну из белых точек):");
                System.out.print("x = ");
                try {
                    x = in.nextInt();
                } catch (InputMismatchException e) {
                    System.out.print("Невозможно прочитать символ X(вводите числа)");
                    continue;
                }
                System.out.print("y = ");
                try {
                    y = in.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("Невозможно прочитать символ Y(вводите числа)");
                    continue;
                }
                if (x < 0 || y < 0 || x >= possibleDesk.length || y >= possibleDesk.length) {
                    System.out.println("Некорректная позиция");
                    continue;
                } else if (possibleDesk.get(x, y) != -2) {
                    System.out.println("Данный ход недоступен (выбирайте среди белых точек)");
                    continue;
                } else if (possibleDesk.get(x, y) == -2) {
                    res = new Point(x, y, number - 1);
                    break;
                }
            }
            return res;
        }
    }

    /**
     * Вложенный приватный класс человек, расширяющий класс игрок
     * Приватные поля:
     * robotTime время отклика компьютера на ход(чтобы удобней было следить за матчем)
     * level уровень компьютера(нереализованный функционал(вне зависимости от выбора будет играться нулевой))
     */
    private class Computer extends Player {
        private final int robotTime;
        private int level;

        /**
         * Приватный конструктор человек, создающий игрока по номеру(использует родительский конструктор), уровню и времени отклика
         * @param num номер игрока
         * @param level уровень компьютера(нереализованный функционал(вне зависимости от выбора будет играться нулевой))
         * @param robotTime время отклика компьютера на ход(чтобы удобней было следить за матчем)
         */
        private Computer(int num, int level, int robotTime) {
            super(num);
            this.level = level;
            this.robotTime = robotTime;
        }
        /**
         * Перегруженный протектед метод для выполнения хода игрока
         * @return Возвращает точку, которую выбрал игрок
         */
        @Override
        protected Point makeATurn() throws InterruptedException {
            Point res = null;
            ArrayList<Pair<Point, Double>> possiblePoints = getPossibleTurns().getSecond();
            if (!this.canMakeATurn) {
                return null;
            }
            Double max = (double)0;
            for (int i = 0; i < possiblePoints.size(); ++i) {
                if (possiblePoints.get(i).getSecond() > max) {
                    max = possiblePoints.get(i).getSecond();
                    res = possiblePoints.get(i).getFirst();
                }
            }
            Thread.sleep(robotTime);
            return res;
        }
    }
}
