-- clean up
delete from user;
delete from token;
delete from location;

-- insert test data (password equals username)
insert into user (fullname, username, password, admin) values ('Test-Admin', 'testadmin', '597f5441e7d174b607873874ed54b974674986ad543e7458e28a038671c9f64c', true);
insert into user (fullname, username, password, admin) values ('Test-User', 'test', '9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08', false);
insert into token (username, token) values ('test', 'TEST123');
insert into location (username, instant, latitude, longitude) values ('test', now(), 52.49, 9.832);
