package garbagetown.domain.repository.tourinfo;

import garbagetown.domain.model.Tourinfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by garbagetown on 10/19/15.
 */
public class TourinfoRepositoryImpl implements TourinfoRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    @Transactional
    public Page<Tourinfo> findPageBySearchCriteria(TourinfoCriteria criteria, Pageable pageable) {

        List<Tourinfo> content = find(criteria, pageable);
        long total = count(criteria);

        Page<Tourinfo> page = new PageImpl<Tourinfo>(content, pageable, total);

        return page;
    }

    protected List<Tourinfo> find(TourinfoCriteria criteria, Pageable pageable) {

        StringBuilder builder = new StringBuilder();
        builder.append("SELECT x ");
        builder.append(createQueryString(criteria));
        builder.append("ORDER BY x.tourDays DESC, x.basePrice DESC");

        TypedQuery<Tourinfo> query = entityManager.createQuery(builder.toString(), Tourinfo.class);
        setParameters(query, criteria);
        query.setFirstResult(pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        List<Tourinfo> result = query.getResultList();

        return result;
    }

    protected long count(TourinfoCriteria criteria) {

        StringBuilder builder = new StringBuilder();
        builder.append("SELECT COUNT(x) ");
        builder.append(createQueryString(criteria));

        TypedQuery <Long> query = entityManager.createQuery(builder.toString(), Long.class);
        setParameters(query, criteria);

        Long count = query.getSingleResult();
        if (count == null) {
            count = 0L;
        }
        return count;
    }

    private String createQueryString(TourinfoCriteria criteria) {

        StringBuilder builder = new StringBuilder();

        builder.append("FROM Tourinfo x ");
        builder.append("LEFT JOIN x.departure dep ");
        builder.append("LEFT JOIN x.arrival arr ");
        builder.append("WHERE (EXISTS (");
        builder.append("SELECT res.tourinfo.tourCode FROM Reserve res ");
        builder.append("WHERE res.tourinfo.tourCode = x.tourCode ");
        builder.append("AND (res.adultCount + res.childCount + :adultCount + :childCount) <= x.avaRecMax ) ");
        builder.append("OR NOT EXISTS (");
        builder.append("SELECT res.tourinfo.tourCode FROM Reserve res ");
        builder.append("WHERE res.tourinfo.tourCode = x.tourCode)) ");
        builder.append("AND x.depDay = :depDay ");
        builder.append("AND dep.depCode = :depCode ");
        builder.append("AND arr.arrCode = :arrCode ");
        if (criteria.getTourDays() != 0) {
            builder.append("AND x.tourDays <= :tourDays ");
        }
        if (criteria.getBasePrice() != 0) {
            builder.append("AND x.basePrice <= :basePrice ");
        }

        return builder.toString();
    }

    private void setParameters(Query query, TourinfoCriteria criteria) {
        query.setParameter("adultCount", criteria.getAdultCount());
        query.setParameter("childCount", criteria.getChildCount());
        query.setParameter("depDay", criteria.getDepDate());
        query.setParameter("depCode", criteria.getDepCode());
        query.setParameter("arrCode", criteria.getArrCode());
        if (criteria.getTourDays() != 0) {
            query.setParameter("tourDays", criteria.getTourDays());
        }
        if (criteria.getBasePrice() != 0) {
            query.setParameter("basePrice", criteria.getBasePrice());
        }
    }
}
