import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Locale;

public class DateFormatter
{
    public static String formatDateToStringForDisplay(LocalDate date)
    {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("E dd MMM uuuu",new Locale("en"));
        return dateTimeFormatter.format(date);
    }


    public static String formatDateToStringForSaving(LocalDate date)
    {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-uuuu");
        return dateTimeFormatter.format(date);
    }

    public static LocalDate formatStringToDate(String date) throws DateTimeParseException
    {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-uuuu").withResolverStyle(ResolverStyle.STRICT);
        return LocalDate.parse(date,dateTimeFormatter);
    }
}
