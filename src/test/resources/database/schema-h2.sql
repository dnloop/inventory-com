create table inventario_comercial.category
(
    id          integer      not null,
    created_at  timestamp,
    deleted     tinyint,
    deleted_at  timestamp,
    description varchar(140) not null,
    modified_at timestamp,
    primary key (id)
);

create table inventario_comercial.client
(
    id          integer     not null,
    address     varchar(280),
    created_at  timestamp,
    cuit        integer,
    deleted     tinyint,
    deleted_at  timestamp,
    dni         varchar(8),
    locality_id integer     not null,
    mail        varchar(320),
    modified_at timestamp,
    name        varchar(60) not null,
    surname     varchar(140),
    primary key (id)
);

create table inventario_comercial.client_phone
(
    id          integer     not null,
    client_id   integer     not null,
    created_at  timestamp,
    deleted     tinyint,
    deleted_at  timestamp,
    modified_at timestamp,
    number      varchar(12) not null,
    primary key (id)
);

create table inventario_comercial.material
(
    id          integer      not null,
    color       varchar(320),
    created_at  timestamp    not null,
    deleted     tinyint,
    deleted_at  timestamp,
    modified_at timestamp,
    type        varchar(320) not null,
    primary key (id)
);

create table inventario_comercial.measure
(
    id          integer    not null,
    created_at  timestamp  not null,
    deleted     tinyint,
    deleted_at  timestamp,
    diameter    double,
    lenght      double,
    modified_at timestamp,
    type        varchar(4) not null,
    width       double,
    primary key (id)
);

create table inventario_comercial.presentation
(
    id          integer      not null,
    created_at  timestamp    not null,
    deleted     tinyint,
    deleted_at  timestamp,
    description varchar(140) not null,
    modified_at timestamp,
    units       integer      not null,
    primary key (id)
);

create table inventario_comercial.product
(
    id          integer       not null,
    category_id integer       not null,
    created_at  timestamp     not null,
    deleted     tinyint,
    deleted_at  timestamp,
    description varchar(140)  not null,
    detail_id   integer       not null,
    image       varchar(500),
    modified_at timestamp,
    price       decimal(4, 0) not null,
    stock       integer       not null,
    primary key (id)
);

create table inventario_comercial.product_detail
(
    id              integer      not null,
    brand           varchar(140) not null,
    created_at      timestamp,
    deleted         tinyint,
    deleted_at      timestamp,
    material_id     integer      not null,
    measure_id      integer      not null,
    modified_at     timestamp,
    presentation_id integer      not null,
    primary key (id)
);

create table inventario_comercial.purchase_detail
(
    id                  integer       not null,
    amount              integer       not null,
    created_at          timestamp,
    deleted             tinyint,
    deleted_at          timestamp,
    iva                 tinyint,
    modified_at         timestamp,
    product_id          integer       not null,
    purchase_invoice_id integer       not null,
    subtotal            decimal(5, 0) not null,
    unit_price          decimal(5, 0) not null,
    primary key (id)
);

create table inventario_comercial.purchase_invoice
(
    id              integer       not null,
    created_at      timestamp,
    deleted         tinyint,
    deleted_at      timestamp,
    discount        decimal(5, 0),
    generation_date timestamp     not null,
    invoice_type    varchar(3),
    modified_at     timestamp,
    number          integer       not null,
    payment_type    varchar(8),
    supplier_id     integer       not null,
    surcharge       decimal(5, 0),
    total           decimal(5, 0) not null,
    primary key (id)
);

create table inventario_comercial.purchase_share
(
    id                  integer not null,
    created_at          timestamp,
    deleted             tinyint,
    deleted_at          timestamp,
    due_date            date    not null,
    modified_at         timestamp,
    number              integer not null,
    payment_date        date,
    purchase_invoice_id integer not null,
    primary key (id)
);

create table inventario_comercial.sale_detail
(
    id              integer       not null,
    amount          integer       not null,
    created_at      timestamp,
    deleted         tinyint,
    deleted_at      timestamp,
    iva             tinyint,
    modified_at     timestamp,
    product_id      integer       not null,
    sale_invoice_id integer       not null,
    unit_price      decimal(5, 0) not null,
    primary key (id)
);

create table inventario_comercial.sale_invoice
(
    id              integer       not null,
    client_id       integer       not null,
    created_at      timestamp,
    deleted         tinyint,
    deleted_at      timestamp,
    discount        decimal(4, 0),
    generation_date timestamp     not null,
    invoice_type    varchar(3),
    modified_at     timestamp,
    number          integer       not null,
    payment_type    varchar(8),
    surcharge       decimal(5, 0),
    total           decimal(5, 0) not null,
    primary key (id)
);

create table inventario_comercial.sale_share
(
    id              integer not null,
    created_at      timestamp,
    deleted         tinyint,
    deleted_at      timestamp,
    due_date        date    not null,
    modified_at     timestamp,
    number          integer not null,
    payment_date    date,
    sale_invoice_id integer not null,
    primary key (id)
);

