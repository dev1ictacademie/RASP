package tests;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class TimeSafeguard {
	
	private static Calendar c1 = Calendar.getInstance();
	private static Timer timer = new Timer();
	private static ExitTask exitTask = new ExitTask();
	
	/**
	 * Constructor
	 */
	public TimeSafeguard() {
		
		validateTimeZone();
		
		c1.setLenient(false); 
		// Setting leniency to false prevents autoExitTodayAt-method below 
		// from accepting inappropriate arguments being passed to it.
		
		autoExitTodayAt(23, 45);
	}
	
	/**
	 * Check if the application is executing in Central European TimeZone.
	 */
	private static void validateTimeZone() {
		Object requiredZone = "Europe/Amsterdam";
		if (!c1.getTimeZone().getID().equals(requiredZone)) {
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
	private static void autoExitTodayAt(int hour, int minute) {
		c1.set(c1.get(Calendar.YEAR), c1.get(Calendar.MONTH), 
				c1.get(Calendar.DAY_OF_MONTH), hour, minute, 0);
		timer.schedule(exitTask, c1.getTime());
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
