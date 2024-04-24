
package acme.features.sponsor.dashboard;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.data.datatypes.Money;
import acme.client.repositories.AbstractRepository;

@Repository
public interface SponsorDashboardRepository extends AbstractRepository {

	@Query("select count(i) from Invoice i join i.sponsorship s where s.sponsor.id = :sponsorId and i.tax <= 0.21")
	Integer numberOfInvoicesWithTaxLessEqualThan21(int sponsorId);

	@Query("select count(s) from Sponsorship s  where s.sponsor.id = :sponsorId and s.link is not null")
	Integer numberOfSponsorshipsWithLink(int sponsorId);

	@Query("Select s.amount from Sponsorship s where s.sponsor.id = :sponsorId")
	Collection<Money> findSponsorshipsAmountBySponsorId(int sponsorId);

	@Query("Select i.quantity from Invoice i where i.sponsorship.sponsor.id = :sponsorId")
	Collection<Money> findInvoicesQuantityBySponsorId(int sponsorId);

}
