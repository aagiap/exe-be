package com.exebe.service;

import com.exebe.base.PageDTO;
import com.exebe.dto.product.ProductDTO;
import com.exebe.entity.Product;
import com.exebe.handler.ProductHandler;
import com.exebe.mapper.ProductMapper;
import com.exebe.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public PageDTO<ProductDTO> search(
            String keyword,
            Long categoryId,
            Double minPrice,
            Double maxPrice,
            int page,
            int size
    ) {
        int currentPage = Math.max(page, 1);

        Pageable pageable = PageRequest.of(
                currentPage - 1,
                size,
                Sort.by("id").descending()
        );

        Specification<Product> spec =
                ProductHandler.searchProduct(
                        keyword,
                        categoryId,
                        minPrice,
                        maxPrice
                );

        Page<Product> result = productRepository.findAll(spec, pageable);

        return PageDTO.of(
                result.getContent(),
                ProductMapper::toProductDTO,
                result.getTotalElements(),
                currentPage,
                size
        );
    }

    @Transactional(readOnly = true)
    public ProductDTO viewProductDetail(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Product not found with id = " + id)
                );

        return ProductMapper.toProductDTO(product);
    }
}
