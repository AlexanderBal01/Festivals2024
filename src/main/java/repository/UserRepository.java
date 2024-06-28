package repository;

import domain.MyUser;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<MyUser, Integer> {
    MyUser findByUsername(String username);
}
