
package acme.features.sponsor.sponsorship;

import java.time.temporal.ChronoUnit;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.projects.Project;
import acme.entities.sponsorships.Sponsorship;
import acme.roles.Sponsor;

@Service
public class SponsorSponsorshipCreateService extends AbstractService<Sponsor, Sponsorship> {

	//Internal state -------------------------------------------

	@Autowired
	private SponsorSponsorshipRepository repository;

	//AbstractService interface ---------------------------------


	@Override
	public void authorise() {
		/*
		 * int masterId;
		 * boolean status;
		 * Sponsorship sponsorship;
		 * 
		 * masterId = super.getRequest().getData("id", int.class);
		 * sponsorship = this.repository.findOneSponsorshipById(masterId);
		 * status = MomentHelper.isBefore(sponsorship.getMoment(), sponsorship.getStartDuration()) && MomentHelper.isLongEnough(sponsorship.getStartDuration(), sponsorship.getEndDuration(), 30, ChronoUnit.DAYS);
		 */
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Sponsorship object = new Sponsorship();
		Sponsor sponsor;

		sponsor = this.repository.findOneSponsorById(super.getRequest().getPrincipal().getActiveRoleId());

		object.setDraftMode(true);
		object.setSponsor(sponsor);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Sponsorship object) {
		assert object != null;

		int projectId;
		Project project;

		projectId = super.getRequest().getData("project", int.class);
		project = this.repository.findOneProjectById(projectId);

		super.bind(object, "code", "moment", "startDuration", "endDuration", "amount", "type", "email", "link");
		object.setProject(project);
	}

	@Override
	public void validate(final Sponsorship object) {
		assert object != null;

		//TODO the sum of the total amount of all their invoices must be equal to the amount of the sponsorship.
		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Sponsorship existing;

			existing = this.repository.findOneSponsorshipByCode(object.getCode());
			super.state(existing == null, "code", "sponsor.sponsorship.form.error.duplicated");
		}
		if (!super.getBuffer().getErrors().hasErrors("endDuration"))
			super.state(MomentHelper.isBefore(object.getMoment(), object.getStartDuration()) && MomentHelper.isLongEnough(object.getStartDuration(), object.getEndDuration(), 30, ChronoUnit.DAYS), "endDuration", "sponsor.sponsorship.form.error.notMinimum");

	}

	@Override
	public void perform(final Sponsorship object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Sponsorship object) {
		assert object != null;

		Collection<Project> projects;
		SelectChoices choices;
		Dataset dataset;

		projects = this.repository.findAllProjects();
		choices = SelectChoices.from(projects, "code", object.getProject());

		dataset = super.unbind(object, "code", "moment", "startDuration", "endDuration", "amount", "type", "email", "link", "draftMode");
		dataset.put("project", choices.getSelected().getKey());
		dataset.put("projects", choices);

		super.getResponse().addData(dataset);
	}

}