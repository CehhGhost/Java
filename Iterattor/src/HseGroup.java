import java.util.ArrayList;
import java.util.List;

// предположим, что группа Вышки - контейнер
public class HseGroup implements Container {
    // список студентов в группе
    private List<String> students = new ArrayList<String>();
    // зачисляем студента
    public void insertStudent (String student) {
        students.add(student);
        System.out.println("Добавляю студента: " + student + " в группу!");
    }
    // отчисляем студента
    public void deleteStudent (String student) {
        int index = 0;
        for (Iterator iter = this.getIterator(); iter.hasNext(); ++index) {
            if (iter.next() == student) {
                students.remove(index);
                System.out.println("Отчисляю студента: " + student + "(причину придумаем позже)!");
                return;
            }
        }
        System.out.println("Ваш гнев не оправдан, такого студента нет в группе!");
    }

    @Override
    public Iterator getIterator() {
        return new StudentIterator();
    }
    // итератор для прохождения по группе
    private class StudentIterator implements Iterator {
        int index = 0;
        @Override
        public boolean hasNext() {
            return index < students.size();
        }

        @Override
        public Object next() {
            if (hasNext()) {
                return students.get(index++);
            }
            return null;
        }
    }
}
