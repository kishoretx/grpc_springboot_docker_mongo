package com.prd;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import com.prod.ProductServiceGrpc;
import com.prod.ProductRequest;
import com.prod.ProductResponse;
import com.prod.ProductId;
import com.prod.ProductList;
import com.google.protobuf.Empty;
import io.grpc.StatusRuntimeException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ProductServiceClient {


    public static void mainMAIN(String[] args) {

        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9090)
                .usePlaintext()
                .build();

        ProductServiceGrpc.ProductServiceBlockingStub blockingStub = ProductServiceGrpc.newBlockingStub(channel);
        // 1. Create Product
        String id = null;
        try {
            ProductId productId = blockingStub.addProduct(ProductRequest.newBuilder().setName("Apple iPhone 8").setPrice(899.99).build());
            id = productId.getId();
            System.out.println("1. Created new Product: " + id);
        } catch (StatusRuntimeException e) {
            System.out.println("Cannot create Product.");
            e.printStackTrace();
        }

        // 2. Update Product
        try {
             blockingStub.updateProduct(ProductRequest.newBuilder().setId(id).setName("Apple iPhone 8s").setPrice(850.99).build());
            ProductResponse productResponse = fetchProduct( blockingStub,  id );

            System.out.println("2. Updated  Product: " + productResponse.toString());
        } catch (StatusRuntimeException e) {
            System.out.println("Cannot update Product.");
            e.printStackTrace();
        }

        // 3. List all Products
        fetchAllProducts( blockingStub);

        // 4. Delete a Product
        try {
            blockingStub.deleteProduct(ProductId.newBuilder().setId(id).build());
            System.out.println( "4. Deleted Product Id: " + id);

        } catch (StatusRuntimeException e) {
            System.out.println("Cannot delete  Product Id: " + id);
            e.printStackTrace();
        }

        // 5. List all Products
        fetchAllProducts( blockingStub);

        channel.shutdown();

    }

    public static void fetchAllProducts(ProductServiceGrpc.ProductServiceBlockingStub blockingStub) {
        System.out.println("  Product list: ");
        try {
            Empty empty = Empty.newBuilder().build();
            ProductList  productList = blockingStub.allProducts(empty);
            List<ProductResponse> list = productList.getProductList();
            AtomicInteger serialNumber = new AtomicInteger(1);
            list.forEach(e -> System.out.println( "\t " + serialNumber.getAndIncrement() + ". " + e.toString()));

        } catch (StatusRuntimeException e) {
            System.out.println("Cannot get  Product list.");
            e.printStackTrace();
        }
    }

    public static ProductResponse fetchProduct(ProductServiceGrpc.ProductServiceBlockingStub blockingStub, String id) {
        ProductId productId = ProductId.newBuilder().setId(id).build();
        return blockingStub.getProduct(productId);
    }

}
