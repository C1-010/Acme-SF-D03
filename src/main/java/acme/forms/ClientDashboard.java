
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
