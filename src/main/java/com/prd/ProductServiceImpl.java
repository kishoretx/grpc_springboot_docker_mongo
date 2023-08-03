package com.prd;


import com.google.protobuf.Empty;
import com.prod.*;
import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl  extends ProductServiceGrpc.ProductServiceImplBase {



    ProductRepoService repoService;
   public ProductServiceImpl( ProductRepoService repoService) {

       this.repoService = repoService;
   }



    @Override
    public void getProduct(ProductId request, StreamObserver<ProductResponse> responseObserver) {

        System.out.println("In getProduct method ---------------------");
        String productId = request.getId();

        //ProductRepoService repoService = new ProductRepoService(repo);
        Optional<Product> product = repoService.getProductById(productId);

        responseObserver.onNext( ProductResponse.newBuilder().setId(product.get().getId()).setName(product.get().getName()).setPrice(product.get().getPrice()).build());
        responseObserver.onCompleted();

    }

    @Override
    public void addProduct(ProductRequest request, StreamObserver<ProductId> responseObserver) {
        System.out.println("In addProduct method ---------------------");
        String name = request.getName();
        double price = request.getPrice();
        Product prod = new Product();
        prod.setName(name);
        prod.setPrice(price);

        //ProductRepoService repoService = new ProductRepoService(repo);
        Product newProduct = repoService.createProduct(prod);
        responseObserver.onNext(ProductId.newBuilder().setId(newProduct.getId()).build());
        responseObserver.onCompleted();
    }

    @Override
    public void updateProduct(ProductRequest request, StreamObserver<Empty> responseObserver) {
        System.out.println("In updateProduct method ---------------------");
        String id = request.getId();
        String name = request.getName();
        double price = request.getPrice();
        Product prod = new Product();
        prod.setId(id);
        prod.setName(name);
        prod.setPrice(price);

        //ProductRepoService repoService = new ProductRepoService(repo);
        Product newProduct = repoService.updateProduct(prod);
        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();
    }

    @Override
    public void deleteProduct(ProductId request, StreamObserver<Empty> responseObserver) {
        System.out.println("In deleteProduct method ---------------------");
        repoService.deleteProduct(request.getId());
        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();
    }

    @Override

    public void allProducts(Empty request, StreamObserver<ProductList> responseObserver) {
        System.out.println("In allProduct method ---------------------");
        List<com.prd.Product> productList = repoService.getAllProducts();

        // Create a ProductList builder
        ProductList.Builder productListBuilder = ProductList.newBuilder();

        // Iterate through the productList and add each product to the builder
        for (com.prd.Product product : productList) {
            com.prod.ProductResponse productResponse = com.prod.ProductResponse.newBuilder()
                    .setId(product.getId())
                    .setName(product.getName())
                    .setPrice(product.getPrice())
                    .build();

            productListBuilder.addProduct(productResponse);
        }

        // Build the final ProductList and send it through the responseObserver
        responseObserver.onNext(productListBuilder.build());
        responseObserver.onCompleted();
    }
}
