package POJOS;

public class EmpregadoProxectoId  implements java.io.Serializable {
     private String nssempregado;
     private int numProxecto;

    public EmpregadoProxectoId() {
    }

    public EmpregadoProxectoId(String nssempregado, int numProxecto) {
       this.nssempregado = nssempregado;
       this.numProxecto = numProxecto;
    }
   
    public String getNssempregado() {
        return this.nssempregado;
    }
    
    public void setNssempregado(String nssempregado) {
        this.nssempregado = nssempregado;
    }
    public int getNumProxecto() {
        return this.numProxecto;
    }
    
    public void setNumProxecto(int numProxecto) {
        this.numProxecto = numProxecto;
    }


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof EmpregadoProxectoId) ) return false;
		 EmpregadoProxectoId castOther = ( EmpregadoProxectoId ) other; 
         
		 return ( (this.getNssempregado()==castOther.getNssempregado()) || ( this.getNssempregado()!=null && castOther.getNssempregado()!=null && this.getNssempregado().equals(castOther.getNssempregado()) ) )
 && (this.getNumProxecto()==castOther.getNumProxecto());
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getNssempregado() == null ? 0 : this.getNssempregado().hashCode() );
         result = 37 * result + this.getNumProxecto();
         return result;
   }   


}


