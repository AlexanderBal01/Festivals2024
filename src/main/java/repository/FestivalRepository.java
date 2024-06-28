package repository;

import domain.Festival;
import domain.MyUser;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FestivalRepository extends CrudRepository<Festival, Integer> {
    Festival findFestivalByName(String name);
    Integer countByFestivalVisitorsAndFestivalid(List<MyUser> users, Integer festival);
    List<Festival> findFestivalsByMusicGenre_MusicgenreidOrderByRegio(Integer musicgenreid);
}
