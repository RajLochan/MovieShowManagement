create table `order_detail`(
`id` bigint auto_increment,
`show_id` integer not null,
`user_id` integer not null,
`cart_id` varchar(20) not null,
`seats`  varchar(50) not null, /* [[row,seat], [row,seat]] */
`promo_id` integer,
`total_amt` double,
`discount_amt` double,
`tax_amt` double,
`payable_amt` double,
`created` timestamp not null DEFAULT CURRENT_TIMESTAMP,
`updated` timestamp not null DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
 primary key (`id`)
 );

create table `orders`(
`id` bigint auto_increment, /* order id */
`ordr_dtl_id` integer not null,
`next_action` tinyint not null, /* 1- initiatePayment, 2- paymentConfirmation, 3- none */
`order_payment_id` varchar(30), /* payment trx id of order */
`status` tinyint not null, /*0-pending,1-processing, 2-success, 3-failed*/
`reason` varchar(50),
`created` timestamp not null DEFAULT CURRENT_TIMESTAMP,
`updated` timestamp not null DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
 primary key (`id`),
 foreign key (`ordr_dtl_id`) references order_detail(`id`),
 unique index idx_unq_pmnt_id (`order_payment_id`)
 );


 create table `seats_allocation_status`(
`show_id` integer not null,
`seat_id` integer not null,
`status` tinyint not null, /* 0- blocked, 1- booked */
 primary key (`show_id`,`seat_id`),
 index idx_shwid (`show_id`)
 );