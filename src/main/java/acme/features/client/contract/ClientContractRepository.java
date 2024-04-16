
package acme.features.client.contract;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.contracts.Contract;
import acme.entities.projects.Project;

@Repository
public interface ClientContractRepository extends AbstractRepository {

	@Query("select c from Contract c where c.client.id = :clientId")
	Collection<Contract> findManyContractsByClientId(int clientId);

	@Query("select c from Contract c where c.id = :id")
	Contract findOneContractById(int id);

	@Query("select p from Project p")
	Collection<Project> findAllProjects();

	@Query("select c.project from Contract c where c.id = :contractId")
	Collection<Project> findOneProjectByContractId(int contractId);

}
