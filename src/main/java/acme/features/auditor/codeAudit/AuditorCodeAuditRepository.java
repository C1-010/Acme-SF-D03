
package acme.features.auditor.codeAudit;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.codeAudits.AuditRecord;
import acme.entities.codeAudits.CodeAudit;
import acme.entities.projects.Project;
import acme.roles.Auditor;

@Repository
public interface AuditorCodeAuditRepository extends AbstractRepository {

	@Query("select a from Auditor a where a.id = :id")
	Auditor findOneAuditorById(int id);

	@Query("select p from Project p")
	Collection<Project> findAllProjects();

	@Query("select p from Project p where p.id = :projectId")
	Project findOneProjectById(int projectId);

	@Query("select ca from CodeAudit ca where ca.auditor.id = :auditorId")
	Collection<CodeAudit> findManyCodeAuditsByAuditorId(int auditorId);

	@Query("select ca from CodeAudit ca where ca.id = :id")
	CodeAudit findOneCodeAuditById(int id);

	@Query("select ca from CodeAudit ca where ca.code = :code")
	CodeAudit findOneCodeAuditByCode(String code);

	@Query("select ar from AuditRecord ar where ar.codeaudit.id = :codeauditId")
	Collection<AuditRecord> findManyAuditRecordsByCodeAuditId(int codeauditId);

	@Query("select ca.project from CodeAudit ca where ca.id = :codeauditId")
	Collection<Project> findManyProjectsByCodeAuditId(int codeauditId);

	@Query("select ar from AuditRecord ar")
	Collection<AuditRecord> findAllAuditRecords();

	@Query("select p from Project p where p.draftMode = false")
	Collection<Project> findManyPublishedProjects();

}
