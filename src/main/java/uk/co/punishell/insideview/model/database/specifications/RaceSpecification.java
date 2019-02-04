package uk.co.punishell.insideview.model.database.specifications;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import uk.co.punishell.insideview.model.database.entities.Race;
import uk.co.punishell.insideview.model.database.entities.Race_;
import uk.co.punishell.insideview.model.database.entities.Runner;
import uk.co.punishell.insideview.model.database.entities.Runner_;
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
        final List<Predicate> runnerPredicates = new ArrayList<>();

        Path<Date> date = root.get(Race_.date);
        Path<String> country = root.get(Race_.country);
        Path<Double> trackLength = root.get(Race_.trackLength);


        // Add Race predicates

        if ((criteria.getDateSince() != null) && (criteria.getDateTo() != null)) {

            racePredicates.add(criteriaBuilder.greaterThanOrEqualTo(date, criteria.getDateSince()));
            racePredicates.add(criteriaBuilder.lessThanOrEqualTo(date, criteria.getDateTo()));
        }

        log.debug("criteria.getCountry(): " + criteria.getCountry());
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

        // Add Runners predicates
        Join<Race, Runner> joinedRunner = root.join(Race_.runners, JoinType.INNER);

        Path<Double> price9 = joinedRunner.get(Runner_.price9);
        if (criteria.getPrice9Min() != 0) {
            runnerPredicates.add(criteriaBuilder.greaterThanOrEqualTo(price9, criteria.getPrice9Min()));
        }
        if (criteria.getPrice9Max() != 0) {
            runnerPredicates.add(criteriaBuilder.lessThanOrEqualTo(price9, criteria.getPrice9Max()));
        }

        Path<Double> price3 = joinedRunner.get(Runner_.price3);
        if (criteria.getPrice3Min() != 0) {
            runnerPredicates.add(criteriaBuilder.greaterThanOrEqualTo(price3, criteria.getPrice3Min()));
        }
        if (criteria.getPrice3Max() != 0) {
            runnerPredicates.add(criteriaBuilder.lessThanOrEqualTo(price3, criteria.getPrice3Max()));
        }

        Path<Double> mov3 = joinedRunner.get(Runner_.mov3);
        if (criteria.getMov3Min() != 0) {
            runnerPredicates.add(criteriaBuilder.greaterThanOrEqualTo(mov3, criteria.getMov3Min()));
        }
        if (criteria.getMov3Max() != 0) {
            runnerPredicates.add(criteriaBuilder.lessThanOrEqualTo(mov3, criteria.getMov3Max()));
        }

        Path<Double> price2 = joinedRunner.get(Runner_.price2);
        if (criteria.getPrice2Min() != 0) {
            runnerPredicates.add(criteriaBuilder.greaterThanOrEqualTo(price2, criteria.getPrice2Min()));
        }
        if (criteria.getPrice2Max() != 0) {
            runnerPredicates.add(criteriaBuilder.lessThanOrEqualTo(price2, criteria.getPrice2Max()));
        }

        Path<Double> mov2 = joinedRunner.get(Runner_.mov2);
        if (criteria.getMov2Min() != 0) {
            runnerPredicates.add(criteriaBuilder.greaterThanOrEqualTo(mov2, criteria.getMov2Min()));
        }
        if (criteria.getMov2Max() != 0) {
            runnerPredicates.add(criteriaBuilder.lessThanOrEqualTo(mov2, criteria.getMov2Max()));
        }

        Path<Double> price1 = joinedRunner.get(Runner_.price1);
        if (criteria.getPrice1Min() != 0) {
            runnerPredicates.add(criteriaBuilder.greaterThanOrEqualTo(price1, criteria.getPrice1Min()));
        }
        if (criteria.getPrice1Max() != 0) {
            runnerPredicates.add(criteriaBuilder.lessThanOrEqualTo(price1,criteria.getPrice1Max()));
        }

        Path<Double> mov1 = joinedRunner.get(Runner_.mov1);
        if (criteria.getMov1Min() != 0) {
            runnerPredicates.add(criteriaBuilder.greaterThanOrEqualTo(mov1, criteria.getMov1Min()));
        }
        if (criteria.getMov1Max() != 0) {
            runnerPredicates.add(criteriaBuilder.lessThanOrEqualTo(mov1, criteria.getMov1Max()));
        }

        Path<Double> mean = joinedRunner.get(Runner_.mean);
        if (criteria.getMeanMin() != 0) {
            runnerPredicates.add(criteriaBuilder.greaterThanOrEqualTo(mean, criteria.getMeanMin()));
        }
        if (criteria.getMeanMax() != 0) {
            runnerPredicates.add(criteriaBuilder.lessThanOrEqualTo(mean, criteria.getMeanMax()));
        }

        Path<Double> mov3to1 = joinedRunner.get(Runner_.mov3to1);
        if (criteria.getMov3to1Min() != 0) {
            runnerPredicates.add(criteriaBuilder.greaterThanOrEqualTo(mov3to1, criteria.getMov3to1Min()));
        }
        if (criteria.getMov3to1Max() != 0) {
            runnerPredicates.add(criteriaBuilder.lessThanOrEqualTo(mov3to1, criteria.getMov3to1Max()));
        }


        log.debug("Race Predicates list size: " + racePredicates.size());
        log.debug("Runner Predicates list size: " + runnerPredicates.size());

        Predicate[] predicates = new Predicate[racePredicates.size() + runnerPredicates.size()];
        Predicate[] racePredicatesArray = racePredicates.toArray(new Predicate[racePredicates.size()]);
        Predicate[] runnerPredicatesArray = runnerPredicates.toArray(new Predicate[runnerPredicates.size()]);

        for (int i = 0; i < predicates.length; i++) {
            if (i < racePredicatesArray.length) {
                predicates[i] = racePredicatesArray[i];
            } else {
                predicates[i] = runnerPredicatesArray[i - racePredicatesArray.length];
            }
        }

        query.distinct(true);

        return criteriaBuilder.and(predicates);
    }

}
