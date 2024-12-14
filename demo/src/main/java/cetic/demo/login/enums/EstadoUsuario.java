package cetic.demo.login.enums;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum EstadoUsuario {
    
    ACTIVO("A", "Activo"),
    INATIVO("I", "Inativo"),
    PENDENTE("P", "Pendente");

    private String codigo;

    @JsonValue
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    private String descricao;

    private EstadoUsuario(String codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;

    }

    @JsonCreator
    public static EstadoUsuario doValor(String codigo) {

        if (codigo.equals("A")) {
            return ACTIVO;
        } else if (codigo.equals("I")) {
            return INATIVO;
        } else if (codigo.equals("P")) {
            return PENDENTE;
        } else {

            return null;
        }

    }

}
