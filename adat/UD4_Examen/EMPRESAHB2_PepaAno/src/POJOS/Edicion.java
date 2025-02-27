package POJOS;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.persistence.Temporal;

@Entity
@Table(name = "EDICION", schema = "dbo", catalog = "EMPRESAHB2_25")
public class Edicion implements java.io.Serializable {

    @EmbeddedId

    private EdicionId id;
    //el curso al que pertenece la edicion

    /* @MapsId("codigo")

Vincula automáticamente el campo curso con codigo de la clave primaria EdicionId.
Evita que tengamos que almacenar codigo dos veces en Edicion.
     */
// El curso al que pertenece la edición
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("codigo") //necesaropro por que la clave es compuesta
    @JoinColumn(name = "Codigo", nullable = false)
    private Curso curso;

    @Column(name = "Data")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date data;

    @Column(name = "Lugar", length = 25)
    private String lugar;

    //    LOS ALUMNOS QUE VAN AL CURRSO
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "EDICIONCURSO_EMPREGADO",
            joinColumns = {
                @JoinColumn(name = "Codigo", referencedColumnName = "Codigo"),  //se refieren a los nombres de las columnas en la tabla intermedia  y referencia al PK edicion
                @JoinColumn(name = "Numero", referencedColumnName = "Numero")

            },
            inverseJoinColumns = @JoinColumn(name = "NSS", referencedColumnName = "NSS")
    )

    private Collection<Empregado> alumnos = new ArrayList();

//     el profesor que da el curso  
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Profesor", nullable = false)
    private Empregadofixo profesor;

    public Edicion() {
       
    }

    public Edicion(Date data, String lugar, Empregadofixo profesor) {
        this.data = data;
        this.lugar = lugar;
        this.profesor = profesor;
       
        }
    

    public EdicionId getId() {
        return this.id;
    }

    public void setId(EdicionId id) {
        this.id = id;
    }

    public Curso getCurso() {
        return this.curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public Empregadofixo getProfesor() {
        return profesor;
    }

    public void setProfesor(Empregadofixo profesor) {
        this.profesor = profesor;
    }

    public Date getData() {
        return this.data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getLugar() {
        return this.lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public Collection<Empregado> getAlumnos() {
        return alumnos;
    }

    public void setAlumnos(Collection<Empregado> alumnos) {
        this.alumnos = alumnos;
    }

}
