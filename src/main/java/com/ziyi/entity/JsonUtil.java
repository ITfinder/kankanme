package com.ziyi.entity;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author ziyi
 * @create 2019/1/25
 */
public class JsonUtil {

    /**
     * JavaBean的属性比Json多时，根据Json更新JavaBean同名字段的值
     *
     * @param bean
     * @param json
     * @param <T>
     */
    public static <T> void patchUpdate(T bean, JSONObject json) throws Exception {
        Set<String> keys = json.keySet();
        Field[] fields = bean.getClass().getDeclaredFields();
        for (String key : keys) {
            for (Field field : fields) {
                if (field.getName().equals(key)) {
                    String value = json.getString(key);
                    String type = field.getType().toString();
                    field.setAccessible(true);
                    if (type.endsWith("String")) {
                        field.set(bean, value);
                    } else if (type.endsWith("int")) {
                        field.set(bean, Integer.parseInt(value));
                    } else if (type.endsWith("double")) {
                        field.set(bean, Double.parseDouble(value));
                    } else if (type.endsWith("Date")) {
                        DateFormat fmtDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date date = fmtDateTime.parse(value);
                        field.set(bean, date);
                    } else if (type.endsWith("List")) {
                        //先获取List的类型：java.util.List<E>
                        ParameterizedType pt = (ParameterizedType)field.getGenericType();
                        //获取泛型：E
                        Class e = (Class)pt.getActualTypeArguments()[0];
                        List list = JSONArray.parseArray(value, e);
                        field.set(bean, list);
                    }
                }
            }
        }
    }

    public final static boolean isJSONValid(String jsonInString ) {
        try {
            JSONObject.parseObject(jsonInString);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

}
