package com.example.demo.service;

import com.example.demo.exception.NotFoundException;
import com.example.demo.exception.UnprocessableEntityException;
import com.example.demo.model.*;
import com.example.demo.param.ProductRequestParameter;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.UserRepository;

import java.util.List;
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
        var productPO = getProductPO(id);
        var productVO = ProductVO.of(productPO);

        var user = userRepository.getOneById(productPO.getCreatorId());
        productVO.setCreatorName(user.getName());

        return productVO;
    }

    private ProductPO getProductPO(String id) {
        var po = productRepository.getOneById(id);
        if (po == null) {
            throw new NotFoundException("Product " + id + "doesn't exist.");
        }
        return po;
    }

    public List<ProductVO> getProductVOs(ProductRequestParameter param) {
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