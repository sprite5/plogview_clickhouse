package vip.zhaoxing.plogview.dto;

import lombok.Data;

import java.util.Date;

@Data
public class SearchLogDTO {
    private Date startDateTime;
    private Date endDateTime;
    private String server;
    private String level;
    private String traceId;
    private String searchKey;
    private Integer pageNum;
    private Integer pageSize;
    //TODO sql query
    private String sql;
}
