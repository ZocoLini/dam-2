package POJOS;
// Generated 09-feb-2023 8:57:23 by Hibernate Tools 3.6.0

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "CURSO", schema = "dbo", catalog = "EMPRESAHB2_25")
public class Curso implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Codigo")

    private int codigo;
    @Column(name = "Nome", length = 30, nullable = false, unique = true)
    private String nome;

    @Column(name = "Horas")
    private Integer horas;

    /*
    @OneToMany(mappedBy = "curso")
    Indica que esta entidad está en una relación uno a muchos con otra entidad.
    mappedBy = "curso" → Especifica que la clave foránea está en Edicion, en el atributo curso.
    cascade = CascadeType.ALL
    Propaga operaciones (insert, update, delete) desde Curso a Edicion.
     */
    //mapeo de las ediciones de un curso
    @OneToMany(mappedBy = "curso", cascade = CascadeType.ALL, orphanRemoval = true)
   // @OrderColumn(name = "Numero", nullable = false)
    //orphanRemoval = true  Si eliminamos una edición de la lista, Hibernate también la eliminará de la base de datos.
    private List<Edicion> edicions = new ArrayList();

    public Curso() {
    }

    public Curso(int codigo, String nome) {
        this.codigo = codigo;
        this.nome = nome;
    }

    public Curso(String nome, Integer horas) {
        this.nome = nome;
        this.horas = horas;
    }

    public int getCodigo() {
        return this.codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getHoras() {
        return this.horas;
    }

    public void setHoras(Integer horas) {
        this.horas = horas;
    }

    public List<Edicion> getEdicions() {
        return edicions;
    }

    public void setEdicions(List<Edicion> edicions) {
        this.edicions = edicions;
    }

    public long getNextEdicionNumero() {
        if (edicions.isEmpty()) {
            return 1;
        }
        return edicions.size() + 1;

    }
}
