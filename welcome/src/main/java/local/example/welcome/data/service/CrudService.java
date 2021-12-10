package local.example.welcome.data.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public abstract class CrudService<T, ID> {

    protected abstract JpaRepository<T, ID> abstractJpaRepository();

    public T create(T entity) {
        return abstractJpaRepository().save(entity);
    }

    public Optional<T> read(ID id) {
        return abstractJpaRepository().findById(id);
    }

    public Page<T> readAll(Pageable pageable) {
        return abstractJpaRepository().findAll(pageable);
    }

    public T update(T entity) {
        return abstractJpaRepository().save(entity);
    }

    public void delete(ID id) {
        abstractJpaRepository().deleteById(id);
    }
}
