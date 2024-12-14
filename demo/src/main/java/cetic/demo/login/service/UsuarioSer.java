package cetic.demo.login.service;

import java.time.Instant;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import cetic.demo.login.dto.UsuarioDTO;
import cetic.demo.login.entidade.RecuperarSenhaEnty;
import cetic.demo.login.entidade.UsuarioEnty;
import cetic.demo.login.entidade.UsuarioVerificarEnt;
import cetic.demo.login.enums.EstadoUsuario;
import cetic.demo.login.repository.RecuperarSenhaRep;
import cetic.demo.login.repository.UsuarioRep;
import cetic.demo.login.repository.UsuarioVerificadorRep;

@Service
public class UsuarioSer {

    @Autowired
    private UsuarioRep usuarioRep;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioVerificadorRep usuarioVerificadorRep;

    @Autowired
    private RecuperarSenhaRep recuperarSenhaRep;

    @Autowired
    private EmailSer emailSer;

    public String adicionarUsuario(UsuarioDTO usuario) {
        try {
            if (usuarioRep.existsByEmail(usuario.getEmail())) {
                return "O e-mail já está registrado. Por favor, use um e-mail diferente.";
            }

            if (usuarioRep.existsByContato(usuario.getContato())) {
                return "O contato já está registrado. Por favor, use um contato diferente.";
            }

            UsuarioEnty usuarioEnty = new UsuarioEnty(usuario);
            usuarioEnty.setSenha(passwordEncoder.encode(usuario.getSenha()));
            usuarioEnty.setContato(usuario.getContato());
            usuarioEnty.setEmail(usuario.getEmail());
            usuarioEnty.setNome(usuario.getNome());
            usuarioEnty.setSituacao(EstadoUsuario.PENDENTE);
            usuarioRep.save(usuarioEnty);

            UsuarioVerificarEnt verificador = new UsuarioVerificarEnt();
            verificador.setUsuarioEnty(usuarioEnty);
            verificador.setUuid(UUID.randomUUID());
            verificador.setDataExpericacao(Instant.now().plusMillis(600000));
            usuarioVerificadorRep.save(verificador);

            String linkVerificacao = "http://localhost:5173/autorizado/verificarusuario/"
                    + verificador.getUuid().toString();
            String mensagem = "Olá, " + usuario.getNome() + ".\n\n" +
                    "Por favor, verifique sua conta clicando no link abaixo:\n" +
                    linkVerificacao + "\n\n" +
                    "Este link expira em 10 minutos.";
            emailSer.enviarEmail(usuario.getEmail(), "Verificação de Conta", mensagem);

            return "Usuário cadastrado com sucesso. Por favor, verifique seu e-mail para ativar a conta.";

        } catch (Exception e) {
            e.printStackTrace();
            return "Erro ao cadastrar o usuário. Verifique os seus dados e tente novamente.";
        }
    }

    public String verificarCadastro(String uuid) {
        UsuarioVerificarEnt usuarioVerificarEnt = usuarioVerificadorRep.findByUuid(UUID.fromString(uuid)).orElse(null);

        if (usuarioVerificarEnt != null) {
            if (usuarioVerificarEnt.getDataExpericacao().compareTo(Instant.now()) >= 0) {
                UsuarioEnty u = usuarioVerificarEnt.getUsuarioEnty();
                u.setSituacao(EstadoUsuario.ACTIVO);
                usuarioRep.save(u);
                return "Usuario Verificado";
            } else {
                usuarioVerificadorRep.delete(usuarioVerificarEnt);
                return "Tempo de verificação expirado";
            }
        } else {
            return "Usuário não encontrado ou não verificado!";
        }
    }

    public String solicitarRecuperacaoSenha(String email) {
        UsuarioEnty usuario = usuarioRep.findUsuarioByEmail(email);  
        System.out.println(usuario);

        if (usuario == null) {
            return "E-mail não registrado. Verifique e tente novamente.";
        }

        String token = UUID.randomUUID().toString();
        RecuperarSenhaEnty recuperacao = new RecuperarSenhaEnty();
        recuperacao.setUsuarioEnty(usuario);
        recuperacao.setUuid(UUID.fromString(token));
        recuperacao.setDataExpericacao(Instant.now().plusMillis(3600000));
        recuperarSenhaRep.save(recuperacao);

        String linkRecuperacao = "http://localhost:5173/recuperar-senha/" + token;
        String mensagem = "Olá, " + usuario.getNome() + ".\n\n" +
                "Para recuperar sua senha, clique no link abaixo:\n" +
                linkRecuperacao + "\n\n" +
                "Este link expira em 1 hora.";

        emailSer.enviarEmail(usuario.getEmail(), "Recuperação de Senha", mensagem);

        return "Instruções para recuperação de senha enviadas para o seu e-mail.";
    }

    public String redefinirSenha(String token, String novaSenha) {
        RecuperarSenhaEnty usuarioVerificarEnt = recuperarSenhaRep.findByUuid(UUID.fromString(token)).orElse(null);

        if (usuarioVerificarEnt == null) {
            return "Token de recuperação de senha inválido.";
        }

        if (usuarioVerificarEnt.getDataExpericacao().compareTo(Instant.now()) < 0) {
            recuperarSenhaRep.delete(usuarioVerificarEnt);
            return "O link de recuperação de senha expirou.";
        }

        UsuarioEnty usuario = usuarioVerificarEnt.getUsuarioEnty();
        usuario.setSenha(passwordEncoder.encode(novaSenha));
        usuarioRep.save(usuario);

        recuperarSenhaRep.delete(usuarioVerificarEnt);

        return "Senha redefinida com sucesso!";
    }

}
