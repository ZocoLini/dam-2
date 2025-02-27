package org.lebastudios.example.trtentities.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cr_receipt_modification")
public class ReceiptModification
{
    @Id
    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "id", nullable = false)
    private Receipt superReceipt;
    
    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "new_receipt_id", nullable = false)
    private Receipt newReceipt;
    
    @Column(name = "reason")
    private String reason;
}
