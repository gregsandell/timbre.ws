package temp;
import java.util.GregorianCalendar;
import java.util.Calendar;

/**
 *  A Palindromic date is one such as 01/11/10 or 11/22/11 (where the year is given only by the last two digits).
 *  This class takes a date on the command line and returns the soonest date after it that is palindromic.
 *  The code simply advances the date by one day until all the month, day and year requirements are met.
 *  This leads to nice looking code, but is not ideal in terms of execution time.  Since there are only six
 *  possible palindromic dates per ten year period, it would be quicker to simply look for those dates.
 *  Starting date must be in the form MM/DD/YYYY.
 */
public class NextPalindromicDate {
  private Calendar calendar;
  public NextPalindromicDate(Calendar _calendar) {
    calendar = _calendar;
    System.out.println("Starting date = " + calendar.getTime().toString());
    calendar.add(Calendar.DATE, 1);
    while (true) {
      if (isDay11() || isDay22()) {
        if (is0110() || is1001() || is1111()) {
          System.out.println("Next palindrome date is: " + calendar.getTime().toString());
          System.exit(0);
        }
      }
      calendar.add(Calendar.DATE, 1);
    }
  }
  public static void main(String[] argv) {
    String[] inputDate = argv[0].split("/");
    int month = (new Integer(inputDate[0])).intValue() - 1;
    int day = (new Integer(inputDate[1])).intValue();
    int year = (new Integer(inputDate[2])).intValue();
    NextPalindromicDate jp = new NextPalindromicDate(new GregorianCalendar(year, month, day));
  }
  private boolean is0110() {
    return(isMonth01() && isYear10());
  }
  private boolean is1001() {
    return(isMonth10() && isYear01());
  }
  private boolean is1111() {
    return(isMonth11() && isYear11());
  }
  private boolean isDay11() {
    return(calendar.get(Calendar.DATE) == 11);
  }
  private boolean isDay22() {
    return(calendar.get(Calendar.DATE) == 22);
  }
  private boolean isYear01() {
    return((calendar.get(Calendar.YEAR) % 100) == 1);
  }
  private boolean isYear10() {
    return((calendar.get(Calendar.YEAR) % 100) == 10);
  }
  private boolean isYear11() {
    return((calendar.get(Calendar.YEAR) % 100) == 11);
  }
  private boolean isMonth01() {
    return(calendar.get(Calendar.MONTH) == 0);
  }
  private boolean isMonth10() {
    return(calendar.get(Calendar.MONTH)  == 9);
  }
  private boolean isMonth11() {
    return(calendar.get(Calendar.MONTH)  == 10);
  }
}
