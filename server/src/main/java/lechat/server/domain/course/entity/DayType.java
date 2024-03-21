package lechat.server.domain.course.entity;

public enum DayType {
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY,
    SUNDAY;

    public static DayType fromDayOfWeek(String dayOfWeek) {
        return switch (dayOfWeek) {
            case "MONDAY" -> MONDAY;
            case "TUESDAY" -> TUESDAY;
            case "WEDNESDAY" -> WEDNESDAY;
            case "THURSDAY" -> THURSDAY;
            case "FRIDAY" -> FRIDAY;
            case "SATURDAY" -> SATURDAY;
            case "SUNDAY" -> SUNDAY;
            default -> throw new IllegalArgumentException("Unknown DayOfWeek: " + dayOfWeek);
        };
    }

}
