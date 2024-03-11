
package acme.forms;

import acme.client.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdministratorDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	Integer						numberOfAuditors;
	Integer						numberOfClients;
	Integer						numberOfConsumers;
	Integer						numberOfDevelopers;
	Integer						numberOfManagers;
	Integer						numberOfProviders;
	Integer						numberOfSponsors;

	Double						ratioOfNoticesWithEmailAndLink;
	Double						ratioOfCriticalObjectives;
	Double						ratioOfNonCriticalObjectives;

	Double						averageValueInRisks;
	Double						deviationValueInRisks;
	Double						maximumValueInRisks;
	Double						minimumValueInRisks;

	Double						averageClaimsPostedLast10Weeks;
	Double						deviationClaimsPostedLast10Weeks;
	Double						maximumClaimsPostedLast10Weeks;
	Double						minimumClaimsPostedLast10Weeks;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

}
