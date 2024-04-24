
package acme.features.sponsor.sponsorship;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.projects.Project;
import acme.entities.sponsorships.Sponsorship;
import acme.roles.Sponsor;

@Service
public class SponsorSponsorshipShowService extends AbstractService<Sponsor, Sponsorship> {

	// Internal state

	@Autowired
	private SponsorSponsorshipRepository repository;

	// AbstractService interface


	@Override
	public void authorise() {
		boolean status;
		int masterId;
		Sponsorship sponsorship;
		Sponsor sponsor;

		masterId = super.getRequest().getData("id", int.class);
		sponsorship = this.repository.findOneSponsorshipById(masterId);
		sponsor = sponsorship == null ? null : sponsorship.getSponsor();
		status = super.getRequest().getPrincipal().hasRole(sponsor) || sponsorship != null && !sponsorship.isDraftMode();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Sponsorship object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneSponsorshipById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void unbind(final Sponsorship object) {
		assert object != null;

		int sponsorshipId;
		Collection<Project> projects;
		SelectChoices choices;
		Dataset dataset;

		if (!object.isDraftMode())
			projects = this.repository.findAllProjects();
		else {
			sponsorshipId = super.getRequest().getData("id", int.class);
			projects = this.repository.findManyProjectsBySponsorshipId(sponsorshipId);
		}

		choices = SelectChoices.from(projects, "code", object.getProject());

		dataset = super.unbind(object, "code", "startDuration", "endDuration", "amount", "type", "email", "link", "draftMode");
		dataset.put("project", choices.getSelected().getKey());
		dataset.put("projects", choices);

		super.getResponse().addData(dataset);
	}

}
