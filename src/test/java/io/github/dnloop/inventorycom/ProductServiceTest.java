package io.github.dnloop.inventorycom;

import io.github.dnloop.inventorycom.model.Measure;
import io.github.dnloop.inventorycom.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.test.annotation.DirtiesContext;

/**
 * Basic product service tests units. Perform CRUD operations and test relationships with dependencies.
 */
@SpringBootTest
@EnableAsync
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ProductServiceTest {

    @Autowired
    ProductService productService;

    /* Product */

    @Test
    void  findProductById() {

    }

    @Test
    void  findProductDeleted() {

    }

    @Test
    void  findAllProducts() {

    }
    

    @Test
    void  findProductAllDeleted() {

    }

    @Test
    void  saveProduct() {

    }


    @Test
    void deleteProduct() {

    }

    /* Category */

    @Test
    void  findCategoryById() {

    }

    @Test
    void  findDeletedCategory() {

    }

    @Test
    void  findAllCategory() {

    }

    @Test
    void  findAllDeletedCategory() {

    }

    @Test
    void  saveCategory( ) {

    }

    @Test
    void deleteCategory() {
    }

    /* Category Level */

    @Test
    void  findCategoryLevelById() {

    }

    @Test
    void  findDeletedCategoryLevel() {

    }

    @Test
    void  findAllCategoryLevel() {

    }

    @Test
    void  findAllDeletedCategoryLevel() {

    }

    @Test
    void  saveCategoryLevel() {

    }

    @Test
    void deleteCategoryLevel() {

    }

    /* Measure */

    @Test
    void  findMeasrueById() {

    }

    @Test
    void  findDeletedMeasure() {

    }

    @Test
    void  findAllMeasures() {

    }

    @Test
    void  findAllDeletedMeasures() {

    }

    @Test
    void  saveMeasures(Measure measure) {

    }

    @Test
    void deleteMeasures(Measure measure) {
        
    }

    /* Presentation */

    @Test
    void  findPresentationById(int id) {

    }

    @Test
    void  findDeletedPresentation() {

    }


    @Test
    void  findAllPresentations() {

    }

    @Test
    void  findAllDeletedPresentations() {

    }

    @Test
    void  savePresentation() {

    }

    @Test
    void deletePresentation() {

    }

    /* Material */

    @Test
    void  findMaterialById() {

    }

    @Test
    void  findDeletedMaterial() {

    }

    @Test
    void  findAllMaterials() {

    }

    @Test
    void  findAllDeletedMaterials() {

    }

    @Test
    void  saveMaterial() {

    }

    @Test
    void  deleteMaterial() {

    }
}
