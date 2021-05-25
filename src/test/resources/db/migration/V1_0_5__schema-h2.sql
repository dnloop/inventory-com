create table PUBLIC.departments
(
    id   integer     not null,
    name varchar(34) not null,
    primary key (id)
);

create table PUBLIC.municipality
(
    id   integer     not null,
    name varchar(38) not null,
    primary key (id)
);

create table PUBLIC.province
(
    id   integer     not null,
    name varchar(55) not null,
    primary key (id)
);

create table PUBLIC.locality
(
    id              integer     not null,
    category        varchar(33),
    centroid_lat    double,
    centroid_lon    double,
    departament_id  integer,
    function        varchar(21),
    municipality_id integer,
    name            varchar(83) not null,
    province_id     integer     not null,
    primary key (id),

    constraint FKklpuubcffmokqtb05oogok2l3 foreign key (departament_id) references departments,
    constraint FKh4q3s8ue8ijt40v28qhc1c0vh foreign key (municipality_id) references municipality,
    constraint FKr0h7og8lyw8n6u9fqr59lmye7 foreign key (province_id) references province
);

create table PUBLIC.category
(
    id          integer auto_increment not null,
    created_at  timestamp default current_timestamp,
    deleted     tinyint   default 0,
    deleted_at  timestamp,
    description varchar(140)           not null,
    modified_at timestamp,
    primary key (id)
);

create table PUBLIC.client
(
    id          integer auto_increment not null,
    address     varchar(280),
    created_at  timestamp default current_timestamp,
    cuit        bigint(20),
    deleted     tinyint   default 0,
    deleted_at  timestamp,
    dni         varchar(8),
    locality_id integer                not null,
    mail        varchar(320),
    modified_at timestamp,
    name        varchar(60)            not null,
    surname     varchar(140),
    primary key (id),

    constraint FKr491vdinbljmrdbrkjlr3k5wl foreign key (locality_id) references locality
);

create table PUBLIC.client_phone
(
    id          integer auto_increment not null,
    client_id   integer                not null,
    created_at  timestamp default current_timestamp,
    deleted     tinyint   default 0,
    deleted_at  timestamp,
    modified_at timestamp,
    number      varchar(12)            not null,
    primary key (id),

    constraint FKnhe87dbv9sos43hos3gekhjpq foreign key (client_id) references PUBLIC.client
);

create table PUBLIC.material
(
    id          integer auto_increment              not null,
    color       varchar(320),
    created_at  timestamp default current_timestamp not null,
    deleted     tinyint   default 0,
    deleted_at  timestamp,
    modified_at timestamp,
    type        varchar(320)                        not null,
    primary key (id)
);

create table PUBLIC.measure
(
    id          integer auto_increment              not null,
    created_at  timestamp default current_timestamp not null,
    deleted     tinyint   default 0,
    deleted_at  timestamp,
    diameter    double,
    length      double,
    modified_at timestamp,
    type        varchar(4)                          not null,
    width       double,
    primary key (id)
);

create table PUBLIC.presentation
(
    id          integer auto_increment              not null,
    created_at  timestamp default current_timestamp not null,
    deleted     tinyint   default 0,
    deleted_at  timestamp,
    description varchar(140)                        not null,
    modified_at timestamp,
    units       integer                             not null,
    primary key (id)
);

create table PUBLIC.product_detail
(
    id              integer auto_increment not null,
    brand           varchar(140)           not null,
    created_at      timestamp default current_timestamp,
    deleted         tinyint   default 0,
    deleted_at      timestamp,
    material_id     integer                not null,
    measure_id      integer                not null,
    modified_at     timestamp,
    presentation_id integer                not null,
    primary key (id),

    constraint FKelrbk54wt31vv07h3us1gap2c foreign key (material_id) references PUBLIC.material,
    constraint FKosdydvosy05yp2frifoyy35j5 foreign key (measure_id) references PUBLIC.measure,
    constraint FK5c9gtif7vw4me7cr7wysxatd1 foreign key (presentation_id) references PUBLIC.presentation
);

create table PUBLIC.product
(
    id           integer auto_increment              not null,
    category_id  integer                             not null,
    created_at   timestamp default current_timestamp not null,
    deleted      tinyint   default 0,
    deleted_at   timestamp,
    description  varchar(320)                        not null,
    detail_id    integer                             not null,
    image        varchar(500),
    modified_at  timestamp,
    product_code varchar(320)                        not null,
    stock        integer                             not null,
    primary key (id),

    constraint FK1mtsbur82frn64de7balymq9s foreign key (category_id) references PUBLIC.category,
    constraint FKjb0cih1rr1wsbsxmjo7bcd45i foreign key (detail_id) references PUBLIC.product_detail

);

create table PUBLIC.supplier
(
    id          integer auto_increment              not null,
    address     varchar(320)                        not null,
    created_at  timestamp default current_timestamp not null,
    cuit        bigint(20)                          not null,
    deleted     tinyint   default 0,
    deleted_at  timestamp,
    description varchar(320),
    locality_id integer                             not null,
    mail        varchar(320),
    modified_at timestamp,
    name        varchar(320)                        not null,
    primary key (id),

    constraint FKo8tkgxhgfy0hqhuoaspeaqgpc foreign key (locality_id) references locality
);

