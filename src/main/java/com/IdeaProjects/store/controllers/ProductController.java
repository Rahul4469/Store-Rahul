package com.IdeaProjects.store.controllers;

import com.IdeaProjects.store.dtos.ProductDto;
import com.IdeaProjects.store.mappers.ProductMapper;
import com.IdeaProjects.store.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @GetMapping
    public List<ProductDto> getAllProducts(
           @RequestParam(required = false, defaultValue = "", name = "sort") String sort
    ){
        if (!Set.of("name", "email").contains(sort))
            sort = "name";

        return productRepository.findAll(Sort.by(sort))
                .stream()
                .map(productMapper::toDto)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable Long id){
        var product = productRepository.findById(id).orElse(null);

        if(product == null){
            return ResponseEntity.notFound().build();
        }

//        var productDto = new ProductDto(product.getId(), product.getName());
        return ResponseEntity.ok(productMapper.toDto(product));
    }
}
