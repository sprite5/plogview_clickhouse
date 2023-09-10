package vip.zhaoxing.plogview.dto;

import lombok.Data;

import java.util.List;

@Data
public class SearchResultDTO {
    private Long count;
    private Integer size;
    private Integer pageNum;
    private Integer pageSize;
    private List<Object> list;

    public static SearchResultDTO build(long count, int pageNum,int pageSize,List<Object> list){
        var resultDTO = new SearchResultDTO();
        resultDTO.setCount(count);
        resultDTO.setPageNum(pageNum);
        resultDTO.setPageSize(pageSize);
        resultDTO.setList(list);
        resultDTO.setSize(list.size());
        return resultDTO;
    }

    public static SearchResultDTO emptyResult(long count, int pageNum,int pageSize){
        var resultDTO = new SearchResultDTO();
        resultDTO.setCount(count);
        resultDTO.setPageNum(pageNum);
        resultDTO.setPageSize(pageSize);
        resultDTO.setList(List.of());
        resultDTO.setSize(0);
        return resultDTO;
    }
}
