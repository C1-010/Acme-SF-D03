
package acme.features.auditor.auditRecord;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.codeAudits.AuditRecord;
import acme.entities.codeAudits.CodeAudit;

@Repository
public interface AuditorAuditRecordRepository extends AbstractRepository {

	@Query("select ca from CodeAudit ca where ca.id = :id")
	CodeAudit findOneCodeAuditById(int id);

	@Query("select ar.codeaudit from AuditRecord ar where ar.id = :id")
	CodeAudit findOneCodeAuditByAuditRecordId(int id);

	@Query("select ar from AuditRecord ar where ar.codeaudit.id = :masterId")
	Collection<AuditRecord> findManyAuditRecordsByMasterId(int masterId);

	@Query("select ar from AuditRecord ar where ar.id = :id")
	AuditRecord findOneAuditRecordById(int id);

	@Query("select ar from AuditRecord ar where ar.code = :code")
	AuditRecord findOneAuditRecordByCode(String code);

}
