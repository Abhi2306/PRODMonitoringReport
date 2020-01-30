select DISTINCT site_name from [edm].[daily_production_coal]
union
select DISTINCT site_name from [edm].[daily_production_kumba]
union
select DISTINCT site_name from [edm].[daily_production_platinum]