package top.yumoyumo.yumobot.service.impl;

import cn.hutool.http.HttpUtil;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import top.yumoyumo.yumobot.annotation.VirtualThread;
import top.yumoyumo.yumobot.pojo.CityBean;
import top.yumoyumo.yumobot.pojo.NowWeatherBean;
import top.yumoyumo.yumobot.service.WeatherService;

import java.util.HashMap;

/**
 * @Author: yumo
 * @Description: TODO
 * @DateTime: 2022/11/3 15:50
 **/
@Service
public class WeatherServiceImpl implements WeatherService {

    @Value("${weather.key}")
    public String key;
    public static final String CITY_URL = "https://geoapi.qweather.com/v2/city/lookup";
    public static final String WEATHER_URL = "https://devapi.qweather.com/v7/weather/now";

    private final static String weatherFormat =
            """
                    数据观测时间: %s
                    温度(℃): %s
                    体感温度: %s
                    风向: %s
                    风力等级: %s
                    风速(km/s): %s
                    相对湿度: %s
                    当前小时累计降水量(mm): %s
                    能见度(km): %s
                    云量: %s
                    """;

    @Override
    public String nowWeather(String location) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("location", location);
        paramMap.put("key", key);
        CityBean city = new Gson().fromJson(HttpUtil.get(CITY_URL, paramMap), CityBean.class);
        paramMap.put("location", city.getLocation().get(0).getId());
        String res = HttpUtil.get(WEATHER_URL, paramMap);
        NowWeatherBean.NowDTO now = new Gson().fromJson(res, NowWeatherBean.class).getNow();
        return String.format(weatherFormat,
                now.getObsTime(),
                now.getTemp(),
                now.getFeelsLike(),
                now.getWindDir(),
                now.getWindScale(),
                now.getWindSpeed(),
                now.getHumidity(),
                now.getPrecip(),
                now.getPressure(),
                now.getCloud());
    }
}
