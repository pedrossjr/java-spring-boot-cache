package io.github.pedrossjr.cache.controllers;

import io.github.pedrossjr.cache.entities.Produto;
import io.github.pedrossjr.cache.exception.ProdutoNotFoundException;
import io.github.pedrossjr.cache.services.ProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/produtos")
@RequiredArgsConstructor
public class ProdutoController {

    private final ProdutoService produtoService;

    @PostMapping("/adicionar")
    @ResponseStatus(HttpStatus.CREATED)
    public Produto adicionar(@RequestBody Produto produto) throws ProdutoNotFoundException {
        return produtoService.add(produto);
    }

    @GetMapping("/listar")
    public List<Produto> listar(){
        return produtoService.listAll();
    }

    @GetMapping("{sku}/listar")
    public Optional<Produto> listarId(@PathVariable String sku){
        return produtoService.listId(sku);
    }

    @PutMapping("{sku}/atualizar")
    public Produto atualizarId(@RequestBody Produto produto) throws ProdutoNotFoundException {
        return produtoService.updateId(produto);
    }

    @DeleteMapping("{sku}/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluirId(@PathVariable String sku) throws ProdutoNotFoundException {
        produtoService.delete(sku);
    }
}