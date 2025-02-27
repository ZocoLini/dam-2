package org.lebastudios.example.trtentities.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "pr_sub_category")
public class SubCategory
{
    @EmbeddedId
    private SubCategoryId id;

    @MapsId("categoryName")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "category_name", referencedColumnName = "name")
    private Category category;

    @OneToMany(mappedBy = "subCategory", fetch = FetchType.LAZY)
    private Set<Product> products;

    @Override
    public int hashCode()
    {
        return Objects.hashCode(id);
    }

    @Override
    public final boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof SubCategory that)) return false;

        return Objects.equals(id, that.id);
    }

    @Embeddable
    public record SubCategoryId(String categoryName, String name) {}
}
