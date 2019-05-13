/*==============================================================*/
/* DBMS name:      PostgreSQL 9.x                               */
/* Created on:     11/5/2019 3:12:55 p. m.                      */
/*==============================================================*/


/*==============================================================*/
/* Table: BENEFICIARIO                                          */
/*==============================================================*/
create table BENEFICIARIO (
   IDBENEFICIARIO       INT4                 not null,
   IDCLIENTE            VARCHAR(1024)        null,
   NUMEROCUENTA         VARCHAR(1024)        not null,
   NOMBREBENEFICIARIO   VARCHAR(1024)        not null,
   TIPOCUENTABENEFICIARIO INT4                 not null,
   CORREOELECTRONICO    VARCHAR(1024)        not null,
   constraint PK_BENEFICIARIO primary key (IDBENEFICIARIO)
);

/*==============================================================*/
/* Index: BENEFICIARIO_PK                                       */
/*==============================================================*/
create unique index BENEFICIARIO_PK on BENEFICIARIO (
IDBENEFICIARIO
);

/*==============================================================*/
/* Index: RELATIONSHIP_2_FK                                     */
/*==============================================================*/
create  index RELATIONSHIP_2_FK on BENEFICIARIO (
IDCLIENTE
);

/*==============================================================*/
/* Table: CLIENTE                                               */
/*==============================================================*/
create table CLIENTE (
   IDCLIENTE            VARCHAR(1024)        not null,
   IDUSUARIO            INT4                 null,
   NOMBRESCLIENTE       VARCHAR(1024)        not null,
   APELLIDOSCLIENTE     VARCHAR(1024)        not null,
   constraint PK_CLIENTE primary key (IDCLIENTE)
);

/*==============================================================*/
/* Index: CLIENTE_PK                                            */
/*==============================================================*/
create unique index CLIENTE_PK on CLIENTE (
IDCLIENTE
);

/*==============================================================*/
/* Index: RELATIONSHIP_6_FK                                     */
/*==============================================================*/
create  index RELATIONSHIP_6_FK on CLIENTE (
IDUSUARIO
);

/*==============================================================*/
/* Table: PRODUCTO                                              */
/*==============================================================*/
create table PRODUCTO (
   ACCOUNTID            VARCHAR(1024)        not null,
   IDCLIENTE            VARCHAR(1024)        null,
   IDTIPOPRODUCTO       INT4                 null,
   STARTDATE            DATE                 not null,
   ENDDATE              DATE                 not null,
   LIMITE               DECIMAL(8,2)         DEFAULT 0,
   AVAILABLE            DECIMAL(8,2)         DEFAULT 0,
   TOTAL                DECIMAL(8,2)         DEFAULT 0,
   DEBIT                DECIMAL(8,2)         DEFAULT 0,
   INTERESTRATE         DECIMAL(8,2)         DEFAULT 0,
   INTERESTAMOUNT       DECIMAL(8,2)         DEFAULT 0,
   MONTHLYCUT           INT4                 DEFAULT 0,
   constraint PK_PRODUCTO primary key (ACCOUNTID)
);

/*==============================================================*/
/* Index: PRODUCTO_PK                                           */
/*==============================================================*/
create unique index PRODUCTO_PK on PRODUCTO (
ACCOUNTID
);

/*==============================================================*/
/* Index: RELATIONSHIP_3_FK                                     */
/*==============================================================*/
create  index RELATIONSHIP_3_FK on PRODUCTO (
IDCLIENTE
);

/*==============================================================*/
/* Index: RELATIONSHIP_4_FK                                     */
/*==============================================================*/
create  index RELATIONSHIP_4_FK on PRODUCTO (
IDTIPOPRODUCTO
);

/*==============================================================*/
/* Table: PRODUCTO_TRANSACCION                                  */
/*==============================================================*/
create table PRODUCTO_TRANSACCION (
   IDTRANSACCION        VARCHAR(1024)        not null,
   ACCOUNTID            VARCHAR(1024)        not null,
   constraint PK_PRODUCTO_TRANSACCION primary key (IDTRANSACCION, ACCOUNTID)
);

