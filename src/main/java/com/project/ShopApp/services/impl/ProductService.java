package com.project.ShopApp.services.impl;

import com.project.ShopApp.constant.SystemContant;
import com.project.ShopApp.dtos.ProductDTO;
import com.project.ShopApp.dtos.ProductImageDTO;
import com.project.ShopApp.exceptions.DataNotFoundException;
import com.project.ShopApp.exceptions.InvalidParamException;
import com.project.ShopApp.models.Category;
import com.project.ShopApp.models.Product;
import com.project.ShopApp.models.ProductImage;
import com.project.ShopApp.repositories.CategoryRepository;
import com.project.ShopApp.repositories.ProductImageRepository;
import com.project.ShopApp.repositories.ProductRepository;
import com.project.ShopApp.responses.ProductResponse;
import com.project.ShopApp.services.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {


    private final ProductImageRepository productImageRepository;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public Product createProduct(ProductDTO productDTO) throws Exception {
        Category existingCategory = categoryRepository.
                findById(productDTO.getCategoryId())
                .orElseThrow(() ->
                        new DataNotFoundException(
                                "Cant not find category with id: "+productDTO.getCategoryId()));

        Product newProduct = Product.builder()
                .name(productDTO.getName())
                .price(productDTO.getPrice())
                .thumbnail(productDTO.getThumbnail())
                .description(productDTO.getDescription())
                .category(existingCategory)
                .build();
        return productRepository.save(newProduct);
    }

    @Override
    public Product getProductById(long id) throws Exception {
        return productRepository.findById(id)
                .orElseThrow(()-> new DataNotFoundException(
                        "Can not find product with id: "+id));
    }

    @Override
    public Page<ProductResponse> getAllProducts(PageRequest pageRequest) {
        return productRepository.findAll(pageRequest).map(product -> {
            ProductResponse productResponse =  ProductResponse.builder()
                      .name(product.getName())
                      .price(product.getPrice())
                      .thumbnail(product.getThumbnail())
                      .description(product.getDescription())
                      .categoryId(product.getCategory().getId())
                      .build();
            productResponse.setCreatedAt(product.getCreatedAt());
            productResponse.setUpdatedAt(product.getUpdatedAt());
            return productResponse;
        });

    }

    @Override
    public Product updateProduct(
            long productId,
            ProductDTO productDTO
    ) throws Exception {
        Product existingProduct = getProductById(productId);
        if(existingProduct != null){
            Category existingCategory = categoryRepository.
                    findById(productDTO.getCategoryId())
                    .orElseThrow(() ->
                            new DataNotFoundException(
                                    "Cant not find category with id: "+productDTO.getCategoryId()));

            existingProduct.setName(productDTO.getName());
            existingProduct.setCategory(existingCategory);
            existingProduct.setPrice(productDTO.getPrice());
            existingProduct.setDescription(productDTO.getDescription());
            existingProduct.setThumbnail(productDTO.getThumbnail());
            // file không lưu trên model mà lưu tên trong bảng product_image
            return productRepository.save(existingProduct);
        }
        return null;
    }

    @Override
    public void deleteProduct(long id) throws Exception {
        Optional<Product> optionalProduct = productRepository.findById(id);
        optionalProduct.ifPresent(productRepository::delete);
    }

    @Override
    public boolean existsByName(String name) {
        return productRepository.existsByName(name);
    }

    @Override
    public ProductImage createProductImage(
            Long productId,
            ProductImageDTO productImageDTO
    ) throws Exception {
        Product existingProduct = productRepository
                .findById(productId)
                .orElseThrow(() -> new DataNotFoundException(
                                "Can not find category with id: "+ productImageDTO.getProductid()));

        ProductImage newProductImage = ProductImage.builder()
                .product(existingProduct)
                .imageUrl(productImageDTO.getImageUrl())
                .build();
        // không cho insert quá 5 hình cho 1 sản phẩm
        int size = productImageRepository.findByProductId(productId).size();
        if(size >= SystemContant.MAXIMUM_IMAGES_PER_PRODUCT) {
            throw new InvalidParamException(
                    "number of image must be <= "
                    + SystemContant.MAXIMUM_IMAGES_PER_PRODUCT);
        }
        return productImageRepository.save(newProductImage);
    }
}
