package org.spring.web.market.products_ws.controllers;

import lombok.RequiredArgsConstructor;
import org.spring.web.market.products_ws.repositories.ProductRepository;
import org.spring.web.market.products_ws.services.ProductService;
import org.spring.web.market.products_ws.xsd.GetProductsRequest;
import org.spring.web.market.products_ws.xsd.GetProductsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.xml.datatype.DatatypeConfigurationException;

@Endpoint
@RequiredArgsConstructor
public class ProductsEndpoint {
    private static final String NAMESPACE_URI = "http://example.com/api/products";
    private ProductService productsService;

    @Autowired
    public ProductsEndpoint(ProductService productsService) {
        this.productsService = productsService;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getProductsRequest")
    @ResponsePayload
    public GetProductsResponse getGreeting(@RequestPayload GetProductsRequest request)
            throws DatatypeConfigurationException {
        GetProductsResponse response = new GetProductsResponse();
        productsService.getAllProducts().forEach(response.getProducts()::add);
        return response;
    }
}

