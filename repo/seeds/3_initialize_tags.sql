INSERT INTO _DATA_ENTITY VALUES (0);

INSERT INTO TAG
(`ID`,
`CREATE_DATE`,
`CREATED_BY`,
`DELETED`,
`DESCRIPTION`,
`ICON_PATH`,
`IS_PRIMARY`,
`NAME`,
`PARENT_TAG_ID`,
`POSITION`)
VALUES
(
(SELECT MAX(id) FROM _DATA_ENTITY),
NOW(),
(SELECT ID FROM USER WHERE USER_NAME = 'user'),
0,
'Art tag',
'pics/tags/primary/art.png',
1,
'Art',
NULL,
100);

SELECT @parentid:= (SELECT MAX(id) FROM _DATA_ENTITY);

INSERT INTO _DATA_ENTITY VALUES (0);

INSERT INTO TAG
(`ID`,
`CREATE_DATE`,
`CREATED_BY`,
`DELETED`,
`DESCRIPTION`,
`ICON_PATH`,
`IS_PRIMARY`,
`NAME`,
`PARENT_TAG_ID`,
`POSITION`)
VALUES
(
(SELECT MAX(id) FROM _DATA_ENTITY),
NOW(),
(SELECT ID FROM USER WHERE USER_NAME = 'user'),
0,
'Architecture tag',
'pics/tags/secondary/architecture.png',
0,
'Architecture',
@parentid,
100);

INSERT INTO _DATA_ENTITY VALUES (0);

INSERT INTO TAG
(`ID`,
`CREATE_DATE`,
`CREATED_BY`,
`DELETED`,
`DESCRIPTION`,
`ICON_PATH`,
`IS_PRIMARY`,
`NAME`,
`PARENT_TAG_ID`,
`POSITION`)
VALUES
(
(SELECT MAX(id) FROM _DATA_ENTITY),
NOW(),
(SELECT ID FROM USER WHERE USER_NAME = 'user'),
0,
'Design tag',
'pics/tags/secondary/design.png',
0,
'Design',
@parentid,
200);

INSERT INTO _DATA_ENTITY VALUES (0);

INSERT INTO TAG
(`ID`,
`CREATE_DATE`,
`CREATED_BY`,
`DELETED`,
`DESCRIPTION`,
`ICON_PATH`,
`IS_PRIMARY`,
`NAME`,
`PARENT_TAG_ID`,
`POSITION`)
VALUES
(
(SELECT MAX(id) FROM _DATA_ENTITY),
NOW(),
(SELECT ID FROM USER WHERE USER_NAME = 'user'),
0,
'Painting tag',
'pics/tags/secondary/painting.png',
0,
'Painting',
@parentid,
300);

INSERT INTO _DATA_ENTITY VALUES (0);

INSERT INTO TAG
(`ID`,
`CREATE_DATE`,
`CREATED_BY`,
`DELETED`,
`DESCRIPTION`,
`ICON_PATH`,
`IS_PRIMARY`,
`NAME`,
`PARENT_TAG_ID`,
`POSITION`)
VALUES
(
(SELECT MAX(id) FROM _DATA_ENTITY),
NOW(),
(SELECT ID FROM USER WHERE USER_NAME = 'user'),
0,
'Photography tag',
'pics/tags/secondary/photography.png',
0,
'Photography',
@parentid,
400);

INSERT INTO _DATA_ENTITY VALUES (0);

INSERT INTO TAG
(`ID`,
`CREATE_DATE`,
`CREATED_BY`,
`DELETED`,
`DESCRIPTION`,
`ICON_PATH`,
`IS_PRIMARY`,
`NAME`,
`PARENT_TAG_ID`,
`POSITION`)
VALUES
(
(SELECT MAX(id) FROM _DATA_ENTITY),
NOW(),
(SELECT ID FROM USER WHERE USER_NAME = 'user'),
0,
'Theater tag',
'pics/tags/secondary/theater.png',
0,
'Theater',
@parentid,
500);

INSERT INTO _DATA_ENTITY VALUES (0);

INSERT INTO TAG
(`ID`,
`CREATE_DATE`,
`CREATED_BY`,
`DELETED`,
`DESCRIPTION`,
`ICON_PATH`,
`IS_PRIMARY`,
`NAME`,
`PARENT_TAG_ID`,
`POSITION`)
VALUES
(
(SELECT MAX(id) FROM _DATA_ENTITY),
NOW(),
(SELECT ID FROM USER WHERE USER_NAME = 'user'),
0,
'Writing tag',
'pics/tags/secondary/writing.png',
0,
'Writing',
@parentid,
600);

INSERT INTO _DATA_ENTITY VALUES (0);

INSERT INTO TAG
(`ID`,
`CREATE_DATE`,
`CREATED_BY`,
`DELETED`,
`DESCRIPTION`,
`ICON_PATH`,
`IS_PRIMARY`,
`NAME`,
`PARENT_TAG_ID`,
`POSITION`)
VALUES
(
(SELECT MAX(id) FROM _DATA_ENTITY),
NOW(),
(SELECT ID FROM USER WHERE USER_NAME = 'user'),
0,
'Beauties tag',
'pics/tags/primary/beauties.png',
1,
'Beauties',
NULL,
200);

SELECT @parentid:= (SELECT MAX(id) FROM _DATA_ENTITY);

INSERT INTO _DATA_ENTITY VALUES (0);

INSERT INTO TAG
(`ID`,
`CREATE_DATE`,
`CREATED_BY`,
`DELETED`,
`DESCRIPTION`,
`ICON_PATH`,
`IS_PRIMARY`,
`NAME`,
`PARENT_TAG_ID`,
`POSITION`)
VALUES
(
(SELECT MAX(id) FROM _DATA_ENTITY),
NOW(),
(SELECT ID FROM USER WHERE USER_NAME = 'user'),
0,
'Short hair tag',
'pics/tags/secondary/shorthair.png',
0,
'Short hair',
@parentid,
100);

INSERT INTO _DATA_ENTITY VALUES (0);

INSERT INTO TAG
(`ID`,
`CREATE_DATE`,
`CREATED_BY`,
`DELETED`,
`DESCRIPTION`,
`ICON_PATH`,
`IS_PRIMARY`,
`NAME`,
`PARENT_TAG_ID`,
`POSITION`)
VALUES
(
(SELECT MAX(id) FROM _DATA_ENTITY),
NOW(),
(SELECT ID FROM USER WHERE USER_NAME = 'user'),
0,
'Medium hair tag',
'pics/tags/secondary/mediumhair.png',
0,
'Medium hair',
@parentid,
200);

INSERT INTO _DATA_ENTITY VALUES (0);

INSERT INTO TAG
(`ID`,
`CREATE_DATE`,
`CREATED_BY`,
`DELETED`,
`DESCRIPTION`,
`ICON_PATH`,
`IS_PRIMARY`,
`NAME`,
`PARENT_TAG_ID`,
`POSITION`)
VALUES
(
(SELECT MAX(id) FROM _DATA_ENTITY),
NOW(),
(SELECT ID FROM USER WHERE USER_NAME = 'user'),
0,
'Long hair tag',
'pics/tags/secondary/longhair.png',
0,
'Long hair',
@parentid,
300);

