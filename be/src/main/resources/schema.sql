-- create a function to generate random datetime
SET GLOBAL log_bin_trust_function_creators = 1 ^;
DROP FUNCTION IF EXISTS generateRandomDateTime ^;
CREATE FUNCTION generateRandomDateTime()
RETURNS DATETIME
NOT DETERMINISTIC
BEGIN
	DECLARE result DATETIME;
    DECLARE MIN DATETIME;
    DECLARE MAX DATETIME;
    SET MIN = '2018-04-30 14:53:27';
    SET MAX = CURRENT_TIMESTAMP();
    SET result = TIMESTAMPADD(SECOND, FLOOR(RAND() * TIMESTAMPDIFF(SECOND, MIN, MAX)), MIN);
    RETURN result;
END ^;
