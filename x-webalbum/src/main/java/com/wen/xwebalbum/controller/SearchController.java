package com.wen.xwebalbum.controller;

import com.alibaba.fastjson.JSON;
import com.wen.xwebalbum.servcie.EsService;
import com.wen.xwebalbum.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * SearchController类
 * @author Mr.文
 */
@RestController
@RequestMapping("/search")
public class SearchController extends BaseController {
    @Resource
    EsService esService;

    @GetMapping("/s/{keyword}")
    public String searchData(@PathVariable("keyword") String keyword) {
        int userId = Integer.parseInt(tokenService.getTokenUserId());
        try {
            int storeId = fileStoreService.queryStoreByUserId(userId).getFileStoreId();
            String rs = JSON.toJSONString(esService.searchData(storeId, keyword));
            return ResponseUtil.success(rs);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseUtil.error("搜索数据错误");
        }
    }
}
