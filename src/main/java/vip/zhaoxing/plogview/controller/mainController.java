package vip.zhaoxing.plogview.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vip.zhaoxing.plogview.dto.SearchLogDTO;
import vip.zhaoxing.plogview.dto.SearchResultDTO;
import vip.zhaoxing.plogview.service.LogSearchService;

@RestController
@RequestMapping("/api/")
public class mainController {
    @Autowired
    private LogSearchService logSearchService;

    @GetMapping("test")
    public String test(){
        return "success";
    }

    @PostMapping("searchLog")
    public SearchResultDTO searchLog(@RequestBody  SearchLogDTO dto){
        return logSearchService.searchLog(dto);
    }
}
