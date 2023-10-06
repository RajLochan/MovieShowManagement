package mbp.booking.util;

import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.UUID;

public class Common {
    private Common() {
    }

    private static final SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String getKey(Object... input) {
        StringBuilder keyBuilder = new StringBuilder();
        Arrays.stream(input).forEach(v -> keyBuilder.append(v).append(":"));
        return keyBuilder.toString();
    }

    public static String getKeyWithFiller(String filler, String... input) {
        StringBuilder keyBuilder = new StringBuilder();
        Arrays.stream(input).forEach(v -> keyBuilder.append(v).append(filler));
        return keyBuilder.toString();
    }

    public static String getCartId() {
        return UUID.randomUUID().toString();
    }

    public static String getTransactionId() {
        return UUID.randomUUID().toString();
    }

    public static java.util.Date combineDateTime(Date sqlDate, Time sqlTime) throws ParseException {
        StringBuilder dateTimeBuilder = new StringBuilder(sqlDate.toString()).append(" ").append(sqlTime);
        java.util.Date dateTime = dateTimeFormatter.parse(dateTimeBuilder.toString());
        return dateTime;
    }

    public static ShowType getShowType(Time time){
        LocalTime localTime = time.toLocalTime();
        return classifyTime(localTime);
    }
    public static ShowType classifyTime(LocalTime time) {
        if (time.isAfter(LocalTime.of(0, 0)) && time.isBefore(LocalTime.of(12, 0))) {
            return ShowType.Morning;
        } else if (time.isAfter(LocalTime.of(12, 0)) && time.isBefore(LocalTime.of(17, 0))) {
            return ShowType.Afternoon;
        } else if (time.isAfter(LocalTime.of(17, 0)) && time.isBefore(LocalTime.of(21, 0))) {
            return ShowType.Evening;
        } else {
            return ShowType.Night;
        }
    }
}
