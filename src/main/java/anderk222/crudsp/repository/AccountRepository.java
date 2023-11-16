package anderk222.crudsp.repository;

import anderk222.crudsp.domain.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public class AccountRepository implements JDBCRepository<Account, Long> {

    @Autowired
    JdbcTemplate jdbcTemplate;


    public List<Account> transaction(long sender, long receiver, BigDecimal cantidad){

        List<Account> accs = jdbcTemplate.query(
                "SELECT * FROM bank.transaction(?,?,?)",
                BeanPropertyRowMapper.newInstance(Account.class),
                sender,receiver,cantidad);

        return accs;

    }

    public Optional<Account> addMoney(BigDecimal cantidad,Long accId){

        try{

            Account acc = jdbcTemplate.queryForObject(
                    "SELECT * FROM bank.add_money(?,?)",
                    BeanPropertyRowMapper.newInstance(Account.class), cantidad, accId);

            return Optional.of(acc);

        }catch (DataAccessException ex){

            ex.printStackTrace();

            return Optional.empty();

        }

    }

    @Deprecated
    public Account save(long userId) {

        Account acc = jdbcTemplate.queryForObject(
                "SELECT * FROM bank.create_account(?)",
                BeanPropertyRowMapper.newInstance(Account.class),
                userId);

        return acc;
    }

    @Override
    public List<Account> findAll() {

        List<Account> users = jdbcTemplate.query(
                "SELECT * FROM bank.get_accounts()",
                BeanPropertyRowMapper.newInstance(Account.class));

        return users;
    }

    @Override
    @Deprecated
    public List<Account> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public Optional<Account> findById(Long id) {

        try{

            Account acc = jdbcTemplate.queryForObject("SELECT * FROM bank.get_account(?)",
                    BeanPropertyRowMapper.newInstance(Account.class),id);

            return Optional.of(acc);

        }catch(DataAccessException ex){

            ex.printStackTrace();

            return Optional.empty();

        }
    }

    @Override
    @Deprecated
    public Account save(Account entity) {

        Account acc = jdbcTemplate.queryForObject(
                "SELECT * FROM bank.create_account(?)",
                BeanPropertyRowMapper.newInstance(Account.class),
                entity.getUserId());

        return acc;
    }


    @Override
    @Deprecated
    public Optional<Account> update(Account entity) {
    return Optional.empty();
    }

    @Override
    @Deprecated
    public Optional<Account> deleteById(Long id) {
        return Optional.empty();
    }
}