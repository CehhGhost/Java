public class Main {
    public static void main(String[] args) {
        Console XBox256 = new Console("XBox25");
        Console PS10 = new Console("PS10");
        new Gamer("Вася", XBox256);
        new Gamer("Петя", XBox256);
        new Gamer("Маша", PS10);
        new Programmer("Игорь", XBox256);
        new Programmer("Михаил", PS10);
        new Journalist("Антонио", XBox256);
        new Journalist("Чилинтанно", PS10);
        new Journalist("Чин Хон Сы", PS10);
        var newGame = new GameInfo("EldunRung", "Резня, Маг, Призыватель, Честолюб, Задрот, Кувырок", "Легендарное продолжение серии, с еще большим числом боссов и уроней сложности!", "Игна основанна на кастылях, страданиях, боли и 1000 заглушек");
        XBox256.SetNewGame(newGame);
        PS10.SetNewGame(newGame);
    }
}