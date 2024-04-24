
package acme.features.auditor.auditRecord;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.codeAudits.AuditRecord;
import acme.entities.codeAudits.CodeAudit;
import acme.roles.Auditor;

@Service
public class AuditorAuditRecordListService extends AbstractService<Auditor, AuditRecord> {

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
		status = codeaudit != null && (!codeaudit.isDraftMode() || super.getRequest().getPrincipal().hasRole(codeaudit.getAuditor()));

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Collection<AuditRecord> objects;
		int masterId;

		masterId = super.getRequest().getData("masterId", int.class);
		objects = this.repository.findManyAuditRecordsByMasterId(masterId);

		super.getBuffer().addData(objects);
	}

	@Override
	public void unbind(final AuditRecord object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "code", "startPeriod", "endPeriod", "mark");

		super.getResponse().addData(dataset);
	}

	@Override
	public void unbind(final Collection<AuditRecord> objects) {
		assert objects != null;

		int masterId;
		CodeAudit codeaudit;
		final boolean showCreate;

		masterId = super.getRequest().getData("masterId", int.class);
		codeaudit = this.repository.findOneCodeAuditById(masterId);
		showCreate = codeaudit.isDraftMode() && super.getRequest().getPrincipal().hasRole(codeaudit.getAuditor());

		super.getResponse().addGlobal("masterId", masterId);
		super.getResponse().addGlobal("showCreate", showCreate);
	}

}
