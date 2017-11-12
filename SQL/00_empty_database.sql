use coffee;

SET FOREIGN_KEY_CHECKS=0;
truncate table order_adjustment_overview;

truncate table desktop;
truncate table order_item;
truncate table orders;
truncate table import_product_count;
truncate table import_product;
truncate table import_history_summary;
truncate table import_history;
truncate table menu_item_language;
truncate table menu_item;
truncate table menu_category_language;
truncate table menu_category;
truncate table menu_item_to_import_product;

SET FOREIGN_KEY_CHECKS=1;