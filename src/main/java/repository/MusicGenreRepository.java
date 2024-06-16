package repository;

import domain.MusicGenre;
import org.springframework.data.repository.CrudRepository;

public interface MusicGenreRepository extends CrudRepository<MusicGenre, Integer> {
    MusicGenre findByName(String name);
}
