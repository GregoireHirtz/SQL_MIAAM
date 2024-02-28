
SELECT * FROM tabl;

SELECT * FROM reservation;

SELECT * FROM reservation;

SELECT *
FROM reservation
WHERE (datres <= '2022-10-11 21:00:00' AND DATE_ADD(datres, INTERVAL nbpers HOUR) >= '2022-10-11 19:00:00')
   OR ('2022-10-11 19:00:00' <= datres AND '2022-10-11 21:00:00' >= DATE_ADD(datres, INTERVAL nbpers HOUR));

SET @datetime = '2021-10-11 19:00:00';


SELECT * FROM tabl
WHERE numtab NOT IN
    (SELECT numtab FROM reservation
    WHERE DATE_SUB(datres, INTERVAL 2 HOUR) < @datetime AND
          ((datpaie IS NULL AND DATE_ADD(datres, INTERVAL 2 HOUR) > @datetime)
            OR (datpaie IS NOT NULL AND datpaie > @datetime)));

