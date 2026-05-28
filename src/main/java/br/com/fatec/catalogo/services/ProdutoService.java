package br.com.fatec.catalogo.services;

import br.com.fatec.catalogo.models.ProdutoAuditoriaModel;
import br.com.fatec.catalogo.models.ProdutoModel;
import br.com.fatec.catalogo.repositories.ProdutoAuditoriaRepository;
import br.com.fatec.catalogo.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository repository;

    @Autowired
    private ProdutoAuditoriaRepository auditoriaRepository;

    public List<ProdutoModel> listarTodos() {
        return repository.findAll();
    }

    public List<ProdutoModel> listarPorNome(String nome) {
        return repository.findByNomeContainingIgnoreCase(nome);
    }

    public ProdutoModel buscarPorId(long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto nao encontrado: " + id));
    }

    public List<ProdutoModel> listarPorCategoria(Long idCategoria) {
        return repository.findByCategoriaIdCategoria(idCategoria);
    }

    public List<ProdutoAuditoriaModel> listarHistoricoAtualizacoes() {
        return auditoriaRepository.findAllByOrderByDataEventoDesc();
    }

    public ProdutoAuditoriaModel buscarAuditoriaPorId(Long idAuditoria) {
        return auditoriaRepository.findById(idAuditoria)
                .orElseThrow(() -> new IllegalArgumentException("Registro de auditoria nao encontrado: " + idAuditoria));
    }

    @Transactional
    public ProdutoModel salvar(ProdutoModel produto) {
        if (produto.getQuantidade() == null || produto.getQuantidade() < 0) {
            throw new IllegalArgumentException("A quantidade em estoque nao pode ser negativa.");
        }

        boolean edicao = produto.getIdProduto() != 0;
        if (!edicao && repository.existsByNome(produto.getNome())) {
            throw new RuntimeException("Ja existe um produto com este nome.");
        }

        ProdutoSnapshot antes = null;
        if (edicao) {
            antes = ProdutoSnapshot.from(buscarPorId(produto.getIdProduto()));
        }

        ProdutoModel salvo = repository.save(produto);
        registrarAuditoria(salvo, edicao ? "UPDATE" : "CREATE", antes);
        return salvo;
    }

    @Transactional
    public void excluir(long id) {
        ProdutoModel produto = buscarPorId(id);
        registrarAuditoria(produto, "DELETE", ProdutoSnapshot.from(produto));
        repository.deleteById(id);
    }

    private void registrarAuditoria(ProdutoModel depois, String acao, ProdutoSnapshot antes) {
        ProdutoAuditoriaModel log = new ProdutoAuditoriaModel();
        log.setIdProduto(depois.getIdProduto());
        log.setNomeProduto(depois.getNome());
        log.setQuantidade(depois.getQuantidade());
        log.setAcao(acao);
        log.setUsuarioAcao(getUsuarioAutenticado());

        if (antes != null) {
            log.setNomeAntes(antes.nome);
            log.setValorAntes(antes.valor);
            log.setQuantidadeAntes(antes.quantidade);
            log.setCategoriaAntes(antes.categoriaNome);
        }

        if (!"DELETE".equals(acao)) {
            log.setNomeDepois(depois.getNome());
            log.setValorDepois(depois.getValor());
            log.setQuantidadeDepois(depois.getQuantidade());
            log.setCategoriaDepois(getCategoriaNome(depois));
        }

        auditoriaRepository.save(log);
    }

    private String getUsuarioAutenticado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getName())) {
            return "sistema";
        }
        return authentication.getName();
    }

    private String getCategoriaNome(ProdutoModel produto) {
        return produto.getCategoria() != null ? produto.getCategoria().getNome() : null;
    }

    private static class ProdutoSnapshot {
        private final String nome;
        private final java.math.BigDecimal valor;
        private final Integer quantidade;
        private final String categoriaNome;

        private ProdutoSnapshot(String nome, java.math.BigDecimal valor, Integer quantidade, String categoriaNome) {
            this.nome = nome;
            this.valor = valor;
            this.quantidade = quantidade;
            this.categoriaNome = categoriaNome;
        }

        private static ProdutoSnapshot from(ProdutoModel produto) {
            return new ProdutoSnapshot(
                    produto.getNome(),
                    produto.getValor(),
                    produto.getQuantidade(),
                    produto.getCategoria() != null ? produto.getCategoria().getNome() : null
            );
        }
    }
}
