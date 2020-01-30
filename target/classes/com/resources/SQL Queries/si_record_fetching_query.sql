With cte as
(
	SELECT DISTINCT SUBSTRING(column_name, 1, 4) as year,
	SUBSTRING(column_name, 6, 2) as month,
	SUBSTRING(column_name, 9, 2) as day
	FROM Table_Name_Variable nolock
	where site_name = 'Site_Name_Variable' 
	and SUBSTRING(column_name,0,11)  <= CONVERT(nvarchar,cast(GETDATE()-1 as date))
)
SELECT distinct year,month , count(*) as numofRec FROM cte
group by   year,month
order by  year,month 