create table inventario_comercial.sub_category
(
    id          integer not null,
    category_id integer not null,
    created_at  timestamp,
    deleted     tinyint,
    deleted_at  timestamp,
    modified_at timestamp,
    primary key (id)
);

create table inventario_comercial.supplier
(
    id          integer      not null,
    address     varchar(255) not null,
    created_at  timestamp    not null,
    cuit        varchar(11)  not null,
    deleted     tinyint,
    deleted_at  timestamp,
    description varchar(255),
    locality_id integer      not null,
    mail        varchar(320),
    modified_at timestamp,
    name        varchar(255) not null,
    primary key (id)
);

create table inventario_comercial.supplier_catalog
(
    id           integer     not null,
    catalog_code varchar(20) not null,
    created_at   timestamp,
    deleted      tinyint,
    deleted_at   timestamp,
    modified_at  timestamp,
    product_id   integer     not null,
    supplier_id  integer     not null,
    primary key (id)
);

create table inventario_comercial.supplier_phone
(
    id          integer     not null,
    created_at  timestamp,
    deleted     tinyint,
    deleted_at  timestamp,
    modified_at timestamp,
    number      varchar(12) not null,
    supplier_id integer     not null,
    primary key (id)
);

create table departments
(
    id   integer     not null,
    name varchar(34) not null,
    primary key (id)
);

create table locality
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
    primary key (id)
);

create table municipality
(
    id   integer     not null,
    name varchar(38) not null,
    primary key (id)
);

create table province
(
    id   integer     not null,
    name varchar(55) not null,
    primary key (id)
);

alter table inventario_comercial.client
    add constraint FKr491vdinbljmrdbrkjlr3k5wl foreign key (locality_id) references locality;

alter table inventario_comercial.client_phone
    add constraint FKnhe87dbv9sos43hos3gekhjpq foreign key (client_id) references inventario_comercial.client;

alter table inventario_comercial.product
    add constraint FK1mtsbur82frn64de7balymq9s foreign key (category_id) references inventario_comercial.category;

alter table inventario_comercial.product
    add constraint FKjb0cih1rr1wsbsxmjo7bcd45i foreign key (detail_id) references inventario_comercial.product_detail;

alter table inventario_comercial.product_detail
    add constraint FKelrbk54wt31vv07h3us1gap2c foreign key (material_id) references inventario_comercial.material;

alter table inventario_comercial.product_detail
    add constraint FKosdydvosy05yp2frifoyy35j5 foreign key (measure_id) references inventario_comercial.measure;

alter table inventario_comercial.product_detail
    add constraint FK5c9gtif7vw4me7cr7wysxatd1 foreign key (presentation_id) references inventario_comercial.presentation;

alter table inventario_comercial.purchase_detail
    add constraint FK79a6tsn4e9qfillme2u9kr3i2 foreign key (product_id) references inventario_comercial.product;

alter table inventario_comercial.purchase_detail
    add constraint FKp7961anxbc2gfd45x203yfn2 foreign key (purchase_invoice_id) references inventario_comercial.purchase_invoice;

alter table inventario_comercial.purchase_invoice
    add constraint FKqtx4kjstn77n9v4wowt0mlxkx foreign key (supplier_id) references inventario_comercial.supplier;

alter table inventario_comercial.purchase_share
    add constraint FK10soyhcwf4nt4xaoi5epitcmd foreign key (purchase_invoice_id) references inventario_comercial.purchase_invoice;

alter table inventario_comercial.sale_detail
    add constraint FK2k26c5k0qkdm1srkiwn7q5009 foreign key (product_id) references inventario_comercial.product;

alter table inventario_comercial.sale_detail
    add constraint FKh1e5sj1nud0pv62qot0jemdrt foreign key (sale_invoice_id) references inventario_comercial.sale_invoice;

alter table inventario_comercial.sale_share
    add constraint FKde77s9nnikqfax26c9l4kqlx8 foreign key (sale_invoice_id) references inventario_comercial.sale_invoice;

alter table inventario_comercial.sub_category
    add constraint FKl65dyy5me2ypoyj8ou1hnt64e foreign key (category_id) references inventario_comercial.category;

alter table inventario_comercial.supplier
    add constraint FKo8tkgxhgfy0hqhuoaspeaqgpc foreign key (locality_id) references locality;

alter table inventario_comercial.supplier_catalog
    add constraint FKgyb98usq8v4sfgjcn7dubkuek foreign key (product_id) references inventario_comercial.product;

alter table inventario_comercial.supplier_catalog
    add constraint FKqpd8eph20l0ogo4rj3bbts5pi foreign key (supplier_id) references inventario_comercial.supplier;

alter table inventario_comercial.supplier_phone
    add constraint FK1f0x81egyt4uenf0nsu1ux078 foreign key (supplier_id) references inventario_comercial.supplier;

alter table locality
    add constraint FKklpuubcffmokqtb05oogok2l3 foreign key (departament_id) references departments;

alter table locality
    add constraint FKh4q3s8ue8ijt40v28qhc1c0vh foreign key (municipality_id) references municipality;

alter table locality
    add constraint FKr0h7og8lyw8n6u9fqr59lmye7 foreign key (province_id) references province;
