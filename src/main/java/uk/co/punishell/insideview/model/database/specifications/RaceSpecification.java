package uk.co.punishell.insideview.model.database.specifications;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import uk.co.punishell.insideview.model.database.entities.Race;
import uk.co.punishell.insideview.model.database.entities.Race_;
import uk.co.punishell.insideview.model.services.web.commands.guiCommands.RaceSearch;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
public class RaceSpecification implements Specification<Race> {

    RaceSearch criteria;

    public RaceSpecification(RaceSearch criteria) {
        this.criteria = criteria;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Predicate toPredicate(Root<Race> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        final List<Predicate> predicates = new ArrayList<>();

        Path<Date> date = root.get(Race_.date);
        Path<String> country = root.get(Race_.country);
        Path<Double> trackLength = root.get(Race_.trackLength);

        if ((criteria.getDateSince() != null) && (criteria.getDateTo() != null)) {

            predicates.add(criteriaBuilder.greaterThanOrEqualTo(date, criteria.getDateSince()));
            predicates.add(criteriaBuilder.lessThanOrEqualTo(date, criteria.getDateTo()));
        }

        log.debug("criteria.getCountry(): " + criteria.getCountry());
        if (criteria.getCountry() != null) {

            predicates.add(criteriaBuilder.like(country, criteria.getCountry()));
        }

        if (criteria.getTrackLengthMin() != 0) {

            predicates.add(criteriaBuilder.greaterThanOrEqualTo(trackLength, criteria.getTrackLengthMin()));
        }

        if (criteria.getTrackLengthMax() != 0) {

            predicates.add(criteriaBuilder.lessThanOrEqualTo(trackLength, criteria.getTrackLengthMax()));
        }

        log.debug("Predicates list size: " + predicates.size());
        return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
    }

}
