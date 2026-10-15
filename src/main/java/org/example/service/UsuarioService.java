package org.example.service;

import org.example.exception.RegraNegocioException;
import org.example.model.Usuario;
import org.example.repository.TipoAcessoRepository;
import org.example.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UsuarioService {
    private final UsuarioRepository repository;
    private final TipoAcessoRepository tipoAcessoRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository repository, TipoAcessoRepository tipoAcessoRepository,
                          PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.tipoAcessoRepository = tipoAcessoRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Usuario> findAll() { return repository.findAll(); }

    public Optional<Usuario> findOne(Long id) { return repository.findById(id); }

    public Usuario save(Usuario usuario) {
        // RN08
        if (usuario.getTipoAcesso() == null || usuario.getTipoAcesso().getId() == null
                || !tipoAcessoRepository.existsById(usuario.getTipoAcesso().getId())) {
            throw new RegraNegocioException("Informe um tipo de acesso válido e previamente cadastrado.");
        }
        Optional<Usuario> existente = repository.findByUsuario(usuario.getUsuario());
        if (existente.isPresent() && !Objects.equals(existente.get().getId(), usuario.getId())) {
            throw new RegraNegocioException("Já existe um usuário com esse login.");
        }
        // mantém a data de cadastro original na edição
        if (usuario.getId() != null) {
            repository.findById(usuario.getId()).ifPresent(antigo -> usuario.setDtCadastro(antigo.getDtCadastro()));
        }
        if (usuario.getDtCadastro() == null) {
            usuario.setDtCadastro(LocalDate.now());
        }
        // RN09: nunca grava a senha em texto puro
        if (!usuario.getSenha().startsWith("$2")) {
            usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        }
        return repository.save(usuario);
    }

    public void delete(Long id) { repository.deleteById(id); }
}
