package com.sunac.elasticsearch.utils;

import com.alibaba.fastjson.JSONObject;
import com.sunac.elasticsearch.entity.Report;
import org.apache.commons.lang3.ArrayUtils;
import org.elasticsearch.action.search.ClearScrollRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchScrollRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.search.Scroll;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class ESUtil {
    private static final Logger logger = LoggerFactory.getLogger(ESUtil.class);

    private static final long SCROLL_TIMEOUT = 180000;

    private final static int SIZE = 1000;

    /**
     * 构建SearchResponse
     *
     * @param client     restHighLevelClient
     * @param query      queryBuilder
     * @return List, 可以使用fun转换为T结果
     *
     */
    public static List<Report> searchResponse(RestHighLevelClient client, BoolQueryBuilder query) throws Exception {
        //滚动查询的Scroll
        Scroll scroll = new Scroll(TimeValue.timeValueMillis(SCROLL_TIMEOUT));

        //构建searchRequest
        SearchRequest request = new SearchRequest("journal_index");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        //加入query语句
        sourceBuilder.query(query);
        //每次滚动的长度
        sourceBuilder.size(SIZE);
        //排序  SortOrder.ASC   SortOrder.DESC
        sourceBuilder.sort("bsegbelnr.keyword", SortOrder.ASC).sort("bsegbuzei.keyword",SortOrder.ASC);
        //加入scroll和构造器
        request.scroll(scroll);
        request.source(sourceBuilder);
        //存储scroll的list
        List<String> scrollIdList = new ArrayList<>();
        //返回结果
        SearchResponse searchResponse = client.search(request, RequestOptions.DEFAULT);
        //拿到第一个ScrollId（游标）
        String scrollId = searchResponse.getScrollId();
        //拿到hits结果
        SearchHit[] hits = searchResponse.getHits().getHits();
        logger.info("总数为：{}",searchResponse.getHits().getTotalHits());
        //保存返回结果List
        List<Report> result = new ArrayList<>();
        scrollIdList.add(scrollId);

        try {
            //滚动查询将SearchHit封装到result中
            while (ArrayUtils.isNotEmpty(hits)) {
                for (SearchHit hit : hits) {
                    JSONObject jsonObject = JSONObject.parseObject(hit.toString());
                    Report report = jsonObject.getJSONObject("_source").toJavaObject(Report.class);
                    result.add(report);
                }
                //说明滚动完了，返回结果即可
                if (hits.length < SIZE) {
                    break;
                }
                //继续滚动，根据上一个游标，得到这次开始查询位置
                SearchScrollRequest searchScrollRequest = new SearchScrollRequest(scrollId);
                searchScrollRequest.scroll(scroll);
                //得到结果
                SearchResponse searchScrollResponse = client.scroll(searchScrollRequest, RequestOptions.DEFAULT);
                //定位游标
                scrollId = searchScrollResponse.getScrollId();
                hits = searchScrollResponse.getHits().getHits();
                scrollIdList.add(scrollId);
            }
        } finally {
            //清理scroll,释放资源
            ClearScrollRequest clearScrollRequest = new ClearScrollRequest();
            clearScrollRequest.setScrollIds(scrollIdList);
            client.clearScroll(clearScrollRequest, RequestOptions.DEFAULT);
        }
        return result;
    }
}
