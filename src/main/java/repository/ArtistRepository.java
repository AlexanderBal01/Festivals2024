package repository;

import domain.Artist;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ArtistRepository extends CrudRepository<Artist, Integer> {
    List<Artist> findArtistsByBooking_Festivalid(int id);
}
