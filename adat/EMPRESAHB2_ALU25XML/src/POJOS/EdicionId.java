package POJOS;

public class EdicionId  implements java.io.Serializable {

     private int codigo;
     private int numero;

    public EdicionId() {
    }

    public EdicionId(int codigo) {
        this.codigo = codigo;
    }

    public EdicionId(int codigo, int numero) {
       this.codigo = codigo;
       this.numero = numero;
    }
   
    public int getCodigo() {
        return this.codigo;
    }
    
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
    public int getNumero() {
        return this.numero;
    }
    
    public void setNumero(int numero) {
        this.numero = numero;
    }


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof EdicionId) ) return false;
		 EdicionId castOther = ( EdicionId ) other; 
         
		 return (this.getCodigo()==castOther.getCodigo())
 && (this.getNumero()==castOther.getNumero());
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + this.getCodigo();
         result = 37 * result + this.getNumero();
         return result;
   }   


}


