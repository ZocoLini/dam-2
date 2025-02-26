package org.lebastudios.example.trtentities.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Session;

import javax.persistence.*;
import java.awt.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "cr_receipt")
public class Receipt
{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "payment_method", nullable = false)
    @Setter private String paymentMethod;

    @Column(name = "table_name", nullable = false)
    @Setter private String tableName;

    /**
     * The amount of money that the client paid. Not the total amount of the receipt.
     */
    @Column(name = "payment_amount", nullable = false)
    @Setter private BigDecimal paymentAmount;

    @Column(name = "taxes_amount", nullable = false)
    @Setter private BigDecimal taxesAmount;

    @Column(name = "client_name")
    private String clientName;

    @Column(name = "client_identifier")
    private String clientIdentifier;

    @Column(name = "employee_name")
    @Setter private String employeeName;

    @OneToMany(mappedBy = "receipt", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @Setter private Set<Product_Receipt> products;

    @OneToOne(mappedBy = "receipt", optional = false, cascade = CascadeType.PERSIST)
    @Setter private Transaction transaction;
    
    /// When the receipt has been modified, this field will be set.
    @OneToOne(mappedBy = "superReceipt")
    ReceiptModification modifiedBy;
    
    /// When this receipt is a modification of another receipt, this field will be set.
    @OneToOne(mappedBy = "newReceipt")
    ReceiptModification modifies;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    @Setter private Status status = Status.DEFAULT;

    public void setAccount(Account account)
    {
        employeeName = account.getName();
    }

    public void setClient(String name, String identifier)
    {
        clientName = name;
        clientIdentifier = identifier;
    }

    public void setOrder(Order order, Session session)
    {
        tableName = order.getOrderName();
        taxesAmount = order.getTotalTaxes();

        products = new HashSet<>();

        for (OrderItem orderItem : order.getOrderItems())
        {
            Product_Receipt productReceipt = new Product_Receipt(orderItem.intoProduct(), orderItem.getQuantity());
            productReceipt.setReceipt(this);

            products.add(productReceipt);
        }

        Transaction transaction = new Transaction();
        transaction.setAmount(order.getTotal());
        transaction.setDate(LocalDateTime.now());
        transaction.setReceipt(this);
        this.transaction = transaction;

        session.persist(this);
        session.flush();

        transaction.setDescription(LangFileLoader.getTranslation("word.receipt") + " #" + id);
        session.merge(this);

    }

    public BigDecimal getTaxedTotal()
    {
        return transaction.getAmount();
    }

    public BigDecimal getNotTaxedTotal()
    {
        return getTaxedTotal().subtract(taxesAmount);
    }

    public String getClientString()
    {
        if (clientName == null)
        {
            return LangFileLoader.getTranslation("phrase.generalpublicclient");
        }
        else
        {
            return clientName + " - " + clientIdentifier;
        }
    }

    public String getAttendantName()
    {
        return employeeName == null
                ? "Unknown employee"
                : employeeName;
    }

    public Image getStatusIcon()
    {
        return ImageLoader.getIcon(status.getIconName());
    }
    
    public enum Status
    {
        DELETED, 
        DEFAULT, 
        MODIFIED;

        public String getIconName()
        {
            return switch (this)
            {
                case DELETED -> "deleted-bill.png";
                case DEFAULT -> "default-bill.png";
                case MODIFIED -> "deleted-bill.png";
                default -> throw new RuntimeException("Unknown receipt status: " + this);
            };
        }
    }
}
