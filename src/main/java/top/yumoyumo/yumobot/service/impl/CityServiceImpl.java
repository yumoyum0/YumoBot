package top.yumoyumo.yumobot.service.impl;

import cn.hutool.http.HttpUtil;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import top.yumoyumo.yumobot.pojo.CityBean;
import top.yumoyumo.yumobot.service.CityService;

import java.util.HashMap;

/**
 * @Author: yumo
 * @Description: TODO
 * @DateTime: 2022/11/3 15:33
 **/
@Service
public class CityServiceImpl implements CityService {
    /**
     * The Key.
     */
    @Value("${weather.key}")
    public String key;


    public static final String CITY_URL = "https://geoapi.qweather.com/v2/city/lookup";

    @Override
    public String location(String location) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("location", location);
        paramMap.put("key", key);
        String res = HttpUtil.get(CITY_URL, paramMap);
        StringBuilder builder = new StringBuilder();
        CityBean city = new Gson().fromJson(res, CityBean.class);
        CityBean.LocationDTO l = city.getLocation().get(0);
        builder.append("名称: ").append(l.getName());
        builder.append("\nID: ").append(l.getId());
        builder.append("\n经度: ").append(l.getLon());
        builder.append("\n纬度: ").append(l.getLat());
        builder.append("\n上级行政区划: ").append(l.getAdm2());
        builder.append("\n所属国家: ").append(l.getCountry());
        builder.append("\n时区: ").append(l.getTz());
        return builder.toString();
    }
}
