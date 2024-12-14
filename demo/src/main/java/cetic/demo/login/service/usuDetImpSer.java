package cetic.demo.login.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import cetic.demo.seguranca.wrapper.UsuDetImp; 
import cetic.demo.login.entidade.UsuarioEnty;
import cetic.demo.login.repository.UsuarioRep;


@Service
public class usuDetImpSer implements UserDetailsService {

    @Autowired
    private UsuarioRep UsuarioRep;

    
    private UsuarioEnty usuarioEnty;

 
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UsuarioEnty usuario = UsuarioRep.findByEmail(username);  // Encontre o usuário pelo email
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuário não encontrado");
        }
        return UsuDetImp.build(usuario);  // Retorna o UsuDetImp com o usuário encontrado
    }
    

    public UUID getId() {
        return usuarioEnty.getId();
    }

    public void UsuDetImp(UsuarioEnty usuarioEnty) {
        this.usuarioEnty = usuarioEnty;
    }
}