INSERT INTO _DATA_ENTITY VALUES (0);

INSERT INTO TAG
(`ID`,
`CREATE_DATE`,
`CREATED_BY`,
`DELETED`,
`DESCRIPTION`,
`ICON_PATH`,
`IS_PRIMARY`,
`NAME`,
`PARENT_TAG_ID`,
`POSITION`)
VALUES
(
(SELECT MAX(id) FROM _DATA_ENTITY),
NOW(),
(SELECT ID FROM USER WHERE USER_NAME = 'user'),
0,
'Blonde tag',
'pics/tags/secondary/blonde.png',
0,
'Blonde',
@parentid,
400);

INSERT INTO _DATA_ENTITY VALUES (0);

INSERT INTO TAG
(`ID`,
`CREATE_DATE`,
`CREATED_BY`,
`DELETED`,
`DESCRIPTION`,
`ICON_PATH`,
`IS_PRIMARY`,
`NAME`,
`PARENT_TAG_ID`,
`POSITION`)
VALUES
(
(SELECT MAX(id) FROM _DATA_ENTITY),
NOW(),
(SELECT ID FROM USER WHERE USER_NAME = 'user'),
0,
'Brunette tag',
'pics/tags/secondary/brunette.png',
0,
'Brunette',
@parentid,
500);

INSERT INTO _DATA_ENTITY VALUES (0);

INSERT INTO TAG
(`ID`,
`CREATE_DATE`,
`CREATED_BY`,
`DELETED`,
`DESCRIPTION`,
`ICON_PATH`,
`IS_PRIMARY`,
`NAME`,
`PARENT_TAG_ID`,
`POSITION`)
VALUES
(
(SELECT MAX(id) FROM _DATA_ENTITY),
NOW(),
(SELECT ID FROM USER WHERE USER_NAME = 'user'),
0,
'Redhead tag',
'pics/tags/secondary/redhead.png',
0,
'Redhead',
@parentid,
600);

INSERT INTO _DATA_ENTITY VALUES (0);

INSERT INTO TAG
(`ID`,
`CREATE_DATE`,
`CREATED_BY`,
`DELETED`,
`DESCRIPTION`,
`ICON_PATH`,
`IS_PRIMARY`,
`NAME`,
`PARENT_TAG_ID`,
`POSITION`)
VALUES
(
(SELECT MAX(id) FROM _DATA_ENTITY),
NOW(),
(SELECT ID FROM USER WHERE USER_NAME = 'user'),
0,
'Computers tag',
'pics/tags/primary/computers.png',
1,
'Computers',
NULL,
300);

SELECT @parentid:= (SELECT MAX(id) FROM _DATA_ENTITY);

INSERT INTO _DATA_ENTITY VALUES (0);

INSERT INTO TAG
(`ID`,
`CREATE_DATE`,
`CREATED_BY`,
`DELETED`,
`DESCRIPTION`,
`ICON_PATH`,
`IS_PRIMARY`,
`NAME`,
`PARENT_TAG_ID`,
`POSITION`)
VALUES
(
(SELECT MAX(id) FROM _DATA_ENTITY),
NOW(),
(SELECT ID FROM USER WHERE USER_NAME = 'user'),
0,
'Games tag',
'pics/tags/secondary/games.png',
0,
'Games',
@parentid,
100);

INSERT INTO _DATA_ENTITY VALUES (0);

INSERT INTO TAG
(`ID`,
`CREATE_DATE`,
`CREATED_BY`,
`DELETED`,
`DESCRIPTION`,
`ICON_PATH`,
`IS_PRIMARY`,
`NAME`,
`PARENT_TAG_ID`,
`POSITION`)
VALUES
(
(SELECT MAX(id) FROM _DATA_ENTITY),
NOW(),
(SELECT ID FROM USER WHERE USER_NAME = 'user'),
0,
'Hardware tag',
'pics/tags/secondary/hardware.png',
0,
'Hardware',
@parentid,
200);

INSERT INTO _DATA_ENTITY VALUES (0);

INSERT INTO TAG
(`ID`,
`CREATE_DATE`,
`CREATED_BY`,
`DELETED`,
`DESCRIPTION`,
`ICON_PATH`,
`IS_PRIMARY`,
`NAME`,
`PARENT_TAG_ID`,
`POSITION`)
VALUES
(
(SELECT MAX(id) FROM _DATA_ENTITY),
NOW(),
(SELECT ID FROM USER WHERE USER_NAME = 'user'),
0,
'It and infrastructure tag',
'pics/tags/secondary/itandinfrastructure.png',
0,
'IT and Infrastructure',
@parentid,
300);

INSERT INTO _DATA_ENTITY VALUES (0);

INSERT INTO TAG
(`ID`,
`CREATE_DATE`,
`CREATED_BY`,
`DELETED`,
`DESCRIPTION`,
`ICON_PATH`,
`IS_PRIMARY`,
`NAME`,
`PARENT_TAG_ID`,
`POSITION`)
VALUES
(
(SELECT MAX(id) FROM _DATA_ENTITY),
NOW(),
(SELECT ID FROM USER WHERE USER_NAME = 'user'),
0,
'Mobile tag',
'pics/tags/secondary/mobile.png',
0,
'Mobile',
@parentid,
400);

INSERT INTO _DATA_ENTITY VALUES (0);

INSERT INTO TAG
(`ID`,
`CREATE_DATE`,
`CREATED_BY`,
`DELETED`,
`DESCRIPTION`,
`ICON_PATH`,
`IS_PRIMARY`,
`NAME`,
`PARENT_TAG_ID`,
`POSITION`)
VALUES
(
(SELECT MAX(id) FROM _DATA_ENTITY),
NOW(),
(SELECT ID FROM USER WHERE USER_NAME = 'user'),
0,
'Programming tag',
'pics/tags/secondary/programming.png',
0,
'Programming',
@parentid,
500);

INSERT INTO _DATA_ENTITY VALUES (0);

INSERT INTO TAG
(`ID`,
`CREATE_DATE`,
`CREATED_BY`,
`DELETED`,
`DESCRIPTION`,
`ICON_PATH`,
`IS_PRIMARY`,
`NAME`,
`PARENT_TAG_ID`,
`POSITION`)
VALUES
(
(SELECT MAX(id) FROM _DATA_ENTITY),
NOW(),
(SELECT ID FROM USER WHERE USER_NAME = 'user'),
0,
'Security tag',
'pics/tags/secondary/security.png',
0,
'Security',
@parentid,
600);

INSERT INTO _DATA_ENTITY VALUES (0);

