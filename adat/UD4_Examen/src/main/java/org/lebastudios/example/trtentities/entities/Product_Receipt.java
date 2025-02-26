package org.lebastudios.example.trtentities.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@NoArgsConstructor
@Table(name = "cr_product_receipt")
@Entity
public class Product_Receipt
{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter private int id;

    @Column(name = "quantity", nullable = false)
    @Getter @Setter private BigDecimal quantity;

    /**
     * The total amount paid for the product. Taxes included. 
     * This is the price of the product multiplied by the 
     * quantity. The value variable is the price of the product with taxes.
     */
    @Column(name = "total_value", nullable = false)
    @Getter @Setter private BigDecimal totalValue;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "receipt_id", referencedColumnName = "ID")
    @Getter @Setter private Receipt receipt;

    @Column(name = "product_value", nullable = false)
    private BigDecimal productValue;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "taxes")
    private BigDecimal taxes = new BigDecimal("0.10");

    @Column(name = "taxes_included", nullable = false)
    private Boolean taxesIncluded = true;
    
    public Product_Receipt(Product product, BigDecimal quantity)
    {
        this.quantity = quantity;
        setProduct(product);
    }
    
    public void setProduct(Product product)
    {
        if (quantity == null) throw new IllegalStateException("Quantity should be set before setting the product.");
        
        productName = product.getName();
        taxesIncluded = product.getTaxesIncluded();
        productValue = taxesIncluded ? product.getPrice() : product.getNotTaxedPrice();
        totalValue = product.getPrice().multiply(quantity);
        taxes = product.getTaxes();
    }
    
    public Product getProduct()
    {
        var product = new Product();
        product.setName(productName);
        product.setPrice(productValue);
        product.setTaxes(taxes);
        product.setTaxesIncluded(taxesIncluded);
        return product;
    }
}
