public class Main {
    public static void main(String[] args) {
        SomeClass calculatorTests = new SomeClass();
        for (java.lang.reflect.Method method : SomeClass.class.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Printable.class)) {
                try {
                    method.invoke(calculatorTests);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}