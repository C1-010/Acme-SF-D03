
package acme.forms;

import acme.client.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuditorDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	Integer						totalNumberOfCodeAuditsForStaticType;
	Integer						totalNumberOfCodeAuditsForDynamicType;
	Double						averageNumberOfAuditRecords;
	Double						deviationNumberOfAuditRecords;
	Double						minimumNumberOfAuditRecords;
	Double						maximumNumberOfAuditRecords;

	Double						averageTimeOfThePeriodLength;
	Double						deviationTimeOfThePeriodLength;
	Double						minimumTimeOfThePeriodLength;
	Double						maximumTimeOfThePeriodLength;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

}
