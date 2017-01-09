delimiter $$

CREATE TABLE web_item_tags (
    ID_OID bigint(20) NOT NULL,
    ID_EID bigint(20) NOT NULL,
    IDX int(11) NOT NULL,
    PRIMARY KEY (IDX , ID_OID),
    KEY WEB_ITEM_TAGS_N49 (ID_OID),
    KEY WEB_ITEM_TAGS_N50 (ID_EID),
    CONSTRAINT WEB_ITEM_TAGS_FK3 FOREIGN KEY (ID_OID)
        REFERENCES web_item (ID),
    CONSTRAINT WEB_ITEM_TAGS_FK4 FOREIGN KEY (ID_EID)
        REFERENCES tag (ID)
)  ENGINE=InnoDB DEFAULT CHARSET=utf8$$

