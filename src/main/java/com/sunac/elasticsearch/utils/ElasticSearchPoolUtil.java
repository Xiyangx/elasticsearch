package com.sunac.elasticsearch.utils;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.elasticsearch.client.RestHighLevelClient;

/**
 * @Description: TODO
 * @Author xiyang
 * @Date 2022/6/11 2:44 下午
 * @Version 1.0
 */
public class ElasticSearchPoolUtil {
    // 对象池配置类，不写也可以，采用默认配置

    private static final GenericObjectPoolConfig POOL_CONFIG = new GenericObjectPoolConfig();

    // 采用默认配置maxTotal是8，池中有8个client
    {
        POOL_CONFIG.setMaxTotal(8);
    }
    // 要池化的对象的工厂类，这个是我们要实现的类

    private static final EsClientPoolFactory ES_CLIENT_POOL_FACTORY = new EsClientPoolFactory();
    // 利用对象工厂类和配置类生成对象池
    private static final GenericObjectPool<RestHighLevelClient> CLIENT_POOL = new GenericObjectPool<>(ES_CLIENT_POOL_FACTORY,
            POOL_CONFIG);

    /**
     * 获得对象
     *
     * @return
     * @throws Exception
     */
    public static RestHighLevelClient getClient() throws Exception {
        // 从池中取一个对象
        return CLIENT_POOL.borrowObject();
    }

    /**
     * 归还对象
     *
     * @param client
     */
    public static void returnClient(RestHighLevelClient client) {
        // 使用完毕之后，归还对象
        CLIENT_POOL.returnObject(client);
    }

}
