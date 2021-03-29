package io.github.dnloop.inventorycom;

import io.github.dnloop.inventorycom.service.LocalityService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

/**
 * Basic locality service tests units. The main purpose is to access related fields
 * by optimizing the search of large elements.
 */
@SpringBootTest
@EnableAsync
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class LocalityServiceTest {

    @Autowired
    LocalityService localityService;

    @Test
    void contextLoads() {}

    @Test
    @Sql({"/db/data/localities-relation.sql"})
    void findById() {}

    @Test
    @Sql({"/db/data/localities-relation.sql"})
    void findAll() {}

    @Test
    @Sql({"/db/data/localities-relation.sql"})
    void findLocalityByMunicipality() {}

    @Test
    @Sql({"/db/data/localities-relation.sql"})
    void findLocalitiesByDepartment() {}

    @Test
    @Sql({"/db/data/localities-relation.sql"})
    void findLocalitiesByProvince() {}

    @Test
    @Sql({"/db/data/localities-relation.sql"})
    void findProvinceById() {}

    @Test
    @Sql({"/db/data/localities-relation.sql"})
    void findProvinces() {}

    @Test
    @Sql({"/db/data/localities-relation.sql"})
    void findDepartmentById() {}

    @Test
    @Sql({"/db/data/localities-relation.sql"})
    void findDepartments() {}
}
