public class Journalist extends Subscriber {

    public Journalist(String name, Console console) {
        super(name, console);
    }

    @Override
    public void notifySub(GameInfo info) {
        System.out.println("Журналист  " + name + " только что узнал, что новая игра на " + consoleName + " представляет из себя:\r\n" + info.getShortDescription());
    }
}
