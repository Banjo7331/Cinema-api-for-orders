package springboot.cinemaapi.cinemaapifororders.entity.order;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products",uniqueConstraints = {@UniqueConstraint(columnNames = {"code"})})
public class Product {

    public enum Type {
        Coffee,
        Bar,
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String code;


    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private Type type;

}
