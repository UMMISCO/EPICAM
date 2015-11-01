CREATE VIEW lottotal AS (
SELECT id, modified, quantiteinitiale AS qte, 1 AS thetype, id AS lot FROM lot
UNION
(SELECT id, modified, quantitereelle AS qte, 1 AS thetype, lot_id AS lot FROM detailinventaire ORDER BY modified DESC LIMIT 1)
UNION
SELECT id, modified, quantite AS qte, 2 AS thetype, lot_id AS lot FROM entreelot
UNION 
SELECT id, modified, - quantite AS qte, 3 AS thetype, lot_id AS lot FROM sortielot)