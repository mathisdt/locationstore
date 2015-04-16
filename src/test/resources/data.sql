-- insert test data (password equals user name)
insert into user (fullname, username, password, admin) values ('Administrator 1', 'admin1', '25f43b1486ad95a1398e3eeb3d83bc4010015fcc9bedb35b432e00298d5021f7', true);
insert into user (fullname, username, password, admin) values ('Test 1', 'test1', '1b4f0e9851971998e732078544c96b36c3d01cedf7caa332359d6f1d83567014', false);
insert into user (fullname, username, password, admin) values ('Test 2', 'test2', '60303ae22b998861bce3b28f33eec1be758a213c86c93c076dbe9f558c11c752', false);
insert into token (username, token) values ('test1', 'TEST1-TOKEN1');
insert into token (username, token) values ('test1', 'TEST1-TOKEN2');
insert into token (username, token) values ('test2', 'TEST2-TOKEN1');
insert into token (username, token) values ('test2', 'TEST2-TOKEN2');
insert into location (username, instant, latitude, longitude) values ('test1', DATEADD('DAY', -2, now()), 52.377, 9.739);
insert into location (username, instant, latitude, longitude) values ('test1', DATEADD('DAY', -1, now()), 52.510, 13.435);
insert into location (username, instant, latitude, longitude) values ('test1', now(), 53.553, 10.006);
insert into location (username, instant, latitude, longitude) values ('test2', DATEADD('DAY', -11, now()), 52.377, 9.739);
insert into location (username, instant, latitude, longitude) values ('test2', DATEADD('DAY', -10, now()), 52.510, 13.435);
insert into location (username, instant, latitude, longitude) values ('test2', DATEADD('DAY', -9, now()), 53.553, 10.006);
insert into location (username, instant, latitude, longitude) values ('test2', DATEADD('DAY', -8, now()), 48.141, 11.556);
