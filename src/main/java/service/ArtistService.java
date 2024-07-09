package service;

import domain.Artist;
import exceptions.ArtistFestivalNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import repository.ArtistRepository;

import java.util.List;
import java.util.Optional;

public class ArtistService implements ArtistRepository {
    @Autowired
    private ArtistRepository artistRepository;

    @Override
    public <S extends Artist> S save(S entity) {
        return artistRepository.save(entity);
    }

    @Override
    public <S extends Artist> Iterable<S> saveAll(Iterable<S> entities) {
        return artistRepository.saveAll(entities);
    }

    @Override
    public Optional<Artist> findById(Integer integer) {
        return artistRepository.findById(integer);
    }

    @Override
    public boolean existsById(Integer integer) {
        return artistRepository.existsById(integer);
    }

    @Override
    public Iterable<Artist> findAll() {
        return artistRepository.findAll();
    }

    @Override
    public Iterable<Artist> findAllById(Iterable<Integer> integers) {
        return artistRepository.findAllById(integers);
    }

    @Override
    public long count() {
        return artistRepository.count();
    }

    @Override
    public void deleteById(Integer integer) {
        artistRepository.deleteById(integer);
    }

    @Override
    public void delete(Artist entity) {
        artistRepository.delete(entity);
    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> integers) {
        artistRepository.deleteAllById(integers);
    }

    @Override
    public void deleteAll(Iterable<? extends Artist> entities) {
        artistRepository.deleteAll(entities);
    }

    @Override
    public void deleteAll() {
        artistRepository.deleteAll();
    }

    @Override
    public List<Artist> findArtistsByBooking_Festivalid(int id) {
        List<Artist> artists = artistRepository.findArtistsByBooking_Festivalid(id);
        if(artists == null) {
            throw new ArtistFestivalNotFoundException(id);
        }
        return artists;
    }
}
