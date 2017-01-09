insert into _data_entity values(0);
SELECT @feedbacktypeId:=LAST_INSERT_ID();
insert into feedback_type values(@feedbacktypeId, 'OSM! bookmark', 'OSM');
/*update all webItemtypes to this feedback option*/
Update web_item_type set feedback_type_id=@feedbacktypeId;

insert into _data_entity values(0);
SELECT @feedbackOptionId:=LAST_INSERT_ID();
insert into feedback_option values(@feedbackOptionId, NULL, 'OSM!', '1', '1', @feedbacktypeId, '1');


/**WARNING the following steps will destroy all previous voting statistics*/
truncate vote;
truncate vote_stat;