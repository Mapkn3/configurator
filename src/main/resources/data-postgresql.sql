MERGE INTO currency_table c
USING (VALUES (0, 'RUR', '₽')) v
ON v.column1 = c.id
WHEN NOT MATCHED
INSERT VALUES (v.column1, v.column2, v.column3)
WHEN MATCHED
UPDATE SET name = v.column2;

MERGE INTO currency_table c
USING (VALUES (1, 'USD', '$')) v
ON v.column1 = c.id
WHEN NOT MATCHED
INSERT VALUES (v.column1, v.column2, v.column3)
WHEN MATCHED
UPDATE SET name = v.column2;

MERGE INTO currency_table c
USING (VALUES (2, 'EUR', '€')) v
ON v.column1 = c.id
WHEN NOT MATCHED
INSERT VALUES (v.column1, v.column2, v.column3)
WHEN MATCHED
UPDATE SET name = v.column2;