/*==============================================================*/
/* Index: RELATIONSHIP_5_PK                                     */
/*==============================================================*/
create unique index RELATIONSHIP_5_PK on PRODUCTO_TRANSACCION (
IDTRANSACCION,
ACCOUNTID
);

/*==============================================================*/
/* Index: RELATIONSHIP_7_FK                                     */
/*==============================================================*/
create  index RELATIONSHIP_7_FK on PRODUCTO_TRANSACCION (
ACCOUNTID
);

/*==============================================================*/
/* Index: RELATIONSHIP_5_FK                                     */
/*==============================================================*/
create  index RELATIONSHIP_5_FK on PRODUCTO_TRANSACCION (
IDTRANSACCION
);

/*==============================================================*/
/* Table: TIPOPRODUCTO                                          */
/*==============================================================*/
create table TIPOPRODUCTO (
   IDTIPOPRODUCTO       INT4                 not null,
   NOMBRETIPOPRODUCTO   VARCHAR(1024)        not null,
   constraint PK_TIPOPRODUCTO primary key (IDTIPOPRODUCTO)
);

/*==============================================================*/
/* Index: TIPOPRODUCTO_PK                                       */
/*==============================================================*/
create unique index TIPOPRODUCTO_PK on TIPOPRODUCTO (
IDTIPOPRODUCTO
);

/*==============================================================*/
/* Table: TRANSACCION                                           */
/*==============================================================*/
create table TRANSACCION (
   IDTRANSACCION        VARCHAR(1024)        not null,
   TRANSACTIONDATE      TIMESTAMP                 not null,
   DESCRIPTION          VARCHAR(1024)        not null,
   AMOUNT               DECIMAL(8,2)         not null,
   constraint PK_TRANSACCION primary key (IDTRANSACCION)
);

/*==============================================================*/
/* Index: TRANSACCION_PK                                        */
/*==============================================================*/
create unique index TRANSACCION_PK on TRANSACCION (
IDTRANSACCION
);

/*==============================================================*/
/* Table: USUARIO                                               */
/*==============================================================*/
create table USUARIO (
   IDUSUARIO            INT4                 not null,
   NOMBREUSUARIO        VARCHAR(1024)        not null,
   CONTRASENIA          VARCHAR(1024)        not null,
   INTENTOS             INT4                 null,
   constraint PK_USUARIO primary key (IDUSUARIO)
);

/*==============================================================*/
/* Index: USUARIO_PK                                            */
/*==============================================================*/
create unique index USUARIO_PK on USUARIO (
IDUSUARIO
);

alter table BENEFICIARIO
   add constraint FK_BENEFICI_RELATIONS_CLIENTE foreign key (IDCLIENTE)
      references CLIENTE (IDCLIENTE)
      on delete restrict on update restrict;

alter table CLIENTE
   add constraint FK_CLIENTE_RELATIONS_USUARIO foreign key (IDUSUARIO)
      references USUARIO (IDUSUARIO)
      on delete restrict on update restrict;

alter table PRODUCTO
   add constraint FK_PRODUCTO_RELATIONS_CLIENTE foreign key (IDCLIENTE)
      references CLIENTE (IDCLIENTE)
      on delete restrict on update restrict;

alter table PRODUCTO
   add constraint FK_PRODUCTO_RELATIONS_TIPOPROD foreign key (IDTIPOPRODUCTO)
      references TIPOPRODUCTO (IDTIPOPRODUCTO)
      on delete restrict on update restrict;

alter table PRODUCTO_TRANSACCION
   add constraint FK_PRODUCTO_RELATIONS_TRANSACC foreign key (IDTRANSACCION)
      references TRANSACCION (IDTRANSACCION)
      on delete restrict on update restrict;

alter table PRODUCTO_TRANSACCION
   add constraint FK_PRODUCTO_RELATIONS_PRODUCTO foreign key (ACCOUNTID)
      references PRODUCTO (ACCOUNTID)
      on delete restrict on update restrict;

