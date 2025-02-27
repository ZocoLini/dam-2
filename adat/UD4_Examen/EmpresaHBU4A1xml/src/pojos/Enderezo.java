package pojos;

public class Enderezo {
    private String rua;
    private String cp;
    private String localidade;
    private String provincia;

    public Enderezo() {
    }

    public Enderezo(String rua, String cp, String localidade, String provincia) {
        this.rua = rua;
        this.cp = cp;
        this.localidade = localidade;
        this.provincia = provincia;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public String getLocalidade() {
        return localidade;
    }

    public void setLocalidade(String localidade) {
        this.localidade = localidade;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }
}
