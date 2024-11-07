use ecommerce;


-- Insert Categories
INSERT INTO category (name, image, is_deleted) VALUES 
('SPORTY', 'sporty.jpg', false),
('CLASSIC', 'classic.jpg', false),
('CASUAL', 'casual.jpg', false),
('SMART', 'smart.jpg', false),
('PREMIUM', 'premium.jpg', false),
('ANALOG', 'analog.jpg', false);

-- SPORTY Category Products
INSERT INTO product (admin_id, category_id, name, price, quantity, brand, description, image, is_deleted, material, case_diameter, water_resistance, gender) VALUES
(1, 1, 'G-Shock Mudmaster', 320.00, 15, 'G-Shock', 'Durable, mud-resistant watch for rugged environments.', 'gshock_mudmaster.jpg', false, 'Resin', 55, '200m', 'Male'),
(1, 1, 'CASIO Pro Trek', 150.00, 20, 'CASIO', 'Outdoor sports watch with altimeter, barometer, and compass.', 'casio_protrek.jpg', false, 'Stainless Steel', 47, '100m', 'Unisex'),
(1, 1, 'Fossil Gen 5E Smartwatch', 199.99, 10, 'Fossil', 'Sleek smartwatch with fitness tracking.', 'fossil_gen5e.jpg', false, 'Stainless Steel', 44, '50m', 'Female'),
(1, 1, 'ALBA Extreme', 85.00, 25, 'ALBA', 'Affordable sporty watch with shock resistance.', 'alba_extreme.jpg', false, 'Silicone', 46, '30m', 'Male'),
(1, 1, 'CITIZEN Eco-Drive Promaster', 275.00, 12, 'CITIZEN', 'Eco-drive watch with solar power and dive-ready features.', 'citizen_promaster.jpg', false, 'Titanium', 48, '200m', 'Unisex'),
(1, 1, 'TOMMY HILFIGER Sport Racer', 160.00, 8, 'TOMMY HILFIGER', 'Stylish sports watch with chronograph.', 'tommy_racer.jpg', false, 'Leather', 45, '50m', 'Male'),
(1, 1, 'NAVIFORCE Military', 95.00, 18, 'NAVIFORCE', 'Military-inspired design for the adventure-seeker.', 'naviforce_military.jpg', false, 'Rubber', 49, '100m', 'Unisex'),
(1, 1, 'TIMEX Ironman Classic', 65.00, 30, 'TIMEX', 'Lightweight watch for triathletes.', 'timex_ironman.jpg', false, 'Plastic', 43, '100m', 'Female'),
(1, 1, 'G-Shock Rangeman', 400.00, 7, 'G-Shock', 'GPS-enabled survival watch for extreme conditions.', 'gshock_rangeman.jpg', false, 'Resin', 54, '200m', 'Male'),
(1, 1, 'CASIO G-Squad', 110.00, 22, 'CASIO', 'Bluetooth-enabled fitness watch.', 'casio_gsquad.jpg', false, 'Silicone', 45, '50m', 'Unisex');

