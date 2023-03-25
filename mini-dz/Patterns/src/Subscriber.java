public abstract class Subscriber {
    protected String consoleName;
    protected String name;

    public Subscriber(String name, Console console) {
        this.name = name;
        console.RegisterSubscriber(this);
        consoleName = console.getName();
    }

    public abstract void notifySub(GameInfo info);
}
