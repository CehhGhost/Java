package org.example;

public class Farm {
    public int CountSheep(int sheppAmount) {
        int i = 1;
        for (; i <= sheppAmount; ++i) {
            System.out.println("Овца номер: " + i);
            if (Math.random() > 0.7) { // не судите строго, он же фермер, парень не очень образованный, может и обсчетаться
                ++i;
            }
        }
        return i;
    }
    public String EverydayRoutine(String time) {
        // иллюзия выбора работяги фермера:
        switch(time) {
            case "0:00":
                return "Опять работать";
            case "1:00":
                return "Опять работать";
            case "2:00":
                return "Опять работать";
            case "3:00":
                return "Опять работать";
            case "4:00":
                return "Опять работать";
            default:
                return "Опять работать";
        }
    }
    public String CheckCowsVoices(int cowsAmount) {
        for (int i = 0; i < cowsAmount; ++i) {
            if (Math.random() > 0.7) {
                return "Мяу"; // очевидно, что обычно коровы не говорят мяу
            }
        }
        return "Мууу";
    }
}
