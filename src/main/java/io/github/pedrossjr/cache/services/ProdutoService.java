package io.github.pedrossjr.cache.services;

import io.github.pedrossjr.cache.entities.Produto;
import io.github.pedrossjr.cache.exception.ProdutoNotFoundException;
import io.github.pedrossjr.cache.repositories.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    static final String cacheName = "cache-produto";
    static final String cacheKey = "";

    // Com a anotation @CacheEvict habilitada, ao atualizar o produto, o cache é limpo, atualizando automaticamente
    // a lista de produtos no cache.
    // Não utilizar o time para limpeza do cache é uma boa estratégia. Sendo mais interessante quando for realizado
    // o cadastro, atualização ou exclusão de um produto.
    @CacheEvict(value = cacheName, key = cacheKey)
    @Transactional
    public Produto add(Produto produto ) throws ProdutoNotFoundException {
        verifyByExists(produto.getSku());
        return produtoRepository.save(produto);
    }

    @Cacheable(value = cacheName, key = cacheKey)
    @Transactional(readOnly = true)
    public List<Produto> listAll(){
        System.out.println("Consulta realizada no banco de dados.");
        System.out.println("Durantes os próximos 15 segundos serão realizadas no cache do Redis.");
        return produtoRepository.findAll();
    }

    @Cacheable(value = cacheName, key = cacheKey)
    @Transactional(readOnly = true)
    public Optional<Produto> listId(String sku) {
        System.out.println("Consulta realizada no banco de dados.");
        System.out.println("Durantes os próximos 15 segundos serão realizadas no cache do Redis.");
        return produtoRepository.findById(sku);
    }

    @CacheEvict(value = cacheName, key = cacheKey)
    @Transactional
    public Produto updateId(Produto produto) throws ProdutoNotFoundException {
        verifyByExists(produto.getSku());
        return produtoRepository.save(produto);
    }

    @CacheEvict(value = cacheName, key = cacheKey)
    @Transactional
    public void delete(String sku) throws ProdutoNotFoundException {
        verifyByExists(sku);
        produtoRepository.deleteById(sku);
    }

    private Produto verifyByExists(String sku) throws ProdutoNotFoundException {
        return produtoRepository.findById(sku)
                .orElseThrow(() -> new ProdutoNotFoundException(sku));
    }
}