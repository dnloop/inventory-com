package io.github.dnloop.inventorycom.service;

import io.github.dnloop.inventorycom.repository.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private static final Log log = LogFactory.getLog(ProductService.class);

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    private final MeasureRepository measureRepository;

    private final PresentationRepository presentationRepository;

    private final MaterialRepository materialRepository;

    private final Pageable pageableFifty = PageRequest.of(
            0, 50,
            Sort.by("description").ascending()
    );

    private final Pageable pageableFiftyDeleted = PageRequest.of(
            0, 50,
            Sort.by("description").ascending()
                .and(Sort.by("deletedAt").ascending())
    );

    public ProductService(
            ProductRepository productRepository,
            CategoryRepository categoryRepository,
            MeasureRepository measureRepository,
            PresentationRepository presentationRepository,
            MaterialRepository materialRepository
    ) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.measureRepository = measureRepository;
        this.presentationRepository = presentationRepository;
        this.materialRepository = materialRepository;
    }
}
