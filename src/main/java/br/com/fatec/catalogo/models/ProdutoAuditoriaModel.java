package br.com.fatec.catalogo.models;

import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "TB_PRODUTO_AUDITORIA")
public class ProdutoAuditoriaModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAuditoria;

    private Long idProduto;

    @Column(nullable = false)
    private String nomeProduto;

    private Integer quantidade;

    @Column(nullable = false)
    private String acao;

    @Column(nullable = false)
    private LocalDateTime dataEvento;

    @Column(name = "usuario_acao")
    private String usuarioAcao;

    private String nomeAntes;
    private String nomeDepois;

    @Column(precision = 10, scale = 2)
    private BigDecimal valorAntes;

    @Column(precision = 10, scale = 2)
    private BigDecimal valorDepois;

    private Integer quantidadeAntes;
    private Integer quantidadeDepois;

    private String categoriaAntes;
    private String categoriaDepois;

    @PrePersist
    protected void onCreate() {
        this.dataEvento = LocalDateTime.now();
    }

    public Long getIdAuditoria() { return idAuditoria; }
    public void setIdAuditoria(Long idAuditoria) { this.idAuditoria = idAuditoria; }
    public Long getIdProduto() { return idProduto; }
    public void setIdProduto(Long idProduto) { this.idProduto = idProduto; }
    public String getNomeProduto() { return nomeProduto; }
    public void setNomeProduto(String nomeProduto) { this.nomeProduto = nomeProduto; }
    public Integer getQuantidade() { return quantidade; }
    public void setQuantidade(Integer quantidade) { this.quantidade = quantidade; }
    public String getAcao() { return acao; }
    public void setAcao(String acao) { this.acao = acao; }
    public LocalDateTime getDataEvento() { return dataEvento; }
    public void setDataEvento(LocalDateTime dataEvento) { this.dataEvento = dataEvento; }
    public String getUsuarioAcao() { return usuarioAcao; }
    public void setUsuarioAcao(String usuarioAcao) { this.usuarioAcao = usuarioAcao; }
    public String getNomeAntes() { return nomeAntes; }
    public void setNomeAntes(String nomeAntes) { this.nomeAntes = nomeAntes; }
    public String getNomeDepois() { return nomeDepois; }
    public void setNomeDepois(String nomeDepois) { this.nomeDepois = nomeDepois; }
    public BigDecimal getValorAntes() { return valorAntes; }
    public void setValorAntes(BigDecimal valorAntes) { this.valorAntes = valorAntes; }
    public BigDecimal getValorDepois() { return valorDepois; }
    public void setValorDepois(BigDecimal valorDepois) { this.valorDepois = valorDepois; }
    public Integer getQuantidadeAntes() { return quantidadeAntes; }
    public void setQuantidadeAntes(Integer quantidadeAntes) { this.quantidadeAntes = quantidadeAntes; }
    public Integer getQuantidadeDepois() { return quantidadeDepois; }
    public void setQuantidadeDepois(Integer quantidadeDepois) { this.quantidadeDepois = quantidadeDepois; }
    public String getCategoriaAntes() { return categoriaAntes; }
    public void setCategoriaAntes(String categoriaAntes) { this.categoriaAntes = categoriaAntes; }
    public String getCategoriaDepois() { return categoriaDepois; }
    public void setCategoriaDepois(String categoriaDepois) { this.categoriaDepois = categoriaDepois; }
}
