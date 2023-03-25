import java.util.ArrayList;
import java.util.List;

public class Console {
    private String name;
    private List<Subscriber> subscribers;
    private GameInfo info;

    public Console(String name) {
        subscribers = new ArrayList<>();
        this.name = name;
    }

    public void SetNewGame(GameInfo newGameInfo) {
        this.info = newGameInfo;
        notifySubscribers();
    }

    public void RegisterSubscriber(Subscriber sub) {
        subscribers.add(sub);
    }

    public void RemoveSubscriber(Subscriber sub) {
        subscribers.remove(sub);
    }

    private void notifySubscribers() {
        for (Subscriber parishioner : subscribers)
            parishioner.notifySub(info);
    }

    public String getName() {
        return name;
    }
}
