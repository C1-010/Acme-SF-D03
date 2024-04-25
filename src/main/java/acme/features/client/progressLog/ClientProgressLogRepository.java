
package acme.features.client.progressLog;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.contracts.Contract;
import acme.entities.contracts.ProgressLog;
import acme.roles.Client;

@Repository
public interface ClientProgressLogRepository extends AbstractRepository {

	@Query("select c from Contract c where c.id = :id")
	Contract findOneContractById(int id);

	@Query("select pl.contract from ProgressLog pl where pl.id = :id")
	Contract findOneContractByProgressLogId(int id);

	@Query("select pl from ProgressLog pl where pl.contract.id = :masterId")
	Collection<ProgressLog> findManyProgressLogsByMasterId(int masterId);

	@Query("select pl from ProgressLog pl where pl.id = :id")
	ProgressLog findOneProgressLogById(int id);

	@Query("select pl from ProgressLog pl where pl.recordId = :recordId")
	ProgressLog findOneProgressLogByRecordId(String recordId);

	@Query("select cl from Client cl where cl.id = :clientId")
	Client findOneClientById(int clientId);

	@Query("select max(pl.completeness) from ProgressLog pl where pl.contract.id = :masterId and pl.draftMode = false")
	Double findMaxPublishedProgressLogCompletenessByMasterId(int masterId);
}