-- CLASSIC Category Products
INSERT INTO product (admin_id, category_id, name, price, quantity, brand, description, image, is_deleted, material, case_diameter, water_resistance, gender) VALUES
(1, 2, 'G-Shock G-Steel', 290.00, 12, 'G-Shock', 'Classic G-Shock with metallic accents.', 'gshock_gsteel.jpg', false, 'Stainless Steel', 50, '200m', 'Male'),
(1, 2, 'CASIO Vintage A168W', 50.00, 40, 'CASIO', 'Retro CASIO watch with digital display.', 'casio_a168w.jpg', false, 'Metal', 35, '30m', 'Unisex'),
(1, 2, 'Fossil Grant Chronograph', 175.00, 20, 'Fossil', 'Leather strap with Roman numeral markers.', 'fossil_grant.jpg', false, 'Leather', 42, '50m', 'Male'),
(1, 2, 'ALBA Heritage', 65.00, 25, 'ALBA', 'Affordable watch with classic design.', 'alba_heritage.jpg', false, 'Stainless Steel', 40, '30m', 'Female'),
(1, 2, 'CITIZEN Eco-Drive Chandler', 200.00, 15, 'CITIZEN', 'Classic and sustainable with solar power.', 'citizen_chandler.jpg', false, 'Leather', 43, '100m', 'Male'),
(1, 2, 'TOMMY HILFIGER Daniel', 160.00, 10, 'TOMMY HILFIGER', 'Stylish watch with leather strap.', 'tommy_daniel.jpg', false, 'Leather', 44, '30m', 'Female'),
(1, 2, 'NAVIFORCE Classic Blue', 78.00, 28, 'NAVIFORCE', 'Classic everyday watch with bold blue dial.', 'naviforce_classicblue.jpg', false, 'Stainless Steel', 45, '50m', 'Unisex'),
(1, 2, 'TIMEX Marlin Hand-Wound', 195.00, 5, 'TIMEX', 'Classic manual-wind watch with vintage appeal.', 'timex_marlin.jpg', false, 'Leather', 34, '30m', 'Male'),
(1, 2, 'G-Shock Full Metal', 600.00, 3, 'G-Shock', 'Metal edition of the iconic G-Shock.', 'gshock_fullmetal.jpg', false, 'Stainless Steel', 49, '200m', 'Male'),
(1, 2, 'CASIO Edifice', 125.00, 35, 'CASIO', 'Classic design with modern chronograph features.', 'casio_edifice.jpg', false, 'Metal', 44, '100m', 'Unisex');

-- CASUAL Category Products
INSERT INTO product (admin_id, category_id, name, price, quantity, brand, description, image, is_deleted, material, case_diameter, water_resistance, gender) VALUES
(1, 3, 'G-Shock GA-2100', 99.00, 25, 'G-Shock', 'Slim G-Shock with analog-digital display.', 'gshock_ga2100.jpg', false, 'Resin', 45, '200m', 'Unisex'),
(1, 3, 'CASIO Enticer', 55.00, 40, 'CASIO', 'Casual and comfortable, ideal for daily wear.', 'casio_enticer.jpg', false, 'Metal', 39, '50m', 'Male'),
(1, 3, 'Fossil Minimalist', 140.00, 20, 'Fossil', 'Simple and sleek with a minimalist design.', 'fossil_minimalist.jpg', false, 'Stainless Steel', 44, '30m', 'Female'),
(1, 3, 'ALBA Casual Slim', 60.00, 30, 'ALBA', 'Lightweight and slim watch for daily wear.', 'alba_casual.jpg', false, 'Silicone', 42, '30m', 'Unisex'),
(1, 3, 'CITIZEN Corso', 180.00, 10, 'CITIZEN', 'Eco-friendly casual watch.', 'citizen_corso.jpg', false, 'Stainless Steel', 41, '100m', 'Male'),
(1, 3, 'TOMMY HILFIGER Casual Denim', 85.00, 15, 'TOMMY HILFIGER', 'Casual watch with denim strap.', 'tommy_denim.jpg', false, 'Denim', 44, '30m', 'Female'),
(1, 3, 'NAVIFORCE Navigator', 88.00, 22, 'NAVIFORCE', 'Casual watch with dual display.', 'naviforce_navigator.jpg', false, 'Plastic', 46, '50m', 'Male'),
(1, 3, 'TIMEX Weekender', 45.00, 35, 'TIMEX', 'Easy-to-wear, everyday casual watch.', 'timex_weekender.jpg', false, 'Nylon', 40, '30m', 'Unisex'),
(1, 3, 'G-Shock Street Series', 150.00, 12, 'G-Shock', 'Urban-inspired watch with rugged style.', 'gshock_street.jpg', false, 'Resin', 47, '200m', 'Male'),
(1, 3, 'CASIO F-91W', 20.00, 50, 'CASIO', 'Iconic casual digital watch.', 'casio_f91w.jpg', false, 'Plastic', 35, '30m', 'Unisex');


