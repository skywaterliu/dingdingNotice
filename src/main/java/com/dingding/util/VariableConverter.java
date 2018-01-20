package com.dingding.util;

import java.util.Map;

/**
 * 用于将变量替换为某些内容
 * @param <P>
 */
public interface VariableConverter<P>{

    public P convert(Map<String,String> map, P target);

}
