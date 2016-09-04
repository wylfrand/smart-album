
  drop table if exists Album 
; 
    drop table if exists Comment
; 
    drop table if exists Image
; 
    drop table if exists Image_MetaTag
; 
    drop table if exists MESSAGE_PART
; 
    drop table if exists MessageHTML
; 
    drop table if exists MetaTag
; 
    drop table if exists Shelf
; 
    drop table if exists User
; 
    create table Album (
        id bigint NOT NULL AUTO_INCREMENT,
        created date,
        description varchar(1024),
        name varchar(255) not null,
        coveringImage_id bigint,
        shelf_id bigint not null,
        primary key (id)
    )
; 
    create table Comment (
        id bigint NOT NULL AUTO_INCREMENT,
        date timestamp,
        message varchar(1024) not null,
        album_id bigint,
        author_id bigint,
        image_id bigint,
        primary key (id)
    )
; 
    create table Image (
        id bigint NOT NULL AUTO_INCREMENT,
        allowComments boolean not null,
        cameraModel varchar(255),
        created timestamp,
        description varchar(1024),
        height integer not null,
        name varchar(255) not null,
        path varchar(1024) not null,
        showMetaInfo boolean,
        size double not null,
        uploaded timestamp,
        width integer not null,
        album_id bigint,
        user_id bigint,
        primary key (id)
    )
; 
    create table Image_MetaTag (
        images_id bigint not null,
        imageTags_id bigint not null
    )
; 
    create table MESSAGE_PART (
        id bigint NOT NULL AUTO_INCREMENT,
        HTML_PART varchar(512),
        PART_ORDER varchar(40),
        messageHTML_id bigint,
        primary key (id)
    )
; 
    create table MessageHTML (
        id bigint NOT NULL AUTO_INCREMENT,
        APP_CODE varchar(128),
        CODE_LANG varchar(40),
        LINE_SIZE integer,
        MESSAGE_TYPE varchar(40),
        update_date timestamp,
        USER_LOGIN varchar(512),
        title varchar(255),
        album_id bigint,
        image_id bigint,
        primary key (id)
    )
; 
    create table MetaTag (
        id bigint NOT NULL AUTO_INCREMENT,
        tag varchar(255) not null,
        primary key (id)
    )
; 
    create table Shelf (
        id bigint NOT NULL AUTO_INCREMENT,
        created date,
        description varchar(1024),
        long_value blob,
        name varchar(255) not null,
        shared boolean not null,
        owner_id bigint,
        primary key (id)
    )
; 
    create table User (
        id bigint NOT NULL AUTO_INCREMENT,
        birthDate timestamp,
        email varchar(255) not null,
        firstName varchar(255) not null,
        hasAvatar boolean,
        login varchar(255) not null,
        passwordHash varchar(255),
        preDefined boolean not null,
        secondName varchar(255) not null,
        sex integer,
        primary key (id)
    )
; 
    alter table MetaTag 
        add constraint UK_me6c4ln7xyk9w70pl4hiri0ev  unique (tag)
; 
    alter table User 
        add constraint UK_587tdsv8u5cvheyo9i261xhry  unique (login)
; 
    alter table User 
        add constraint UK_e6gkqunxajvyxl5uctpl2vl2p  unique (email)
; 
    alter table Album 
        add constraint FK_maso23c869ch22pa5q0sh6tok 
        foreign key (coveringImage_id) 
        references Image
; 
    alter table Album 
        add constraint FK_j8gbvffe8l1pvbewibj4ft62m 
        foreign key (shelf_id) 
        references Shelf
; 
    alter table Comment 
        add constraint FK_tb58jmdmxt44jxr9u5kky03mw 
        foreign key (album_id) 
        references Album
; 
    alter table Comment 
        add constraint FK_j94pith5sd971k29j6ysxuk7 
        foreign key (author_id) 
        references User 
        on delete cascade
; 
    alter table Comment 
        add constraint FK_qh25b5js0rt4fqv9syhyjv781 
        foreign key (image_id) 
        references Image
; 
    alter table Image 
        add constraint FK_62s883scl1tf1fl7357bbtxy9 
        foreign key (album_id) 
        references Album
; 
    alter table Image 
        add constraint FK_5p7ujqrvcvv0e3cg6l2890k83 
        foreign key (user_id) 
        references User
; 
    alter table Image_MetaTag 
        add constraint FK_qw6hpgbun1gnhr0nimrao8gxc 
        foreign key (imageTags_id) 
        references MetaTag
; 
    alter table Image_MetaTag 
        add constraint FK_rb6l6aoyv7bss6hto77wqnvfg 
        foreign key (images_id) 
        references Image
; 
    alter table MESSAGE_PART 
        add constraint FK_d5a0buavnbql4bd067k29vonk 
        foreign key (messageHTML_id) 
        references MessageHTML
; 
    alter table MessageHTML 
        add constraint FK_dx199707m5ey9adjauuo7u62c 
        foreign key (album_id) 
        references Album
; 
    alter table MessageHTML 
        add constraint FK_7pu7r58hv9fuxe6o3mb4puf4e 
        foreign key (image_id) 
        references Image
; 
    alter table Shelf 
        add constraint FK_3unm5rbreqkjurmy4hpfl89yr 
        foreign key (owner_id) 
        references User