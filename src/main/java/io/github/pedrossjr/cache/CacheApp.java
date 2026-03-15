package io.github.pedrossjr.cache;

import io.github.pedrossjr.cache.entities.Produto;
import io.github.pedrossjr.cache.repositories.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@SpringBootApplication
@EnableCaching
@RequiredArgsConstructor
public class CacheApp implements CommandLineRunner {

    private final ProdutoRepository produtoRepository;

    private static final List<String> PRODUTOS_BASE = List.of(
        "Switch HDMI 4K Automático",
        "Cabo HDMI 2.1 8K",
        "Mini PC Intel N100",
        "Monitor 27\" IPS 144Hz",
        "Teclado Mecânico RGB",
        "Mouse Gamer 26000 DPI",
        "Adaptador USB-C Multiportas",
        "SSD NVMe 1TB Gen4",
        "Fonte 650W 80+ Bronze",
        "Memória DDR5 32GB 6000MHz",
        "Notebook DELL 15 32GB SSD 500GB"
    );

	public static void main(String[] args) {
		SpringApplication.run(CacheApp.class, args);
	}

    @Override
    public void run(String... args) throws Exception {
        if (produtoRepository.count() > 0) {
            System.out.println("Eu não preciso fazer o insert.");
            return;
        }

        Random random = new Random();

        int quantidade = 50_000;
        System.out.println("Gerando " + quantidade + " produtos...");

        List<Produto> lista = new ArrayList<>();

        LocalDateTime horaInicial = LocalDateTime.now();

        for (int i = 1; i <= quantidade; i++) {
            String sku = String.format("P%05d", i);
            String desc = PRODUTOS_BASE.get(random.nextInt(PRODUTOS_BASE.size()))
                + " - Var. " + (random.nextInt(9000) + 100);
            BigDecimal preco = BigDecimal.valueOf(Math.round((29.90 + random.nextDouble() * 870.00) * 100.0) / 100.0);
            int qtd = random.nextInt(151);

            Produto p = new Produto();

            p.setSku(sku);
            p.setAtivo(true);
            p.setDescricao(desc);
            p.setPreco(preco);
            p.setQuantidade(qtd);

            lista.add(p);

            if (lista.size() % 1000 == 0) {
                System.out.println("Tamanho da lista: " + lista.size());
                produtoRepository.saveAll(lista);
                lista.clear();
            }
        }

        if (!lista.isEmpty()) {
            produtoRepository.saveAll(lista);
        }

        LocalDateTime horaFinal = LocalDateTime.now();

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        Duration duracao = Duration.between(horaInicial, horaFinal);

        long minutos = duracao.toMinutes();
        long segundos = duracao.getSeconds() % 60;

        String tempoFormatado = String.format("%d min %d s", minutos, segundos);

        System.out.printf("Geração iniciada às %s e concluída às %s (%s)!%n",
            horaInicial.format(fmt),
            horaFinal.format(fmt),
            tempoFormatado);
    }
}
