-- --------------------------------------------------------
-- Host:                         localhost
-- Server version:               8.0.30 - MySQL Community Server - GPL
-- Server OS:                    Linux
-- HeidiSQL Version:             12.0.0.6468
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

-- Dumping data for table ponesi.cash_payment: ~16 rows (approximately)

-- Dumping data for table ponesi.category: ~0 rows (approximately)

-- Dumping data for table ponesi.cc_payment: ~0 rows (approximately)
INSERT INTO `cc_payment` (`order_payment_id`, `credit_card_id`) VALUES
	(17, 3);

-- Dumping data for table ponesi.country: ~4 rows (approximately)
INSERT INTO `country` (`id`, `shorthand`, `full_name`) VALUES
	(1, 'BIH', 'Bosnia and Herzegovina'),
	(2, 'SRB', 'Serbia'),
	(3, 'HRV', 'Croatia'),
	(4, 'MNE', 'Montenegro');

-- Dumping data for table ponesi.courier: ~4 rows (approximately)
INSERT INTO `courier` (`id`, `name`) VALUES
	(1, 'Drago'),
	(2, 'Jovan'),
	(3, 'Sreten'),
	(4, 'Dron');

-- Dumping data for table ponesi.credit_card: ~1 rows (approximately)
INSERT INTO `credit_card` (`id`, `name`, `card_number`, `exp_date`, `cvc`, `country_id`, `label`, `user_id`) VALUES
	(1, 'Marko kard', '1234123412353111', '12/13', '123', 1, NULL, 1),
	(3, 'Marko Markovic', '1234123412341234', '12/23', '123', 1, 'Labela', 1);

-- Dumping data for table ponesi.delivery_type: ~3 rows (approximately)
INSERT INTO `delivery_type` (`id`, `name`) VALUES
	(1, 'Car'),
	(2, 'Moped'),
	(3, 'Bicycle');

-- Dumping data for table ponesi.extra_group: ~5 rows (approximately)
INSERT INTO `extra_group` (`id`, `name`, `max_choices`, `min_choices`) VALUES
	(1, 'Need cutlery?', 1, 0),
	(2, 'Need cutlery? Sticks?', 1, 0),
	(3, 'Extra vegetables', 5, 0),
	(4, 'Want any drinks with that?', 3, 0),
	(5, 'Sauce kind', 1, 0);

