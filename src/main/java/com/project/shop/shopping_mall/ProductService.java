package com.project.shop.shopping_mall;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // 상품 목록 조회
    public List<Product> getAllProducts() {
        return productRepository.findAll(); // 모든 상품을 반환
    }

    // 상품 ID로 조회
    public Product getProductById(Long productId) {
        Optional<Product> product = productRepository.findById(productId);
        return product.orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다.")); // 상품이 존재하지 않으면 예외 발생
    }

    // 새로운 상품 추가
    public Product addProduct(Product product) {
        return productRepository.save(product); // 새로운 상품을 저장
    }

    // 상품 수정
    public Product updateProduct(Long productId, Product updatedProduct) {
        // 먼저 상품을 ID로 조회
        Product existingProduct = getProductById(productId);

        // 기존 상품 정보 업데이트
        existingProduct.setName(updatedProduct.getName());
        existingProduct.setDescription(updatedProduct.getDescription());
        existingProduct.setPrice(updatedProduct.getPrice());
        existingProduct.setStock(updatedProduct.getStock());
        existingProduct.setCategory(updatedProduct.getCategory());

        // 수정된 상품 저장
        return productRepository.save(existingProduct);
    }

    // 상품 삭제
    public void deleteProduct(Long productId) {
        Product product = getProductById(productId);
        productRepository.delete(product); // 상품을 삭제
    }

}