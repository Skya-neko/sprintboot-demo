package com.example.demo.service;

import com.example.demo.exception.NotFoundException;
import com.example.demo.exception.UnprocessableEntityException;
import com.example.demo.model.*;
import com.example.demo.param.ProductRequestParameter;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.UserRepository;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class ProductService {
    //Should use @Autowired
    private static final ProductRepository productRepository = new ProductRepository();
    private static final UserRepository userRepository = new UserRepository();


    public ProductPO createProduct(ProductCreateRequest productReq) {
        ProductPO productPO = null;
        System.out.println("=========== Start ProductService.createProduct ===========");
        try {
            var userPO = userRepository.getOneById(productReq.getCreatorId());
            if (userPO == null) {
                throw new UnprocessableEntityException("Product creator " + productReq.getCreatorId() + " doesn't exist.");
            }
            productPO = ProductPO.of(productReq);
            productPO = productRepository.insert(productPO);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;

        } finally {
            System.out.println("=========== End ProductService.createProduct ===========");

        }


        return productPO;
    }

    public ProductVO getProductVO(String id) {
        ProductVO productVO = null;
        System.out.println("=========== Start ProductService.getProductVO ===========");
        try {
            ProductPO productPO = getProductPO(id);
            productVO = ProductVO.of(productPO);

            UserPO user = userRepository.getOneById(productPO.getCreatorId());
            productVO.setCreatorName(user.getName());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;

        } finally {
            System.out.println("=========== End ProductService.getProductVO ===========");
        }
        return productVO;
    }

    private ProductPO getProductPO(String id) {
        var po = productRepository.getOneById(id);
        if (po == null) {
            throw new NotFoundException("Product " + id + " doesn't exist.");
        }
        return po;
    }

    public List<ProductVO> getProductVOs(ProductRequestParameter param) {
        List<ProductVO> list = null;
        System.out.println("=========== Start ProductService.getProductVOs ===========");
        try {
            List<ProductPO> productPOs = productRepository.getMany(param);
            List<String> userIds = productPOs.stream().map(ProductPO::getCreatorId).toList();
            Map<String, String> userIdNameMap = userRepository.getManyByIds(userIds)
                    .stream()
                    .collect(Collectors.toMap(UserPO::getId, UserPO::getName));
            list = productPOs.stream()
                    .map(po ->
                    {
                        var vo = ProductVO.of(po);
                        vo.setCreatorName(userIdNameMap.get(vo.getCreatorId()));
                        return vo;
                    }).toList();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        } finally {
            System.out.println("=========== End ProductService.getProductVOs ===========");
        }
        return list;

    }

    public void updateProduct(String id, ProductUpdateRequest productReq) {
        var productPO = getProductPO(id);
        productPO.setName(productReq.getName());
        productPO.setPrice(productReq.getPrice());
        productRepository.update(productPO);
    }

    public void deleteProduct(String id) {
        var productPO = getProductPO(id);
        productRepository.deleteById(productPO.getId());
    }


}