-- Dumping data for table ponesi.item: ~194 rows (approximately)
INSERT INTO `item` (`id`, `restaurant_id`, `name`, `description`, `price`, `item_kind_id`) VALUES
	(1, 2, 'MCB Banjalu??ki kraft plati?? 10l, dobije?? 2l gratis', NULL, 55.00, 19),
	(2, 2, 'MCB Pilsner plati?? 10l, dobije?? 2l gratis', NULL, 55.00, 19),
	(3, 2, 'MCB Pale ale plati?? 10l, dobije?? 2l gratis', NULL, 70.00, 19),
	(4, 2, 'MCB Banjalu??ki kraft 4x330 ml', '4x330 ml', 11.50, 20),
	(5, 2, 'MCB Pilsner 4x330 ml', '4x330 ml', 11.50, 20),
	(6, 2, 'MCB Pale ale 4x330 ml', '4x330 ml', 13.00, 20),
	(7, 2, 'MCB IPA 4x330 ml', '4x330 ml', 14.00, 20),
	(8, 2, 'MCB Stout 4x330 ml', '4x330 ml', 13.50, 20),
	(9, 2, 'MCB mix promo paket 4x330 ml', 'Banjalu??ki kraft, pale ale, ipa, stout', 12.50, 20),
	(10, 2, 'MCB poklon vre??ica sa podmeta??em za pivo 4/1', NULL, 2.00, 20),
	(11, 2, 'Master doru??ak i kafa gratis', 'Jaja na oko, doma??a kobasica sa sirom, prilog pavlaka, pecivo', 7.00, 14),
	(12, 2, 'Omlet sa Cheddar sirom i kafa gratis', 'Jaja, Cheddar sir, prilog pavlaka, pecivo', 5.00, 14),
	(13, 2, 'Omlet sa piletinom i kafa gratis', 'Jaja, grilovani pile??i file, Cheddar sir, prilog pavlaka, pecivo', 5.50, 14),
	(14, 2, 'Omlet sa ??unkom i kafa gratis', 'Jaja, ??unka, prilog pavlaka, pecivo', 5.00, 14),
	(15, 2, 'Omlet sa ??unkom i Cheddar sirom i kafa gratis', 'Jaja, ??unka, Cheddar sir, prilog pavlaka, pecivo', 6.00, 14),
	(16, 2, 'Omlet sa piletinom i sirom, kafa gratis', 'Jaja, grilovani pile??i file, cheddarsir, pavlaka, pecivo', 6.50, 14),
	(17, 2, 'Burger Master Smith', 'Burger od ??istog june??eg vrata, pomfrit, Cheddar sir, paradajz, zelena salata, sos sa majonezom', 12.00, 3),
	(18, 2, 'Burger s june??im mesom', 'Burger od ??istog june??eg vrata, paradajz, zelena salata, sos sa lukom', 10.50, 3),
	(19, 2, 'Burger smoked rooster', 'Dimljeni upajcani pile??i batak, tartar sos, paradajz, zelena salata, pomfrit', 12.00, 3),
	(20, 2, 'MCB burger sa sosom od kamenica', 'Burger od ??istog june??eg vrata, sos od kamenica, soja sos, doma??i potkozarski med, Cheddar sir, tartar sos, pomfrit', 12.00, 3),
	(21, 2, 'Doma??e kobasice sa sirom i marmeladom od luka', 'Doma??a kobasica punjena sa dvije vrste sira, pomfrit, marmelada od luka', 11.00, 21),
	(22, 2, 'Hrskavi pile??i ??tapi??i', 'Pile??i file pohovan susamom, pomfrit, tartar sos', 9.50, 21),
	(23, 2, 'Pikantne kobasice sa sosom goru??ice', 'Doma??a ljuta kobasica, pomfrit, sos sa pavlakom i zrnom goru??ice', 11.00, 21),
	(24, 2, 'Pile??a krilca u slatko-ljutom sosu', 'Pile??a krilca, slatko-ljuti tajlandski sos', 10.50, 21),
	(25, 2, 'Pile??i file s njokama u pikant sosu', 'Grilovani pile??i file, njoke, pikant sos', 11.00, 21),
	(26, 2, 'Pivske kobasice sa sirom urolane u slaninu', 'Berner Wurstel, pomfrit,', 14.00, 21),
	(27, 2, 'Rebarca sa sosom od kamenica', 'Svinjska rebra, pekarski krompir, sos od kamenica, soja sos, doma??i potkozarski med', 16.00, 21),
	(28, 2, 'Zape??ena koljenica sa kremom od hrena', 'Zape??ena svinjska dimljena koljenica, krema od hrena i pavlake, prilog pomfrit', 16.00, 21),
	(29, 2, 'Pile??i dimljeni batak', 'Dimljeni upajcani pile??i batak, curry sos, pomfrit', 10.50, 21),
	(30, 2, 'MCB plata 3 kobasice', 'Kobasice sa dvije vrste sira i marmeladom od luka, pivske kobasice sa sirom urolane u slaninu i pikantne kobasice sa sosom od goru??ice, prilog pomfrit', 34.00, 22),
	(31, 2, 'Master plata za 4 osobe', 'Rebarca u sosu od kamenica sa potkozarskim medom, krilca u slatko-ljutom tajlandskom sosu, kobasice sa dvije vrste sira i marmeladom od luka, prilog pomfrit', 35.00, 22),
	(32, 2, 'Pomfrit', 'Pomfrit', 3.50, 23),
	(33, 2, 'Pekarski krompir', 'Pekarski krompir', 4.00, 23),
	(34, 2, '??aj Milford', 'Kamilica, menta, vo??ni', 2.00, 24),
	(35, 2, 'Limunada', 'Limunada', 3.00, 17),
	(36, 2, 'Cedevita narand??a', 'Cedevita narand??a', 3.00, 17),
	(37, 2, 'Vitaminka sokovi', 'Borovnica, jabuka, ??us', 3.00, 17),
	(38, 2, 'Cockta', 'Cockta', 3.00, 17),
	(39, 2, 'Orangina', 'Orangina', 3.50, 17),
	(40, 2, 'Red Bull', 'Energetsko pi??e', 6.00, 17),
	(41, 2, 'MCB Banjalu??ki kraft 2 l', '(Cijena piva je 5.50 KM/L, a u pakovanju su 2L) (Lagano i pitko, zanatski ra??eno pivo. Na?? poklon Banjaluci, session u koji smo ugradili mnogo ljubavi i pa??nje, sa 4,5% alkohola, idealno je pivo sa nje??nim cvjetnim i citrusnim notama za hidrataciju)', 11.00, 25),
	(42, 2, 'MCB Pilsner 2 l', '(Cijena piva je 5,50 KM/L, a u pakovanju su 2L) (Zlatno ??ute boje sa bogatom bijelom pjenom, ovo pivo je na?? odgovor na industrijski lager. Bogate cvijetno bivskvitne note, sa malo zaostale slatko??e koja dolazi od plemenitom njema??kog hmelja)', 11.00, 25),
	(43, 2, 'MCB Pale ale 2 l', '(Cijena piva je 7,00 KM/L, a u pakovanju su 2L) (Pale ale koji ??e Vas odu??evljavati svakim gutljajem sastavaljen kombinacijom pa??ljivo biranih njema??kih sladova i ameri??kih sorti hmelja, koje daju arome mandaraine i grejpa sa tragovima borovine)', 14.00, 25),
	(44, 2, 'MCB poklon vre??ica sa podmeta??em za pivo 2L', NULL, 2.00, 25),
	(45, 2, 'Cappucinno', 'Cappucinno', 2.00, 26),
	(46, 2, 'Espresso kratki', 'Espresso kratki', 1.50, 26),
	(47, 2, 'Hladna kafa', 'Hladna kafa', 2.50, 26),
	(48, 2, 'Hladni Nesscafe', 'Hladni Nesscafe', 2.50, 26),
	(49, 2, 'Ness Cappuccino', 'Ness Cappuccino. ??okolada, vanilia', 2.50, 26),
	(50, 2, 'Nesscafe', 'Nesscafe', 2.00, 26),
	(51, 2, 'Espresso dugi', 'Espresso dugi', 1.50, 26),
	(52, 2, 'Espresso sa mlijekom', 'Espresso sa mlijekom', 2.00, 26),
	(53, 2, 'Espresso sa ??lagom', 'Espresso sa ??lagom', 2.00, 26),
	(54, 2, 'MCB Banjalu??ki kraft 0,33 l', '0,33 l', 4.50, 27),
	(55, 2, 'MCB Pilsner 0,33 l', '0,33 l', 4.50, 27),
	(56, 2, 'MCB Pale Ale 0,33 l', '0,33 l', 5.00, 27),
	(57, 2, 'MCB Stout 0,33 l', '0,33 l', 6.00, 27),
	(58, 2, 'IPA 0,33 l', '0,33 l', 6.00, 27),
	(59, 3, 'Ferrero Rocher pala??inka', 'Namaz ferrero, preliv Nutella i lje??njak, crne mrvice, sladoled', 7.00, 1),
	(60, 3, 'Raffaello pala??inka', 'Raffaello pala??inka', 7.00, 1),
	(61, 3, 'Maxi king pala??inka', 'Namaz, linolada gold, krema, lje??njak, karamel, preliv Nutella, crne mrvice, sladoled', 7.00, 1),
	(62, 3, 'Bueno pala??inka', 'Namaz bueno, preliv bueno, crne mrvice, sladoled', 7.00, 1),
	(63, 3, 'Bueno white pala??inka', 'Namaz bueno, preliv bijela ??okolada, crne mrvice', 7.00, 1),
	(64, 3, 'Bueno black pala??inka', 'Namaz bueno, previl Nutella, crne mrvice', 7.00, 1),
	(65, 3, 'Kit Kat pala??inka', 'Namaz linolada gold, preliv linolada kit kat, punjenje i dekoracija, sladoled', 7.00, 1),
	(66, 3, 'Bueno Coconut pala??inka', 'Bueno Coconut pala??inka', 7.00, 1),
	(67, 3, 'Snickers pala??inka', 'Namaz linolada, karamel, kikiriki, preliv linolada i karamel', 7.00, 1),
	(68, 3, 'Schwarzwald pala??inka', 'Schwarzwald pala??inka', 7.00, 1),
	(69, 3, 'Nutella Banana pala??inka', 'Nutella Banana pala??inka', 7.00, 1),
	(70, 3, 'Pop\'s pala??inka', 'Pop\'s pala??inka', 7.00, 1),
	(71, 3, 'Bounty pala??inka', 'Bounty pala??inka', 7.00, 1),
	(72, 3, 'Twix pala??inka', 'Twix pala??inka', 7.00, 1),
	(73, 3, 'Twix whit pala??inka', 'Twix whit pala??inka', 7.00, 1),
	(74, 3, 'Kinder pala??inka', 'Kinder pala??inka', 7.00, 1),
	(75, 3, 'Banana whit pala??inka', 'Banana whit pala??inka', 7.00, 1),
	(76, 3, 'Nutella pala??inka', 'Nutella pala??inka', 7.00, 1),
	(77, 3, 'Strawberry pala??inka', 'Strawberry pala??inka', 7.00, 1),
	(78, 3, 'Linolada black pala??inka', 'Linolada black pala??inka', 7.00, 1),
	(79, 3, 'Linolada white pala??inka', 'Banana Crounch pala??inka', 7.00, 1),
	(80, 3, 'Cherry pala??inka', 'Cherry pala??inka', 7.00, 1),
	(81, 3, 'Blueberry pala??inka', 'Blueberry pala??inka', 7.00, 1),
	(82, 3, '??umsko vo??e pala??inka', '??umsko vo??e pala??inka', 7.00, 1),
	(83, 3, 'Pop\'s American pala??inke', 'Pop\'s American pala??inke', 8.00, 2),
	(84, 3, 'Cherry American pala??inke', 'Namaz vi??nja, preliv bijela ??okolada, vi??nje, crne mrvice', 8.00, 2),
	(85, 3, 'Blueberry American pala??inke', 'Namaz borovnica, preljev bijela ??okolada, borovnica, sladoled', 8.00, 2),
	(86, 3, 'Strawberry American pala??inke', 'Namaz jagoda, preliv linolada i sladoled', 8.00, 2),
	(87, 3, 'Schwarzwald American pala??inke', 'Schwarzwald American pala??inke', 8.00, 2),
	(88, 3, 'Nutella banana American pala??inke', 'Namaz Nutella, banana, plazma, preliv Nutella, ??okoladica b-ready', 8.00, 2),
	(89, 3, 'Ferrero Rocher American pala??inke', 'Namaz Ferrero, preliv Nutella, lje??njak, ??okoladica Ferrero, crne mrvice', 8.00, 2),
	(90, 3, 'Maxi king American pala??inke', 'Namaz nutella, krema, karamel, lje??njak, preliv Nutella, crne mrvice', 8.00, 2),
	(91, 3, 'Bueno American pala??inke', 'Namaz bueno, preliv bueno, ??okoladica bueno, crne mrvice', 8.00, 2),
	(92, 3, 'Nutella American pala??inke', 'Namaz Nutella, preliv, sladoled', 8.00, 2),
	(93, 3, 'Snickers American pala??inke', 'Namaz Nutella, karamel, kikiriki, preliv Nutella', 8.00, 2),
	(94, 3, 'Raffaello American pala??inke', 'Namaz linolada kokos, preliv bijela ??okolada, kokos, ??okoladica raffaello i bademi', 8.00, 2),
	(95, 3, 'Burger', 'Govedina 50 gr, 50 gr pecivo', 3.50, 3),
	(96, 3, 'Cheeseburger', 'Pecivo, govedina, sir, burger sos, ketchup, kiseli krastavac, salata', 3.50, 3),
	(97, 3, 'Mexican burger', 'Pecivo, govedina, sir, chilli majoneza, sriracha, ketchup, kiseli krastavac, salata', 3.50, 3),
	(98, 3, 'BBQ burger', '100 % premium gove??e meso, sir, BBQ sos, pops umak, pardajz, zelena salata, krastavac', 3.50, 3),
	(99, 3, 'Greek burger', '100 % premium gove??e meso, feta sir, BBQ sos, ketchup, paradajz, zelena salata, krastavac', 3.50, 3),
	(100, 3, 'Chickenburger', '130 gr pohovana piletina, sir, majoneza, paradajz, zelena salata', 3.50, 3),
	(101, 3, 'Cesar chicken burger', '130 gr pohovana piletina, sir, cesar sos, paradajz, zelena salata, krastavac', 3.50, 3),
	(102, 3, 'Burger meni', 'Big burger, pomfrit, pi??e', 7.50, 4),
	(103, 3, 'Cheeseburger meni', 'Big burger, pomfrit, pi??e', 7.50, 4),
	(104, 3, 'Mexican burger meni', 'Big burger, pomfrit, pi??e', 7.50, 4),
	(105, 3, 'BBQ burger meni', 'Big burger, pomfrit, pi??e', 7.50, 4),
	(106, 3, 'Greek burger meni', 'Big burger, pomfrit, pi??e', 7.50, 4),
	(107, 3, 'Chicken burger meni', 'Chicken burger, pomfrit, pi??e', 7.50, 4),
	(108, 3, 'Cesar chicken burger meni', 'Cesar chicken burger, pomfrit, pi??e', 7.50, 4),
	(109, 3, 'Premium classic burger', 'Pecivo, 140 gr gove??e meso, sir, burger sos, ketchup, kiseli krastavac, salata', 8.00, 5),
	(110, 3, 'Premium American Monster', 'Pecivo, 140 gr gove??e meso, sir, jaja, burger sos, karamelizirani luk, ketchup, kiseli krastavac, salata', 8.00, 5),
	(111, 3, 'Premium Texas Burger', 'Pecivo, 140 gr gove??e meso, sir, burger sos, karamelizirani luk, ketchup, kiseli krastavac, salata', 8.00, 5),
	(112, 3, 'Premium Pop\'s Burger', 'Pecivo, 140 gr gove??e meso, sir, burger sos, ketchup, karamelizirani luk, kiseli krastavac, salata', 8.00, 5),
	(113, 3, 'Premium Hot burger', 'Pecivo, 140 gr gove??e meso, sir, srisacha sos, karamelizirani luk, chilli majoneza, kiseli krastavac, salata', 8.00, 5),
	(114, 3, 'Premium Mr. Jack Chickenburger', 'Pecivo, Mr. Jack pohovana piletina, sir, BBQ sos, Pops sos, salata', 8.00, 5),
	(115, 3, 'Classic burger premium meni', 'Burger, pomfrit, pi??e', 11.40, 6),
	(116, 3, 'American Monster premium meni', 'Burger, pomfrit, pi??e', 11.40, 6),
	(117, 3, 'Texas burger premium meni', 'Burger, pomfrit, pi??e', 11.40, 6),
	(118, 3, 'Pop\'s burger premium meni', 'Burger, pomfrit, pi??e', 11.40, 6),
	(119, 3, 'Hot burger premium meni', 'Burger, pomfrit, pi??e', 11.40, 6),
	(120, 3, 'Mr. Jack Chickenburger premium meni', 'Chickenburger, pomfrit, pi??e', 11.40, 6),
	(121, 3, 'Tortilja Beef classic', 'Tortilja, govedina, sir, burger sos, paradajz, salata', 8.00, 7),
	(122, 3, 'Tortilja POP\'s', 'Tortilja, 120 gr pohovane piletine, sir Chedar feta sir, Pops sos, paradajz, salata', 8.00, 7),
	(123, 3, 'Tortilja Mr Jack', 'Tortilja, Mr. Jack piletina, sir, BBQ sos, paradajz, salata', 8.00, 7),
	(124, 3, 'Tortilja Tex Mex', 'Tortilja, pohovana piletina, Cheddar, Pops sos, paradajz, salata', 8.00, 7),
	(125, 3, 'Tortilja Bangkok', 'Tortilja, pohovana piletina, Cheddar, Bankok sos, paradajz, salata', 8.00, 7),
	(126, 3, 'Tortilja Classic', 'Tortilja, piletina, feta sir, Cesar sos, kukuruz, paradajz, salata', 8.00, 7),
	(127, 3, 'Tortilja Mexican', 'Tortilja, piletina, Cheddar, sriracha, luk, chilli majonez, paradajz, salata', 8.00, 7),
	(128, 3, 'Tortilja Beef classic meni', 'Tortilja, pomfrit, pi??e', 11.40, 8),
	(129, 3, 'Tortilja POP\'S meni', 'Tortilja, pomfrit, pi??e', 11.40, 8),
	(130, 3, 'Tortilja Mr Jack Meni', 'Tortilja, pomfrit, pi??e', 11.40, 8),
	(131, 3, 'Tortilja Tex Mex meni', 'Tortilja, pomfrit, pi??e', 11.40, 8),
	(132, 3, 'Tortilja Bangkok meni', 'Tortilja, pomfrit, pi??e', 11.40, 8),
	(133, 3, 'Tortilja Classic meni', 'Tortilja, pomfrit, pi??e', 11.40, 8),
	(134, 3, 'Tortilja Mexicana meni', 'Tortilja, pomfrit, pi??e', 11.40, 8),
	(135, 3, 'Tortilja Curry meni', 'Tortilja, pomfrit, pi??e', 11.50, 8),
	(136, 3, 'Tortilja Quritto meni', 'Tortilja, pomfrit, pi??e', 11.40, 8),
	(137, 3, 'Classic salata', 'Piletine, cesar sos, salata', 8.00, 9),
	(138, 3, 'Tex Mex salata', 'Pohovane piletine, Pops sos, salata', 8.00, 9),
	(139, 3, 'Bangkok salata', 'Pohovane piletine, salata, Bankok sos', 8.00, 9),
	(140, 3, 'Burger salata', 'Burger, Pops sos, salata', 8.00, 9),
	(141, 3, 'Mr. Jack salata', 'Mr. Jack piletine, BBQ sos, salata', 8.00, 9),
	(142, 3, 'Piletina', 'Piletina 300 gr, pomfrit + pecivo', 11.40, 10),
	(143, 3, 'Pohovana piletina', 'Pohovana piletina 300 gr, pomfrit, pecivo', 11.40, 10),
	(144, 3, 'Tex Mex piletina', 'Pohovana piletina 300 gr, pomfrit, pecivo', 11.40, 10),
	(145, 3, 'Bankok piletina', 'Piletina 300 gr, pomfrit, pecivo', 11.40, 10),
	(146, 3, 'Veggieburger', 'Veggieburger', 4.50, 11),
	(147, 3, 'Fishburger', 'Mala ili velika porcija', 3.50, 11),
	(148, 3, 'Pohovane lignje', 'Mala ili velika porcija', 3.50, 11),
	(149, 3, 'Mr. Jack Fingers', '200 gr pile??eg mesa', 9.00, 12),
	(150, 3, 'Big Mr. Jack Fingers', '300 gr pile??eg mesa', 12.50, 12),
	(151, 3, 'Big Mr. Jack Fingers Meni', '300 gr pile??eg mesa, Coca-Cola, pomfrit', 12.50, 12),
	(152, 3, 'Burger Kids meni', 'Burger, pomfrit, sok, igra??ka gratis', 8.40, 13),
	(153, 3, 'Nuggets Kids meni', 'Nuggets, pomfrit, sok, igra??ka gratis', 8.40, 13),
	(154, 3, 'Chicken burger Kids meni', 'Chicken burger, pomfrit, sok, igra??ka gratis', 8.40, 13),
	(155, 3, 'Cheeseburger Kids', 'Cheeseburger', 3.50, 13),
	(156, 3, 'Chicken burger Kids', 'Chicken burger', 3.50, 13),
	(157, 3, 'Tost Pop\'s', 'Piletina 150 gr, sir 120 gr, tost, pops umak', 6.40, 14),
	(158, 3, 'Tost Bangkok', 'Piletina 150 gr, sir 120 gr, tost, bangkok sos', 6.40, 14),
	(159, 3, 'Hot dog', 'Hot dog', 3.50, 14),
	(160, 3, 'Pomfrit junior 100 gr', '100 gr', 3.00, 15),
	(161, 3, 'Big pomfrit', 'Big pomfrit', 3.50, 15),
	(162, 3, 'Nuggets', '6 kom ili 10 kom', 4.50, 15),
	(163, 3, 'Nuggets meni', 'Mali ili veliki meni', 9.50, 15),
	(164, 3, 'Milkshake Ice coffie', 'Ice coffie', 4.00, 16),
	(165, 3, 'Milkshake ??umsko vo??e', 'Milkshake ??umsko vo??e', 4.00, 16),
	(166, 3, 'Milkshake Banana', 'Milkshake Banana', 4.00, 16),
	(167, 3, 'Milkshake Vanilija', 'Milkshake Vanilija', 4.00, 16),
	(168, 3, 'Milkshake ??okolada', 'Milkshake ??okolada', 4.00, 16),
	(169, 3, 'Milkshake Jagoda', 'Milkshake Jagoda', 4.00, 16),
	(170, 3, 'Coca-Cola Zero 0.25 l', '0.25 l', 3.00, 17),
	(171, 3, 'Coca-Cola 0.25 l', '0.25 l', 3.00, 17),
	(172, 3, 'Sprite 0.25 l', '0.25 l', 3.00, 17),
	(173, 3, 'Cappy 0.2 l', '0.2 l', 3.00, 17),
	(174, 3, 'Ledeni ??aj 0.25 l', '0.25 l', 2.50, 17),
	(175, 3, 'Voda 0.33 l', '0.33 l', 2.00, 17),
	(176, 3, 'Voda mineralna 0.33 l', '0.33 l', 2.00, 17),
	(177, 3, 'Cedevita', 'Limun ili narand??a', 2.50, 17),
	(178, 3, 'Red bull 0.25 l', '0.25 l', 5.00, 17),
	(179, 3, 'Pop\'s sushi pancake', 'Namaz bijela ??okolada, ??umsko vo??e , preljev bijela ??okolada', 8.00, 18),
	(180, 3, 'Banana white sushi pancake', 'Namaz crounch, banana, preljev bijela ??okolada, sladoled', 8.00, 18),
	(181, 3, 'Cherry sushi pancake', 'Namaz vi??nja, preljev bijela ??okolada, sladoled', 8.00, 18),
	(182, 3, 'Blueberry sushi pancake', 'Namaz borovnica , preljev bijela ??okolada, borovnica, sladoled', 8.00, 18),
	(183, 3, 'Strawberry sushi pancake', 'Namaz Nuttella, krema vi??nja, preljev bijela ??okolada, sladoled', 8.00, 18),
	(184, 3, 'Schwarzwald sushi pancake', 'Namaz jagoda, preljev bijela ??okolada, sladoled', 8.00, 18),
	(185, 3, 'Nutella banana sushi pancake', 'Namaz Nuttela,banana, preljev Nuttella, sladoled', 8.00, 18),
	(186, 3, 'Ferrero rocher sushi pancake', 'Namaz linolada gold, preljev Nuttella i lje??njak, sladoled', 8.00, 18),
	(187, 3, 'Maxi king sushi pancake', 'Namaz linolada gold krema, karamel, lje??njak, preljev Nuttella, sladoled', 8.00, 18),
	(188, 1, 'Gong Pao piletina', 'Piletina, mix povr??a i kikiriki u umaku od soja sosa i sosa od kamenica. Ri??a ili p??eni??ne nudle', 9.80, 28),
	(189, 1, 'Fit wok', 'Piletina ili svinjetina ili teletina, mix povr??a, umak od soja sosa i sos od kamenica. Piletina ili svinjetina ili teletina. Pr??ena ri??a, p??eni??ne nudle, ri??a ili pr??ene p??eni??ne nudle', 10.50, 28),
	(190, 1, 'Mongolska teletina', 'Teletina, brokule i paprika u soja i Oyster sosu. Pr??ena ri??a, p??eni??ne nudle, ri??a ili pr??ene p??eni??ne nudle', 13.00, 28),
	(191, 1, 'Steak wok za gljivama i ??umbirom', 'Teletina, gljive i ??umbir u umak od soja sosa i Hoisin sosa, ri??a', 13.50, 28),
	(192, 1, 'Slatko kisela piletina ili svinjetina', 'Piletina ili svinjetina, mix povr??e u slatkom i kiselom umaku. Ri??a ili p??eni??ne nudle', 10.50, 28),
	(193, 1, '??areni wok', 'Pr??ena ri??a, p??eni??ne nudle, ri??a ili pr??ene p??eni??ne nudle, meso pile??e ili svinjsko ili tele??e u woker sosu.', 11.50, 28),
	(194, 1, 'Vegi wok', 'Mix povr??e u paradajz sosu i Hoisin sosu. Ri??a ili p??eni??ne nudle', 8.90, 28);

