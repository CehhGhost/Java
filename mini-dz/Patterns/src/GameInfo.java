public class GameInfo {
    private final String name;
    private final String achievements;
    private final String shortDescription;
    private final String technicalData;
    public GameInfo(String name, String achievements, String shortDescription, String technicalData) {
        this.name = name;
        this.achievements = achievements;
        this.shortDescription = shortDescription;
        this.technicalData = technicalData;
    }

    public String getName() {
        return name;
    }
    public String getTechnicalData() {
        return technicalData;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getAchievements() {
        return achievements;
    }
}
