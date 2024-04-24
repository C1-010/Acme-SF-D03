
package acme.forms;

import acme.client.data.AbstractForm;
import acme.client.data.datatypes.Money;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SponsorDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	Integer						numberOfInvoicesWithTaxLessEqualThan21;
	Integer						numberOfSponsorshipsWithLink;

	Money						averageSponsorshipsAmount;
	Money						deviationSponsorshipsAmount;
	Money						maximumSponsorshipsAmount;
	Money						minimumSponsorshipsAmount;

	Money						averageInvoicesQuantity;
	Money						deviationInvoicesQuantity;
	Money						maximumInvoicesQuantity;
	Money						minimumInvoicesQuantity;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

}
