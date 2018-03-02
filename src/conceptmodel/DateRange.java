package conceptmodel;

import java.time.LocalDate;

/** 
 * This class represents a 'date-range' consisting of two LocalDate instances
 * where the first one serves as 'starting date' and the second as 'end date'
 * Thus, it can be used to store and edit information about a period in which 
 * someone has a certain schedule, is enlisted at ICT Academie and so on.
 * 
 * @author ttimmermans
 * @version 02-03-2018
 */

public class DateRange {
	
	private LocalDate startDate;
	private LocalDate endDate;
	
	/**
	 * Constructor. Assign and initialize starting- and ending date.
	 * @param  startDate   the start date
	 * @param  endDate   the end date
	 */
	public DateRange(LocalDate startDate, LocalDate endDate) {
		this.startDate = startDate;
		this.endDate = endDate;
	}

	/**
	 * Get the starting date.
	 * @return  the start date
	 */
	public LocalDate getStartDate() {
		return startDate;
	}

	/**
	 * Get the ending date.
	 * @return  the end date
	 */
	public LocalDate getEndDate() {
		return endDate;
	}
	
	/**
	 * Set the starting date.
	 * @param  startDate  the start date
	 */
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	/**
	 * Set the ending date.
	 * @param  endDate  the end date
	 */
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

}
