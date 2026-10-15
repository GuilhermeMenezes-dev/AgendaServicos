# Agenda de Serviços

Spring Boot 3.5 + Thymeleaf + Bootstrap + Spring Data JPA + H2.

## Rodar
`mvn spring-boot:run` (ou rodar a classe `Main` na IDE) e abrir http://localhost:8080

- Login inicial criado automaticamente: `admin` / `admin123`
- Console do H2: http://localhost:8080/h2-console (JDBC URL: `jdbc:h2:file:./data/agenda`, user `sa`, senha vazia)

## Ordem de cadastro
Tipo de acesso → Endereço → Cliente / Usuário / Serviço → Agendamento.

## Regras de negócio (camada Service)
| RN | Onde |
|----|------|
| RN01 data futura | AgendaService.save |
| RN02 conflito de horário | AgendaService.save + AgendaRepository |
| RN03 valor > 0 | Servico (@Positive) + ServicoService.save |
| RN04 cliente obrigatório | Agenda (@NotNull) + AgendaService.save |
| RN05 excluir serviço | ServicoService.delete |
| RN06 excluir cliente | ClienteService.delete |
| RN07 e-mail válido/único | Cliente (@Email) + ClienteService.save |
| RN08 tipo de acesso válido | UsuarioService.save |
| RN09 BCrypt | UsuarioService.save |
| RN10 pesquisa | AgendaService.pesquisar (filtros em /agendamentos) |

Dashboard (opcional) na página inicial `/`.
