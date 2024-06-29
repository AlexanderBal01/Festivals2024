package service;

import domain.Festival;
import domain.MyUser;
import org.springframework.beans.factory.annotation.Autowired;
import repository.FestivalRepository;

import java.util.List;
import java.util.Optional;

public class FestivalService implements FestivalRepository {

    @Autowired
    private FestivalRepository festivalRepository;

    @Override
    public Festival findFestivalByName(String name) {
        return festivalRepository.findFestivalByName(name);
    }

    @Override
    public Integer countByFestivalVisitorsAndFestivalid(List<MyUser> users, Integer festival) {
        return festivalRepository.countByFestivalVisitorsAndFestivalid(users, festival);
    }

    @Override
    public List<Festival> findFestivalsByMusicGenre_MusicgenreidOrderByRegioAscDatumAsc(Integer musicgenreid) {
        return festivalRepository.findFestivalsByMusicGenre_MusicgenreidOrderByRegioAscDatumAsc(musicgenreid);
    }

    @Override
    public <S extends Festival> S save(S entity) {
        return festivalRepository.save(entity);
    }

    @Override
    public <S extends Festival> Iterable<S> saveAll(Iterable<S> entities) {
        return festivalRepository.saveAll(entities);
    }

    @Override
    public Optional<Festival> findById(Integer integer) {
        return festivalRepository.findById(integer);
    }

    @Override
    public boolean existsById(Integer integer) {
        return festivalRepository.existsById(integer);
    }

    @Override
    public Iterable<Festival> findAll() {
        return festivalRepository.findAll();
    }

    @Override
    public Iterable<Festival> findAllById(Iterable<Integer> integers) {
        return festivalRepository.findAllById(integers);
    }

    @Override
    public long count() {
        return festivalRepository.count();
    }

    @Override
    public void deleteById(Integer integer) {
        festivalRepository.deleteById(integer);
    }

    @Override
    public void delete(Festival entity) {
        festivalRepository.delete(entity);
    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> integers) {
        festivalRepository.deleteAllById(integers);
    }

    @Override
    public void deleteAll(Iterable<? extends Festival> entities) {
        festivalRepository.deleteAll(entities);
    }

    @Override
    public void deleteAll() {
        festivalRepository.deleteAll();
    }
}