-- SMART Category Products
INSERT INTO product (admin_id, category_id, name, price, quantity, brand, description, image, is_deleted, material, case_diameter, water_resistance, gender) VALUES
(1, 4, 'G-Shock Move', 299.00, 20, 'G-Shock', 'Smart G-Shock with fitness tracking and GPS.', 'gshock_move.jpg', false, 'Resin', 45, '200m', 'Unisex'),
(1, 4, 'CASIO Smart Outdoor', 220.00, 15, 'CASIO', 'Outdoor smartwatch with rugged build and GPS.', 'casio_smart_outdoor.jpg', false, 'Plastic', 48, '50m', 'Male'),
(1, 4, 'Fossil Hybrid HR', 179.00, 12, 'Fossil', 'Hybrid smartwatch with analog hands and digital features.', 'fossil_hybrid_hr.jpg', false, 'Stainless Steel', 44, '30m', 'Female'),
(1, 4, 'ALBA Active Smart', 99.00, 18, 'ALBA', 'Entry-level smartwatch with activity tracking.', 'alba_active.jpg', false, 'Silicone', 42, '30m', 'Unisex'),
(1, 4, 'CITIZEN CZ Smart', 395.00, 10, 'CITIZEN', 'Premium smartwatch with Wear OS and long battery life.', 'citizen_cz_smart.jpg', false, 'Stainless Steel', 46, '50m', 'Male'),
(1, 4, 'TOMMY HILFIGER SmartWatch', 150.00, 8, 'TOMMY HILFIGER', 'Fashion-forward smartwatch with notifications.', 'tommy_smart.jpg', false, 'Leather', 45, '30m', 'Female'),
(1, 4, 'NAVIFORCE Connect', 110.00, 20, 'NAVIFORCE', 'Smartwatch with call and message notifications.', 'naviforce_connect.jpg', false, 'Plastic', 44, '30m', 'Unisex'),
(1, 4, 'TIMEX Metropolitan R', 125.00, 25, 'TIMEX', 'Sleek smartwatch with fitness features.', 'timex_metropolitan.jpg', false, 'Metal', 42, '30m', 'Female'),
(1, 4, 'G-Shock Smart Tactical', 350.00, 5, 'G-Shock', 'Tactical smartwatch with military-grade durability.', 'gshock_tactical.jpg', false, 'Resin', 50, '200m', 'Male'),
(1, 4, 'CASIO G-Smart', 180.00, 12, 'CASIO', 'CASIO smartwatch with fitness and health tracking.', 'casio_gsmart.jpg', false, 'Plastic', 45, '50m', 'Unisex');

-- PREMIUM Category Products
INSERT INTO product (admin_id, category_id, name, price, quantity, brand, description, image, is_deleted, material, case_diameter, water_resistance, gender) VALUES
(1, 5, 'G-Shock Full Metal Gold', 650.00, 3, 'G-Shock', 'Premium metal G-Shock in gold finish.', 'gshock_gold.jpg', false, 'Gold-Plated Steel', 49, '200m', 'Male'),
(1, 5, 'CASIO Edifice Sapphire', 220.00, 10, 'CASIO', 'Edifice with sapphire crystal and luxury finish.', 'casio_sapphire.jpg', false, 'Stainless Steel', 46, '100m', 'Male'),
(1, 5, 'Fossil Swiss Chrono', 450.00, 7, 'Fossil', 'High-end Fossil watch with Swiss-made movement.', 'fossil_swiss.jpg', false, 'Stainless Steel', 44, '50m', 'Female'),
(1, 5, 'ALBA Limited Edition', 300.00, 5, 'ALBA', 'Limited edition with luxurious leather strap.', 'alba_limited.jpg', false, 'Leather', 42, '30m', 'Male'),
(1, 5, 'CITIZEN Signature', 600.00, 2, 'CITIZEN', 'CITIZEN signature series with fine craftsmanship.', 'citizen_signature.jpg', false, 'Titanium', 47, '100m', 'Unisex'),
(1, 5, 'TOMMY HILFIGER Luxe', 180.00, 8, 'TOMMY HILFIGER', 'Premium watch with elegant design.', 'tommy_luxe.jpg', false, 'Gold-Plated Steel', 44, '50m', 'Female'),
(1, 5, 'NAVIFORCE Prestige', 220.00, 15, 'NAVIFORCE', 'High-end NAVIFORCE model with sapphire crystal.', 'naviforce_prestige.jpg', false, 'Stainless Steel', 46, '30m', 'Male'),
(1, 5, 'TIMEX Heritage', 195.00, 9, 'TIMEX', 'Classic luxury model with heritage-inspired design.', 'timex_heritage.jpg', false, 'Leather', 42, '30m', 'Male'),
(1, 5, 'G-Shock High-End Resin', 500.00, 5, 'G-Shock', 'Exclusive resin edition with upgraded features.', 'gshock_highend.jpg', false, 'Resin', 50, '200m', 'Male'),
(1, 5, 'CASIO Oceanus', 700.00, 3, 'CASIO', 'Premium CASIO Oceanus series with solar power.', 'casio_oceanus.jpg', false, 'Titanium', 47, '100m', 'Male');

