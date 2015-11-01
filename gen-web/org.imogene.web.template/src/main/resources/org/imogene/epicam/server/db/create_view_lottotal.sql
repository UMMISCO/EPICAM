CREATE OR REPLACE VIEW lottotal AS (
SELECT id, modified, quantiteinitiale AS qte , 1 AS thetype, id AS lot FROM lot
UNION
SELECT id, modified, quantitereelle AS qte, 1 AS thetype, lot_id AS lot FROM detailinventaire
UNION
SELECT DISTINCT id, modified, quantite AS qte, 2 AS thetype, lot_id AS lot FROM entreelot
UNION 
SELECT DISTINCT id, modified, - quantite AS qte, 3 AS thetype, lot_id AS lot FROM sortielot)