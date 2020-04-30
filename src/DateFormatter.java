import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatter {
    private static String pattern = "dd-MM-yyyy";
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

    public static String formatDateToString(Date date){
        return simpleDateFormat.format(date);
    }

    public static Date formatStringToDate(String date) throws ParseException {
        return simpleDateFormat.parse(date);
    }
}
