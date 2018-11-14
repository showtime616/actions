insert into product values (1, 'Пылесос', 1);
insert into product values (2, 'Апельсин', 100);
insert into product values (3, 'Карандаш', 4);
insert into product values (4, 'Книга', 2);

insert into `action` (`id`, `name`, `quantity`) values (1, 'Первая акция', 5);
insert into `action` (`id`, `name`, `quantity`) values (2, 'Вторая акция', 3);

insert into action_product (`action_id`, `product_id`) values (1, 1);
insert into action_product (`action_id`, `product_id`) values (1, 2);
insert into action_product (`action_id`, `product_id`) values (2, 1);
insert into action_product (`action_id`, `product_id`) values (2, 3);
insert into action_product (`action_id`, `product_id`) values (2, 4);