-- Dumping data for table ponesi.item_extra: ~7 rows (approximately)
INSERT INTO `item_extra` (`id`, `extra_group_id`, `name`, `additional_cost`) VALUES
	(1, 2, 'None', 0.00),
	(2, 2, 'Knife and fork', 0.00),
	(3, 2, 'Sticks', 0.00),
	(4, 3, 'Tomatoes', 1.00),
	(5, 3, 'Peas', 0.50),
	(6, 3, 'Corn', 0.80),
	(7, 3, 'Rice', 0.50);

-- Dumping data for table ponesi.item_has_item_extra: ~10 rows (approximately)
INSERT INTO `item_has_item_extra` (`item_id`, `item_extra_id`) VALUES
	(188, 1),
	(189, 1),
	(188, 2),
	(189, 2),
	(188, 3),
	(189, 3),
	(189, 4),
	(189, 5),
	(189, 6),
	(189, 7);

-- Dumping data for table ponesi.item_kind: ~28 rows (approximately)
INSERT INTO `item_kind` (`id`, `name`) VALUES
	(20, 'Akcija MCB piva u fla??ici 4/1'),
	(19, 'AKCIJA Plati?? 10l a dobije?? 12l'),
	(2, 'American pala??inke'),
	(4, 'Burger meni'),
	(3, 'Burgeri'),
	(24, '??aj'),
	(13, 'Dje??ji meni'),
	(14, 'Doru??ak'),
	(28, 'Glavna jela'),
	(21, 'Jela sa ro??tilja'),
	(27, 'MCB - KRAFT PIVA 0.33L'),
	(25, 'MCB to??eno pivo za ponijeti'),
	(16, 'Milkshake'),
	(12, 'Mr. Jack Piletina'),
	(1, 'Pala??inke'),
	(17, 'Pi??a'),
	(22, 'Plate'),
	(10, 'Pop\'s piletina'),
	(6, 'Premium burger meni'),
	(5, 'Premium burgeri'),
	(23, 'Prilog'),
	(9, 'Salate'),
	(15, 'Snacks'),
	(18, 'Sushi Pancake'),
	(26, 'Topla pi??a'),
	(8, 'Tortilja meni'),
	(7, 'Tortilje'),
	(11, 'Veggie & fish');

