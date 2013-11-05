drop table item;
CREATE TABLE `item` (     
	`data_id` INTEGER NOT NULL,
	`name` VARCHAR(150) NOT NULL,
     `rarity` INTEGER NOT NULL,
  `restriction_level` INTEGER NOT NULL,
     `img` VARCHAR(255) NOT NULL,
     `type_id` INTEGER,
     `sub_type_id` INTEGER,
  `price_last_changed` VARCHAR,
     `max_offer_unit_price` INTEGER NOT NULL,
     `min_sale_unit_price` INTEGER NOT NULL,
     `offer_availability` INTEGER DEFAULT 0 NOT NULL,
     `sale_availability` INTEGER DEFAULT 0 NOT NULL,
     `gw2db_external_id` INTEGER,
     `sale_price_change_last_hour` INTEGER DEFAULT 0,
     `offer_price_change_last_hour` INTEGER DEFAULT 0,
     PRIMARY KEY (`data_id`) 
) ENGINE=MyISAM	