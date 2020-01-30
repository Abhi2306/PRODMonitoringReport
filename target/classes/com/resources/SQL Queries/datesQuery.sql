select distinct column_name_variable FROM table_name_variable nolock
where site_name = 'site_name_variable' 
and column_name_variable like '%year_month_variable%'
order by column_name_variable