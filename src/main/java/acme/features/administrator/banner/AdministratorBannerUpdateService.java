
package acme.features.administrator.banner;

import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Administrator;
import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.group.Banner;

@Service
public class AdministratorBannerUpdateService extends AbstractService<Administrator, Banner> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AdministratorBannerRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int masterId;
		Banner banner;

		masterId = super.getRequest().getData("id", int.class);
		banner = this.repository.findOneBannerById(masterId);
		status = banner != null && MomentHelper.isBefore(banner.getInstantiationMoment(), banner.getStartPeriod()) && MomentHelper.isLongEnough(banner.getStartPeriod(), banner.getEndPeriod(), 7, ChronoUnit.DAYS);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Banner object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneBannerById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Banner object) {
		assert object != null;

		super.bind(object, "startPeriod", "endPeriod", "picture", "slogan", "target");
	}

	@Override
	public void validate(final Banner object) {
		assert object != null;
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

		Dataset dataset;

		dataset = super.unbind(object, "startPeriod", "endPeriod", "picture", "slogan", "target");

		super.getResponse().addData(dataset);
	}
}
