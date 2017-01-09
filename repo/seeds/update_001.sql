update web_item set web_item_type_id = 6 where web_item_type_id in (7, 8, 9, 12);

delete from data_request_log_web_item_types where id_eid in (7, 8, 9, 12);

delete from web_item_type where id in (7, 8, 9, 12);

drop table `tsosm`.`web_item_tags_old`;

delete from web_item_tags where id_eid in (25, 45, 46, 47, 48, 98);

delete from data_request_log_tags where id_eid in (25, 45, 46, 47, 48, 98);

delete from tag where id in (25, 45, 46, 47, 48, 98);

update tag set name = 'Moodifiers', description = 'Moodifiers tag' where id =  66;
update tag set name = 'Women''s', description = 'Women''s tag' where id =  55;
update tag set name = 'Men''s', description = 'Men''s tag' where id =  56;
