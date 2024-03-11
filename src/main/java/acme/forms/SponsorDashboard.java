
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

	//total number of invoices with a tax less than or equal to 21.00%
	Integer						numberOfInvoicesWithTaxLessEqualThan21;
	Integer						numberOfSponsorshipsWithLink;

	Double						averageSponsorshipsAmount;
	Double						deviationSponsorshipsAmount;
	Money						maximumSponsorshipsAmount;
	Money						minimumSponsorshipsAmount;

	Double						averageInvoicesQuantity;
	Double						deviationInvoicesQuantity;
	Money						maximumInvoicesQuantity;
	Money						minimumInvoicesQuantity;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

}
