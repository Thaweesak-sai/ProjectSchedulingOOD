import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Locale;


/**
 * A class using for formatting date and string
 *
 *   Created by Jednipit Tantaletong (Pleum) 60070503411
 *              Thaweesa Saiwongse (Note) 60070503429
 *              26/04/2020
 */
public class DateFormatter
{

    /**
     * The method converts date to string according to the format
     * @param date date to format
     * @return string of date information in this format eg. "Tue 10 May 2020"
     */
    public static String formatDateToStringForDisplay(LocalDate date)
    {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("E dd MMM uuuu",new Locale("en"));
        return dateTimeFormatter.format(date);
    }


    /**
     * The method converts date to string according to the format
     * @param date date to format
     * @return string of date information in this format eg. "10-05-2020"
     */
    public static String formatDateToStringForSaving(LocalDate date)
    {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-uuuu");
        return dateTimeFormatter.format(date);
    }

    /**
     * The method converts string to date according to the format
     * @param date string to convert
     * @return date after converted
     * @throws DateTimeParseException exception when user enter string in wrong format or invalid date
     */
    public static LocalDate formatStringToDate(String date) throws DateTimeParseException
    {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-uuuu").withResolverStyle(ResolverStyle.STRICT);
        return LocalDate.parse(date,dateTimeFormatter);
    }
}
