package com.example.demo.service;

import com.example.demo.exception.NotFoundException;
import com.example.demo.exception.UnprocessableEntityException;
import com.example.demo.model.*;
import com.example.demo.param.ProductRequestParameter;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.UserRepository;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ProductService {
    //Should use @Autowired
    private static final ProductRepository productRepository = new ProductRepository();
    private static final UserRepository userRepository = new UserRepository();


    public ProductPO createProduct(ProductCreateRequest productReq) {
        var userPO = userRepository.getOneById(productReq.getCreatorId());
        if (userPO == null) {
            throw new UnprocessableEntityException("Product creator " + productReq.getCreatorId() + " doesn't exist.");
        }
        var productPO = ProductPO.of(productReq);
        productPO = productRepository.insert(productPO);

        return productPO;
    }

    public ProductVO getProductVO(String id) {
        System.out.println("=========== Start ProductService.getProductVO ============");

        ProductVO productVO = null;
        try {
            ProductPO productPO = productRepository.getOneById(id);
            if (Objects.isNull(productPO)) {
                throw new NotFoundException("Product " + id + "doesn't exist.");
            }

            productVO = ProductVO.of(productPO);


            var user = userRepository.getOneById(productPO.getCreatorId());

            productVO.setCreatorName(user.getName());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        } finally {
            System.out.println("=========== End ProductService.getProductVO ============");
        }


        return productVO;
    }

    public List<ProductVO> getProductVOs(ProductRequestParameter param) {
        System.out.println("=========== Start ProductService.getProductVOs ============");

        try {
            var productPOs = productRepository.getMany(param);
            var userIds = productPOs.stream().map(ProductPO::getCreatorId).toList();
            var userIdNameMap = userRepository.getManyByIds(userIds)
                    .stream()
                    .collect(Collectors.toMap(UserPO::getId, UserPO::getName));

            return productPOs.stream()
                    .map(po ->
                    {
                        var vo = ProductVO.of(po);
                        vo.setCreatorName(userIdNameMap.get(vo.getCreatorId()));
                        return vo;
                    }).toList();
        } catch (Exception e) {
            System.out.println("Error");
            System.out.println(e.getMessage());
        } finally {
            System.out.println("=========== End ProductService.getProductVOs ============");
        }
        return null;

    }

    public void updateProduct(String id, ProductUpdateRequest productReq) {
        var productPO = productRepository.getOneById(id);
        productPO.setName(productReq.getName());
        productPO.setPrice(productReq.getPrice());
        productRepository.update(productPO);
    }

    public void deleteProduct(String id) {
        var productPO = productRepository.getOneById(id);
        productRepository.deleteById(productPO.getId());
    }


}
