package uk.co.punishell.insideview.model.database.specifications;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import uk.co.punishell.insideview.model.database.entities.Runner;
import uk.co.punishell.insideview.model.database.entities.Runner_;
import uk.co.punishell.insideview.view.commands.guiCommands.RunnerSearch;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class RunnerSpecification implements Specification<Runner> {

    private RunnerSearch criteria;

    public RunnerSpecification(RunnerSearch criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<Runner> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        final List<Predicate> runnerPredicates = new ArrayList<>();

        Path<Double> price9 = root.get(Runner_.price9);
        if (criteria.getPrice9Min() != 0) {
            runnerPredicates.add(criteriaBuilder.greaterThanOrEqualTo(price9, criteria.getPrice9Min()));
        }
        if (criteria.getPrice9Max() != 0) {
            runnerPredicates.add(criteriaBuilder.lessThanOrEqualTo(price9, criteria.getPrice9Max()));
        }

        Path<Double> price3 = root.get(Runner_.price3);
        if (criteria.getPrice3Min() != 0) {
            runnerPredicates.add(criteriaBuilder.greaterThanOrEqualTo(price3, criteria.getPrice3Min()));
        }
        if (criteria.getPrice3Max() != 0) {
            runnerPredicates.add(criteriaBuilder.lessThanOrEqualTo(price3, criteria.getPrice3Max()));
        }

        Path<Double> mov3 = root.get(Runner_.mov3);
        if (criteria.getMov3Min() != 0) {
            runnerPredicates.add(criteriaBuilder.greaterThanOrEqualTo(mov3, criteria.getMov3Min()));
        }
        if (criteria.getMov3Max() != 0) {
            runnerPredicates.add(criteriaBuilder.lessThanOrEqualTo(mov3, criteria.getMov3Max()));
        }

        Path<Double> price2 = root.get(Runner_.price2);
        if (criteria.getPrice2Min() != 0) {
            runnerPredicates.add(criteriaBuilder.greaterThanOrEqualTo(price2, criteria.getPrice2Min()));
        }
        if (criteria.getPrice2Max() != 0) {
            runnerPredicates.add(criteriaBuilder.lessThanOrEqualTo(price2, criteria.getPrice2Max()));
        }

        Path<Double> mov2 = root.get(Runner_.mov2);
        if (criteria.getMov2Min() != 0) {
            runnerPredicates.add(criteriaBuilder.greaterThanOrEqualTo(mov2, criteria.getMov2Min()));
        }
        if (criteria.getMov2Max() != 0) {
            runnerPredicates.add(criteriaBuilder.lessThanOrEqualTo(mov2, criteria.getMov2Max()));
        }

        Path<Double> price1 = root.get(Runner_.price1);
        if (criteria.getPrice1Min() != 0) {
            runnerPredicates.add(criteriaBuilder.greaterThanOrEqualTo(price1, criteria.getPrice1Min()));
        }
        if (criteria.getPrice1Max() != 0) {
            runnerPredicates.add(criteriaBuilder.lessThanOrEqualTo(price1,criteria.getPrice1Max()));
        }

        Path<Double> mov1 = root.get(Runner_.mov1);
        if (criteria.getMov1Min() != 0) {
            runnerPredicates.add(criteriaBuilder.greaterThanOrEqualTo(mov1, criteria.getMov1Min()));
        }
        if (criteria.getMov1Max() != 0) {
            runnerPredicates.add(criteriaBuilder.lessThanOrEqualTo(mov1, criteria.getMov1Max()));
        }

        Path<Double> mean = root.get(Runner_.mean);
        if (criteria.getMeanMin() != 0) {
            runnerPredicates.add(criteriaBuilder.greaterThanOrEqualTo(mean, criteria.getMeanMin()));
        }
        if (criteria.getMeanMax() != 0) {
            runnerPredicates.add(criteriaBuilder.lessThanOrEqualTo(mean, criteria.getMeanMax()));
        }

        Path<Double> mov3to1 = root.get(Runner_.mov3to1);
        if (criteria.getMov3to1Min() != 0) {
            runnerPredicates.add(criteriaBuilder.greaterThanOrEqualTo(mov3to1, criteria.getMov3to1Min()));
        }
        if (criteria.getMov3to1Max() != 0) {
            runnerPredicates.add(criteriaBuilder.lessThanOrEqualTo(mov3to1, criteria.getMov3to1Max()));
        }

        Predicate[] runnerPredicatesArray = runnerPredicates.toArray(new Predicate[runnerPredicates.size()]);

        query.distinct(true);

        return criteriaBuilder.and(runnerPredicatesArray);
    }
}
