package gundam.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DataUtils extends org.apache.commons.lang.time.DateUtils{
    public static final String DATE_FORMAT_YEAR_MONTH_DAY = "yyyy-MM-dd";
    public static final String DATE_FORMAT_HOUR_MINUTE_SECOND = "HH:mm:ss";
    public static final String DATE_FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND = "yyyy-MM-dd HH:mm:ss";

    public static final String DATE_FORMAT_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    public static final ThreadLocal<DateFormat> dateFormatYearMonthDay = new ThreadLocal<DateFormat>();

    static{
        dateFormatYearMonthDay.set(new SimpleDateFormat(DATE_FORMAT_YEAR_MONTH_DAY));
    }

    public static Timestamp now(){
        return new Timestamp(System.currentTimeMillis());
    }

    public static java.util.Date parseDate(String strDateValue, String format) throws Exception{
        return new SimpleDateFormat(format).parse(strDateValue);
    }

    public static java.util.Date toDate(Timestamp timestamp){
        return new java.util.Date(timestamp.getTime());
    }

    public static Timestamp addDays(Timestamp date, int days){
        return new Timestamp(org.apache.commons.lang.time.DateUtils.addDays(toDate(date), days).getTime());
    }

    public static String formatNow(String format){
        DateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date());
    }

    public static String formatTime(Timestamp date, String format){
        return DataUtils.formatTime(date.getTime(), format);
    }

    public static String formatTime(Date date, String format){
        return DataUtils.formatTime(date.getTime(), format);
    }

    public static String formatTime(long timeInMills, String format){
        DateFormat dateFormat = new SimpleDateFormat(format);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeInMills);
        return dateFormat.format(calendar.getTime());
    }

    public static Timestamp truncate(Timestamp date, int field){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date.getTime());
        Date retDate = org.apache.commons.lang.time.DateUtils.truncate(calendar.getTime(), field);
        return new Timestamp(retDate.getTime());
    }

    public static Timestamp getExpireDate(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2099);
        calendar.set(Calendar.MONTH, 12);
        calendar.set(Calendar.DAY_OF_MONTH, 31);
        Date retDate = truncate(calendar.getTime(), Calendar.HOUR_OF_DAY);
        return new Timestamp(retDate.getTime());
    }
}
