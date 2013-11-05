CREATE TABLE `recipe`
(
    `data_id` INTEGER NOT NULL,
    `name` VARCHAR(255) NOT NULL,
    `result_count` INTEGER NOT NULL,
    `result_item_data_id` INTEGER,
	`discipline_id` INTEGER,
	`result_item_max_offer_unit_price` INTEGER,
	`result_item_min_sale_unit_offer` INTEGER,
    `crafting_cost` INTEGER,
	`rating` INTEGER(4) DEFAULT 0,
    PRIMARY KEY (`data_id`)
) ENGINE=MyISAM;