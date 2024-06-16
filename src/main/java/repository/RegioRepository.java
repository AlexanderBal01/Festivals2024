package repository;

import domain.Regio;
import org.springframework.data.repository.CrudRepository;

public interface RegioRepository extends CrudRepository<Regio, Integer> {
    Regio findByName(String name);
}
