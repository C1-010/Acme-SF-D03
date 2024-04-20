
package acme.features.administrator.banner;

import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Administrator;
import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.group.Banner;

@Service
public class AdministratorBannerCreateService extends AbstractService<Administrator, Banner> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AdministratorBannerRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Banner object = new Banner();

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Banner object) {
		assert object != null;

		super.bind(object, "instantiationMoment", "startPeriod", "endPeriod", "picture", "slogan", "target");
	}

	@Override
	public void validate(final Banner object) {
		assert object != null;

		//boolean confirmation;

		//confirmation = super.getRequest().getData("confirmation", boolean.class);
		//super.state(confirmation, "confirmation", "javax.validation.constraints.AssertTrue.message");

		if (!super.getBuffer().getErrors().hasErrors("endPeriod"))
			super.state(MomentHelper.isBefore(object.getInstantiationMoment(), object.getStartPeriod()) && MomentHelper.isLongEnough(object.getStartPeriod(), object.getEndPeriod(), 7, ChronoUnit.DAYS), "endPeriod",
				"administrator.banner.form.error.notMinimum");

	}

	@Override
	public void perform(final Banner object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Banner object) {
		assert object != null;

		SelectChoices choices;
		Dataset dataset;

		dataset = super.unbind(object, "instantiationMoment", "startPeriod", "endPeriod", "picture", "slogan", "target");

		super.getResponse().addData(dataset);
	}

}
