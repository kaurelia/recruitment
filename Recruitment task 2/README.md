# Recruitment task 2

## Task 1

```sql
CREATE TABLE `temp` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `time` TIMESTAMP NOT NULL,
  `measurement` DOUBLE,
  PRIMARY KEY (`id`),
)
```

## Task 2

```sql
CREATE PROCEDURE proc_temp(
  IN time TIMESTAMP,
  IN amount INT
  )
BEGIN
  DECLARE i INT DEFAULT 0;
  DECLARE new_date TIMESTAMP;
  DECLARE date_hour INT;
  WHILE i < amount DO
      SET new_date = DATE_ADD(time, INTERVAL i MINUTE);
      SET date_hour = HOUR(new_date)
      INSERT INTO temp (measurement, time) VALUES (IF(date_hour < 6 OR date_hour > 17, NULL, ROUND(RAND()*(25-10)+10, 2)), new_date);
      SET i = i + 1;
  END WHILE;
END

call proc_temp('2021-01-06 00:00:00', 24*60);
call proc_temp('2021-01-07 00:00:00', 24*60);
call proc_temp('2021-01-08 00:00:00', 24*60);
```

## Task 3

```sql
SELECT MAX(`time`) AS sunset, MIN(`time`) AS sunrise FROM `temp` WHERE measurement IS NOT NULL GROUP BY CAST(`time` AS DATE);
```

## Task 4

Baza relacyjna nie jest najlepszym rozwiązaniem do gromadzenia tego typu danych, ponieważ nie występuje w tych danych żadna relacja i występują pomiary z interwałem czasowym. Najlepiej w tym przypadku sprawdziłyby się bazy nierelacyjne (NoSQL) takie jak MongoDB lub bazy typu TSDB takie jak InfluxDB czy Prometheus.
