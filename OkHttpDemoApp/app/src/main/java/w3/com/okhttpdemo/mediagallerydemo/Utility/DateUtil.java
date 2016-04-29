package w3.com.okhttpdemo.mediagallerydemo.Utility;

/**
 * Created by shihab on 10/2/2015.
 */
public class DateUtil {
    public static String DATE_FORMAT_1 = "MMM dd, yyyy";
    public static String DATE_FORMAT_2 = "MM/dd/yyyy hh:mm:ss aa";

    public static String getDateString(long date) {

        android.text.format.DateFormat df = new android.text.format.DateFormat();
        String today = df.format(DATE_FORMAT_1, System.currentTimeMillis()).toString();
        String yesterday = df.format(DATE_FORMAT_1, System.currentTimeMillis() - 24 * 60 * 60 * 1000).toString();

        String day = df.format(DATE_FORMAT_1, date).toString();

        if (day.equals(today)) {
            return "Today";
        }
        if (day.equals(yesterday)) {
            return "Yesterday";
        }


        return day;

    }

    public static String getDateString(long date, String format) {

        android.text.format.DateFormat df = new android.text.format.DateFormat();
        String dateString = df.format(format, date).toString();

        return dateString;

    }


}


