package io.github.dnloop.inventorycom.service;

import io.github.dnloop.inventorycom.model.*;
import io.github.dnloop.inventorycom.repository.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.HashSet;
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
        return CompletableFuture.completedFuture(productRepository.findAll(pageableFifty));
    }

    @Async
    public CompletableFuture<Page<Product>> findAllProducts(Pageable pageable) {
        return CompletableFuture.completedFuture(productRepository.findAll(pageable));
    }

    @Async
    public CompletableFuture<Page<Product>> findAllDeletedProducts() {
        return CompletableFuture.completedFuture(productRepository.findAllDeleted(pageableFiftyDeleted));
    }

    @Async
    public CompletableFuture<Page<Product>> findAllDeletedProducts(Pageable pageable) {
        return CompletableFuture.completedFuture(productRepository.findAllDeleted(pageable));
    }

    @Async
    public CompletableFuture<Product> saveProduct(Product product) {
        return CompletableFuture.completedFuture(productRepository.save(product));
    }


    @Transactional
    public void deleteProduct(Product product) {
        productRepository.delete(product);
        log.debug("Record Deleted: " + product.toString());
        log.debug("Deleting Relationships");
        ProductDetail productDetail = product.getProductDetailByDetailId();
        log.debug("Product Detail Deleted.");
        productDetailRepository.delete(productDetail);
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
    public CompletableFuture<HashSet<Category>> findAllCategory() {
        return CompletableFuture.completedFuture(categoryRepository.findAll());
    }

    @Async
    public CompletableFuture<HashSet<Category>> findAllDeletedCategory() {
        return CompletableFuture.completedFuture(categoryRepository.findAllDeleted());
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

    @Transactional
    public void deleteCategory(Category category) {
        categoryRepository.delete(category);
        log.debug("Record Deleted: " + category.toString());
        log.debug("Deleting Relationships");
        final Collection<CategoryLevel> catLevels = category.getCategoryLevelsById();
        catLevels.forEach(categoryLevel -> {
            categoryLevelRepository.delete(categoryLevel);
            log.debug("Record Deleted: " + categoryLevel.toString());
        });
        log.debug("Records Deleted: " + catLevels.size());
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
     *
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
                log.debug("Record Deleted: " + categoryLevel.toString());
                return true;
            } else {
                log.debug("Root node is not empty");
                return false;
            }
            // otherwise its a child node
        } else {
            if (categoryLevelRepository.categoryExistsInProduct(categoryId) == 0) {
                categoryLevelRepository.deleteNode(categoryId);
                log.debug("Record Deleted: " + categoryLevel.toString());
                return true;
            } else {
                log.debug("Child node is not unassigned");
                return false;
            }
        }
    }

    /**
     * Method to delete a root node. It requires control methods to ensure it is not deleting child nodes
     * assigned to a product.
     */
    @Async
    @Transactional
    public CompletableFuture<Void> deleteRootNode(int levelOne) {
        return CompletableFuture.runAsync(() -> categoryLevelRepository.deleteRootNode(levelOne));
    }

    /**
     * Method to delete a node. It requires control methods to ensure it is not deleting a node
     * or a root node with leaves assigned to a product.
     */
    @Async
    @Transactional
    public CompletableFuture<Void> deleteNode(int categoryId) {
        return CompletableFuture.runAsync(() -> categoryLevelRepository.deleteNode(categoryId));
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
        return CompletableFuture.completedFuture(measureRepository.findAll(pageableFifty));
    }

    @Async
    public CompletableFuture<Page<Measure>> findAllMeasures(Pageable pageable) {
        return CompletableFuture.completedFuture(measureRepository.findAll(pageable));
    }

    @Async
    public CompletableFuture<Page<Measure>> findAllDeletedMeasures() {
        return CompletableFuture.completedFuture(measureRepository.findAllDeleted(pageableFiftyDeleted));
    }

    @Async
    public CompletableFuture<Measure> saveMeasures(Measure measure) {
        return CompletableFuture.completedFuture(measureRepository.save(measure));
    }

    @Async
    public void deleteMeasures(Measure measure) {
        measureRepository.delete(measure);
        log.debug("Record Deleted: " + measure.toString());
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
        return CompletableFuture.completedFuture(presentationRepository.findAll(pageableFifty));
    }

    @Async
    public CompletableFuture<Page<Presentation>> findAllPresentations(Pageable pageable) {
        return CompletableFuture.completedFuture(presentationRepository.findAll(pageable));
    }

    @Async
    public CompletableFuture<Page<Presentation>> findAllDeletedPresentations() {
        return CompletableFuture.completedFuture(presentationRepository.findAllDeleted(pageableFiftyDeleted));
    }

    @Async
    public CompletableFuture<Presentation> savePresentation(Presentation presentation) {
        return CompletableFuture.completedFuture(presentationRepository.save(presentation));
    }

    @Async
    public void deletePresentation(Presentation presentation) {
        presentationRepository.delete(presentation);
        log.debug("Record Deleted: " + presentation.toString());
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
        return CompletableFuture.completedFuture(materialRepository.findAll(pageableFifty));
    }

    @Async
    public CompletableFuture<Page<Material>> findAllMaterials(Pageable pageable) {
        return CompletableFuture.completedFuture(materialRepository.findAll(pageable));
    }

    @Async
    public CompletableFuture<Page<Material>> findAllDeletedMaterials() {
        return CompletableFuture.completedFuture(materialRepository.findAllDeleted(pageableFiftyDeleted));
    }

    @Async
    public CompletableFuture<Material> saveMaterial(Material material) {
        return CompletableFuture.completedFuture(materialRepository.save(material));
    }

    @Async
    public void deleteMaterial(Material material) {
        materialRepository.delete(material);
        log.debug("Record Deleted: " + material.toString());
    }
}
