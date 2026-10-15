package com.ejemplo.cachedemo.config;

import com.ejemplo.cachedemo.model.Producto;
import com.ejemplo.cachedemo.repository.ProductoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner cargarDatos(ProductoRepository repository) {
        return args -> {
            String[] categorias = {"Electronica", "Redes", "Perifericos", "Almacenamiento", "Software"};
            Random random = new Random();
            List<Producto> lista = new ArrayList<>();

            for (int i = 1; i <= 10000; i++) {
                String codigo = String.format("PROD-%05d", i);
                String nombre = "Producto " + i;
                String categoria = categorias[i % categorias.length];
                double precio = Math.round((10 + random.nextDouble() * 500) * 100.0) / 100.0;
                int stock = 1 + random.nextInt(100);
                lista.add(new Producto(codigo, nombre, categoria, precio, stock));
            }

            repository.saveAll(lista);
            System.out.println("Se cargaron " + repository.count() + " registros en la base de datos.");
        };
    }
}
