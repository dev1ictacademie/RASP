package tests;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This class checks if the application is running in the required time zone
 * and throws an exception if not.
 * 
 * This class also provides a mechanism for automatically exiting the 
 * application at a certain time of this current date. 
 * 
 * This auto-shutdown is implemented for the off chance that users forget to 
 * exit the application at the end of the day which would lead to currentDate
 * variables used by other classes to no longer refer to the correct date after
 * midnight when the calendar rolls over since the calls to LocalDate.now() 
 * are made when instances of these classes initialize.
 * 
 * @author ttimmermans
 * @version 24-08-2018
 */
public class TestTimeGuard {
	
	private Calendar now = Calendar.getInstance();
	private Timer timer = new Timer();
	private ExitTask exitTask = new ExitTask();
	
	/**
	 * Constructor
	 */
	public TestTimeGuard() {
		
		validateTimeZone();
		
		now.setLenient(false); 
		// Setting leniency to false prevents autoExitTodayAt-method below 
		// from accepting inappropriate arguments being passed to it.
		
		autoExitTodayAt(23, 45);
	}
	
	/**
	 * Check if the application is executing in Central European TimeZone.
	 */
	private void validateTimeZone() {
		Object requiredZone = "Europe/Amsterdam";
		if (!now.getTimeZone().getID().equals(requiredZone)) {
			throw new IllegalTimeZoneException("Application should start in " +
					"Central European TimeZone.");
		}
	}

	/**
	 * Set a time for today at which the application will auto-terminate.
	 * 
	 * @param hour    the hour (0-23) of today's time at which application 
	 *                will terminate.
	 * @param minute  the minute (0-59) of today's time at which application 
	 *                will terminate.
	 */
	private void autoExitTodayAt(int hour, int minute) {
		now.set(now.get(Calendar.YEAR), now.get(Calendar.MONTH), 
				now.get(Calendar.DAY_OF_MONTH), hour, minute, 0);
		timer.schedule(exitTask, now.getTime());
	}
	
}

/**
 * An instance of this class will terminate the application when run and is 
 * suitable to be scheduled by a Timer.
 */
class ExitTask extends TimerTask {
	
	@Override
	public void run() {
		System.out.println("Terminating...");
		System.exit(0);
	}
	
}

/**
 * Thrown to indicate that an application is executing in an illegal or
 * inappropriate TimeZone.
 */
class IllegalTimeZoneException extends RuntimeException {

	public IllegalTimeZoneException() {
        super();
    }

    public IllegalTimeZoneException(String s) {
        super(s);
    }
    
	private static final long serialVersionUID = 311522023684136995L;

}
