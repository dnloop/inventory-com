create table if not exists category
(
    id          int unsigned auto_increment
        primary key,
    description varchar(140) not null,
    deleted     tinyint(1)   null,
    created_at  timestamp    null,
    modified_at timestamp    null,
    deleted_at  timestamp    null
)
    collate = utf8mb4_bin;

create table if not exists departments
(
    name varchar(34) not null,
    id   int         not null
        primary key
)
    collate = utf8mb4_bin;

create table if not exists municipality
(
    name varchar(38) not null,
    id   int         not null
        primary key
)
    collate = utf8mb4_bin;

create table if not exists product
(
    id           int unsigned auto_increment
        primary key,
    description  varchar(140)                  not null,
    stock        int unsigned default 1        not null,
    price        decimal(15, 4) unsigned       not null,
    deleted      tinyint(1)                    null,
    created_at   timestamp                     null,
    modified_at  timestamp                     null,
    category_id  int unsigned                  not null,
    deleted_at   timestamp                     null,
    image        varchar(500)                  null,
    presentation varchar(100) default 'unidad' null,
    constraint product_FK
        foreign key (category_id) references category (id)
)
    collate = utf8mb4_bin;

create table if not exists province
(
    name varchar(55) not null,
    id   int         not null
        primary key
)
    collate = utf8mb4_bin;

create table if not exists locality
(
    name            varchar(83) not null,
    id              int         not null
        primary key,
    function        varchar(21) null,
    category        varchar(33) null,
    centroid_lat    float       null,
    centroid_lon    float       null,
    municipality_id int         null,
    departament_id  int         null,
    province_id     int         not null,
    constraint locality_FK
        foreign key (municipality_id) references municipality (id),
    constraint locality_FK_1
        foreign key (departament_id) references departments (id),
    constraint locality_FK_2
        foreign key (province_id) references province (id)
)
    collate = utf8mb4_bin;

create table if not exists client
(
    id          int unsigned auto_increment
        primary key,
    name        varchar(60)      not null,
    surname     varchar(140)     null,
    address     varchar(280)     null,
    cuit        int(11) unsigned null,
    dni         varchar(8)       null,
    locality_id int              not null,
    deleted     tinyint(1)       null,
    created_at  timestamp        null,
    modified_at timestamp        null,
    deleted_at  timestamp        null,
    mail        varchar(320)     null,
    constraint client_FK
        foreign key (locality_id) references locality (id)
)
    collate = utf8mb4_bin;

create index if not exists localidad_id
    on client (locality_id);

create table if not exists client_phone
(
    id          int          not null
        primary key,
    number      varchar(12)  not null,
    deleted     tinyint(1)   null,
    created_at  timestamp    null,
    modified_at timestamp    null,
    deleted_at  timestamp    null,
    client_id   int unsigned not null,
    constraint client_phone_FK
        foreign key (client_id) references client (id)
)
    collate = utf8mb4_bin;

create index if not exists fk_telefono_cliente_clientes1_idx
    on client_phone (client_id);

create index if not exists departamento_id
    on locality (departament_id);

create index if not exists municipio_id
    on locality (municipality_id);

create index if not exists provincia_id
    on locality (province_id);

create table if not exists sale_invoice
(
    id              int unsigned auto_increment
        primary key,
    number          int(12) unsigned                                    not null,
    generation_date timestamp               default current_timestamp() not null on update current_timestamp(),
    surcharge       decimal(15, 5) unsigned default 0.00000             null,
    total           decimal(15, 5) unsigned                             not null,
    invoice_type    varchar(3)                                          null,
    payment_type    varchar(8)                                          null,
    deleted         tinyint(1)                                          null,
    created_at      timestamp                                           null,
    discount        decimal(15, 4)          default 0.0000              null,
    modified_at     timestamp                                           null,
    deleted_at      timestamp                                           null,
    client_id       int unsigned                                        not null
)
    collate = utf8mb4_bin;

create table if not exists sale_detail
(
    id              int unsigned auto_increment
        primary key,
    amount          int unsigned                   not null,
    unit_price      decimal(15, 5) unsigned        not null,
    iva             tinyint(2) unsigned default 21 null,
    deleted         tinyint(1)                     null,
    created_at      timestamp                      null,
    modified_at     timestamp                      null,
    deleted_at      timestamp                      null,
    sale_invoice_id int unsigned                   not null,
    product_id      int unsigned                   not null,
    constraint sale_detail_FK
        foreign key (sale_invoice_id) references sale_invoice (id),
    constraint sale_detail_FK_1
        foreign key (product_id) references product (id)
)
    collate = utf8mb4_bin;

create index if not exists factura_venta_id
    on sale_detail (sale_invoice_id);

create index if not exists producto_id
    on sale_detail (product_id);

create index if not exists fk_factura_venta_clientes1_idx
    on sale_invoice (client_id);

