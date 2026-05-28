package br.com.fatec.catalogo.controllers;

import br.com.fatec.catalogo.models.CategoriaModel;
import br.com.fatec.catalogo.services.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping("/novo")
    @PreAuthorize("hasRole('ADMIN')")
    public String exibirForm(Model model) {
        if (!model.containsAttribute("categoria")) {
            model.addAttribute("categoria", new CategoriaModel());
        }
        model.addAttribute("categorias", categoriaService.listarTodas());
        return "cadastro-categoria";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute("categoria") CategoriaModel categoria,
                         BindingResult result,
                         Model model,
                         RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("categorias", categoriaService.listarTodas());
            return "cadastro-categoria";
        }
        categoriaService.salvar(categoria);
        redirectAttributes.addFlashAttribute("mensagemSucesso", "Categoria cadastrada com sucesso.");
        return "redirect:/categorias/novo";
    }
}
