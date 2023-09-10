package vip.zhaoxing.plogview.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vip.zhaoxing.plogview.base.Tuple;
import vip.zhaoxing.plogview.base.Tuple2;
import vip.zhaoxing.plogview.config.CkConfig;
import vip.zhaoxing.plogview.dto.SearchLogDTO;
import vip.zhaoxing.plogview.dto.SearchResultDTO;
import vip.zhaoxing.plogview.util.CkHttpClientUtil;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class LogSearchService {
    @Autowired
    private CkHttpClientUtil ckClient;

    private  String tableName = CkConfig.getTable();


    public SearchResultDTO searchLog(SearchLogDTO dto){
        //页码不传固定第一页20条
        if(Objects.isNull(dto.getPageNum())) dto.setPageNum(1);
        if(Objects.isNull(dto.getPageSize())) dto.setPageSize(20);
        log.info("search start");
        var sqlTuple = genSqlBySearchDTO(dto);
        var countResult = ckClient.search(sqlTuple.getT1());
        log.info("search count sql:{},  result:{}",sqlTuple.getT1(), JSONUtil.toJsonStr(countResult));
        var count = Objects.isNull(countResult)?0:
                JSONUtil.parseObj(countResult.firstRecord().getValue(0).asString()).getLong("count");
        var offset = (dto.getPageNum()-1) * dto.getPageSize();
        if(count == 0 || count< offset ) return SearchResultDTO.emptyResult(count,dto.getPageNum(),dto.getPageSize());
        var queryResult = ckClient.search(sqlTuple.getT2());
        log.info("search sql:{},  result:{}",sqlTuple.getT2(), JSONUtil.toJsonStr(queryResult));
        if(Objects.isNull(queryResult)) return SearchResultDTO.emptyResult(count,dto.getPageNum(),dto.getPageSize());
        List<Object> recordList = queryResult.stream().map(x->JSONUtil.parseObj(x.getValue(0).asString())).collect(Collectors.toList());
        return SearchResultDTO.build(count,dto.getPageNum(),dto.getPageSize(), recordList);
    }

    //返回两个sql,t1:countSql,T2 pageSql
    public  Tuple2<String,String> genSqlBySearchDTO(SearchLogDTO dto){
        var startTimeStr = DateUtil.formatDateTime(DateUtil.offsetSecond(dto.getStartDateTime(),-1));
        var endTImeStr =  DateUtil.formatDateTime(DateUtil.offsetMinute(dto.getEndDateTime(),1));
        var sbForCount = new  StringBuilder("select count(1) as count from ").append(tableName).append(" where ");
        //时间，服务器必传
        sbForCount.append(" time > '")
                .append(startTimeStr).append("' " )
                .append(" and ")
                .append("time < '")
                .append(endTImeStr).append("' " )
                .append(" and ")
                .append(" server = '").append(dto.getServer()).append("' ");
        if(StringUtils.isNotBlank(dto.getTraceId())) sbForCount.append(" and ").append(" traceId = '").append(dto.getTraceId()).append("' ");
        if(StringUtils.isNotBlank(dto.getLevel())) sbForCount.append(" and ").append(" level = '").append(dto.getLevel()).append("' ");
        if(StringUtils.isNotBlank(dto.getSearchKey())) sbForCount.append(" and ").append(" simpleMsg like '%").append(dto.getSearchKey()).append("%' ");
        var sbForSql = new  StringBuilder("select message from ").append(tableName).append(" where ");
        sbForSql.append(" time > '")
                .append(startTimeStr).append("' " )
                .append(" and ")
                .append("time < '")
                .append(endTImeStr).append("' " )
                .append(" and ")
                .append(" server = '").append(dto.getServer()).append("' ");

        if(StringUtils.isNotBlank(dto.getTraceId())) sbForSql.append(" and ").append(" traceId = '").append(dto.getTraceId()).append("' ");
        if(StringUtils.isNotBlank(dto.getLevel())) sbForSql.append(" and ").append(" level = '").append(dto.getLevel()).append("' ");
        if(StringUtils.isNotBlank(dto.getSearchKey())) sbForSql.append(" and ").append(" simpleMsg like '%").append(dto.getSearchKey()).append("%' ");
        //拼页
        var offset = (dto.getPageNum()-1) * dto.getPageSize();
        sbForSql.append(" order by time desc ");
        sbForSql.append(" limit ").append(dto.getPageSize()).append(" offset ").append(offset);
        return Tuple.of(sbForCount.toString(),sbForSql.toString());
    }

    public static void main(String[] args) {
        SearchLogDTO searchLogDTO = new SearchLogDTO();
        searchLogDTO.setStartDateTime(DateUtil.parseDateTime("2023-09-08 11:00:00"));
        searchLogDTO.setEndDateTime(DateUtil.parseDateTime("2023-09-08 12:00:00"));
        searchLogDTO.setServer("gps_prod");
        searchLogDTO.setPageSize(20);
        searchLogDTO.setPageNum(1);
        //System.out.println(genSqlBySearchDTO(searchLogDTO));
    }
}
