package com.example.demo.repository;

import com.example.demo.exception.UnprocessableEntityException;
import com.example.demo.model.ProductPO;
import com.example.demo.param.ProductRequestParameter;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.*;
import java.util.stream.Stream;

@Primary
@Repository("listProductRepository")
public class ListProductRepository implements IProductRepository {
    private static final List<ProductPO> productList = new ArrayList<>();

    static {
        Stream.of(
                ProductPO.of("P1", "Android Development (Java)", 380, "U1"),
                ProductPO.of("P2", "Android Development (Kotlin)", 420, "U2"),
                ProductPO.of("P3", "Data Structure (Java)", 250, "U1"),
                ProductPO.of("P4", "Finance Management", 450, "U2"),
                ProductPO.of("P5", "Human Resource Management", 330, "U1")
        ).forEach(productList::add);
    }

    @Override
    public ProductPO getOneById(String id) {
        return productList.stream()
                .filter(p -> Objects.equals(p.getId(), id))
                .findFirst().
                orElse(null);
    }

    @Override
    public ProductPO insert(ProductPO product) {
        if (product.getId() == null) {
            product.setId(generateRandomId());
        }

        if (getOneById(product.getId()) != null) {
            throw new UnprocessableEntityException("Product id " + product.getId() + " is existing.");
        }

        product.setCreatedTime(Instant.now().getEpochSecond());
        product.setUpdatedTime(product.getCreatedTime());
        productList.add(product);
        return product;
    }

    public String generateRandomId() {
        var uuid = UUID.randomUUID().toString();
        return uuid.substring(0, uuid.indexOf("-"));
    }

    @Override
    public void update(ProductPO product) {
        boolean isExist = false;
        int idx = 0;
        for (ProductPO p : productList) {
            if (p.getId().equals(product.getId())) {
                isExist = true;
                break;
            }
            idx++;
        }


        if (!isExist) {
            throw new UnprocessableEntityException("Product " + product.getId() + "doesn't exist.");
        }

        product.setUpdatedTime(Instant.now().getEpochSecond());
        productList.set(idx, product);
    }

    @Override
    public void deleteById(String id) {
        productList.removeIf(p -> p.getId().equals(id));
    }

    @Override
    public List<ProductPO> getMany(ProductRequestParameter param) {
        var searchKey = param.getSearchKey();
        var sortDir = param.getSortDir();
        var sortField = param.getSortField();

        Comparator<ProductPO> comparator;
        if ("name".equals(sortField)) {
            comparator = Comparator.comparing(p -> p.getName().toLowerCase());
        } else if ("price".equals(sortField)) {
            comparator = Comparator.comparing(ProductPO::getPrice);
        } else {
            comparator = (a, b) -> 0;
        }

        if ("desc".equalsIgnoreCase(sortDir)) {
            comparator = comparator.reversed();
        }

        return productList
                .stream()
                .filter(p -> p.getName().toLowerCase().contains(searchKey.toLowerCase())
                        || p.getId().contains(searchKey))
                .sorted(comparator)
                .toList();


    }
}
