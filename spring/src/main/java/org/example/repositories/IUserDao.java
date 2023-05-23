package org.example.repositories;

import org.example.entities.User;
import org.example.utilities.SearchCriteria;

import java.util.List;

public interface IUserDao {
  public List<User> searchUser(List<SearchCriteria> params);

  public void save(User entity);
}
