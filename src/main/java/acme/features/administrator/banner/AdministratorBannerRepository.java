
package acme.features.administrator.banner;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.group.Banner;

@Repository
public interface AdministratorBannerRepository extends AbstractRepository {

	@Query("select b from Banner b where b.id = :id")
	Banner findOneBannerById(int id);

	@Query("select b from Banner b")
	Collection<Banner> findAllBanners();

	@Query("select b from Banner b where b.instantiationMoment > :deadline")
	Collection<Banner> findRecentBanners(Date deadline);

}
