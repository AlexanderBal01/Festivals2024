package service;

import domain.MusicGenre;
import org.springframework.beans.factory.annotation.Autowired;
import repository.MusicGenreRepository;

import java.util.Optional;

public class MusicGenreService implements MusicGenreRepository {
    @Autowired
    private MusicGenreRepository musicGenreRepository;

    @Override
    public <S extends MusicGenre> S save(S entity) {
        return musicGenreRepository.save(entity);
    }

    @Override
    public <S extends MusicGenre> Iterable<S> saveAll(Iterable<S> entities) {
        return musicGenreRepository.saveAll(entities);
    }

    @Override
    public Optional<MusicGenre> findById(Integer integer) {
        return musicGenreRepository.findById(integer);
    }

    @Override
    public boolean existsById(Integer integer) {
        return musicGenreRepository.existsById(integer);
    }

    @Override
    public Iterable<MusicGenre> findAll() {
        return musicGenreRepository.findAll();
    }

    @Override
    public Iterable<MusicGenre> findAllById(Iterable<Integer> integers) {
        return musicGenreRepository.findAllById(integers);
    }

    @Override
    public long count() {
        return musicGenreRepository.count();
    }

    @Override
    public void deleteById(Integer integer) {
        musicGenreRepository.deleteById(integer);
    }

    @Override
    public void delete(MusicGenre entity) {
        musicGenreRepository.delete(entity);
    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> integers) {
        musicGenreRepository.deleteAllById(integers);
    }

    @Override
    public void deleteAll(Iterable<? extends MusicGenre> entities) {
        musicGenreRepository.deleteAll(entities);
    }

    @Override
    public void deleteAll() {
        musicGenreRepository.deleteAll();
    }

    @Override
    public MusicGenre findByName(String name) {
        return musicGenreRepository.findByName(name);
    }
}
