
package acme.features.client.dashboard;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.data.datatypes.Money;
import acme.client.repositories.AbstractRepository;

@Repository
public interface ClientDashboardRepository extends AbstractRepository {

	@Query("select count(pl) from ProgressLog pl join pl.contract c where c.client.id = :clientId and pl.completeness < 25 and pl.draftMode = false")
	Integer totalProgressLogsLessThan25(int clientId);

	@Query("select count(pl) from ProgressLog pl join pl.contract c where c.client.id = :clientId and pl.completeness >= 25 and pl.completeness < 50 and pl.draftMode = false")
	Integer totalProgressLogsBetween25And50(int clientId);

	@Query("select count(pl) from ProgressLog pl join pl.contract c where c.client.id = :clientId and pl.completeness >= 50 and pl.completeness < 75 and pl.draftMode = false")
	Integer totalProgressLogsBetween50And75(int clientId);

	@Query("select count(pl) from ProgressLog pl join pl.contract c where c.client.id = :clientId and pl.completeness >= 75 and pl.draftMode = false")
	Integer totalProgressLogsAbove75(int clientId);

	@Query("Select c.budget from Contract c where c.client.id = :clientId and c.draftMode = false")
	Collection<Money> findBudgetsByClientId(int clientId);

}
