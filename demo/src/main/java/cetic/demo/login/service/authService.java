package cetic.demo.login.service;

import java.security.SecureRandom;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import cetic.demo.login.dto.LoginDTO;
import cetic.demo.login.dto.acessDTO;
import cetic.demo.login.entidade.Codigo2FAEnt;
import cetic.demo.login.repository.Codigo2FARep;
import cetic.demo.seguranca.jwt.utilJwt;
import cetic.demo.seguranca.wrapper.UsuDetImp;

@Service
public class authService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private utilJwt utilJwt;

    @Autowired
    private EmailSer emailSer;

    @Autowired
    private Codigo2FARep codigo2faRep;
     @Autowired
    private PasswordEncoder passwordEncoder;  // Injeta o PasswordEncoder

    public acessDTO login(LoginDTO authDTO) {
        try {
            UsernamePasswordAuthenticationToken userAut = new UsernamePasswordAuthenticationToken(authDTO.getEmail(), authDTO.getSenha());

            Authentication authentication = authenticationManager.authenticate(userAut);
            UsuDetImp userAutenticado = (UsuDetImp) authentication.getPrincipal();

            if (userAutenticado == null || !authDTO.getEmail().equals(userAutenticado.getEmail()) 
                    || !passwordEncoder.matches(authDTO.getSenha(), userAutenticado.getPassword())) { 
                throw new BadCredentialsException("Dados da conta inválidos");
            }

            if (!userAutenticado.isAccountNonLocked()) {
                throw new BadCredentialsException("Conta não está ativa");
            }

            String tokenTemp = utilJwt.gerarToken(userAutenticado);

            SecureRandom random = new SecureRandom();
            int codigo = 100000 + random.nextInt(900000);
            Instant dataExpiracao = Instant.now().plusSeconds(300);

            Codigo2FAEnt codigo2FAEnt = new Codigo2FAEnt(String.valueOf(codigo), userAutenticado.getUsuario(), dataExpiracao);
            codigo2faRep.save(codigo2FAEnt);

            String mensagem = "Seu código de autenticação de dois fatores é: " + codigo + ". O código expira em 5 minutos.";
            emailSer.enviarEmail(userAutenticado.getUsername(), "Código de Autenticação 2FA", mensagem);

            return new acessDTO(tokenTemp);

        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Erro na autenticação: " + e.getMessage()); // Re-throwing the exception
        }
    }
    public acessDTO verificarCodigo2FA(String codigo2FA, Authentication authentication) {
        if (authentication == null) {
            throw new BadCredentialsException("Autenticação não disponível");
        }

        try {
            UsuDetImp userAutenticado = (UsuDetImp) authentication.getPrincipal();
            Codigo2FAEnt codigo2FAEnt = codigo2faRep.findByUsuario(userAutenticado.getUsuario());

            if (codigo2FAEnt == null || codigo2FAEnt.isUsado()) {
                throw new BadCredentialsException("Código 2FA inválido ou já utilizado.");
            }

            if (Instant.now().isAfter(codigo2FAEnt.getDataExpiracao())) {
                throw new BadCredentialsException("Código de autenticação expirado.");
            }

            if (!codigo2FAEnt.getCodigo2FA().equals(codigo2FA)) {
                throw new BadCredentialsException("Código de autenticação inválido.");
            }

            codigo2FAEnt.setUsado(true);
            codigo2faRep.save(codigo2FAEnt);

            String tokenFinal = utilJwt.gerarTokenCompleto(userAutenticado);
            codigo2faRep.delete(codigo2FAEnt);

            return new acessDTO(tokenFinal);

        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Erro na verificação do código 2FA: " + e.getMessage());
        }
    }
}
