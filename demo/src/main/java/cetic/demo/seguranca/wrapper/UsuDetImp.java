package cetic.demo.seguranca.wrapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import cetic.demo.login.entidade.UsuarioEnty;
import cetic.demo.login.enums.EstadoUsuario;

public class UsuDetImp implements UserDetails {

    private UsuarioEnty usuario;  // A entidade do usuário
    private UUID id;               // ID do usuário
    private String email;          // Email do usuário
    private String password;       // Senha do usuário
    private EstadoUsuario situacao; // Situação (ativo, inativo)
    private Collection<? extends GrantedAuthority> authorities; // Autoridades (roles)

    // Construtor que recebe uma instância de UsuarioEnty e inicializa o UsuDetImp
    public UsuDetImp(UsuarioEnty usuario) {
        this.usuario = usuario;
        this.id = usuario.getId();
        this.email = usuario.getEmail();
        this.password = usuario.getSenha();
        this.situacao = usuario.getSituacao();
        this.authorities = new ArrayList<>();  // Inicialize conforme necessário (roles, permissões, etc.)
    }

    // Método estático para criar uma instância de UsuDetImp a partir de UsuarioEnty
    public static UsuDetImp build(UsuarioEnty usuarioEnty) {
        return new UsuDetImp(usuarioEnty);
    }

    // Implementação dos métodos da interface UserDetails

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;  // Retorna as authorities (roles) do usuário
    }

    @Override
    public String getPassword() {
        return password;  // Retorna a senha do usuário
    }

    @Override
    public String getUsername() {
        return email;  // Retorna o email do usuário como username
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;  // A conta nunca expira
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;  // As credenciais nunca expiram
    }

    @Override
    public boolean isEnabled() {
        return this.situacao == EstadoUsuario.ACTIVO;  
    }

    public UsuarioEnty getUsuario() {
        return this.usuario;
    }

    public UUID getId() {
        return id;  
    }


    public String getNome() {
        return usuario != null ? usuario.getNome() : "Nome não disponível";
    }

    public String getEmail() {
        return email;
    }

    public EstadoUsuario getSituacao() {
        return situacao;
    }
}
