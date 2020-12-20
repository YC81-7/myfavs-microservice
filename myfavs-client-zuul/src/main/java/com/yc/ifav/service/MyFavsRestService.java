package com.yc.ifav.service;

import com.google.gson.Gson;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import com.yc.ifav.client.MyFavsClient;

import com.yc.ifav.entity.MyFavs;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

public class MyFavsRestService {
    @Autowired
    private MyFavsClient favClient;


    @HystrixCommand(fallbackMethod = "findAllFallback")
    public String findAll(int muid) {
        return favClient.findAll(muid);
    }

    private String findAllFallback() {
        Map map = new HashMap();
        map.put("code", "-1");
        map.put("msg", "服务异常");
        return new Gson().toJson(map);
    }



    @HystrixCommand(fallbackMethod = "createFallback")
    public String create(MyFavs fav) {
        return favClient.create(fav);
    }

    private String createFallback(MyFavs fav) {
        Map map = new HashMap();
        map.put("code", "-1");
        map.put("msg", "服务异常，无法添加"+fav.getMfname());
        return new Gson().toJson(map);
    }

    @HystrixCommand(fallbackMethod = "deleteFallback")
    public String delete(Integer id) {
        return favClient.delete(id);
    }

    private String deleteFallback(Integer id) {
        Map map = new HashMap();
        map.put("code", "-1");
        map.put("msg", "服务异常，无法删除" + id);
        return new Gson().toJson(map);
    }
}
