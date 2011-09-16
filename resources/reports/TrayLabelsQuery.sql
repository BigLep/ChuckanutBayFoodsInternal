SELECT
     quickbooks_items.`unique_instructions` AS quickbooks_items_unique_instructions,
     quickbooks_item_supplements.`short_name` AS quickbooks_item_supplements_product_type,
     quickbooks_item_supplements.`cases_per_tray` AS quickbooks_item_supplements_cases_per_tray,
     tray_labels.`id` AS tray_labels_id,
     tray_labels.`lot_code` AS tray_labels_lot_code,
     tray_labels.`cases` AS tray_labels_cases,
     tray_labels.`cakes_per_case` AS tray_labels_cakes_per_case,
     tray_labels.`cases_per_tray` AS tray_labels_cases_per_tray,
     sales_orders.`purchase_order` AS sales_orders_purchase_order,
     sales_orders.`customer_name` AS sales_orders_customer_name,
     sales_orders.`ship_date` AS sales_orders_ship_date,
     sales_order_line_items.`cases` AS sales_order_line_items_cases,
     nutrition_labels.`is_allergen` AS nutrition_labels_is_allergen,
     nutrition_labels.`batter_amount` AS nutrition_labels_batter_amount,
     nutrition_labels.`batter_type` AS nutrition_labels_batter_type,
     nutrition_labels.`crust_amount` AS nutrition_labels_crust_amount,
     nutrition_labels.`crust_type` AS nutrition_labels_crust_type,
     nutrition_labels.`decoration_amount` AS nutrition_labels_decoration_amount,
     nutrition_labels.`decoration_type` AS nutrition_labels_decoration_type,
     quickbooks_item_supplements.`product_type` AS quickbooks_item_supplements_product_type,
     quickbooks_items.`pack` AS quickbooks_items_pack,
     quickbooks_items.`id` AS quickbooks_items_id,
     quickbooks_items_A.`pack` AS quickbooks_items_A_pack,
     quickbooks_items_A.`unique_instructions` AS quickbooks_items_A_unique_instructions,
     quickbooks_item_supplements_A.`short_name` AS quickbooks_item_supplements_A_product_type,
     quickbooks_item_supplements_A.`cases_per_tray` AS quickbooks_item_supplements_A_cases_per_tray,
     quickbooks_items_B.`pack` AS quickbooks_items_B_pack,
     quickbooks_items_B.`unique_instructions` AS quickbooks_items_B_unique_instructions,
     quickbooks_item_supplements_B.`short_name` AS quickbooks_item_supplements_B_product_type,
     quickbooks_item_supplements_B.`cases_per_tray` AS quickbooks_item_supplements_B_cases_per_tray,
     nutrition_labels_A.`is_allergen` AS nutrition_labels_A_is_allergen,
     nutrition_labels_A.`batter_amount` AS nutrition_labels_A_batter_amount,
     nutrition_labels_A.`batter_type` AS nutrition_labels_A_batter_type,
     nutrition_labels_A.`crust_amount` AS nutrition_labels_A_crust_amount,
     nutrition_labels_A.`crust_type` AS nutrition_labels_A_crust_type,
     nutrition_labels_A.`decoration_amount` AS nutrition_labels_A_decoration_amount,
     nutrition_labels_A.`decoration_type` AS nutrition_labels_A_decoration_type,
     nutrition_labels_B.`is_allergen` AS nutrition_labels_B_is_allergen,
     nutrition_labels_B.`batter_amount` AS nutrition_labels_B_batter_amount,
     nutrition_labels_B.`batter_type` AS nutrition_labels_B_batter_type,
     nutrition_labels_B.`crust_amount` AS nutrition_labels_B_crust_amount,
     nutrition_labels_B.`crust_type` AS nutrition_labels_B_crust_type,
     nutrition_labels_B.`decoration_amount` AS nutrition_labels_B_decoration_amount,
     nutrition_labels_B.`decoration_type` AS nutrition_labels_B_decoration_type,
     tray_labels.`sub_item_id` AS quickbooks_items_A_id,
     tray_labels.`qb_item_id` AS quickbooks_items_B_id,
     sales_orders.`special_instructions` AS sales_orders_special_instructions
FROM
    
     `tray_labels` tray_labels LEFT OUTER JOIN `sales_order_line_items` sales_order_line_items ON sales_order_line_items.`id` = tray_labels.`line_item_id` LEFT OUTER JOIN `quickbooks_items` quickbooks_items ON quickbooks_items.`id` = sales_order_line_items.`qb_item_id` LEFT OUTER JOIN `quickbooks_item_supplements` quickbooks_item_supplements ON quickbooks_item_supplements.`id` = `quickbooks_items`.`quickbooks_item_supplement_id`
     LEFT OUTER JOIN `sales_orders` sales_orders ON sales_order_line_items.`sales_order_id` = sales_orders.`id`
     LEFT OUTER JOIN `quickbooks_items` quickbooks_items_A ON tray_labels.`sub_item_id` = quickbooks_items_A.`id`
     LEFT OUTER JOIN `quickbooks_items` quickbooks_items_B ON tray_labels.`qb_item_id` = quickbooks_items_B.`id`
     LEFT OUTER JOIN `quickbooks_item_supplements` quickbooks_item_supplements_B ON quickbooks_items_B.`quickbooks_item_supplement_id` = quickbooks_item_supplements_B.`id`
     LEFT OUTER JOIN `nutrition_labels` nutrition_labels_B ON quickbooks_item_supplements_B.`nutrition_label_id` = nutrition_labels_B.`id`
     LEFT OUTER JOIN `quickbooks_item_supplements` quickbooks_item_supplements_A ON quickbooks_items_A.`quickbooks_item_supplement_id` = quickbooks_item_supplements_A.`id`
     LEFT OUTER JOIN `nutrition_labels` nutrition_labels_A ON quickbooks_item_supplements_A.`nutrition_label_id` = nutrition_labels_A.`id`
     LEFT OUTER JOIN `nutrition_labels` nutrition_labels ON quickbooks_item_supplements.`nutrition_label_id` = nutrition_labels.`id`
WHERE `tray_labels`.`id` IN ($P!{TRAY_LABEL_IDS})