-- ANALOG Category Products
INSERT INTO product (admin_id, category_id, name, price, quantity, brand, description, image, is_deleted, material, case_diameter, water_resistance, gender) VALUES
(1, 6, 'G-Shock Analog Carbon Core', 120.00, 20, 'G-Shock', 'Analog G-Shock with carbon core guard structure.', 'gshock_analog.jpg', false, 'Resin', 48, '200m', 'Male'),
(1, 6, 'CASIO Analog MQ24', 20.00, 50, 'CASIO', 'Simple analog watch, perfect for everyday wear.', 'casio_mq24.jpg', false, 'Plastic', 35, '30m', 'Unisex'),
(1, 6, 'Fossil Jacqueline', 125.00, 18, 'Fossil', 'Classic analog watch with delicate design.', 'fossil_jacqueline.jpg', false, 'Leather', 36, '30m', 'Female'),
(1, 6, 'ALBA Traditional', 60.00, 22, 'ALBA', 'Analog ALBA with timeless design.', 'alba_traditional.jpg', false, 'Metal', 42, '30m', 'Unisex'),
(1, 6, 'CITIZEN Analog Field', 210.00, 12, 'CITIZEN', 'Analog field watch with eco-drive.', 'citizen_field.jpg', false, 'Stainless Steel', 44, '100m', 'Male'),
(1, 6, 'TOMMY HILFIGER Analog Dress', 155.00, 15, 'TOMMY HILFIGER', 'Dress watch with a minimalist analog face.', 'tommy_dress.jpg', false, 'Leather', 40, '30m', 'Female'),
(1, 6, 'NAVIFORCE Analog Chrono', 95.00, 25, 'NAVIFORCE', 'Chronograph analog watch with date display.', 'naviforce_chrono.jpg', false, 'Metal', 45, '50m', 'Male'),
(1, 6, 'TIMEX Easy Reader', 50.00, 30, 'TIMEX', 'Classic analog with easy-to-read dial.', 'timex_easyreader.jpg', false, 'Leather', 38, '30m', 'Unisex'),
(1, 6, 'G-Shock Analog-Digital', 175.00, 10, 'G-Shock', 'Analog-digital hybrid with LED backlight.', 'gshock_analog_digital.jpg', false, 'Resin', 46, '200m', 'Male'),
(1, 6, 'CASIO MTP-V002', 35.00, 40, 'CASIO', 'Affordable analog watch with a stainless steel case.', 'casio_mtp.jpg', false, 'Stainless Steel', 39, '50m', 'Male');



use ecommerce;
UPDATE product
SET image = CONCAT('/img/watch/', id, '.jpg')
WHERE id BETWEEN 51 AND 60;