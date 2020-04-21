import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatter {
    private static String pattern = "dd-MM-yyyy";
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

    public static String formatDate(Date date){
        return simpleDateFormat.format(date);
    }
}
