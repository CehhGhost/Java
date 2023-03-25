public class Gamer extends Subscriber {
    public Gamer(String name, Console console) {
        super(name, console);
    }

    @Override
    public void notifySub(GameInfo info) {
        System.out.println("Игрок " + name + " только что узнал о новой игре на " + consoleName + " под названием: " + info.getName() + ". И в ней будут достижения:\r\n" + info.getAchievements());
    }
}
