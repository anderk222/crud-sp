package anderk222.crudsp.domain;

import com.google.gson.annotations.Expose;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
@Table(name = "bank_account", schema="bank")
public class Account {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    @Expose
    private Long id;
    @Expose
    private BigDecimal saldo = BigDecimal.ZERO;

    @Expose
    private Long userId;
}
