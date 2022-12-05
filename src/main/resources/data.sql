INSERT INTO "TIME_SLOT"
VALUES ('48767ba4-a9a2-4c0b-9ce9-cf0c53e8e45e', 'Investigate fulfilment workflow', 2.0, null),
       ('8db2356e-c5a4-461d-b231-6e13d4b48757', 'Deliver changes', 1.0, null);

INSERT INTO `USERS`(`id`, `is_active`, `password`, `roles`, `username`)
values ('48767ba4-a9a2-4c0b-9ce9-cf0c53e8e45e', true, '$2a$12$crF0VpZEywOO4rQ3PdOn9egz0ub3rmU6ZAjR8gkiTTjVrHsHESK1.', 'ROLE_USER', 'user'),
       ('8db2356e-c5a4-461d-b231-6e13d4b48757', true, '$2a$12$//7weNtEO/oLBnX51P603.4u4uHt7G1FVXBh9mtDaEfsFzMTk.iWi', 'ROLE_ADMIN', 'admin');