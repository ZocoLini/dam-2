package org.lebastudios.example.trtentities.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "pr_category")
public class Category
{

    @Id
    @Column(name = "name")
    private String name;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category", cascade = CascadeType.REMOVE)
    private Set<SubCategory> subCategories;
    
    @Override
    public int hashCode()
    {
        return Objects.hashCode(name);
    }

    @Override
    public boolean equals(Object o)
    {
        if (o == null || getClass() != o.getClass()) return false;

        Category category = (Category) o;
        return Objects.equals(name, category.name) &&
                Objects.equals(subCategories, category.subCategories);
    }
}
