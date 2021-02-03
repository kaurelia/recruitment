# Recruitment task 1

```sql
CREATE TABLE `it_matter`.`account` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `login` VARCHAR(50) NOT NULL,
  `password` VARCHAR(100) NOT NULL,
  `is_blocked` TINYINT NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `login_UNIQUE` (`login` ASC) VISIBLE);

CREATE TABLE `it_matter`.`account_data` (
  `name` VARCHAR(50) NOT NULL,
  `surname` VARCHAR(70) NOT NULL,
  `age` INT NOT NULL,
  `account_id` INT NOT NULL,
  FOREIGN KEY (account_id) REFERENCES Account(`id`),
UNIQUE (`account_id`));

CREATE TABLE `it_matter`.`notes` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(255) NOT NULL,
  `content` TEXT NOT NULL,
  `upload_date` DATETIME NOT NULL,
  `account_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`account_id`) REFERENCES Account(`id`));

SELECT notes.id, notes.title, notes.content FROM notes  INNER JOIN account ON notes.account_id = account.id INNER JOIN account_data on account.id = account_data.account_id WHERE account_data.name = 'Jan' AND account_data.surname = 'Kowalski';
```
