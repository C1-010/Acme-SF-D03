
package acme.features.sponsor.invoice;

import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.sponsorships.Invoice;
import acme.entities.sponsorships.Sponsorship;
import acme.roles.Sponsor;

@Service
public class SponsorInvoiceUpdateService extends AbstractService<Sponsor, Invoice> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private SponsorInvoiceRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int invoiceId;
		Sponsorship sponsorship;

		invoiceId = super.getRequest().getData("id", int.class);
		sponsorship = this.repository.findOneSponsorshipByInvoiceId(invoiceId);
		status = sponsorship != null && sponsorship.isDraftMode() && super.getRequest().getPrincipal().hasRole(sponsorship.getSponsor());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Invoice object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneInvoiceById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Invoice object) {
		assert object != null;

		super.bind(object, "code", "dueDate", "quantity", "tax", "link");
	}

	@Override
	public void validate(final Invoice object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Invoice existing;

			existing = this.repository.findOneInvoiceByCode(object.getCode());
			super.state(existing == null || existing.equals(object), "code", "sponsor.invoice.form.error.duplicated");
		}
		if (!super.getBuffer().getErrors().hasErrors("dueDate"))
			super.state(MomentHelper.isLongEnough(object.getRegistrationTime(), object.getDueDate(), 30, ChronoUnit.DAYS), "dueDate", "sponsor.invoice.form.error.notMinimum");
		if (!super.getBuffer().getErrors().hasErrors("quantity"))
			super.state(object.getQuantity().getAmount() > 0 && object.getQuantity().getAmount() <= 1000000.00, "quantity", "sponsor.invoice.form.error.negative-quantity");
		if (!super.getBuffer().getErrors().hasErrors("tax"))
			super.state(object.getTax() >= 0 && object.getTax() <= 1.00, "tax", "sponsor.invoice.form.error.negative-tax");

	}

	@Override
	public void perform(final Invoice object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Invoice object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "code", "dueDate", "quantity", "tax", "link");
		dataset.put("masterId", object.getSponsorship().getId());
		dataset.put("draftMode", object.getSponsorship().isDraftMode());

		super.getResponse().addData(dataset);
	}

}
