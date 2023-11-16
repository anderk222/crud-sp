package anderk222.crudsp.repository;

import anderk222.crudsp.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository implements JDBCRepository<User, Long> {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<User> findAll() {

        List<User> users = jdbcTemplate.query(
                "SELECT * FROM bank.get_users()", BeanPropertyRowMapper.newInstance(User.class));
        return users;
    }

    @Override
    public List<User> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public Optional<User> findById(Long id) {

        try{
        User user = jdbcTemplate.queryForObject(
                "SELECT * FROM bank.get_user(?)",
                BeanPropertyRowMapper.newInstance(User.class), id);

        return Optional.of(user);

        }catch (DataAccessException ex){
            ex.printStackTrace();

            return Optional.empty();
        }
    }

    @Override
    public User save(User entity) {

        User user = jdbcTemplate.queryForObject(
                "SELECT * FROM bank.save_user(?,?)",
                BeanPropertyRowMapper.newInstance(User.class),
                entity.getNombre(),
                entity.getTelefono());

        return user;
    }

    @Override
    public Optional<User> update(User entity) {

        try{

            User user = jdbcTemplate.queryForObject(
                    "SELECT * FROM bank.update_user(?,?,?)",
                    BeanPropertyRowMapper.newInstance(User.class),
                    entity.getNombre(),
                    entity.getTelefono(),
                    entity.getId());

            return Optional.of(user);

        }catch (DataAccessException ex){

            ex.printStackTrace();

            return Optional.empty();

        }
    }

    @Override
    public Optional<User> deleteById(Long id) {

        try{

            User user = jdbcTemplate.queryForObject(
                    "SELECT * FROM bank.delete_user(?)",
                    BeanPropertyRowMapper.newInstance(User.class),
                    id);

            return Optional.of(user);
        }catch(DataAccessException ex){

            ex.printStackTrace();

            return Optional.empty();

        }
    }
}
