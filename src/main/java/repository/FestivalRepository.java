package repository;

import domain.Festival;
import domain.MyUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FestivalRepository extends CrudRepository<Festival, Integer> {
    Festival findFestivalByName(String name);
    Integer countByFestivalVisitorsAndFestivalid(List<MyUser> users, Integer festival);

    @Query("SELECT f FROM Festival f where f.musicGenre.musicgenreid = :musicgenreId ORDER BY f.regio.name asc, f.datum")
    List<Festival> findFestivalsByMusicGenre_MusicgenreidOrderByRegioAscDatumAsc(@Param("musicgenreId") Integer musicgenreid);

    @Query("SELECT f FROM Festival f WHERE f.regio.regioid = :regioId ORDER BY f.musicGenre.name asc, f.datum asc")
    List<Festival> findFestivalsByRegio_RegioidOrderByMusicGenreAscDatumAsc(@Param("regioId") Integer regioid);

    List<Festival> findFestivalsByMusicGenre_Musicgenreid(int id);
}