INSERT INTO TAG
(`ID`,
`CREATE_DATE`,
`CREATED_BY`,
`DELETED`,
`DESCRIPTION`,
`ICON_PATH`,
`IS_PRIMARY`,
`NAME`,
`PARENT_TAG_ID`,
`POSITION`)
VALUES
(
(SELECT MAX(id) FROM _DATA_ENTITY),
NOW(),
(SELECT ID FROM USER WHERE USER_NAME = 'user'),
0,
'Software tag',
'pics/tags/secondary/software.png',
0,
'Software',
@parentid,
700);

INSERT INTO _DATA_ENTITY VALUES (0);

INSERT INTO TAG
(`ID`,
`CREATE_DATE`,
`CREATED_BY`,
`DELETED`,
`DESCRIPTION`,
`ICON_PATH`,
`IS_PRIMARY`,
`NAME`,
`PARENT_TAG_ID`,
`POSITION`)
VALUES
(
(SELECT MAX(id) FROM _DATA_ENTITY),
NOW(),
(SELECT ID FROM USER WHERE USER_NAME = 'user'),
0,
'Economy tag',
'pics/tags/primary/economy.png',
1,
'Economy',
NULL,
400);

SELECT @parentid:= (SELECT MAX(id) FROM _DATA_ENTITY);

INSERT INTO _DATA_ENTITY VALUES (0);

INSERT INTO TAG
(`ID`,
`CREATE_DATE`,
`CREATED_BY`,
`DELETED`,
`DESCRIPTION`,
`ICON_PATH`,
`IS_PRIMARY`,
`NAME`,
`PARENT_TAG_ID`,
`POSITION`)
VALUES
(
(SELECT MAX(id) FROM _DATA_ENTITY),
NOW(),
(SELECT ID FROM USER WHERE USER_NAME = 'user'),
0,
'Career tag',
'pics/tags/secondary/career.png',
0,
'Career',
@parentid,
100);

INSERT INTO _DATA_ENTITY VALUES (0);

INSERT INTO TAG
(`ID`,
`CREATE_DATE`,
`CREATED_BY`,
`DELETED`,
`DESCRIPTION`,
`ICON_PATH`,
`IS_PRIMARY`,
`NAME`,
`PARENT_TAG_ID`,
`POSITION`)
VALUES
(
(SELECT MAX(id) FROM _DATA_ENTITY),
NOW(),
(SELECT ID FROM USER WHERE USER_NAME = 'user'),
0,
'Entrepreneurship tag',
'pics/tags/secondary/entrepreneurship.png',
0,
'Entrepreneurship',
@parentid,
200);

INSERT INTO _DATA_ENTITY VALUES (0);

INSERT INTO TAG
(`ID`,
`CREATE_DATE`,
`CREATED_BY`,
`DELETED`,
`DESCRIPTION`,
`ICON_PATH`,
`IS_PRIMARY`,
`NAME`,
`PARENT_TAG_ID`,
`POSITION`)
VALUES
(
(SELECT MAX(id) FROM _DATA_ENTITY),
NOW(),
(SELECT ID FROM USER WHERE USER_NAME = 'user'),
0,
'Finance tag',
'pics/tags/secondary/finance.png',
0,
'Finance',
@parentid,
300);

INSERT INTO _DATA_ENTITY VALUES (0);

INSERT INTO TAG
(`ID`,
`CREATE_DATE`,
`CREATED_BY`,
`DELETED`,
`DESCRIPTION`,
`ICON_PATH`,
`IS_PRIMARY`,
`NAME`,
`PARENT_TAG_ID`,
`POSITION`)
VALUES
(
(SELECT MAX(id) FROM _DATA_ENTITY),
NOW(),
(SELECT ID FROM USER WHERE USER_NAME = 'user'),
0,
'Global tag',
'pics/tags/secondary/global.png',
0,
'Global',
@parentid,
400);

INSERT INTO _DATA_ENTITY VALUES (0);

INSERT INTO TAG
(`ID`,
`CREATE_DATE`,
`CREATED_BY`,
`DELETED`,
`DESCRIPTION`,
`ICON_PATH`,
`IS_PRIMARY`,
`NAME`,
`PARENT_TAG_ID`,
`POSITION`)
VALUES
(
(SELECT MAX(id) FROM _DATA_ENTITY),
NOW(),
(SELECT ID FROM USER WHERE USER_NAME = 'user'),
0,
'Management tag',
'pics/tags/secondary/management.png',
0,
'Management',
@parentid,
500);

INSERT INTO _DATA_ENTITY VALUES (0);

INSERT INTO TAG
(`ID`,
`CREATE_DATE`,
`CREATED_BY`,
`DELETED`,
`DESCRIPTION`,
`ICON_PATH`,
`IS_PRIMARY`,
`NAME`,
`PARENT_TAG_ID`,
`POSITION`)
VALUES
(
(SELECT MAX(id) FROM _DATA_ENTITY),
NOW(),
(SELECT ID FROM USER WHERE USER_NAME = 'user'),
0,
'Personal finance tag',
'pics/tags/secondary/personalfinance.png',
0,
'Personal finance',
@parentid,
600);

INSERT INTO _DATA_ENTITY VALUES (0);

INSERT INTO TAG
(`ID`,
`CREATE_DATE`,
`CREATED_BY`,
`DELETED`,
`DESCRIPTION`,
`ICON_PATH`,
`IS_PRIMARY`,
`NAME`,
`PARENT_TAG_ID`,
`POSITION`)
VALUES
(
(SELECT MAX(id) FROM _DATA_ENTITY),
NOW(),
(SELECT ID FROM USER WHERE USER_NAME = 'user'),
0,
'PR and Marketing tag',
'pics/tags/secondary/prandmarketing.png',
0,
'PR and Marketing',
@parentid,
700);

INSERT INTO _DATA_ENTITY VALUES (0);

INSERT INTO TAG
(`ID`,
`CREATE_DATE`,
`CREATED_BY`,
`DELETED`,
`DESCRIPTION`,
`ICON_PATH`,
`IS_PRIMARY`,
`NAME`,
`PARENT_TAG_ID`,
`POSITION`)
VALUES
(
(SELECT MAX(id) FROM _DATA_ENTITY),
NOW(),
(SELECT ID FROM USER WHERE USER_NAME = 'user'),
0,
'Fashion tag',
'pics/tags/primary/fashion.png',
1,
'Fashion',
NULL,
500);

SELECT @parentid:= (SELECT MAX(id) FROM _DATA_ENTITY);

INSERT INTO _DATA_ENTITY VALUES (0);

INSERT INTO TAG
(`ID`,
`CREATE_DATE`,
`CREATED_BY`,
`DELETED`,
`DESCRIPTION`,
`ICON_PATH`,
`IS_PRIMARY`,
`NAME`,
`PARENT_TAG_ID`,
`POSITION`)
VALUES
(
(SELECT MAX(id) FROM _DATA_ENTITY),
NOW(),
(SELECT ID FROM USER WHERE USER_NAME = 'user'),
0,
'Accessories tag',
'pics/tags/secondary/accessories.png',
0,
'Accessories',
@parentid,
100);

