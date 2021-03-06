package io.github.dnloop.inventorycom.service;

import io.github.dnloop.inventorycom.model.*;
import io.github.dnloop.inventorycom.repository.*;
import io.github.dnloop.inventorycom.support.uiloader.PageableProperty;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class ProductService {

    private static final Log log = LogFactory.getLog(ProductService.class);

    private final ProductRepository productRepository;

    private final ProductDetailRepository productDetailRepository;

    private final CategoryRepository categoryRepository;

    private final CategoryLevelRepository categoryLevelRepository;

    private final MeasureRepository measureRepository;

    private final PresentationRepository presentationRepository;

    private final MaterialRepository materialRepository;

    private final PageableProperty pageableProperty = new PageableProperty();

    public ProductService(
            ProductRepository productRepository,
            ProductDetailRepository productDetailRepository,
            CategoryRepository categoryRepository,
            CategoryLevelRepository categoryLevelRepository,
            MeasureRepository measureRepository,
            PresentationRepository presentationRepository,
            MaterialRepository materialRepository
    ) {
        this.productRepository = productRepository;
        this.productDetailRepository = productDetailRepository;
        this.categoryRepository = categoryRepository;
        this.categoryLevelRepository = categoryLevelRepository;
        this.measureRepository = measureRepository;
        this.presentationRepository = presentationRepository;
        this.materialRepository = materialRepository;
    }

    /* Product */

    @Async
    public CompletableFuture<Optional<Product>> findProductById(int id) {
        return CompletableFuture.completedFuture(productRepository.findById(id));
    }

    @Async
    public CompletableFuture<Optional<Product>> findDeletedProduct(Integer id) {
        return CompletableFuture.completedFuture(productRepository.findDeleted(id));
    }

    @Async
    public CompletableFuture<Page<Product>> findAllProducts() {
        return CompletableFuture.completedFuture(productRepository.findAll(pageableProperty.getPageable()));
    }

    @Async
    public CompletableFuture<Page<Product>> findAllProducts(PageableProperty pageable) {
        return CompletableFuture.completedFuture(productRepository.findAll(pageable.getPageable()));
    }

    @Async
    public CompletableFuture<Page<Product>> findAllDeletedProducts() {
        return CompletableFuture.completedFuture(productRepository.findAllDeleted(
                pageableProperty.getPageableDeleted()));
    }

    @Async
    public CompletableFuture<Page<Product>> findAllDeletedProducts(PageableProperty pageable) {
        return CompletableFuture.completedFuture(productRepository.findAllDeleted(pageable.getPageable()));
    }

    @Async
    public CompletableFuture<Product> saveProduct(Product product) {
        return CompletableFuture.completedFuture(productRepository.save(product));
    }

    @Async
    public CompletableFuture<Iterable<Product>> saveAllProducts(List<Product> products) {
        return CompletableFuture.completedFuture(productRepository.saveAll(products));
    }

    @Transactional
    public void deleteProduct(Product product) {
        productRepository.delete(product);
        log.debug("Product Deleted: " + product.toString());
    }

    /* Product Detail */

    @Async
    public CompletableFuture<Optional<ProductDetail>> findProductDetailById(int id) {
        return CompletableFuture.completedFuture(productDetailRepository.findById(id));
    }

    @Async
    public CompletableFuture<Optional<ProductDetail>> findDeletedProductDetail(Integer id) {
        return CompletableFuture.completedFuture(productDetailRepository.findDeleted(id));
    }

    @Async
    public CompletableFuture<Page<ProductDetail>> findAllProductDetails() {
        PageableProperty pageableProperty = new PageableProperty("brand");
        return CompletableFuture.completedFuture(productDetailRepository.findAll(pageableProperty.getPageable()));
    }

    @Async
    public CompletableFuture<Page<ProductDetail>> findAllProductDetails(PageableProperty pageable) {
        return CompletableFuture.completedFuture(productDetailRepository.findAll(pageable.getPageable()));
    }

    @Async
    public CompletableFuture<Page<ProductDetail>> findAllDeletedProductDetails() {
        PageableProperty pageableProperty = new PageableProperty("brand");
        return CompletableFuture.completedFuture(productDetailRepository.findAllDeleted(
                pageableProperty.getPageableDeleted()));
    }

    @Async
    public CompletableFuture<Page<ProductDetail>> findAllDeletedProductDetails(PageableProperty pageable) {
        return CompletableFuture.completedFuture(productDetailRepository.findAllDeleted(pageable.getPageable()));
    }

    @Async
    public CompletableFuture<ProductDetail> saveProductDetail(ProductDetail product) {
        return CompletableFuture.completedFuture(productDetailRepository.save(product));
    }

    @Transactional
    public void deleteProductDetail(ProductDetail productDetail) {
        productDetailRepository.delete(productDetail);
        log.debug("Record Deleted: " + productDetail.toString());
        log.debug("Product Detail Deleted.");
    }

    /* Category */

    @Async
    public CompletableFuture<Optional<Category>> findCategoryById(int id) {
        return CompletableFuture.completedFuture(categoryRepository.findById(id));
    }

    @Async
    public CompletableFuture<Optional<Category>> findDeletedCategory(Integer id) {
        return CompletableFuture.completedFuture(categoryRepository.findDeleted(id));
    }

    @Async
    public CompletableFuture<Page<Category>> findAllCategory() {
        return CompletableFuture.completedFuture(categoryRepository.findAll(pageableProperty.getPageable()));
    }

    @Async
    public CompletableFuture<Page<Category>> findAllDeletedCategory() {
        return CompletableFuture.completedFuture(categoryRepository.findAllDeleted(
                pageableProperty.getPageableDeleted()));
    }

    @Async
    public CompletableFuture<Category> saveCategory(Category category) {
        return CompletableFuture.completedFuture(categoryRepository.save(category));
    }

    /**
     * Controls that the category ID is assigned to a product to prevent
     * deleting a category level with elements under its hierarchy.
     */
    @Async
    public CompletableFuture<Integer> categoryExistsInProduct(int catId) {
        return CompletableFuture.completedFuture(categoryRepository.existsInProduct(catId));
    }

    /**
     * Method to delete a category.
     * Record deletion must be prevented if its either assigned to a product or a category level.
     * Even when the category level is deleted, it still possible to be reassigned later,
     * this behaviour prevents deletion of the category;
     * only possibility will be change its description.
     */
    @Transactional
    public boolean deleteCategory(Category category) {
        int categoryId = category.getId();
        // if its unassigned in products
        if (categoryRepository.existsInProduct(categoryId) == 0) {
            // and its unassigned in category level
            if (categoryRepository.existsInCategoryLevel(categoryId) == 0) {
                categoryRepository.delete(categoryId);
                log.debug("[Category] Record Deleted: " + category.toString());
                return true;
            } else {
                // otherwise category level is assigned
                log.debug("[Category Level] is not unassigned");
                return false;
            }
            // otherwise category is assigned
        } else {
            log.debug("[Category] is not unassigned");
            return false;
        }
    }

    /* Category Level */

    @Async
    public CompletableFuture<Optional<CategoryLevel>> findCategoryLevelById(int id) {
        return CompletableFuture.completedFuture(categoryLevelRepository.findById(id));
    }

    @Async
    public CompletableFuture<Optional<CategoryLevel>> findCategoryLevelByCategoryId(int categoryId) {
        return CompletableFuture.completedFuture(categoryLevelRepository.findByCategoryId(categoryId));
    }

    @Async
    public CompletableFuture<Optional<CategoryLevel>> findDeletedCategoryLevel(int id) {
        return CompletableFuture.completedFuture(categoryLevelRepository.findDeleted(id));
    }

    @Async
    public CompletableFuture<HashSet<CategoryLevel>> findAllCategoryLevel() {
        return CompletableFuture.completedFuture(categoryLevelRepository.findAll());
    }

    @Async
    public CompletableFuture<HashSet<CategoryLevel>> findAllDeletedCategoryLevel() {
        return CompletableFuture.completedFuture(categoryLevelRepository.findAllDeleted());
    }

    /**
     * Control method to find if child nodes in a tree has products assigned.
     */
    @Async
    public CompletableFuture<Integer> childNodesExistsInProduct(int levelOne) {
        return CompletableFuture.completedFuture(categoryLevelRepository.childNodesExistsInProduct(levelOne));
    }

    /**
     * Control method to find if a category level has products assigned.
     */
    @Async
    public CompletableFuture<Integer> categoryLevelExistsInProduct(int categoryId) {
        return CompletableFuture.completedFuture(categoryLevelRepository.categoryExistsInProduct(categoryId));
    }

    @Async
    public CompletableFuture<CategoryLevel> saveCategoryLevel(CategoryLevel category) {
        return CompletableFuture.completedFuture(categoryLevelRepository.save(category));
    }

    /**
     * Implementation of a control to ensure the correct deletion of a node.
     * <p>
     * It takes the L1 value of a record allowing the deletion of multiple
     * rows without necessarily selecting 'root' category
     */
    @Transactional
    public boolean deleteCategoryLevel(CategoryLevel categoryLevel) {
        // extract primitives
        int levelOne = categoryLevel.getL1();
        int levelTwo = categoryLevel.getL2();
        int levelThree = categoryLevel.getL3();
        int categoryId = categoryLevel.getCategoryId();
        // if it is a root node
        if (levelTwo == 0 && levelThree == 0) {
            if (categoryLevelRepository.childNodesExistsInProduct(levelOne) == 0) {
                categoryLevelRepository.deleteRootNode(levelOne);
                log.debug("[Root Node] Record Deleted: " + categoryLevel.toString());
                return true;
            } else {
                log.debug("Root node is not empty");
                return false;
            }
            // otherwise its a child node
        } else {
            if (categoryLevelRepository.categoryExistsInProduct(categoryId) == 0) {
                categoryLevelRepository.deleteNode(categoryId);
                log.debug("[Child Node] Record Deleted: " + categoryLevel.toString());
                return true;
            } else {
                log.debug("Child node is not unassigned");
                return false;
            }
        }
    }

    /* Measure */

    @Async
    public CompletableFuture<Optional<Measure>> findMeasuresById(int id) {
        return CompletableFuture.completedFuture(measureRepository.findById(id));
    }

    @Async
    public CompletableFuture<Optional<Measure>> findDeletedMeasures(Integer id) {
        return CompletableFuture.completedFuture(measureRepository.findDeleted(id));
    }

    @Async
    public CompletableFuture<Page<Measure>> findAllMeasures() {
        PageableProperty pageableProperty = new PageableProperty("type");
        return CompletableFuture.completedFuture(measureRepository.findAll(pageableProperty.getPageable()));
    }

    @Async
    public CompletableFuture<Page<Measure>> findAllMeasures(PageableProperty pageable) {
        return CompletableFuture.completedFuture(measureRepository.findAll(pageable.getPageable()));
    }

    @Async
    public CompletableFuture<Page<Measure>> findAllDeletedMeasures() {
        PageableProperty pageableProperty = new PageableProperty("type");
        return CompletableFuture.completedFuture(measureRepository.findAllDeleted(
                pageableProperty.getPageableDeleted()));
    }

    @Async
    public CompletableFuture<Measure> saveMeasures(Measure measure) {
        return CompletableFuture.completedFuture(measureRepository.save(measure));
    }

    @Transactional
    public boolean deleteMeasures(Measure measure) {
        int measureId = measure.getId();
        // if its unassigned in product detail
        if (measureRepository.existsInProductDetail(measureId) == 0) {
            measureRepository.delete(measureId);
            log.debug("[Material] Record Deleted: " + measure.toString());
            return true;
        } else {
            // otherwise measure is assigned
            log.debug("[Material] is not unassigned");
            return false;
        }
        // otherwise material is assigned
    }

    /* Presentation */

    @Async
    public CompletableFuture<Optional<Presentation>> findPresentationById(int id) {
        return CompletableFuture.completedFuture(presentationRepository.findById(id));
    }

    @Async
    public CompletableFuture<Optional<Presentation>> findDeletedPresentation(Integer id) {
        return CompletableFuture.completedFuture(presentationRepository.findDeleted(id));
    }

    @Async
    public CompletableFuture<Page<Presentation>> findAllPresentations() {
        return CompletableFuture.completedFuture(presentationRepository.findAll(pageableProperty.getPageable()));
    }

    @Async
    public CompletableFuture<Page<Presentation>> findAllPresentations(PageableProperty pageable) {
        return CompletableFuture.completedFuture(presentationRepository.findAll(pageable.getPageable()));
    }

    @Async
    public CompletableFuture<Page<Presentation>> findAllDeletedPresentations() {
        return CompletableFuture.completedFuture(presentationRepository.findAllDeleted(
                pageableProperty.getPageableDeleted()));
    }

    @Async
    public CompletableFuture<Presentation> savePresentation(Presentation presentation) {
        return CompletableFuture.completedFuture(presentationRepository.save(presentation));
    }

    @Transactional
    public boolean deletePresentation(Presentation presentation) {
        int presentationId = presentation.getId();
        // if its unassigned in product detail
        if (presentationRepository.existsInProductDetail(presentationId) == 0) {
            presentationRepository.delete(presentationId);
            log.debug("[Presentation] Record Deleted: " + presentation.toString());
            return true;
        } else {
            // otherwise presentation is assigned
            log.debug("[Presentation] is not unassigned");
            return false;
        }
        // otherwise presentation is assigned
    }

    /* Material */

    @Async
    public CompletableFuture<Optional<Material>> findMaterialById(int id) {
        return CompletableFuture.completedFuture(materialRepository.findById(id));
    }

    @Async
    public CompletableFuture<Optional<Material>> findDeletedMaterial(Integer id) {
        return CompletableFuture.completedFuture(materialRepository.findDeleted(id));
    }

    @Async
    public CompletableFuture<Page<Material>> findAllMaterials() {
        PageableProperty pageableProperty = new PageableProperty("type");
        return CompletableFuture.completedFuture(materialRepository.findAll(pageableProperty.getPageable()));
    }

    @Async
    public CompletableFuture<Page<Material>> findAllMaterials(PageableProperty pageable) {
        PageableProperty pageableProperty = new PageableProperty("type");
        return CompletableFuture.completedFuture(materialRepository.findAll(pageable.getPageable()));
    }

    @Async
    public CompletableFuture<Page<Material>> findAllDeletedMaterials() {
        PageableProperty pageableProperty = new PageableProperty("type");
        return CompletableFuture.completedFuture(materialRepository.findAllDeleted(
                pageableProperty.getPageableDeleted()));
    }

    @Async
    public CompletableFuture<Material> saveMaterial(Material material) {
        return CompletableFuture.completedFuture(materialRepository.save(material));
    }

    @Transactional
    public boolean deleteMaterial(Material material) {
        int materialId = material.getId();
        // if its unassigned in product detail
        if (materialRepository.existsInProductDetail(materialId) == 0) {
            materialRepository.delete(materialId);
            log.debug("[Material] Record Deleted: " + material.toString());
            return true;
        } else {
            // otherwise material is assigned
            log.debug("[Material] is not unassigned");
            return false;
        }
        // otherwise material is assigned
    }
}
