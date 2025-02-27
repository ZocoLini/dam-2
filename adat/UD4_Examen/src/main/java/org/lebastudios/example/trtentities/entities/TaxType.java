package org.lebastudios.example.trtentities.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.lebastudios.database.Database;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "pr_tax_type")
public class TaxType
{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(name = "name", unique = true, nullable = false) 
    private String name;

    /**
     * The tax value. It is a number between 0 and 1.
     */
    @Column(name = "value") 
    private BigDecimal value = new BigDecimal("0.10");

    @Column(name = "description") 
    private String description = "";
    
    @OneToMany(mappedBy = "taxType", fetch = FetchType.LAZY)
    private final Set<Product> products = new HashSet<>();

    public TaxType(@NonNull String name, @NonNull BigDecimal value, String description)
    {
        setProperties(name, value, description);
    }

    public void setProperties(@NonNull String name, @NonNull BigDecimal value, String description)
    {
        if (value.compareTo(BigDecimal.ZERO) < 0 || value.compareTo(BigDecimal.ONE) > 0)
        {
            throw new IllegalArgumentException("The value must be between 0 and 1 (Both included).");
        }

        this.name = name;
        this.value = value;
        this.description = description;
    }
    
    public static boolean isNameAvailable(String name)
    {
        return Database.getInstance().connectQuery(session -> 
                session.createQuery("SELECT COUNT(*) FROM TaxType WHERE name = :name", Long.class)
                        .setParameter("name", name).getSingleResult() == 0);
    }

    @Override
    public final boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof TaxType taxType)) return false;

        return name.equals(taxType.name);
    }

    @Override
    public int hashCode()
    {
        return name.hashCode();
    }
}
