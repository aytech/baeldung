package org.example.utilities;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.example.entities.User;

import java.util.function.Consumer;

public class UserSearchQueryCriteriaConsumer implements Consumer<SearchCriteria> {

  private Predicate predicate;
  private CriteriaBuilder criteriaBuilder;
  private Root<User> root;

  public UserSearchQueryCriteriaConsumer(Predicate predicate, CriteriaBuilder criteriaBuilder, Root<User> root) {

  }

  public Predicate getPredicate() {
    return predicate;
  }

  @Override
  public void accept(SearchCriteria searchCriteria) {
    if (searchCriteria.getOperation().equalsIgnoreCase(">")) {
      predicate = criteriaBuilder
          .and(predicate, criteriaBuilder.greaterThanOrEqualTo(
              root.get(searchCriteria.getKey()), searchCriteria.getValue().toString()));
    } else if (searchCriteria.getOperation().equalsIgnoreCase("<")) {
      predicate = criteriaBuilder
          .and(predicate, criteriaBuilder.lessThanOrEqualTo(
              root.get(searchCriteria.getKey()), searchCriteria.getValue().toString()));
    } else if (searchCriteria.getOperation().equalsIgnoreCase(":")) {
      if (root.get(searchCriteria.getKey()).getJavaType() == String.class) {
        predicate = criteriaBuilder
            .and(predicate, criteriaBuilder.like(
                root.get(searchCriteria.getKey()), "%" + searchCriteria.getValue() + "%"));
      } else {
        predicate = criteriaBuilder
            .and(predicate, criteriaBuilder.equal(root.get(searchCriteria.getKey()), searchCriteria.getValue()));
      }
    }
  }
}
