package uk.co.punishell.insideview.model.database.specifications;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import uk.co.punishell.insideview.model.database.entities.Race;
import uk.co.punishell.insideview.model.database.entities.Race_;
import uk.co.punishell.insideview.view.commands.guiCommands.RaceSearch;

import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.util.ArrayList;
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


        Path<LocalDate> localDate = root.get(Race_.localDate);
        Path<String> country = root.get(Race_.country);
        Path<Double> trackLength = root.get(Race_.trackLength);


        // Add Race predicates

        if ((criteria.getLocalDateSince() != null) && (criteria.getLocalDateTo() != null)) {

            racePredicates.add(criteriaBuilder.greaterThanOrEqualTo(localDate, criteria.getLocalDateSince()));
            racePredicates.add(criteriaBuilder.lessThanOrEqualTo(localDate, criteria.getLocalDateTo()));
        }

        if (!criteria.getCountry().equalsIgnoreCase("all")) {

            racePredicates.add(criteriaBuilder.like(country, criteria.getCountry()));
        }

        if (criteria.getTrackLengthMin() != 0) {
            racePredicates.add(criteriaBuilder.ge(trackLength, criteria.getTrackLengthMin()));
        }
        if (criteria.getTrackLengthMax() != 0) {
            racePredicates.add(criteriaBuilder.le(trackLength, criteria.getTrackLengthMax()));
        }

        if (criteria.getRunnersCountMin() != 0) {
            racePredicates.add(criteriaBuilder.ge(criteriaBuilder.size(root.get(Race_.RUNNERS)),
                                                  criteria.getRunnersCountMin()));
        }
        if (criteria.getRunnersCountMax() != 0) {
            racePredicates.add(criteriaBuilder.le(criteriaBuilder.size(root.get(Race_.RUNNERS)),
                                                  criteria.getRunnersCountMax()));
        }

        log.debug("Race Predicates list size: " + racePredicates.size());

        Predicate[] racePredicatesArray = racePredicates.toArray(new Predicate[racePredicates.size()]);

        query.distinct(true);

        return criteriaBuilder.and(racePredicatesArray);
    }

}
