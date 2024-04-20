
package acme.components;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import acme.client.helpers.MomentHelper;
import acme.client.helpers.RandomHelper;
import acme.client.repositories.AbstractRepository;
import acme.entities.group.Banner;

@Repository
public interface BannerRepository extends AbstractRepository {

	@Query("select count(b) from Banner b")
	int countBanners();

	@Query("select b from Banner b")
	List<Banner> findManyBanners(PageRequest pageRequest);

	@Query("select count(b) from Banner b where b.startPeriod <= :currentDate and b.endPeriod >= :currentDate")
	int countActiveBanners(@Param("currentDate") Date currentDate);

	@Query("select b from Banner b where b.startPeriod <= :currentDate and b.endPeriod >= :currentDate")
	List<Banner> findActiveBanners(@Param("currentDate") Date currentDate);

	@Query("select b from Banner b where b.startPeriod <= :currentDate and b.endPeriod >= :currentDate")
	List<Banner> findActiveBanners(@Param("currentDate") Date currentDate, PageRequest pageRequest);

	default Banner findRandomBanner() {
		Banner result;
		int count, index;
		PageRequest page;
		List<Banner> list;

		count = this.countBanners();
		if (count == 0)
			result = null;
		else {
			index = RandomHelper.nextInt(0, count);

			page = PageRequest.of(index, 1, Sort.by(Direction.ASC, "id"));
			list = this.findManyBanners(page);
			result = list.isEmpty() ? null : list.get(0);
		}

		return result;

	}
	default Banner findRandomActiveBanner() {
		Banner result;
		int count, index;
		PageRequest page;
		List<Banner> list;
		Date currentDate = MomentHelper.getCurrentMoment();

		count = this.countActiveBanners(currentDate);
		System.out.println(count);
		if (count == 0)
			result = null;
		else {
			index = RandomHelper.nextInt(0, count);

			page = PageRequest.of(index, 1, Sort.by(Direction.ASC, "id"));
			list = this.findActiveBanners(currentDate, page);
			result = list.isEmpty() ? null : list.get(0);
		}

		return result;

	}

}