INSERT INTO _DATA_ENTITY VALUES (0);

INSERT INTO TAG
(`ID`,
`CREATE_DATE`,
`CREATED_BY`,
`DELETED`,
`DESCRIPTION`,
`ICON_PATH`,
`IS_PRIMARY`,
`NAME`,
`PARENT_TAG_ID`,
`POSITION`)
VALUES
(
(SELECT MAX(id) FROM _DATA_ENTITY),
NOW(),
(SELECT ID FROM USER WHERE USER_NAME = 'user'),
0,
'Clothing tag',
'pics/tags/secondary/clothing.png',
0,
'Clothing',
@parentid,
200);

INSERT INTO _DATA_ENTITY VALUES (0);

INSERT INTO TAG
(`ID`,
`CREATE_DATE`,
`CREATED_BY`,
`DELETED`,
`DESCRIPTION`,
`ICON_PATH`,
`IS_PRIMARY`,
`NAME`,
`PARENT_TAG_ID`,
`POSITION`)
VALUES
(
(SELECT MAX(id) FROM _DATA_ENTITY),
NOW(),
(SELECT ID FROM USER WHERE USER_NAME = 'user'),
0,
'Hairstyle tag',
'pics/tags/secondary/hairstyle.png',
0,
'Hairstyle',
@parentid,
300);

INSERT INTO _DATA_ENTITY VALUES (0);

INSERT INTO TAG
(`ID`,
`CREATE_DATE`,
`CREATED_BY`,
`DELETED`,
`DESCRIPTION`,
`ICON_PATH`,
`IS_PRIMARY`,
`NAME`,
`PARENT_TAG_ID`,
`POSITION`)
VALUES
(
(SELECT MAX(id) FROM _DATA_ENTITY),
NOW(),
(SELECT ID FROM USER WHERE USER_NAME = 'user'),
0,
'Shoes tag',
'pics/tags/secondary/shoes.png',
0,
'Shoes',
@parentid,
400);

INSERT INTO _DATA_ENTITY VALUES (0);

INSERT INTO TAG
(`ID`,
`CREATE_DATE`,
`CREATED_BY`,
`DELETED`,
`DESCRIPTION`,
`ICON_PATH`,
`IS_PRIMARY`,
`NAME`,
`PARENT_TAG_ID`,
`POSITION`)
VALUES
(
(SELECT MAX(id) FROM _DATA_ENTITY),
NOW(),
(SELECT ID FROM USER WHERE USER_NAME = 'user'),
0,
'Female tag',
'pics/tags/secondary/female.png',
0,
'Female',
@parentid,
500);

INSERT INTO _DATA_ENTITY VALUES (0);

INSERT INTO TAG
(`ID`,
`CREATE_DATE`,
`CREATED_BY`,
`DELETED`,
`DESCRIPTION`,
`ICON_PATH`,
`IS_PRIMARY`,
`NAME`,
`PARENT_TAG_ID`,
`POSITION`)
VALUES
(
(SELECT MAX(id) FROM _DATA_ENTITY),
NOW(),
(SELECT ID FROM USER WHERE USER_NAME = 'user'),
0,
'Male tag',
'pics/tags/secondary/male.png',
0,
'Male',
@parentid,
600);

INSERT INTO _DATA_ENTITY VALUES (0);

INSERT INTO TAG
(`ID`,
`CREATE_DATE`,
`CREATED_BY`,
`DELETED`,
`DESCRIPTION`,
`ICON_PATH`,
`IS_PRIMARY`,
`NAME`,
`PARENT_TAG_ID`,
`POSITION`)
VALUES
(
(SELECT MAX(id) FROM _DATA_ENTITY),
NOW(),
(SELECT ID FROM USER WHERE USER_NAME = 'user'),
0,
'Humor tag',
'pics/tags/primary/humor.png',
1,
'Humor',
NULL,
600);

SELECT @parentid:= (SELECT MAX(id) FROM _DATA_ENTITY);

INSERT INTO _DATA_ENTITY VALUES (0);

INSERT INTO TAG
(`ID`,
`CREATE_DATE`,
`CREATED_BY`,
`DELETED`,
`DESCRIPTION`,
`ICON_PATH`,
`IS_PRIMARY`,
`NAME`,
`PARENT_TAG_ID`,
`POSITION`)
VALUES
(
(SELECT MAX(id) FROM _DATA_ENTITY),
NOW(),
(SELECT ID FROM USER WHERE USER_NAME = 'user'),
0,
'Comics tag',
'pics/tags/secondary/comics.png',
0,
'Comics',
@parentid,
100);

INSERT INTO _DATA_ENTITY VALUES (0);

INSERT INTO TAG
(`ID`,
`CREATE_DATE`,
`CREATED_BY`,
`DELETED`,
`DESCRIPTION`,
`ICON_PATH`,
`IS_PRIMARY`,
`NAME`,
`PARENT_TAG_ID`,
`POSITION`)
VALUES
(
(SELECT MAX(id) FROM _DATA_ENTITY),
NOW(),
(SELECT ID FROM USER WHERE USER_NAME = 'user'),
0,
'Memes tag',
'pics/tags/secondary/memes.png',
0,
'Memes',
@parentid,
200);

INSERT INTO _DATA_ENTITY VALUES (0);

INSERT INTO TAG
(`ID`,
`CREATE_DATE`,
`CREATED_BY`,
`DELETED`,
`DESCRIPTION`,
`ICON_PATH`,
`IS_PRIMARY`,
`NAME`,
`PARENT_TAG_ID`,
`POSITION`)
VALUES
(
(SELECT MAX(id) FROM _DATA_ENTITY),
NOW(),
(SELECT ID FROM USER WHERE USER_NAME = 'user'),
0,
'Pets tag',
'pics/tags/secondary/pets.png',
0,
'Pets',
@parentid,
300);

INSERT INTO _DATA_ENTITY VALUES (0);

INSERT INTO TAG
(`ID`,
`CREATE_DATE`,
`CREATED_BY`,
`DELETED`,
`DESCRIPTION`,
`ICON_PATH`,
`IS_PRIMARY`,
`NAME`,
`PARENT_TAG_ID`,
`POSITION`)
VALUES
(
(SELECT MAX(id) FROM _DATA_ENTITY),
NOW(),
(SELECT ID FROM USER WHERE USER_NAME = 'user'),
0,
'Life tag',
'pics/tags/primary/life.png',
1,
'Life',
NULL,
700);

SELECT @parentid:= (SELECT MAX(id) FROM _DATA_ENTITY);

INSERT INTO _DATA_ENTITY VALUES (0);

INSERT INTO TAG
(`ID`,
`CREATE_DATE`,
`CREATED_BY`,
`DELETED`,
`DESCRIPTION`,
`ICON_PATH`,
`IS_PRIMARY`,
`NAME`,
`PARENT_TAG_ID`,
`POSITION`)
VALUES
(
(SELECT MAX(id) FROM _DATA_ENTITY),
NOW(),
(SELECT ID FROM USER WHERE USER_NAME = 'user'),
0,
'Food and drinks tag',
'pics/tags/secondary/foodanddrinks.png',
0,
'Food and Drinks',
@parentid,
100);

