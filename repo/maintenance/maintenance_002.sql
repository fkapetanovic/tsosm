delimiter $$

CREATE DEFINER=`tsosm`@`%` PROCEDURE `Migrate_Tags`()
BEGIN
DECLARE done INT DEFAULT FALSE;
declare WebItemId Bigint(20);
declare TagId  Bigint(20);
declare MaxId Int(11);
DECLARE curs CURSOR FOR SELECT id_OID,ID_EID FROM web_item_tags_old;

DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

OPEN curs;

read_loop: LOOP
	FETCH curs into WebItemId,TagId;
	IF done THEN
      LEAVE read_loop;
    END IF;
	select coalesce(max(idx),0) into MaxId from web_item_tags where id_oid=WebItemId;

	insert into web_item_tags values(WebItemId,TagId,MaxId+1);

END LOOP;

close curs;

END$$

