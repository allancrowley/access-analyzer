create table if not exists services (
    service_name varchar(255) primary key,
    email varchar(255) not null
);

delete from attack_attempts;
delete from ip_addresses;
delete from services;

insert into ip_addresses (ip_subnet) values ('100.100.100'), ('100.100.101');
insert into services (service_name, email) values
                                               ('service1', 'email1@mail.com'),
                                                  ('service2', 'email2@mail.com'),
                                                  ('service3', 'email3@mail.com');
insert into attack_attempts (id, ip_subnet, service_name, timestamp) values
                                                                         (1, '100.100.100', 'service1', 1);