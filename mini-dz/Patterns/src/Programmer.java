public class Programmer extends Subscriber {

    public Programmer(String name, Console console) {
        super(name, console);
    }

    @Override
    public void notifySub(GameInfo info) {
        System.out.println("Разработчик  " + name + " только что узнал, что новая игра на " + consoleName + " содержит в себе техническую информацию:\r\n" + info.getTechnicalData());
    }
}