INSERT INTO _DATA_ENTITY VALUES (0);

INSERT INTO TAG
(`ID`,
`CREATE_DATE`,
`CREATED_BY`,
`DELETED`,
`DESCRIPTION`,
`ICON_PATH`,
`IS_PRIMARY`,
`NAME`,
`PARENT_TAG_ID`,
`POSITION`)
VALUES
(
(SELECT MAX(id) FROM _DATA_ENTITY),
NOW(),
(SELECT ID FROM USER WHERE USER_NAME = 'user'),
0,
'Health tag',
'pics/tags/secondary/health.png',
0,
'Health',
@parentid,
200);

INSERT INTO _DATA_ENTITY VALUES (0);

INSERT INTO TAG
(`ID`,
`CREATE_DATE`,
`CREATED_BY`,
`DELETED`,
`DESCRIPTION`,
`ICON_PATH`,
`IS_PRIMARY`,
`NAME`,
`PARENT_TAG_ID`,
`POSITION`)
VALUES
(
(SELECT MAX(id) FROM _DATA_ENTITY),
NOW(),
(SELECT ID FROM USER WHERE USER_NAME = 'user'),
0,
'Home tag',
'pics/tags/secondary/home.png',
0,
'Home',
@parentid,
300);

INSERT INTO _DATA_ENTITY VALUES (0);

INSERT INTO TAG
(`ID`,
`CREATE_DATE`,
`CREATED_BY`,
`DELETED`,
`DESCRIPTION`,
`ICON_PATH`,
`IS_PRIMARY`,
`NAME`,
`PARENT_TAG_ID`,
`POSITION`)
VALUES
(
(SELECT MAX(id) FROM _DATA_ENTITY),
NOW(),
(SELECT ID FROM USER WHERE USER_NAME = 'user'),
0,
'Travel tag',
'pics/tags/secondary/travel.png',
0,
'Travel',
@parentid,
400);

INSERT INTO _DATA_ENTITY VALUES (0);

INSERT INTO TAG
(`ID`,
`CREATE_DATE`,
`CREATED_BY`,
`DELETED`,
`DESCRIPTION`,
`ICON_PATH`,
`IS_PRIMARY`,
`NAME`,
`PARENT_TAG_ID`,
`POSITION`)
VALUES
(
(SELECT MAX(id) FROM _DATA_ENTITY),
NOW(),
(SELECT ID FROM USER WHERE USER_NAME = 'user'),
0,
'Moods and sentiments tag',
'pics/tags/primary/moodsandsentiments.png',
1,
'Moods and Sentiments',
NULL,
800);

SELECT @parentid:= (SELECT MAX(id) FROM _DATA_ENTITY);

INSERT INTO _DATA_ENTITY VALUES (0);

INSERT INTO TAG
(`ID`,
`CREATE_DATE`,
`CREATED_BY`,
`DELETED`,
`DESCRIPTION`,
`ICON_PATH`,
`IS_PRIMARY`,
`NAME`,
`PARENT_TAG_ID`,
`POSITION`)
VALUES
(
(SELECT MAX(id) FROM _DATA_ENTITY),
NOW(),
(SELECT ID FROM USER WHERE USER_NAME = 'user'),
0,
'Aletenative tag',
'pics/tags/secondary/alternative.png',
0,
'Alternative',
@parentid,
100);

INSERT INTO _DATA_ENTITY VALUES (0);

INSERT INTO TAG
(`ID`,
`CREATE_DATE`,
`CREATED_BY`,
`DELETED`,
`DESCRIPTION`,
`ICON_PATH`,
`IS_PRIMARY`,
`NAME`,
`PARENT_TAG_ID`,
`POSITION`)
VALUES
(
(SELECT MAX(id) FROM _DATA_ENTITY),
NOW(),
(SELECT ID FROM USER WHERE USER_NAME = 'user'),
0,
'Cutting egde tag',
'pics/tags/secondary/cuttingedge.png',
0,
'Cutting Edge',
@parentid,
200);

INSERT INTO _DATA_ENTITY VALUES (0);

INSERT INTO TAG
(`ID`,
`CREATE_DATE`,
`CREATED_BY`,
`DELETED`,
`DESCRIPTION`,
`ICON_PATH`,
`IS_PRIMARY`,
`NAME`,
`PARENT_TAG_ID`,
`POSITION`)
VALUES
(
(SELECT MAX(id) FROM _DATA_ENTITY),
NOW(),
(SELECT ID FROM USER WHERE USER_NAME = 'user'),
0,
'Funny tag',
'pics/tags/secondary/funny.png',
0,
'Funny',
@parentid,
300);

INSERT INTO _DATA_ENTITY VALUES (0);

INSERT INTO TAG
(`ID`,
`CREATE_DATE`,
`CREATED_BY`,
`DELETED`,
`DESCRIPTION`,
`ICON_PATH`,
`IS_PRIMARY`,
`NAME`,
`PARENT_TAG_ID`,
`POSITION`)
VALUES
(
(SELECT MAX(id) FROM _DATA_ENTITY),
NOW(),
(SELECT ID FROM USER WHERE USER_NAME = 'user'),
0,
'Insightful tag',
'pics/tags/secondary/insightful.png',
0,
'Insightful',
@parentid,
400);

INSERT INTO _DATA_ENTITY VALUES (0);

INSERT INTO TAG
(`ID`,
`CREATE_DATE`,
`CREATED_BY`,
`DELETED`,
`DESCRIPTION`,
`ICON_PATH`,
`IS_PRIMARY`,
`NAME`,
`PARENT_TAG_ID`,
`POSITION`)
VALUES
(
(SELECT MAX(id) FROM _DATA_ENTITY),
NOW(),
(SELECT ID FROM USER WHERE USER_NAME = 'user'),
0,
'Inspiring tag',
'pics/tags/secondary/inspiring.png',
0,
'Inspiring',
@parentid,
500);

INSERT INTO _DATA_ENTITY VALUES (0);

INSERT INTO TAG
(`ID`,
`CREATE_DATE`,
`CREATED_BY`,
`DELETED`,
`DESCRIPTION`,
`ICON_PATH`,
`IS_PRIMARY`,
`NAME`,
`PARENT_TAG_ID`,
`POSITION`)
VALUES
(
(SELECT MAX(id) FROM _DATA_ENTITY),
NOW(),
(SELECT ID FROM USER WHERE USER_NAME = 'user'),
0,
'Relaxing tag',
'pics/tags/secondary/relaxing.png',
0,
'Relaxing',
@parentid,
600);

INSERT INTO _DATA_ENTITY VALUES (0);