-- Dumping data for table ponesi.order: ~4 rows (approximately)
INSERT INTO `order` (`id`, `user_id`, `order_payment_id`, `order_address`, `courier_id`, `delivery_type_id`) VALUES
	(7, 1, NULL, NULL, 1, 2),
	(8, 1, NULL, NULL, 4, 3),
	(9, 1, NULL, NULL, 4, 1),
	(10, 1, NULL, NULL, 4, 1),
	(11, 1, NULL, NULL, 1, 2),
	(12, 1, NULL, NULL, 3, 3),
	(13, 1, NULL, NULL, 1, 1),
	(14, 1, 17, NULL, 4, 2);

-- Dumping data for table ponesi.order_has_order_status: ~0 rows (approximately)
INSERT INTO `order_has_order_status` (`order_id`, `order_status_id`, `timestamp`) VALUES
	(14, 1, '2022-09-07 12:34:45');

-- Dumping data for table ponesi.order_item: ~9 rows (approximately)
INSERT INTO `order_item` (`id`, `item_id`, `order_id`, `ordered_item_price`, `quantity`) VALUES
	(10, 188, 8, 9.80, 1),
	(11, 189, 8, 10.50, 1),
	(12, 188, 9, 9.80, 1),
	(13, 189, 9, 10.50, 1),
	(14, 190, 9, 13.00, 1),
	(15, 191, 9, 13.50, 1),
	(16, 1, 10, 55.00, 1),
	(17, 2, 10, 55.00, 1),
	(18, 3, 10, 70.00, 1),
	(19, 59, 11, 7.00, 1),
	(20, 60, 11, 7.00, 1),
	(21, 188, 12, 9.80, 1),
	(22, 189, 12, 10.50, 1),
	(23, 188, 13, 9.80, 1),
	(24, 189, 13, 10.50, 1),
	(25, 188, 14, 9.80, 1),
	(26, 189, 14, 10.50, 1);

