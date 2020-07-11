package com.bigdata.bigdata.service;

import org.elasticsearch.action.search.ClearScrollRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchScrollRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.CountRequest;
import org.elasticsearch.client.core.CountResponse;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class EsServices {
    private static final String SHOREWALL = "shorewall";
    public static final String TIMESTAMP = "@timestamp";
    private final RestHighLevelClient client;
    private static final long TIMEOUT = 2l;

    @Autowired
    public EsServices(RestHighLevelClient client) {
        this.client = client;
    }

//    public Optional<Long> countAll() {
//        SearchRequest searchRequest = new SearchRequest(SHOREWALL);
//        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//
//        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
//        searchRequest.source(searchSourceBuilder);
//        searchRequest.scroll(TimeValue.timeValueMinutes(1L));
//
//        long count = 0;
//
//        try {
//            final SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
//            final String scrollId = searchResponse.getScrollId();
//            final SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId);
//            scrollRequest.scroll(TimeValue.timeValueSeconds(30));
//
//            return Optional.of(client.search(searchRequest, RequestOptions.DEFAULT).getHits().getTotalHits().value);
//        } catch (IOException e) {
//            return Optional.empty();
//        }
//    }

    public Optional<Long> countAll() {
        CountRequest countRequest = new CountRequest(SHOREWALL);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());

        try {
            return Optional.of(client.count(countRequest, RequestOptions.DEFAULT).getCount());

        } catch (IOException e) {
            return Optional.empty();
        }
    }

    public Iterator<JSONObject> getAll(int pageSize) {
        SearchRequest searchRequest = new SearchRequest(SHOREWALL);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchSourceBuilder.size(pageSize);
        searchRequest.source(searchSourceBuilder);
        searchRequest.scroll(TimeValue.timeValueMinutes(1L));
        try {
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            return new serachIterator(searchResponse, client);
        } catch (IOException e) {
            return Collections.emptyIterator();
        }

    }

    public Iterator<JSONObject> getAll() {
        return getAll(10);
    }

    public Iterator<JSONObject> getNewest(String isoTime, int pageSize) {
        SearchRequest searchRequest = new SearchRequest(SHOREWALL);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.boolQuery().must(QueryBuilders.rangeQuery(TIMESTAMP).gt(isoTime)));
        searchSourceBuilder.sort(TIMESTAMP, SortOrder.ASC);
        searchSourceBuilder.size(pageSize);
        searchRequest.source(searchSourceBuilder);
        searchRequest.scroll(TimeValue.timeValueMinutes(TIMEOUT));
        try {
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            return new serachIterator(searchResponse, client);
        } catch (IOException e) {
            return Collections.emptyIterator();
        }

    }

    public Optional<Long> countGetNewest(String isoTime, int pageSize) {
        CountRequest countRequest = new CountRequest(SHOREWALL);

        countRequest.query(QueryBuilders.boolQuery().must(QueryBuilders.rangeQuery(TIMESTAMP).gt(isoTime)));

        try {
            final CountResponse countResponse = client.count(countRequest, RequestOptions.DEFAULT);
            return Optional.of(countResponse.getCount());
        } catch (IOException e) {
            return Optional.empty();
        }

    }

    private static class serachIterator implements Iterator<JSONObject> {

        private final RestHighLevelClient client;
        private final SearchResponse searchResponse;
        private Iterator<SearchHit> iterator;
        private String scrollId;


        public serachIterator(SearchResponse searchResponse, RestHighLevelClient client) {
            this.searchResponse = searchResponse;
            iterator = searchResponse.getHits().iterator();
            scrollId = searchResponse.getScrollId();
            this.client = client;
        }

        @Override
        public boolean hasNext() {
            if (iterator.hasNext()) return true;
            else {
                SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId);
                scrollRequest.scroll(TimeValue.timeValueSeconds(120));
                try {
                    SearchResponse searchScrollResponse = client.scroll(scrollRequest, RequestOptions.DEFAULT);
                    searchScrollResponse = client.scroll(scrollRequest, RequestOptions.DEFAULT);
                    scrollId = searchScrollResponse.getScrollId();
                    iterator = searchScrollResponse.getHits().iterator();
                } catch (IOException e) {
                    iterator = Collections.emptyIterator();
                }
                return iterator.hasNext();
            }
        }

        @Override
        public JSONObject next() {
            if (hasNext()) return new JSONObject(iterator.next());
            else throw new NoSuchElementException();
        }
    }
}
