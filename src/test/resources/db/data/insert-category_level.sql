INSERT INTO PUBLIC.CATEGORY_LEVEL
(CATEGORY_ID, L1, L2, L3, L4, CREATED_AT, DELETED, DELETED_AT, MODIFIED_AT)
VALUES
(1, 1, 0, 0, 0, CURRENT_TIMESTAMP(), 0, null, null),
(2, 1, 1, 0, 0, CURRENT_TIMESTAMP(), 0, null, null),
(3, 1, 2, 1, 0, CURRENT_TIMESTAMP(), 0, null, null),
(4, 1, 2, 2, 0, CURRENT_TIMESTAMP(), 0, null, null),
(5, 2, 0, 0, 0, CURRENT_TIMESTAMP(), 0, null, null),
(6, 2, 1, 0, 0, CURRENT_TIMESTAMP(), 0, null, null),
(7, 0, 0, 0, 0, CURRENT_TIMESTAMP(), 1, null, null),
(8, 0, 0, 0, 0, CURRENT_TIMESTAMP(), 1, null, null);