INSERT INTO TAG
(`ID`,
`CREATE_DATE`,
`CREATED_BY`,
`DELETED`,
`DESCRIPTION`,
`ICON_PATH`,
`IS_PRIMARY`,
`NAME`,
`PARENT_TAG_ID`,
`POSITION`)
VALUES
(
(SELECT MAX(id) FROM _DATA_ENTITY),
NOW(),
(SELECT ID FROM USER WHERE USER_NAME = 'user'),
0,
'Surprising tag',
'pics/tags/secondary/surprising.png',
0,
'Surprising',
@parentid,
700);

INSERT INTO _DATA_ENTITY VALUES (0);

INSERT INTO TAG
(`ID`,
`CREATE_DATE`,
`CREATED_BY`,
`DELETED`,
`DESCRIPTION`,
`ICON_PATH`,
`IS_PRIMARY`,
`NAME`,
`PARENT_TAG_ID`,
`POSITION`)
VALUES
(
(SELECT MAX(id) FROM _DATA_ENTITY),
NOW(),
(SELECT ID FROM USER WHERE USER_NAME = 'user'),
0,
'Moving pictures tag',
'pics/tags/primary/movingpictures.png',
1,
'Moving Pictures',
NULL,
900);

SELECT @parentid:= (SELECT MAX(id) FROM _DATA_ENTITY);

INSERT INTO _DATA_ENTITY VALUES (0);

INSERT INTO TAG
(`ID`,
`CREATE_DATE`,
`CREATED_BY`,
`DELETED`,
`DESCRIPTION`,
`ICON_PATH`,
`IS_PRIMARY`,
`NAME`,
`PARENT_TAG_ID`,
`POSITION`)
VALUES
(
(SELECT MAX(id) FROM _DATA_ENTITY),
NOW(),
(SELECT ID FROM USER WHERE USER_NAME = 'user'),
0,
'Cartoons tag',
'pics/tags/secondary/cartoons.png',
0,
'Cartoons',
@parentid,
100);

INSERT INTO _DATA_ENTITY VALUES (0);

INSERT INTO TAG
(`ID`,
`CREATE_DATE`,
`CREATED_BY`,
`DELETED`,
`DESCRIPTION`,
`ICON_PATH`,
`IS_PRIMARY`,
`NAME`,
`PARENT_TAG_ID`,
`POSITION`)
VALUES
(
(SELECT MAX(id) FROM _DATA_ENTITY),
NOW(),
(SELECT ID FROM USER WHERE USER_NAME = 'user'),
0,
'Movies tag',
'pics/tags/secondary/movies.png',
0,
'Movies',
@parentid,
200);

INSERT INTO _DATA_ENTITY VALUES (0);

INSERT INTO TAG
(`ID`,
`CREATE_DATE`,
`CREATED_BY`,
`DELETED`,
`DESCRIPTION`,
`ICON_PATH`,
`IS_PRIMARY`,
`NAME`,
`PARENT_TAG_ID`,
`POSITION`)
VALUES
(
(SELECT MAX(id) FROM _DATA_ENTITY),
NOW(),
(SELECT ID FROM USER WHERE USER_NAME = 'user'),
0,
'TV shows tag',
'pics/tags/secondary/tvshows.png',
0,
'TV Shows',
@parentid,
300);

INSERT INTO _DATA_ENTITY VALUES (0);

INSERT INTO TAG
(`ID`,
`CREATE_DATE`,
`CREATED_BY`,
`DELETED`,
`DESCRIPTION`,
`ICON_PATH`,
`IS_PRIMARY`,
`NAME`,
`PARENT_TAG_ID`,
`POSITION`)
VALUES
(
(SELECT MAX(id) FROM _DATA_ENTITY),
NOW(),
(SELECT ID FROM USER WHERE USER_NAME = 'user'),
0,
'Music tag',
'pics/tags/primary/music.png',
1,
'Music',
NULL,
1000);

SELECT @parentid:= (SELECT MAX(id) FROM _DATA_ENTITY);

INSERT INTO _DATA_ENTITY VALUES (0);

INSERT INTO TAG
(`ID`,
`CREATE_DATE`,
`CREATED_BY`,
`DELETED`,
`DESCRIPTION`,
`ICON_PATH`,
`IS_PRIMARY`,
`NAME`,
`PARENT_TAG_ID`,
`POSITION`)
VALUES
(
(SELECT MAX(id) FROM _DATA_ENTITY),
NOW(),
(SELECT ID FROM USER WHERE USER_NAME = 'user'),
0,
'Electro tag',
'pics/tags/secondary/electro.png',
0,
'Electro',
@parentid,
100);

INSERT INTO _DATA_ENTITY VALUES (0);

INSERT INTO TAG
(`ID`,
`CREATE_DATE`,
`CREATED_BY`,
`DELETED`,
`DESCRIPTION`,
`ICON_PATH`,
`IS_PRIMARY`,
`NAME`,
`PARENT_TAG_ID`,
`POSITION`)
VALUES
(
(SELECT MAX(id) FROM _DATA_ENTITY),
NOW(),
(SELECT ID FROM USER WHERE USER_NAME = 'user'),
0,
'Hip hop tag',
'pics/tags/secondary/hiphop.png',
0,
'Hip hop',
@parentid,
200);

INSERT INTO _DATA_ENTITY VALUES (0);

INSERT INTO TAG
(`ID`,
`CREATE_DATE`,
`CREATED_BY`,
`DELETED`,
`DESCRIPTION`,
`ICON_PATH`,
`IS_PRIMARY`,
`NAME`,
`PARENT_TAG_ID`,
`POSITION`)
VALUES
(
(SELECT MAX(id) FROM _DATA_ENTITY),
NOW(),
(SELECT ID FROM USER WHERE USER_NAME = 'user'),
0,
'Pop tag',
'pics/tags/secondary/pop.png',
0,
'Pop',
@parentid,
300);

INSERT INTO _DATA_ENTITY VALUES (0);

INSERT INTO TAG
(`ID`,
`CREATE_DATE`,
`CREATED_BY`,
`DELETED`,
`DESCRIPTION`,
`ICON_PATH`,
`IS_PRIMARY`,
`NAME`,
`PARENT_TAG_ID`,
`POSITION`)
VALUES
(
(SELECT MAX(id) FROM _DATA_ENTITY),
NOW(),
(SELECT ID FROM USER WHERE USER_NAME = 'user'),
0,
'Rock tag',
'pics/tags/secondary/rock.png',
0,
'Rock',
@parentid,
400);

INSERT INTO _DATA_ENTITY VALUES (0);

INSERT INTO TAG
(`ID`,
`CREATE_DATE`,
`CREATED_BY`,
`DELETED`,
`DESCRIPTION`,
`ICON_PATH`,
`IS_PRIMARY`,
`NAME`,
`PARENT_TAG_ID`,
`POSITION`)
VALUES
(
(SELECT MAX(id) FROM _DATA_ENTITY),
NOW(),
(SELECT ID FROM USER WHERE USER_NAME = 'user'),
0,
'Science tag',
'pics/tags/primary/science.png',
1,
'Science',
NULL,
1100);

