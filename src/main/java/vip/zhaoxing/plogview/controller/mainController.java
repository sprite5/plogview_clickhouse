package vip.zhaoxing.plogview.controller;

import org.springframework.web.bind.annotation.*;
import vip.zhaoxing.plogview.base.Response;

@RestController
@RequestMapping("/api/")
public class mainController {
    @GetMapping("test")
    public Response<String> test(){
        return Response.successResponse("success");
    }

}
