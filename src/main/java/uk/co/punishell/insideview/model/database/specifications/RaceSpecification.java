package uk.co.punishell.insideview.model.database.specifications;

import org.springframework.data.jpa.domain.Specification;
import uk.co.punishell.insideview.model.database.entities.Race;
import uk.co.punishell.insideview.model.services.web.forms.QueryFormData;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class RaceSpecification implements Specification<Race> {

    QueryFormData criteria;

    @SuppressWarnings("unchecked")
    @Override
    public Predicate toPredicate(Root<Race> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        final List<Predicate> predicates = new ArrayList<>();

        // TODO

        return null;
    }

}
