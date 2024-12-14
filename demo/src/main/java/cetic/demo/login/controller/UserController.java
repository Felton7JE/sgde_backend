package cetic.demo.login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cetic.demo.login.dto.EmailDTO;
import cetic.demo.login.dto.LoginDTO;
import cetic.demo.login.dto.TwoFactorDTO;
import cetic.demo.login.dto.UsuarioDTO;
import cetic.demo.login.dto.acessDTO;
import cetic.demo.login.service.UsuarioSer;

@RestController
@CrossOrigin
@RequestMapping("/autorizado")
public class UserController {

    @Autowired
    private cetic.demo.login.service.authService authService;

    @Autowired
    private UsuarioSer usuarioSer;

    @PostMapping(value = "/novousuario")
    public ResponseEntity<String> inserirNovoCadastro(@RequestBody UsuarioDTO usuarioDTO) {
        String resultado = usuarioSer.adicionarUsuario(usuarioDTO);

        if (resultado.contains("já está registrado")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(resultado);
        }

        if (resultado.contains("Usuário cadastrado com sucesso")) {
            return ResponseEntity.status(HttpStatus.OK).body(resultado);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Erro ao cadastrar o usuário. Verifique os seus dados e tente novamente.");
    }

    @GetMapping(value = "/verificarusuario/{uuid}")
    public String verificarCadastro(@PathVariable("uuid") String uuid) {
        return usuarioSer.verificarCadastro(uuid);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO authDTO) {
        try {
            return ResponseEntity.ok(authService.login(authDTO));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Credenciais inválidas: " + e.getMessage());
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Erro na autenticação: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro inesperado: " + e.getMessage());
        }
    }

    @PostMapping("/verificar2fa")
    public ResponseEntity<acessDTO> verificarCodigo2FA(@RequestBody TwoFactorDTO codigo2FA,
            Authentication authentication) {
        try {
            acessDTO acessToken = authService.verificarCodigo2FA(codigo2FA.getCodigo2FA(), authentication);
            return ResponseEntity.ok(acessToken);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new acessDTO(e.getMessage()));
        }
    }

    @PostMapping("/recuperar")
    public ResponseEntity<String> solicitarRecuperacaoSenha(@RequestBody EmailDTO emailDTO) {
        String email = emailDTO.getEmail().trim().toLowerCase(); // Sanitizar o email
        String resultado = usuarioSer.solicitarRecuperacaoSenha(email);
        return ResponseEntity.ok(resultado);
    }

    @PostMapping("/redefinirSenha/{token}")
    public ResponseEntity<String> redefinirSenha(@PathVariable String token, @RequestBody String novaSenha) {
        String resultado = usuarioSer.redefinirSenha(token, novaSenha);
        return ResponseEntity.ok(resultado);
    }
}
