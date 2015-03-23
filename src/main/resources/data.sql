-- clean up
delete from user;
delete from token;
delete from location;

-- insert test data
insert into user (username, password) values ('test', 'test');
insert into token (username, token) values ('test', 'TEST123');
insert into location (username, instant, latitude, longitude) values ('test', now(), 52.49, 9.832);
