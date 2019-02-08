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

        Path<Double> price10 = root.get(Runner_.price10);
        if (criteria.getPrice10Min() != 0) {
            runnerPredicates.add(criteriaBuilder.greaterThanOrEqualTo(price10, criteria.getPrice10Min()));
        }
        if (criteria.getPrice10Max() != 0) {
            runnerPredicates.add(criteriaBuilder.lessThanOrEqualTo(price10, criteria.getPrice10Max()));
        }

        Path<Double> price11 = root.get(Runner_.price11);
        if (criteria.getPrice11Min() != 0) {
            runnerPredicates.add(criteriaBuilder.greaterThanOrEqualTo(price11, criteria.getPrice11Min()));
        }
        if (criteria.getPrice11Max() != 0) {
            runnerPredicates.add(criteriaBuilder.lessThanOrEqualTo(price11, criteria.getPrice11Max()));
        }

        Path<Double> mov9to11 = root.get(Runner_.mov9to11);
        if (criteria.getMov9to11Min() != 0) {
            runnerPredicates.add(criteriaBuilder.greaterThanOrEqualTo(mov9to11, criteria.getMov9to11Min()));
        }
        if (criteria.getMov9to11Max() != 0) {
            runnerPredicates.add(criteriaBuilder.lessThanOrEqualTo(mov9to11, criteria.getMov9to11Max()));
        }

        Path<Double> price60 = root.get(Runner_.price60);
        if (criteria.getPrice60Min() != 0) {
            runnerPredicates.add(criteriaBuilder.greaterThanOrEqualTo(price60, criteria.getPrice60Min()));
        }
        if (criteria.getPrice60Max() != 0) {
            runnerPredicates.add(criteriaBuilder.greaterThanOrEqualTo(price60, criteria.getPrice60Max()));
        }

        Path<Double> mov60 = root.get(Runner_.mov60);
        if (criteria.getMov60Min() != 0) {
            runnerPredicates.add(criteriaBuilder.lessThanOrEqualTo(mov60, criteria.getMov60Min()));
        }
        if (criteria.getMov60Max() != 0) {
            runnerPredicates.add(criteriaBuilder.greaterThanOrEqualTo(mov60, criteria.getMov60Max()));
        }

        Path<Double> price30 = root.get(Runner_.price30);
        if (criteria.getPrice30Min() != 0) {
            runnerPredicates.add(criteriaBuilder.lessThanOrEqualTo(price30, criteria.getPrice3Min()));
        }
        if (criteria.getPrice30Max() != 0) {
            runnerPredicates.add(criteriaBuilder.greaterThanOrEqualTo(price30, criteria.getPrice30Max()));
        }

        Path<Double> mov30 = root.get(Runner_.mov30);
        if (criteria.getMov30Max() != 0) {
            runnerPredicates.add(criteriaBuilder.lessThanOrEqualTo(mov30, criteria.getMov30Min()));
        }
        if (criteria.getMov30Max() != 0) {
            runnerPredicates.add(criteriaBuilder.greaterThanOrEqualTo(mov30, criteria.getMov30Max()));
        }

        Path<Double> price15 = root.get(Runner_.price15);
        if (criteria.getPrice15Min() != 0) {
            runnerPredicates.add(criteriaBuilder.lessThanOrEqualTo(price15, criteria.getPrice15Min()));
        }
        if (criteria.getPrice15Max() != 0) {
            runnerPredicates.add(criteriaBuilder.greaterThanOrEqualTo(price15, criteria.getPrice15Max()));
        }

        Path<Double> mov15 = root.get(Runner_.mov15);
        if (criteria.getMov15Min() != 0) {
            runnerPredicates.add(criteriaBuilder.lessThanOrEqualTo(mov15, criteria.getMov15Min()));
        }
        if (criteria.getMov15Max() != 0) {
            runnerPredicates.add(criteriaBuilder.greaterThanOrEqualTo(mov15, criteria.getMov15Max()));
        }

        Path<Double> price5 = root.get(Runner_.price5);
        if (criteria.getPrice5Min() != 0) {
            runnerPredicates.add(criteriaBuilder.lessThanOrEqualTo(price5, criteria.getPrice5Min()));
        }
        if (criteria.getPrice5Max() != 0) {
            runnerPredicates.add(criteriaBuilder.greaterThanOrEqualTo(price5, criteria.getPrice5Max()));

        }

        Path<Double> mov5 = root.get(Runner_.mov5);
        if (criteria.getMov5Min() != 0) {
            runnerPredicates.add(criteriaBuilder.lessThanOrEqualTo(mov5, criteria.getMov5Min()));
        }
        if (criteria.getMov5Max() != 0) {
            runnerPredicates.add(criteriaBuilder.greaterThanOrEqualTo(mov5, criteria.getMov5Max()));
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

        Path<Integer> nptips = root.get(Runner_.nptips);
        if (criteria.getNptipsCountMin() != 0) {
            runnerPredicates.add(criteriaBuilder.greaterThanOrEqualTo(nptips, criteria.getNptipsCountMin()));
        }
        if (criteria.getNptipsCountMax() != 0) {
            runnerPredicates.add(criteriaBuilder.lessThanOrEqualTo(nptips, criteria.getNptipsCountMax()));
        }

        Path<Boolean> winner = root.get(Runner_.winner);
        Path<Boolean> placed = root.get(Runner_.placed);
        if (criteria.getResult().equalsIgnoreCase("winner")) {
            runnerPredicates.add(criteriaBuilder.isTrue(winner));
        }
        if (criteria.getResult().equalsIgnoreCase("placed")) {
            runnerPredicates.add(criteriaBuilder.isTrue(placed));
        }
        if (criteria.getResult().equalsIgnoreCase("notPlaced")) {
            runnerPredicates.add(criteriaBuilder.isFalse(placed));
        }

        Path<Integer> cpr = root.get(Runner_.cpr);
        if (criteria.getCprMin() != 0) {
            runnerPredicates.add(criteriaBuilder.greaterThanOrEqualTo(cpr, criteria.getCprMin()));
        }
        if (criteria.getCprMax() != 0) {
            runnerPredicates.add(criteriaBuilder.lessThanOrEqualTo(cpr, criteria.getCprMax()));
        }

        Path<Integer> stars = root.get(Runner_.stars);
        if (criteria.getRunnerStarsMin() != 0) {
            runnerPredicates.add(criteriaBuilder.greaterThanOrEqualTo(stars, criteria.getRunnerStarsMin()));
        }
        if (criteria.getRunnerStarsMax() != 0) {
            runnerPredicates.add(criteriaBuilder.lessThanOrEqualTo(stars, criteria.getRunnerStarsMax()));
        }



        Predicate[] runnerPredicatesArray = runnerPredicates.toArray(new Predicate[runnerPredicates.size()]);

        query.distinct(true);

        return criteriaBuilder.and(runnerPredicatesArray);
    }
}
