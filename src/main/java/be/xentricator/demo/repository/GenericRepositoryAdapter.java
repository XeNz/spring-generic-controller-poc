package be.xentricator.demo.repository;

import be.xentricator.demo.entity.AbstractEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Optional;

@Service
public class GenericRepositoryAdapter {

    private final RepositoryUtils repositoryUtils;

    public GenericRepositoryAdapter(RepositoryUtils repositoryUtils) {
        this.repositoryUtils = repositoryUtils;
    }

    public <ET extends AbstractEntity<T>, T extends Serializable> ET save(ET entity, Class<T> pkClass) {
        CrudRepository repositoryFor = getRepositoryFor(entity, pkClass);
        ET save = (ET) repositoryFor.save(entity);
        return save;
    }

    public <T extends Serializable> Optional<? extends AbstractEntity> findById(AbstractEntity<T> entity, Class<T> pkClass) {
        CrudRepository<? extends AbstractEntity, T> repositoryFor = getRepositoryFor(entity, pkClass);
        return repositoryFor.findById(entity.getId());
    }

    public <T extends Serializable> Iterable<? extends AbstractEntity> findAll(AbstractEntity<T> entity, Class<T> pkClass) {

        CrudRepository<? extends AbstractEntity, T> repositoryFor = getRepositoryFor(entity, pkClass);
        Iterable<? extends AbstractEntity> all = repositoryFor.findAll();
        return all;
    }

    public <ET extends AbstractEntity<T>, T extends Serializable> void delete(ET entity, Class<T> pkClass) {
        CrudRepository repositoryFor = getRepositoryFor(entity, pkClass);
        repositoryFor.deleteById(entity.getId());
    }

    private <T extends Serializable> CrudRepository<? extends AbstractEntity, T> getRepositoryFor(AbstractEntity<T> entity, Class<T> pkClass) {
        return repositoryUtils.getRepositoryFor(entity.getClass(), pkClass);
    }
}