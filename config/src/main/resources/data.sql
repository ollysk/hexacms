INSERT IGNORE INTO `node` (`id`, `body`, `teaser`) VALUES ('1', '1 It works! This is long long long long long long lorem ipsum', 'It works!');
INSERT IGNORE INTO `node_meta` (`id`, `changed`, `created`, `ip`, `is_published`, `lang_id`, `path`, `title`, `user_id`) VALUES ('1', '2020-02-16 23:19:15', '2020-02-16 23:19:15', '12', '1', '1', '20/02/16/1', 'Hexa news #1', '1');
INSERT IGNORE INTO `node` (`id`, `body`, `teaser`) VALUES ('2', '2 It works! This is long long long long long long lorem ipsum', 'It works!');
INSERT IGNORE INTO `node_meta` (`id`, `changed`, `created`, `ip`, `is_published`, `lang_id`, `path`, `title`, `user_id`) VALUES ('2', '2020-02-16 23:19:15', '2020-02-17 23:19:15', '12', '1', '1', '20/02/16/2', 'Hexa news #1', '1');
INSERT IGNORE INTO `node` (`id`, `body`, `teaser`) VALUES ('3', '3 It works! This is long long long long long long lorem ipsum', 'It works!');
INSERT IGNORE INTO `node_meta` (`id`, `changed`, `created`, `ip`, `is_published`, `lang_id`, `path`, `title`, `user_id`) VALUES ('3', '2020-02-16 23:19:15', '2020-02-18 23:19:15', '12', '1', '1', '20/02/16/3', 'Hexa news #1', '1');
INSERT IGNORE INTO `node` (`id`, `body`, `teaser`) VALUES ('4', '4 It works! This is long long long long long long lorem ipsum', 'It works!');
INSERT IGNORE INTO `node_meta` (`id`, `changed`, `created`, `ip`, `is_published`, `lang_id`, `path`, `title`, `user_id`) VALUES ('4', '2020-02-16 23:19:15', '2020-02-19 23:19:15', '12', '1', '1', '20/02/16/4', 'Hexa news #1', '1');
INSERT IGNORE INTO `node` (`id`, `body`, `teaser`) VALUES ('5', '5 It works! This is long long long long long long lorem ipsum', 'It works!');
INSERT IGNORE INTO `node_meta` (`id`, `changed`, `created`, `ip`, `is_published`, `lang_id`, `path`, `title`, `user_id`) VALUES ('5', '2020-02-16 23:19:15', '2020-02-20 23:19:15', '12', '1', '1', '20/02/16/5', 'Hexa news #1', '1');
INSERT IGNORE INTO `node` (`id`, `body`, `teaser`) VALUES ('6', '6 It works! This is long long long long long long lorem ipsum', 'It works!');
INSERT IGNORE INTO `node_meta` (`id`, `changed`, `created`, `ip`, `is_published`, `lang_id`, `path`, `title`, `user_id`) VALUES ('6', '2020-02-16 23:19:15', '2020-02-21 23:19:15', '12', '1', '1', '20/02/16/6', 'Hexa news #1', '1');
INSERT IGNORE INTO `node` (`id`, `body`, `teaser`) VALUES ('7', '7 It works! This is long long long long long long lorem ipsum', 'It works!');
INSERT IGNORE INTO `node_meta` (`id`, `changed`, `created`, `ip`, `is_published`, `lang_id`, `path`, `title`, `user_id`) VALUES ('7', '2020-02-16 23:19:15', '2020-02-22 23:19:15', '12', '1', '1', '20/02/16/7', 'Hexa news #1', '1');

INSERT IGNORE INTO `category` (`id`, `description`, `name`, `weight`) VALUES
(27, NULL, 'Finances', 0),
(28, '', 'Tech', 0),
(29, '', 'Marketing, PR', 0),
(45, '', 'World', -10),
(46, '', 'USA', 0),
(47, '', 'Europe', -9),
(48, '', 'UK', 0);

INSERT IGNORE INTO `user` (`id`, `alias_id`, `company`, `country_id`, `created`, `email`, `full_name`, `is_enabled`, `last_access`, `last_log_in`, `password`, `position`, `site`, `telephone`, `username`, `word`) VALUES ('1', '', NULL, '', '2021-03-02 23:19:15', 'example@email.com', 'admin', b'1', '2020-02-16 23:19:15', '2020-02-16 23:19:15', '21232f297a57a5a743894a0e4a801fc3', NULL, NULL, NULL, 'admin', NULL);