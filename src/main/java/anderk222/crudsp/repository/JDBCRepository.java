package anderk222.crudsp.repository;

import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface JDBCRepository<T,I> {

    List<T> findAll();

    List<T> findAll(Pageable pageable);

    Optional<T> findById(I id);

    T save(T entity);

    Optional<T> update(T entity);

    Optional<T> deleteById(I id);

}