-- Dumping data for table ponesi.order_item_has_item_extra: ~7 rows (approximately)
INSERT INTO `order_item_has_item_extra` (`order_item_id`, `item_extra_id`, `ordered_extra_price`, `ordered_extra_quantity`) VALUES
	(11, 2, 0.00, 1),
	(11, 4, 1.00, 1),
	(11, 5, 0.50, 1),
	(11, 6, 0.80, 1),
	(12, 2, 0.00, 1),
	(13, 5, 0.50, 1),
	(13, 7, 0.50, 1);

-- Dumping data for table ponesi.order_payment: ~16 rows (approximately)
INSERT INTO `order_payment` (`id`) VALUES
	(17);

-- Dumping data for table ponesi.order_status: ~6 rows (approximately)
INSERT INTO `order_status` (`id`, `status`) VALUES
	(1, 'Waiting for restaurant'),
	(2, 'Received'),
	(3, 'Preparing'),
	(4, 'Awaiting pickup'),
	(5, 'Picked up'),
	(6, 'Delivered');

-- Dumping data for table ponesi.restaurant: ~3 rows (approximately)
INSERT INTO `restaurant` (`id`, `name`, `wait_time`, `address`, `has_takeout`, `accepting_orders`) VALUES
	(1, 'WOKER', '30-45m', '', 1, 1),
	(2, 'The Mastercraft Brewery', '25-30m', '', 1, 1),
	(3, 'POP\'s TC Delta', '30-45m', '', 1, 1);

-- Dumping data for table ponesi.restaurant_has_category: ~0 rows (approximately)

-- Dumping data for table ponesi.review: ~0 rows (approximately)

-- Dumping data for table ponesi.user: ~5 rows (approximately)
INSERT INTO `user` (`id`, `username`, `email`, `password`, `address`, `name`) VALUES
	(1, 'marko', 'm@mail.com', 'mpwd', 'Karadjordjeva 13, Banja Luka', 'Marko Markovic'),
	(2, 'simo', 's@mail.com', 'spwd', 'Patre 8, Banja Luka', 'Simo Simic'),
	(3, 'pero', 'p@mail.com', 'ppwd', 'Carice Milice 31, Banja Luka', 'Pero Peric'),
	(4, 'dejo', 'd@mail.com', 'dpwd', 'Orlovacki put 15, Banja Luka', 'Dejan Mitrovic'),
	(5, 'deki', 'd@mail.com', 'dpwddw', 'Bul. Voj. S. St. 134', 'Dejan Dejanovic');

-- Dumping data for table ponesi.working_hours: ~0 rows (approximately)

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
