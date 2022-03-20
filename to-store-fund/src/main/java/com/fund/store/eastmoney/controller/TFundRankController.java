package com.fund.store.eastmoney.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.enums.SqlKeyword;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fund.store.eastmoney.entity.TFundRank;
import com.fund.store.eastmoney.service.ITFundRankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 排名 前端控制器
 * </p>
 *
 * @author tbb
 * @since 2022-02-14
 */
@RestController
@RequestMapping("/eastmoney/fund_rank")
public class TFundRankController {

    @Autowired
    ITFundRankService itFundRankService;

    @PostMapping(value="/save", consumes ="application/json")
    public String insertData(@RequestBody List<TFundRank> tFundRank){
        boolean isSucc =  itFundRankService.saveBatch(tFundRank);
        if (isSucc){
            return "true";
        }else{
            return "error";
        }
    }

    /**
     * 获取基金排行列表
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @GetMapping("/list")
    public IPage<TFundRank> list(@RequestParam("page_index") Integer pageIndex,
                                 @RequestParam("page_size") Integer pageSize){
        IPage<TFundRank> page = new Page<>(pageIndex, pageSize);
        QueryWrapper<TFundRank> queryWrapper = new QueryWrapper();
        queryWrapper.orderByAsc("fund_code");
        return itFundRankService.page(page, queryWrapper);
    }

    /**
     * 获取精简版基金排行列表
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @GetMapping("/list_simplify")
    public IPage<TFundRank> listSimplify(@RequestParam("page_index") Integer pageIndex,
                                         @RequestParam("page_size") Integer pageSize){
        IPage<TFundRank> page = new Page<>(pageIndex, pageSize);
        QueryWrapper<TFundRank> queryWrapper = new QueryWrapper();
        queryWrapper.select("fund_code", "fund_short_name");
        queryWrapper.orderByAsc("fund_code");
        return itFundRankService.page(page, queryWrapper);
    }

    /**
     * 获取自定义基金排行列表
     * @param pageIndex
     * @param pageSize
     * @param selectColumn
     * @param orderColumn
     * @param direction
     * @return
     */
    @GetMapping("/list_custom")
    public IPage<TFundRank> listCustom(@RequestParam("page_index") Integer pageIndex,
                                       @RequestParam("page_size") Integer pageSize,
                                       @RequestParam("select_column") String selectColumn,
                                       @RequestParam("order_column") String orderColumn,
                                       @RequestParam String direction) {
        IPage<TFundRank> page = new Page<>(pageIndex, pageSize);
        QueryWrapper<TFundRank> queryWrapper = new QueryWrapper();
        queryWrapper.select(selectColumn);
        queryWrapper.orderBy(true, SqlKeyword.ASC.toString().equals(direction), orderColumn);
        return itFundRankService.page(page, queryWrapper);
    }

}
