package com.star.calendarview;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * 24节气单元测试
 * Created by huangstar on 2018/2/9.
 */
public class SolarTermUtilTest {
    @Test
    public void getSolarTerms() throws Exception {
        SolarTermUtil.getSolarTerms(2017);
    }

}