package top.yumoyumo.yumobot.service.impl;

import cn.hutool.http.HttpUtil;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
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

    @Override
    public String nowWeather(String location) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("location", location);
        paramMap.put("key", key);
        CityBean city = new Gson().fromJson(HttpUtil.get(CITY_URL, paramMap), CityBean.class);
        paramMap.put("location", city.getLocation().get(0).getId());
        String res = HttpUtil.get(WEATHER_URL, paramMap);
        StringBuilder builder = new StringBuilder();
        NowWeatherBean nowWeather = new Gson().fromJson(res, NowWeatherBean.class);
        NowWeatherBean.NowDTO now = nowWeather.getNow();
        builder.append("数据观测时间: " + now.getObsTime());
        builder.append("\n温度(℃): " + now.getTemp());
        builder.append("\n体感温度: " + now.getFeelsLike());
        builder.append("\n风向: " + now.getWindDir());
        builder.append("\n风力等级: " + now.getWindScale());
        builder.append("\n风速(km/s): " + now.getWindSpeed());
        builder.append("\n相对湿度(%): " + now.getHumidity());
        builder.append("\n当前小时累计降水量(mm): " + now.getPrecip());
        builder.append("\n能见度(km): " + now.getPressure());
        builder.append("\n云量(%): " + now.getCloud());
        return builder.toString();
    }
}
