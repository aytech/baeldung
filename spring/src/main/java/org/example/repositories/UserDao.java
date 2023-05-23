package org.example.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.example.entities.User;
import org.example.utilities.SearchCriteria;
import org.example.utilities.UserSearchQueryCriteriaConsumer;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDao implements IUserDao {
  @PersistenceContext
  private EntityManager entityManager;


  @Override
  public List<User> searchUser(List<SearchCriteria> params) {
    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);
    Root<User> root = query.from(User.class);

    Predicate predicate = criteriaBuilder.conjunction();

    UserSearchQueryCriteriaConsumer searchQueryCriteriaConsumer =
        new UserSearchQueryCriteriaConsumer(predicate, criteriaBuilder, root);

    params.forEach(searchQueryCriteriaConsumer);

    predicate = searchQueryCriteriaConsumer.getPredicate();

    query.where(predicate);

    return entityManager.createQuery(query).getResultList();
  }

  @Override
  public void save(User entity) {
    entityManager.persist(entity);
  }
}
