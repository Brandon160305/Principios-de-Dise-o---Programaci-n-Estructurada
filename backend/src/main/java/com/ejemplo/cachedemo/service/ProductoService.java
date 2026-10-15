package com.ejemplo.cachedemo.service;

import com.ejemplo.cachedemo.model.Producto;
import com.ejemplo.cachedemo.repository.ProductoRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public List<Producto> obtenerSinCache() {
        simularLatenciaBD();
        return productoRepository.findAll();
    }

    @Cacheable("productos")
    public List<Producto> obtenerConCache() {
        simularLatenciaBD();
        return productoRepository.findAll();
    }

    @CacheEvict(value = "productos", allEntries = true)
    public void limpiarCache() {
        System.out.println("Cache limpiada.");
    }

    private void simularLatenciaBD() {
        try {
            Thread.sleep(120);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
