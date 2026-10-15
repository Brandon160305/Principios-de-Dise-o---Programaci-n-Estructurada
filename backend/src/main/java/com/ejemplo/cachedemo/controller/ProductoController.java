package com.ejemplo.cachedemo.controller;

import com.ejemplo.cachedemo.model.Producto;
import com.ejemplo.cachedemo.service.ProductoService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "*")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping("/sin-cache")
    public Map<String, Object> listarSinCache() {
        long inicio = System.currentTimeMillis();
        List<Producto> productos = productoService.obtenerSinCache();
        long fin = System.currentTimeMillis();

        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("modo", "SIN CACHE (Base de Datos)");
        respuesta.put("tiempoMs", (fin - inicio));
        respuesta.put("totalRegistros", productos.size());
        respuesta.put("muestra", productos.subList(0, Math.min(20, productos.size())));
        return respuesta;
    }

    @GetMapping("/con-cache")
    public Map<String, Object> listarConCache() {
        long inicio = System.currentTimeMillis();
        List<Producto> productos = productoService.obtenerConCache();
        long fin = System.currentTimeMillis();

        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("modo", "CON CACHE (@Cacheable)");
        respuesta.put("tiempoMs", (fin - inicio));
        respuesta.put("totalRegistros", productos.size());
        respuesta.put("muestra", productos.subList(0, Math.min(20, productos.size())));
        return respuesta;
    }

    @DeleteMapping("/cache")
    public Map<String, String> limpiarCache() {
        productoService.limpiarCache();
        Map<String, String> respuesta = new HashMap<>();
        respuesta.put("mensaje", "Cache vaciada correctamente");
        return respuesta;
    }
}
