package org.spring.web.market.products_ws.services;

import org.spring.web.market.products_ws.entities.ProductEntity;
import org.spring.web.market.products_ws.repositories.ProductRepository;
import org.spring.web.market.products_ws.xsd.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private ProductRepository productRepository;

    public static final Function<ProductEntity, Product> functionProductToSoap = se -> {
        Product p = new Product();
        p.setId(se.getId());
        p.setCategory(se.getCategory().getTitle());
        p.setVendorCode(se.getVendorCode());
        p.setTitle(se.getTitle());
        p.setShortDescription(se.getShortDescription());
        p.setFullDescription(se.getFullDescription());
        p.setPrice(se.getPrice());
        String iso = se.getCreateAt().toString();
        if (se.getCreateAt().getSecond() == 0 && se.getCreateAt().getNano() == 0) {
            iso += ":00"; // necessary hack because the second part is not optional in XML
        }
        XMLGregorianCalendar xml = null;
        try {
            xml = DatatypeFactory.newInstance().newXMLGregorianCalendar(iso);
        } catch (DatatypeConfigurationException e) {
            throw new RuntimeException(e);
        }
        p.setCreateAt(xml);
        iso = se.getCreateAt().toString();
        if (se.getCreateAt().getSecond() == 0 && se.getCreateAt().getNano() == 0) {
            iso += ":00"; // necessary hack because the second part is not optional in XML
        }
        xml = null;
        try {
            xml = DatatypeFactory.newInstance().newXMLGregorianCalendar(iso);
        } catch (DatatypeConfigurationException e) {
            throw new RuntimeException(e);
        }
        p.setUpdateAt(xml);
        return p;
    };

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll().stream().map(functionProductToSoap).collect(Collectors.toList());
    }

}
