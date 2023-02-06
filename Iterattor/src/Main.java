import java.util.ArrayList;
import java.util.List;
public class Main {
    public static void main(String[] args) {
        HseGroup bpi217 = new HseGroup();
        bpi217.insertStudent("Цейтин Андрей");
        bpi217.insertStudent("Троицкий Денис");
        bpi217.insertStudent("Епифанов Артем");
        bpi217.insertStudent("Пересторонин Максим");
        bpi217.deleteStudent("Троицкий Денис");
        bpi217.deleteStudent("Иван Иванов");
        System.out.println("Состав группы 217:");
        for (Iterator iter = bpi217.getIterator(); iter.hasNext();) {
            System.out.println(iter.next());
        }
    }
}