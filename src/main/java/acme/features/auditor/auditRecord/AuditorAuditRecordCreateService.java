
package acme.features.auditor.auditRecord;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.codeAudits.AuditRecord;
import acme.entities.codeAudits.CodeAudit;
import acme.entities.codeAudits.Mark;
import acme.roles.Auditor;

@Service
public class AuditorAuditRecordCreateService extends AbstractService<Auditor, AuditRecord> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuditorAuditRecordRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int masterId;
		CodeAudit codeaudit;

		masterId = super.getRequest().getData("masterId", int.class);
		codeaudit = this.repository.findOneCodeAuditById(masterId);
		status = codeaudit != null && super.getRequest().getPrincipal().hasRole(codeaudit.getAuditor());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		AuditRecord object = new AuditRecord();
		int masterId;
		CodeAudit codeaudit;

		masterId = super.getRequest().getData("masterId", int.class);
		codeaudit = this.repository.findOneCodeAuditById(masterId);

		object.setCodeaudit(codeaudit);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final AuditRecord object) {
		assert object != null;

		super.bind(object, "code", "startPeriod", "endPeriod", "mark", "furtherInformationLink");

	}

	@Override
	public void validate(final AuditRecord object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			AuditRecord existing = this.repository.findOneAuditRecordByCode(object.getCode());
			super.state(existing == null, "code", "auditor.audit-record.form.error.duplicated");
		}

		if (!super.getBuffer().getErrors().hasErrors("endPeriod")) {

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(object.getStartPeriod());
			calendar.add(Calendar.HOUR_OF_DAY, 1);
			Date minimumEnd = calendar.getTime();

			super.state(object.getEndPeriod().after(minimumEnd), "endPeriod", "auditor.audit-record.form.error.too-close");
		}
	}

	@Override
	public void perform(final AuditRecord object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final AuditRecord object) {
		assert object != null;

		SelectChoices choices;
		Dataset dataset;

		choices = SelectChoices.from(Mark.class, object.getMark());

		dataset = super.unbind(object, "code", "startPeriod", "endPeriod", "mark", "furtherInformationLink");
		dataset.put("marks", choices);
		dataset.put("masterId", super.getRequest().getData("masterId", int.class));

		super.getResponse().addData(dataset);
	}

}
