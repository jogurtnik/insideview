package uk.co.punishell.insideview.model.database.specifications;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import uk.co.punishell.insideview.model.database.entities.Race;
import uk.co.punishell.insideview.model.database.entities.Race_;
import uk.co.punishell.insideview.view.commands.guiCommands.RaceSearch;

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

        final List<Predicate> racePredicates = new ArrayList<>();


        Path<Date> date = root.get(Race_.date);
        Path<String> country = root.get(Race_.country);
        Path<Double> trackLength = root.get(Race_.trackLength);


        // Add Race predicates

        if ((criteria.getDateSince() != null) && (criteria.getDateTo() != null)) {

            racePredicates.add(criteriaBuilder.greaterThanOrEqualTo(date, criteria.getDateSince()));
            racePredicates.add(criteriaBuilder.lessThanOrEqualTo(date, criteria.getDateTo()));
        }

        if (criteria.getCountry() != null) {

            racePredicates.add(criteriaBuilder.like(country, criteria.getCountry()));
        }

        if (criteria.getTrackLengthMin() != 0) {
            racePredicates.add(criteriaBuilder.greaterThanOrEqualTo(trackLength, criteria.getTrackLengthMin()));
        }

        if (criteria.getTrackLengthMax() != 0) {
            racePredicates.add(criteriaBuilder.lessThanOrEqualTo(trackLength, criteria.getTrackLengthMax()));
        }

        if (criteria.getRunnersCountMin() != 0) {
            racePredicates.add(criteriaBuilder.ge(criteriaBuilder.size(root.get(Race_.RUNNERS)), criteria.getRunnersCountMin()));
        }

        if (criteria.getRunnersCountMax() != 0) {
            racePredicates.add(criteriaBuilder.le(criteriaBuilder.size(root.get(Race_.RUNNERS)), criteria.getRunnersCountMax()));
        }


        log.debug("Race Predicates list size: " + racePredicates.size());

        Predicate[] racePredicatesArray = racePredicates.toArray(new Predicate[racePredicates.size()]);

        query.distinct(true);

        return criteriaBuilder.and(racePredicatesArray);
    }

}