create table PUBLIC.purchase_invoice
(
    id              integer auto_increment not null,
    created_at      timestamp default current_timestamp,
    deleted         tinyint   default 0,
    deleted_at      timestamp,
    discount        decimal(15, 5),
    generation_date timestamp              not null,
    invoice_type    varchar(3),
    modified_at     timestamp,
    number          integer                not null,
    payment_type    varchar(8),
    supplier_id     integer                not null,
    surcharge       decimal(15, 5),
    total           decimal(15, 5)         not null,
    primary key (id),

    constraint FKqtx4kjstn77n9v4wowt0mlxkx foreign key (supplier_id) references PUBLIC.supplier
);

create table PUBLIC.purchase_detail
(
    id                  integer auto_increment not null,
    amount              integer                not null,
    created_at          timestamp default current_timestamp,
    deleted             tinyint   default 0,
    deleted_at          timestamp,
    iva                 tinyint,
    modified_at         timestamp,
    product_id          integer                not null,
    purchase_invoice_id integer                not null,
    subtotal            decimal(15, 5)         not null,
    unit_price          decimal(15, 5)         not null,
    primary key (id),

    constraint FK79a6tsn4e9qfillme2u9kr3i2 foreign key (product_id) references PUBLIC.product,
    constraint FKp7961anxbc2gfd45x203yfn2 foreign key (purchase_invoice_id) references PUBLIC.purchase_invoice
);

create table PUBLIC.purchase_share
(
    id                  integer auto_increment not null,
    created_at          timestamp default current_timestamp,
    deleted             tinyint   default 0,
    deleted_at          timestamp,
    due_date            date                   not null,
    modified_at         timestamp,
    number              integer                not null,
    payment_date        date,
    purchase_invoice_id integer                not null,
    primary key (id),

    constraint FK10soyhcwf4nt4xaoi5epitcmd foreign key (purchase_invoice_id) references PUBLIC.purchase_invoice
);

create table PUBLIC.sale_invoice
(
    id              integer auto_increment not null,
    client_id       integer                not null,
    created_at      timestamp default current_timestamp,
    deleted         tinyint   default 0,
    deleted_at      timestamp,
    discount        decimal(4, 0),
    generation_date timestamp              not null,
    invoice_type    varchar(3),
    modified_at     timestamp,
    number          integer                not null,
    payment_type    varchar(8),
    surcharge       decimal(15, 5),
    total           decimal(15, 5)         not null,
    primary key (id)
);

create table PUBLIC.sale_detail
(
    id              integer auto_increment not null,
    amount          integer                not null,
    created_at      timestamp default current_timestamp,
    deleted         tinyint   default 0,
    deleted_at      timestamp,
    iva             tinyint,
    modified_at     timestamp,
    product_id      integer                not null,
    sale_invoice_id integer                not null,
    subtotal        decimal(15, 5)         not null,
    unit_price      decimal(15, 5)         not null,
    primary key (id),

    constraint FK2k26c5k0qkdm1srkiwn7q5009 foreign key (product_id) references PUBLIC.product,
    constraint FKh1e5sj1nud0pv62qot0jemdrt foreign key (sale_invoice_id) references PUBLIC.sale_invoice

);

create table PUBLIC.sale_share
(
    id              integer auto_increment not null,
    created_at      timestamp default current_timestamp,
    deleted         tinyint   default 0,
    deleted_at      timestamp,
    due_date        date                   not null,
    modified_at     timestamp,
    number          integer                not null,
    payment_date    date,
    sale_invoice_id integer                not null,
    primary key (id),

    constraint FKde77s9nnikqfax26c9l4kqlx8 foreign key (sale_invoice_id) references PUBLIC.sale_invoice
);

create table PUBLIC.category_level
(
    id          integer auto_increment not null,
    category_id integer                not null,
    L1          integer   default 0,
    L2          integer   default 0,
    L3          integer   default 0,
    L4          integer   default 0,
    created_at  timestamp default current_timestamp,
    deleted     tinyint   default 0,
    deleted_at  timestamp,
    modified_at timestamp,
    primary key (id),

    constraint FKl65dyy5me2ypoyj8ou1hnt64e foreign key (category_id) references PUBLIC.category
);

create table PUBLIC.supplier_catalog
(
    id           integer auto_increment not null,
    catalog_code varchar(20)            not null,
    created_at   timestamp default current_timestamp,
    deleted      tinyint   default 0,
    deleted_at   timestamp,
    modified_at  timestamp,
    product_id   integer                not null,
    supplier_id  integer                not null,
    primary key (id),

    constraint FKgyb98usq8v4sfgjcn7dubkuek foreign key (product_id) references PUBLIC.product,
    constraint FKqpd8eph20l0ogo4rj3bbts5pi foreign key (supplier_id) references PUBLIC.supplier,
    constraint FK1f0x81egyt4uenf0nsu1ux078 foreign key (supplier_id) references PUBLIC.supplier
);

create table PUBLIC.supplier_phone
(
    id          integer auto_increment not null,
    created_at  timestamp default current_timestamp,
    deleted     tinyint   default 0,
    deleted_at  timestamp,
    modified_at timestamp,
    number      varchar(12)            not null,
    supplier_id integer                not null,
    primary key (id)
);

create sequence PURCHASE_SHARE_SEQUENCE start with 6 -- only for tests
    increment by 100;

create sequence SALE_SHARE_SEQUENCE start with 6 -- only for tests
    increment by 100;

create sequence PRODUCT_SEQUENCE start with 7 -- only for tests
    increment by 100;