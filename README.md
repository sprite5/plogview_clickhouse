## clickhouse simple log view
- my log table create sql:
```sql 
create table default.log(
  time DateTime64(3, 'Asia/Shanghai'),
  traceId String,
  level LowCardinality(String),
  server LowCardinality(String),
  prod LowCardinality(String),
  className String,
  simpleMsg String,
  message String,
)Engine = MergeTree()
PARTITION BY toYYYYMMDD(time)
ORDER BY time 
TTL toDate(time) + toIntervalDay(20)
```

## for kafka+Filebeat+clickhouse
- see: <https://blog.jascript.cn/posts/kafka+Filebeat+clickhouse%E6%97%A5%E5%BF%97%E6%8B%89%E5%88%B0%E6%9C%AC%E5%9C%B0%E5%AD%98%E5%82%A8%E6%9F%A5%E8%AF%A2.html>