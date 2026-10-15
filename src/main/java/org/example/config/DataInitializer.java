package org.example.config;

import org.example.model.TipoAcesso;
import org.example.model.Usuario;
import org.example.repository.TipoAcessoRepository;
import org.example.repository.UsuarioRepository;
import org.example.service.UsuarioService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final TipoAcessoRepository tipoAcessoRepository;
    private final UsuarioRepository usuarioRepository;
    private final UsuarioService usuarioService;

    public DataInitializer(TipoAcessoRepository tipoAcessoRepository, UsuarioRepository usuarioRepository,
                           UsuarioService usuarioService) {
        this.tipoAcessoRepository = tipoAcessoRepository;
        this.usuarioRepository = usuarioRepository;
        this.usuarioService = usuarioService;
    }

    @Override
    public void run(String... args) {
        if (tipoAcessoRepository.count() == 0) {
            TipoAcesso admin = new TipoAcesso();
            admin.setDescricao("Administrador");
            tipoAcessoRepository.save(admin);

            TipoAcesso comum = new TipoAcesso();
            comum.setDescricao("Usuário comum");
            tipoAcessoRepository.save(comum);
        }
        if (usuarioRepository.count() == 0) {
            Usuario u = new Usuario();
            u.setUsuario("admin");
            u.setSenha("admin123");
            u.setTipoAcesso(tipoAcessoRepository.findAll().get(0));
            usuarioService.save(u);
        }
    }
}
