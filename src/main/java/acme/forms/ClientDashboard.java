
package acme.forms;

import java.util.ArrayList;
import java.util.List;

import acme.client.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID				= 1L;

	// Attributes -------------------------------------------------------------

	// A list based on three quartiles, used to store the total number of progress
	// logs categorized by completeness ranges.
	List<Integer>				progressLogsByCompletenessRange	= new ArrayList<>();

	//Total number of progress logs with a completeness rate below 25%
	Integer						totalProgressLogsLessThan25;

	//Total number of progress logs with a completeness rate between 25% and 50%
	Integer						totalProgressLogsBetween25And50;

	//Total number of progress logs with a completeness rate between 50% and 75%
	Integer						totalProgressLogsBetween50And75;

	//Total number of progress logs with a completeness rate above 75%
	Integer						totalProgressLogsAbove75;

	Double						averageContractBudget;
	Double						deviationContractBudget;
	Double						minContractBudget;
	Double						maxContractBudget;


	public ClientDashboard() {
		for (int i = 0; i < 4; i++)
			this.progressLogsByCompletenessRange.add(0);
	}

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------
}