SELECT @parentid:= (SELECT MAX(id) FROM _DATA_ENTITY);

INSERT INTO _DATA_ENTITY VALUES (0);

INSERT INTO TAG
(`ID`,
`CREATE_DATE`,
`CREATED_BY`,
`DELETED`,
`DESCRIPTION`,
`ICON_PATH`,
`IS_PRIMARY`,
`NAME`,
`PARENT_TAG_ID`,
`POSITION`)
VALUES
(
(SELECT MAX(id) FROM _DATA_ENTITY),
NOW(),
(SELECT ID FROM USER WHERE USER_NAME = 'user'),
0,
'Body and mind tag',
'pics/tags/secondary/bodyandmind.png',
0,
'Body and Mind',
@parentid,
100);

INSERT INTO _DATA_ENTITY VALUES (0);

INSERT INTO TAG
(`ID`,
`CREATE_DATE`,
`CREATED_BY`,
`DELETED`,
`DESCRIPTION`,
`ICON_PATH`,
`IS_PRIMARY`,
`NAME`,
`PARENT_TAG_ID`,
`POSITION`)
VALUES
(
(SELECT MAX(id) FROM _DATA_ENTITY),
NOW(),
(SELECT ID FROM USER WHERE USER_NAME = 'user'),
0,
'Education',
'pics/tags/secondary/education.png',
0,
'Education',
@parentid,
200);

INSERT INTO _DATA_ENTITY VALUES (0);

INSERT INTO TAG
(`ID`,
`CREATE_DATE`,
`CREATED_BY`,
`DELETED`,
`DESCRIPTION`,
`ICON_PATH`,
`IS_PRIMARY`,
`NAME`,
`PARENT_TAG_ID`,
`POSITION`)
VALUES
(
(SELECT MAX(id) FROM _DATA_ENTITY),
NOW(),
(SELECT ID FROM USER WHERE USER_NAME = 'user'),
0,
'Energy',
'pics/tags/secondary/energy.png',
0,
'Energy',
@parentid,
300);

INSERT INTO _DATA_ENTITY VALUES (0);

INSERT INTO TAG
(`ID`,
`CREATE_DATE`,
`CREATED_BY`,
`DELETED`,
`DESCRIPTION`,
`ICON_PATH`,
`IS_PRIMARY`,
`NAME`,
`PARENT_TAG_ID`,
`POSITION`)
VALUES
(
(SELECT MAX(id) FROM _DATA_ENTITY),
NOW(),
(SELECT ID FROM USER WHERE USER_NAME = 'user'),
0,
'Nature',
'pics/tags/secondary/nature.png',
0,
'Nature',
@parentid,
400);

INSERT INTO _DATA_ENTITY VALUES (0);

INSERT INTO TAG
(`ID`,
`CREATE_DATE`,
`CREATED_BY`,
`DELETED`,
`DESCRIPTION`,
`ICON_PATH`,
`IS_PRIMARY`,
`NAME`,
`PARENT_TAG_ID`,
`POSITION`)
VALUES
(
(SELECT MAX(id) FROM _DATA_ENTITY),
NOW(),
(SELECT ID FROM USER WHERE USER_NAME = 'user'),
0,
'Space',
'pics/tags/secondary/space.png',
0,
'Space',
@parentid,
500);

INSERT INTO _DATA_ENTITY VALUES (0);

INSERT INTO TAG
(`ID`,
`CREATE_DATE`,
`CREATED_BY`,
`DELETED`,
`DESCRIPTION`,
`ICON_PATH`,
`IS_PRIMARY`,
`NAME`,
`PARENT_TAG_ID`,
`POSITION`)
VALUES
(
(SELECT MAX(id) FROM _DATA_ENTITY),
NOW(),
(SELECT ID FROM USER WHERE USER_NAME = 'user'),
0,
'Social tag',
'pics/tags/primary/social.png',
1,
'Social',
NULL,
1200
);

SELECT @parentid:= (SELECT MAX(id) FROM _DATA_ENTITY);

INSERT INTO _DATA_ENTITY VALUES (0);

INSERT INTO TAG
(`ID`,
`CREATE_DATE`,
`CREATED_BY`,
`DELETED`,
`DESCRIPTION`,
`ICON_PATH`,
`IS_PRIMARY`,
`NAME`,
`PARENT_TAG_ID`,
`POSITION`)
VALUES
(
(SELECT MAX(id) FROM _DATA_ENTITY),
NOW(),
(SELECT ID FROM USER WHERE USER_NAME = 'user'),
0,
'Family',
'pics/tags/secondary/family.png',
0,
'Family',
@parentid,
100);

INSERT INTO _DATA_ENTITY VALUES (0);

INSERT INTO TAG
(`ID`,
`CREATE_DATE`,
`CREATED_BY`,
`DELETED`,
`DESCRIPTION`,
`ICON_PATH`,
`IS_PRIMARY`,
`NAME`,
`PARENT_TAG_ID`,
`POSITION`)
VALUES
(
(SELECT MAX(id) FROM _DATA_ENTITY),
NOW(),
(SELECT ID FROM USER WHERE USER_NAME = 'user'),
0,
'Nightlife',
'pics/tags/secondary/nightlife.png',
0,
'Nightlife',
@parentid,
200);

INSERT INTO _DATA_ENTITY VALUES (0);

INSERT INTO TAG
(`ID`,
`CREATE_DATE`,
`CREATED_BY`,
`DELETED`,
`DESCRIPTION`,
`ICON_PATH`,
`IS_PRIMARY`,
`NAME`,
`PARENT_TAG_ID`,
`POSITION`)
VALUES
(
(SELECT MAX(id) FROM _DATA_ENTITY),
NOW(),
(SELECT ID FROM USER WHERE USER_NAME = 'user'),
0,
'Relationships',
'pics/tags/secondary/relationships.png',
0,
'Relationships',
@parentid,
300);

INSERT INTO _DATA_ENTITY VALUES (0);

INSERT INTO TAG
(`ID`,
`CREATE_DATE`,
`CREATED_BY`,
`DELETED`,
`DESCRIPTION`,
`ICON_PATH`,
`IS_PRIMARY`,
`NAME`,
`PARENT_TAG_ID`,
`POSITION`)
VALUES
(
(SELECT MAX(id) FROM _DATA_ENTITY),
NOW(),
(SELECT ID FROM USER WHERE USER_NAME = 'user'),
0,
'Sport tag',
'pics/tags/primary/sport.png',
1,
'Sport',
NULL,
1300
);

SELECT @parentid:= (SELECT MAX(id) FROM _DATA_ENTITY);



INSERT INTO _DATA_ENTITY VALUES (0);

