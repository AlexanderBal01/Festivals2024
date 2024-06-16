package service;

import domain.Regio;
import org.springframework.beans.factory.annotation.Autowired;
import repository.RegioRepository;

import java.util.Optional;

public class RegioService implements RegioRepository {
    @Autowired
    private RegioRepository regioRepository;

    @Override
    public <S extends Regio> S save(S entity) {
        return regioRepository.save(entity);
    }

    @Override
    public <S extends Regio> Iterable<S> saveAll(Iterable<S> entities) {
        return regioRepository.saveAll(entities);
    }

    @Override
    public Optional<Regio> findById(Integer integer) {
        return regioRepository.findById(integer);
    }

    @Override
    public boolean existsById(Integer integer) {
        return regioRepository.existsById(integer);
    }

    @Override
    public Iterable<Regio> findAll() {
        return regioRepository.findAll();
    }

    @Override
    public Iterable<Regio> findAllById(Iterable<Integer> integers) {
        return regioRepository.findAllById(integers);
    }

    @Override
    public long count() {
        return regioRepository.count();
    }

    @Override
    public void deleteById(Integer integer) {
        regioRepository.deleteById(integer);
    }

    @Override
    public void delete(Regio entity) {
        regioRepository.delete(entity);
    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> integers) {
        regioRepository.deleteAllById(integers);
    }

    @Override
    public void deleteAll(Iterable<? extends Regio> entities) {
        regioRepository.deleteAll(entities);
    }

    @Override
    public void deleteAll() {
        regioRepository.deleteAll();
    }

    @Override
    public Regio findByName(String name) {
        return regioRepository.findByName(name);
    }
}
