package com.sunac.elasticsearch.utils;

import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description: TODO
 * @Author xiyang
 * @Date 2022/6/11 2:39 下午
 * @Version 1.0
 */
public class EsClientPoolFactory implements PooledObjectFactory<RestHighLevelClient> {

    private static final Logger logger = LoggerFactory.getLogger(PooledObjectFactory.class);

    @Override
    public void activateObject(PooledObject<RestHighLevelClient> arg0) {
        logger.info("创建es连接对象");

    }

    /**
     * 销毁对象
     */
    @Override
    public void destroyObject(PooledObject<RestHighLevelClient> pooledObject) throws Exception {
        RestHighLevelClient highLevelClient = pooledObject.getObject();
        highLevelClient.close();
    }

    /**
     * 生产对象
     */
    @Override
    public PooledObject<RestHighLevelClient> makeObject() {
        RestHighLevelClient client = null;
        try {
            client = new RestHighLevelClient(RestClient.builder(
                    new HttpHost("hdp-ch-44-32.sunac.com.cn", 9200, "http"),
                    new HttpHost("hdp-ch-44-33.sunac.com.cn", 9200, "http"),
                    new HttpHost("hdp-ch-44-34.sunac.com.cn", 9200, "http")));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new DefaultPooledObject<>(client);
    }

    @Override
    public void passivateObject(PooledObject<RestHighLevelClient> arg0) {
        logger.info("创建es连接对象");
    }

    @Override
    public boolean validateObject(PooledObject<RestHighLevelClient> arg0) {
        return true;
    }
}
