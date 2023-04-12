package org.TheSlack.repository;

import org.TheSlack.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
//
//    @Query("SELECT firstname, lastname FROM t_users t WHERE t.firstname LIKE COCAT ('%', :query, '%')")
//    List<User> searchUsers(String query);
}
