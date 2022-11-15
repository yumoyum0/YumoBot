package top.yumoyumo.yumobot.service.impl;

import cn.hutool.http.HttpUtil;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import top.yumoyumo.yumobot.common.Result;
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
    private final static String cityFormat =
            """
                    名称: %s
                    ID: %s
                    经度: %s
                    纬度: %s
                    上级行政区划: %s
                    所属国家: %s
                    时区: %s
                    """;

    @Override
    public String location(String location) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("location", location);
        paramMap.put("key", key);
        String res = HttpUtil.get(CITY_URL, paramMap);
        CityBean.LocationDTO l = new Gson().fromJson(res, CityBean.class).getLocation().get(0);
        return String.format(cityFormat,
                l.getName(),
                l.getId(),
                l.getLon(),
                l.getLat(),
                l.getAdm2(),
                l.getCountry(),
                l.getTz());
    }
}
