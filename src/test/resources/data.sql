-- clean up
delete from user;
delete from token;
delete from location;

-- insert test data
insert into user (username, password) values ('test1', 'test1pw');
insert into user (username, password) values ('test2', 'test2pw');
insert into token (username, token) values ('test1', 'TEST1-TOKEN1');
insert into token (username, token) values ('test1', 'TEST1-TOKEN2');
insert into token (username, token) values ('test2', 'TEST2-TOKEN1');
insert into token (username, token) values ('test2', 'TEST2-TOKEN2');
insert into location (username, instant, latitude, longitude) values ('test1', DATEADD('DAY', -2, now()), 51.49, 7.123);
insert into location (username, instant, latitude, longitude) values ('test1', DATEADD('DAY', -1, now()), 52.59, 8.234);
insert into location (username, instant, latitude, longitude) values ('test1', now(), 53.69, 9.345);
insert into location (username, instant, latitude, longitude) values ('test2', DATEADD('DAY', -11, now()), 40.49, 1.123);
insert into location (username, instant, latitude, longitude) values ('test2', DATEADD('DAY', -10, now()), 41.49, 2.123);
insert into location (username, instant, latitude, longitude) values ('test2', DATEADD('DAY', -9, now()), 42.59, 3.234);
insert into location (username, instant, latitude, longitude) values ('test2', DATEADD('DAY', -8, now()), 43.69, 4.345);