create table if not exists sale_share
(
    id              int unsigned auto_increment
        primary key,
    number          int(12) unsigned not null,
    payment_date    date             null,
    due_date        date             not null,
    deleted         tinyint(1)       null,
    created_at      timestamp        null,
    modified_at     timestamp        null,
    deleted_at      timestamp        null,
    sale_invoice_id int unsigned     not null,
    constraint sale_share_FK
        foreign key (sale_invoice_id) references sale_invoice (id)
)
    collate = utf8mb4_bin;

create index if not exists fk_cuota_factura1_idx
    on sale_share (sale_invoice_id);

create table if not exists supplier
(
    id          int          not null
        primary key,
    name        tinytext     not null,
    description tinytext     null,
    mail        varchar(320) null,
    locality_id int          not null,
    deleted     tinyint(1)   null,
    created_at  timestamp    null,
    modified_at timestamp    null,
    deleted_at  timestamp    null,
    domicilio   tinytext     not null,
    cuit        varchar(11)  not null,
    constraint supplier_FK
        foreign key (locality_id) references locality (id)
)
    collate = utf8mb4_bin;

create table if not exists purchase_invoice
(
    id              int unsigned auto_increment
        primary key,
    number          int(12) unsigned                                    not null,
    generation_date timestamp               default current_timestamp() not null on update current_timestamp(),
    payment_type    varchar(8)                                          null,
    invoice_type    varchar(3)                                          null,
    surcharge       decimal(15, 5) unsigned default 0.00000             null,
    discount        decimal(15, 5) unsigned default 0.00000             null,
    total           decimal(15, 5) unsigned                             not null,
    deleted         tinyint(1)                                          null,
    created_at      timestamp                                           null,
    modified_at     timestamp                                           null,
    deleted_at      timestamp                                           null,
    supplier_id     int                                                 not null,
    constraint purchase_invoice_FK
        foreign key (supplier_id) references supplier (id)
)
    collate = utf8mb4_bin;

create table if not exists purchase_detail
(
    id                  int unsigned auto_increment
        primary key,
    amount              int unsigned        default 1  not null,
    unit_price          decimal(15, 5) unsigned        not null,
    subtotal            decimal(15, 5) unsigned        not null,
    deleted             tinyint(1)                     null,
    created_at          timestamp                      null,
    modified_at         timestamp                      null,
    deleted_at          timestamp                      null,
    purchase_invoice_id int unsigned                   not null,
    product_id          int unsigned                   not null,
    iva                 tinyint(2) unsigned default 21 null,
    constraint purchase_detail_FK
        foreign key (purchase_invoice_id) references purchase_invoice (id),
    constraint purchase_detail_FK_1
        foreign key (product_id) references product (id)
)
    collate = utf8mb4_bin;

create index if not exists factura_venta_id
    on purchase_detail (purchase_invoice_id);

create index if not exists producto_id
    on purchase_detail (product_id);

create index if not exists fk_factura_compra_proveedores1_idx
    on purchase_invoice (supplier_id);

create table if not exists purchase_share
(
    id                  int unsigned auto_increment
        primary key,
    number              int(12) unsigned not null,
    payment_date        date             null,
    due_date            date             not null,
    deleted             tinyint(1)       null,
    created_at          timestamp        null,
    modified_at         timestamp        null,
    deleted_at          timestamp        null,
    purchase_invoice_id int unsigned     not null,
    constraint purchase_share_FK
        foreign key (purchase_invoice_id) references purchase_invoice (id)
)
    collate = utf8mb4_bin;

create index if not exists fk_cuota_venta_copy1_factura_compra1_idx
    on purchase_share (purchase_invoice_id);

create index if not exists localidad_id
    on supplier (locality_id);

create table if not exists supplier_catalog
(
    modified_at  timestamp    null,
    deleted_at   timestamp    null,
    deleted      tinyint(1)   null,
    created_at   timestamp    null,
    catalog_code varchar(20)  not null,
    id           int unsigned auto_increment
        primary key,
    product_id   int unsigned not null,
    supplier_id  int          not null,
    constraint producto_id
        unique (product_id),
    constraint supplier_catalog_FK
        foreign key (product_id) references product (id),
    constraint supplier_catalog_FK_1
        foreign key (supplier_id) references supplier (id)
)
    collate = utf8mb4_bin;

create table if not exists supplier_phone
(
    id          int         not null
        primary key,
    number      varchar(12) not null,
    deleted     tinyint(1)  null,
    created_at  timestamp   null,
    modified_at timestamp   null,
    deleted_at  timestamp   null,
    supplier_id int         not null,
    constraint supplier_phone_FK
        foreign key (supplier_id) references supplier (id)
)
    collate = utf8mb4_bin;

create index if not exists fk_telefono_proveedor_proveedores1_idx
    on supplier_phone (supplier_id);

create index if not exists proveedores_id
    on supplier_phone (supplier_id);


