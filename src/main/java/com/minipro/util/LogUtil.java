package com.minipro.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by chuckulu on 2017/7/9.
 */
public class LogUtil {
    private static final Logger logger = LoggerFactory.getLogger(Logger.class);

    public static void log(String loginfo) {
       logger.info(loginfo);
    }

}
