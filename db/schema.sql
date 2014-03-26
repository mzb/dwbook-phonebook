CREATE TABLE IF NOT EXISTS `contact` (
    `id`        INT(11)         NOT NULL AUTO_INCREMENT,
    `firstName` VARCHAR(255)    NOT NULL,
    `lastName`  VARCHAR(255)    NOT NULL,
    `phone`     VARCHAR(30)     NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 AUTO_INCREMENT=1