-- insert test data (password equals user name)
insert into user (fullname, username, password, admin) values ('Administrator 1', 'admin1', '25f43b1486ad95a1398e3eeb3d83bc4010015fcc9bedb35b432e00298d5021f7', true);
insert into user (fullname, username, password, admin) values ('Test 1', 'test1', '1b4f0e9851971998e732078544c96b36c3d01cedf7caa332359d6f1d83567014', false);
insert into user (fullname, username, password, admin) values ('Test 2', 'test2', '60303ae22b998861bce3b28f33eec1be758a213c86c93c076dbe9f558c11c752', false);
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