INSERT INTO TAG
(`ID`,
`CREATE_DATE`,
`CREATED_BY`,
`DELETED`,
`DESCRIPTION`,
`ICON_PATH`,
`IS_PRIMARY`,
`NAME`,
`PARENT_TAG_ID`,
`POSITION`)
VALUES
(
(SELECT MAX(id) FROM _DATA_ENTITY),
NOW(),
(SELECT ID FROM USER WHERE USER_NAME = 'user'),
0,
'Basketball tag',
'pics/tags/secondary/basketball.png',
0,
'Basketball',
@parentid,
100);

INSERT INTO _DATA_ENTITY VALUES (0);

INSERT INTO TAG
(`ID`,
`CREATE_DATE`,
`CREATED_BY`,
`DELETED`,
`DESCRIPTION`,
`ICON_PATH`,
`IS_PRIMARY`,
`NAME`,
`PARENT_TAG_ID`,
`POSITION`)
VALUES
(
(SELECT MAX(id) FROM _DATA_ENTITY),
NOW(),
(SELECT ID FROM USER WHERE USER_NAME = 'user'),
0,
'Fitness and bodybuilding tag',
'pics/tags/secondary/fitnessandbodybuilding.png',
0,
'Fitness and Bodybuilding',
@parentid,
200);

INSERT INTO _DATA_ENTITY VALUES (0);

INSERT INTO TAG
(`ID`,
`CREATE_DATE`,
`CREATED_BY`,
`DELETED`,
`DESCRIPTION`,
`ICON_PATH`,
`IS_PRIMARY`,
`NAME`,
`PARENT_TAG_ID`,
`POSITION`)
VALUES
(
(SELECT MAX(id) FROM _DATA_ENTITY),
NOW(),
(SELECT ID FROM USER WHERE USER_NAME = 'user'),
0,
'Football tag',
'pics/tags/secondary/football2.png',
0,
'Football',
@parentid,
300);

INSERT INTO _DATA_ENTITY VALUES (0);

INSERT INTO TAG
(`ID`,
`CREATE_DATE`,
`CREATED_BY`,
`DELETED`,
`DESCRIPTION`,
`ICON_PATH`,
`IS_PRIMARY`,
`NAME`,
`PARENT_TAG_ID`,
`POSITION`)
VALUES
(
(SELECT MAX(id) FROM _DATA_ENTITY),
NOW(),
(SELECT ID FROM USER WHERE USER_NAME = 'user'),
0,
'Football tag',
'pics/tags/secondary/football.png',
0,
'Football',
@parentid,
400);

INSERT INTO _DATA_ENTITY VALUES (0);

INSERT INTO TAG
(`ID`,
`CREATE_DATE`,
`CREATED_BY`,
`DELETED`,
`DESCRIPTION`,
`ICON_PATH`,
`IS_PRIMARY`,
`NAME`,
`PARENT_TAG_ID`,
`POSITION`)
VALUES
(
(SELECT MAX(id) FROM _DATA_ENTITY),
NOW(),
(SELECT ID FROM USER WHERE USER_NAME = 'user'),
0,
'Motorsports tag',
'pics/tags/secondary/motorsports.png',
0,
'Motorsports',
@parentid,
500);

INSERT INTO _DATA_ENTITY VALUES (0);

INSERT INTO TAG
(`ID`,
`CREATE_DATE`,
`CREATED_BY`,
`DELETED`,
`DESCRIPTION`,
`ICON_PATH`,
`IS_PRIMARY`,
`NAME`,
`PARENT_TAG_ID`,
`POSITION`)
VALUES
(
(SELECT MAX(id) FROM _DATA_ENTITY),
NOW(),
(SELECT ID FROM USER WHERE USER_NAME = 'user'),
0,
'Winter sports tag',
'pics/tags/secondary/wintersports.png',
0,
'Winter Sports',
@parentid,
600);

INSERT INTO _DATA_ENTITY VALUES (0);

INSERT INTO TAG
(`ID`,
`CREATE_DATE`,
`CREATED_BY`,
`DELETED`,
`DESCRIPTION`,
`ICON_PATH`,
`IS_PRIMARY`,
`NAME`,
`PARENT_TAG_ID`,
`POSITION`)
VALUES
(
(SELECT MAX(id) FROM _DATA_ENTITY),
NOW(),
(SELECT ID FROM USER WHERE USER_NAME = 'user'),
0,
'Technology tag',
'pics/tags/primary/technology.png',
1,
'Technology',
NULL,
1400
);

SELECT @parentid:= (SELECT MAX(id) FROM _DATA_ENTITY);

INSERT INTO _DATA_ENTITY VALUES (0);

INSERT INTO TAG
(`ID`,
`CREATE_DATE`,
`CREATED_BY`,
`DELETED`,
`DESCRIPTION`,
`ICON_PATH`,
`IS_PRIMARY`,
`NAME`,
`PARENT_TAG_ID`,
`POSITION`)
VALUES
(
(SELECT MAX(id) FROM _DATA_ENTITY),
NOW(),
(SELECT ID FROM USER WHERE USER_NAME = 'user'),
0,
'Communications tag',
'pics/tags/secondary/communications.png',
0,
'Communications',
@parentid,
100);

INSERT INTO _DATA_ENTITY VALUES (0);

INSERT INTO TAG
(`ID`,
`CREATE_DATE`,
`CREATED_BY`,
`DELETED`,
`DESCRIPTION`,
`ICON_PATH`,
`IS_PRIMARY`,
`NAME`,
`PARENT_TAG_ID`,
`POSITION`)
VALUES
(
(SELECT MAX(id) FROM _DATA_ENTITY),
NOW(),
(SELECT ID FROM USER WHERE USER_NAME = 'user'),
0,
'Consumer electronics tag',
'pics/tags/secondary/consumerelectronics.png',
0,
'Consumer Electronics',
@parentid,
200);

INSERT INTO _DATA_ENTITY VALUES (0);

INSERT INTO TAG
(`ID`,
`CREATE_DATE`,
`CREATED_BY`,
`DELETED`,
`DESCRIPTION`,
`ICON_PATH`,
`IS_PRIMARY`,
`NAME`,
`PARENT_TAG_ID`,
`POSITION`)
VALUES
(
(SELECT MAX(id) FROM _DATA_ENTITY),
NOW(),
(SELECT ID FROM USER WHERE USER_NAME = 'user'),
0,
'Gadgets tag',
'pics/tags/secondary/gadgets.png',
0,
'Gadgets',
@parentid,
300);

INSERT INTO _DATA_ENTITY VALUES (0);

INSERT INTO TAG
(`ID`,
`CREATE_DATE`,
`CREATED_BY`,
`DELETED`,
`DESCRIPTION`,
`ICON_PATH`,
`IS_PRIMARY`,
`NAME`,
`PARENT_TAG_ID`,
`POSITION`)
VALUES
(
(SELECT MAX(id) FROM _DATA_ENTITY),
NOW(),
(SELECT ID FROM USER WHERE USER_NAME = 'user'),
0,
'Vehicles tag',
'pics/tags/secondary/vehicles.png',
0,
'Vehicles',
@parentid,
400);