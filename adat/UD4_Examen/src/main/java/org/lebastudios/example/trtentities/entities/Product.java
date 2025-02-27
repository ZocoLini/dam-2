package org.lebastudios.example.trtentities.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "pr_product")
public class Product implements Cloneable
{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", nullable = false)
    private String name = "Unknown Product";

    @Column(name = "price", nullable = false)
    private BigDecimal price = BigDecimal.ZERO;

    @Column(name = "img_path", nullable = false, length = 999999)
    private String imgPath = "";
    
    private BigDecimal taxes = new BigDecimal("0.10");
    
    @ManyToOne
    @JoinColumn(name = "taxes_type", referencedColumnName = "id")
    private TaxType taxType;

    @Column(name = "taxes_included", nullable = false)
    private Boolean taxesIncluded = true;

    @Column(name = "enabled", nullable = false)
    private boolean enabled = true;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "category_name", referencedColumnName = "category_name")
    @JoinColumn(name = "sub_category_name", referencedColumnName = "name")
    private SubCategory subCategory;

    public BigDecimal getTaxes()
    {
        return taxType == null ? taxes : taxType.getValue();
    }
    
    @Deprecated
    public void setTaxes(BigDecimal taxes)
    {
        if (taxes.compareTo(BigDecimal.ZERO) < 0 || taxes.compareTo(BigDecimal.ONE) > 0)
        {
            throw new IllegalArgumentException("The value must be between 0 and 1 (Both included).");
        }
        
        this.taxes = taxes;
    }
    
    /**
     * Returns the price of the product with taxes.
     * @return The price of the product with taxes.
     */
    public BigDecimal getPrice()
    {
        return taxesIncluded ? price : price.add(price.multiply(getTaxes()));
    }
    
    public void setTaxedPrice(BigDecimal price)
    {
        this.price = taxesIncluded ? price : BigDecimalOperations.dividePrecise(price, getTaxes().add(BigDecimal.ONE));
    }
    
    public BigDecimal getNotTaxedPrice()
    {
        return taxesIncluded ? BigDecimalOperations.dividePrecise(price, getTaxes().add(BigDecimal.ONE)) : price;
    }

    @Override
    public int hashCode()
    {
        int result = id;
        result = 31 * result + Objects.hashCode(name);
        result = 31 * result + Objects.hashCode(price);
        return result;
    }

    @Override
    public final boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof Product product)) return false;

        return id == product.id && Objects.equals(name, product.name) &&
                Objects.equals(price, product.price);
    }

    @Override
    public Product clone()
    {
        try
        {
            return (Product) super.clone();
        }
        catch (CloneNotSupportedException e)
        {
            throw new AssertionError();
        }
    }
}
