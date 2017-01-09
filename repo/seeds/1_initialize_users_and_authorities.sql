INSERT INTO _DATA_ENTITY  VALUES (0);

INSERT INTO AUTHORITY
(`ID`,
`AUTHORITY`)
VALUES
(
(SELECT LAST_INSERT_ID()),
'ROLE_USER'
);

INSERT INTO _DATA_ENTITY  VALUES (0);

INSERT INTO AUTHORITY
(`ID`,
`AUTHORITY`)
VALUES
(
(SELECT LAST_INSERT_ID()),
'ROLE_EDITOR'
);


ALTER TABLE USER DROP FOREIGN KEY `USER_FK2` ;
ALTER TABLE USER CHANGE COLUMN `CREATED_BY` `CREATED_BY` BIGINT(20) NULL  ,
  ADD CONSTRAINT `USER_FK2`
  FOREIGN KEY (`CREATED_BY` )
  REFERENCES USER (`ID` );


INSERT INTO _DATA_ENTITY  VALUES (0);

INSERT INTO USER
(`ID`,
`ACCOUNT_NON_EXPIRED`,
`ACCOUNT_NON_LOCKED`,
`CREATE_DATE`,
`CREATED_BY`,
`CREDENTIALS_NON_EXPIRED`,
`DELETED`,
`E_MAIL_ADDRESS`,
`ENABLED`,
`PASSWORD`,
`UPDATE_DATE`,
`UPDATED_BY`,
`USER_NAME`)
VALUES
(
(SELECT LAST_INSERT_ID()),
1,
1,
NOW(),
NULL,
1,
0,
'user@tsosm.com',
0,
'$2a$10$Nlz77vdB17CdTw0hd.qVqea2YZkulGhLIPRgCltPdUo2R256pR6BG',
NULL,
NULL,
'user'
);

INSERT INTO USER_AUTHORITIES
(`ID_OID`,
`ID_EID`)
VALUES
(
(SELECT LAST_INSERT_ID()),
(SELECT ID FROM AUTHORITY WHERE AUTHORITY = 'ROLE_USER')
);

INSERT INTO _DATA_ENTITY  VALUES (0);

INSERT INTO USER
(`ID`,
`ACCOUNT_NON_EXPIRED`,
`ACCOUNT_NON_LOCKED`,
`CREATE_DATE`,
`CREATED_BY`,
`CREDENTIALS_NON_EXPIRED`,
`DELETED`,
`E_MAIL_ADDRESS`,
`ENABLED`,
`PASSWORD`,
`UPDATE_DATE`,
`UPDATED_BY`,
`USER_NAME`)
VALUES
(
(SELECT LAST_INSERT_ID()),
0,
0,
NOW(),
NULL,
0,
0,
'anonymous@tsosm.com',
0,
'$2a$10$Nlz77vdB17CdTw0hd.qVqea2YZkulGhLIPRgCltPdUo2R256pR6BG',
NULL,
NULL,
'anonymousUser'
);

INSERT INTO USER_AUTHORITIES
(`ID_OID`,
`ID_EID`)
VALUES
(
(SELECT LAST_INSERT_ID()),
(SELECT ID FROM AUTHORITY WHERE AUTHORITY = 'ROLE_USER')
);

SELECT @id:= (SELECT ID FROM USER WHERE USER_NAME = 'user');
UPDATE USER SET CREATED_BY = (@id);

ALTER TABLE USER DROP FOREIGN KEY `USER_FK2` ;
ALTER TABLE USER CHANGE COLUMN `CREATED_BY` `CREATED_BY` BIGINT(20) NOT NULL  ,
  ADD CONSTRAINT `USER_FK2`
  FOREIGN KEY (`CREATED_BY` )
  REFERENCES USER (`ID` );
