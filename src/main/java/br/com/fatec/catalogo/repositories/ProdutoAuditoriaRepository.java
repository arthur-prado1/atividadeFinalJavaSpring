package br.com.fatec.catalogo.repositories;

import br.com.fatec.catalogo.models.ProdutoAuditoriaModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProdutoAuditoriaRepository extends JpaRepository<ProdutoAuditoriaModel, Long> {
    List<ProdutoAuditoriaModel> findAllByOrderByDataEventoDesc();
}
