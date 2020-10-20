insert into users(username, password, enabled)
values ("user", "$2a$10$vc5z3QdL3SYQzoGMCSUm1.AeiSg0bI0u6AgmzBgj9AQ65Yu0mHEUO", true);

insert into users(username, password, enabled)
values ("admin", "$2a$10$MrfDpR4khF4yNEU/Xg8ykO3RrxLBton/B0l4U66gMQwBN3ETneU2q", true);

insert into authorities(username, authority)
values("user", "ROLE_USER");

insert into authorities(username, authority)
values("admin", "ROLE_ADMIN");