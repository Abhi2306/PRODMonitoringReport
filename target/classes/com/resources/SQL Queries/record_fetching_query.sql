With cte as
(
	SELECT DISTINCT SUBSTRING(CAST(date_key AS VARCHAR(50)), 1, 4) as year,
	SUBSTRING(CAST(date_key AS VARCHAR(50)), 5, 2) as month,
	SUBSTRING(CAST(date_key AS VARCHAR(50)), 7, 2) as day
	FROM Table_Name_Variable nolock
	where site_name = 'Site_Name_Variable' 
	and date_key <= CONVERT(int,REPLACE(cast(GETDATE()-1 as date),'-',''))
)
SELECT distinct year,month , count(*) as numofRec FROM cte
group by   year,month
order by  year,month 