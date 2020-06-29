package com.zhengwei.clound.service;

import com.alibaba.fastjson.JSON;
import com.zhengwei.clound.bean.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.elasticsearch.search.suggest.Suggest;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.SuggestBuilders;
import org.elasticsearch.search.suggest.SuggestionBuilder;
import org.elasticsearch.search.suggest.completion.CompletionSuggestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * TODO
 *
 * @author zevin aibaokeji
 * @version 1.0
 * 2019/6/1612:08
 **/
@Service
@Slf4j
public class UserServiceImpl implements  UserService {

    @Autowired
    RestHighLevelClient restHighLevelClient;

    @Value("${spring.data.elasticsearch.host}")
    private String host;
    @Value("${spring.data.elasticsearch.port}")
    private int port;

    /**
     * 索引名称
     */
    @Value("${es.user.index.name}")
    private String indexName;
    /**
     * 分片数 （因为ES是个分布式的搜索引擎,
     *        所以索引通常都会分解成不同部分, 而这些分布在不同节点的数据就是分片.
     *        ES自动管理和组织分片, 并在必要的时候对分片数据进行再平衡分配,
     *        所以用户基本上不用担心分片的处理细节，一个分片默认最大文档数量是20亿）
     */
    @Value("${es.user.shard.count}")
    private int shardCount;
    /**
     * 副本数 （ES默认为一个索引创建5个主分片, 并分别为其创建一个副本分片.
     *          也就是说每个索引都由5个主分片成本, 而每个主分片都相应的有一个copy）
     */
    @Value("${es.user.replica.count}")
    private int replicaCount;

    /**
     * 类型
     */
    @Value("${es.user.type}")
    private String type;

    @Override
    public boolean createUserIndex() {
        log.info("创建用户索引开始");
        try {
            if(existsIndex()){
                log.info("用户索引已经存在");
                return false;
            }
            // 1.创建索引名
            CreateIndexRequest request = new CreateIndexRequest(indexName);
            // 2.索引setting配置
            // 指定分片数 shardCount
            // 指定副本数 replicaCount
            // 指定分词器 ik_smart 默认分词器
//            request.settings(Settings.builder().put("index.number_of_shards",shardCount)
//                    .put("index.number_of_replicas", replicaCount)
//                    .put("analysis.analyzer.default.tokenizer","ik_smart")
//            );

            // 3.设置索引的mapping
//            request.mapping(type,
//                    "  {\n" +
//                            "    \""+ type +"\": {\n" +
//                            "      \"properties\": {\n" +
//                            "        \"userName\": {\n" +
//                            "          \"type\": \"text\"\n" +
//                            "        },\n" +
//                            "        \"age\": {\n" +
//                            "          \"type\": \"integer\"\n" +
//                            "        },\n" +
//                            "        \"userId\": {\n" +
//                            "          \"type\": \"long\",\n" +
//                            "          \"index\": \"not_analyzed\",\n" +
//                            "        },\n" +
//                            "        \"phone\": {\n" +
//                            "          \"type\": \"text\"\n" +
//                            "        },\n" +
//                            "        \"address\": {\n" +
//                            "          \"type\": \"completion\"\n" +
//                            "          \"contexts\":[\n" +
//                            "              {\n" +
//                            "                 \"name\": \"cityId_tab\",\n" +
//                            "                 \"type\": \"category\",\n" +
//                            "                 \"path\": \"cityId\"\n" +
//                            "              }\n     " +
//                            "           ]"+
//                            "        },\n" +
//                            "        \"cityId\": {\n" +
//                            "          \"type\": \"keyword\"\n" +
//                            "        }\n" +
//                            "      }\n" +
//                            "    }\n" +
//                            "  }",
//                    XContentType.JSON);
//
//            // 设置索引别名
////            request.alias(new Alias("user"));
//
//            XContentBuilder builder = XContentFactory.jsonBuilder();
//            builder.startObject();
//            {
//                builder.startObject(type);
//                {
//                    builder.startObject("properties");
//                    {
//                        builder.startObject("userName");
//                        {
//                            builder.field("type", "text");
//                        }
//                        builder.endObject();
//                        builder.startObject("age");
//                        {
//                            builder.field("type", "integer");
//                        }
//                        builder.endObject();
//                        builder.startObject("userId");
//                        {
//                            builder.field("type", "long");
//                        }
//                        builder.endObject();
//                        builder.startObject("phone");
//                        {
//                            builder.field("type", "text");
//                        }
//                        builder.endObject();
//                        builder.startObject("address");
//                        {
//                            builder.field("type", "completion").field("contexts","");
//                        }
//                        builder.endObject();
//                        builder.startObject("cityId");
//                        {
//                            builder.field("type", "text");
//                        }
//                        builder.endObject();
//                    }
//                    builder.endObject();
//                }
//                builder.endObject();
//            }
//            builder.endObject();
//            request.mapping(type, builder);

            // 5.发送请求
            // 5.1同步方式
            CreateIndexResponse response = restHighLevelClient.indices().create(request, RequestOptions.DEFAULT);

            // 处理响应
            boolean acknowledged = response.isAcknowledged();
            boolean shardsAcknowledged = response.isShardsAcknowledged();
            log.info("创建用户索引结果：" + acknowledged + ":" + shardsAcknowledged);
            return acknowledged;

        }catch (IOException e){
            log.info("创建用户索引异常");
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean addUser(User user) {
        log.info("创建用户文档开始");
        try {
            IndexRequest indexRequest = new IndexRequest(indexName, type, user.getUserId().toString());

//        String jsonString = "{" +
//                "\"user\":\"kimchy\"," +
//                "\"postDate\":\"2013-01-30\"," +
//                "\"message\":\"trying out Elasticsearch\"" +
//                "}";
            indexRequest.source(JSON.toJSON(user), XContentType.JSON);
            IndexResponse indexResponse = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
            log.info("创建用户文档结束：" + indexResponse.getId());
            return indexResponse.getId() == null;
        }catch (Exception e){
            log.info("创建用户文档异常");
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public String getAllUser() {
        log.info("查询所有用户开始");
        try {
            HttpEntity entity = new NStringEntity(
                    "{ \"query\": { \"match_all\": {}}}",
                    ContentType.APPLICATION_JSON);
            String endPoint = "/" + indexName + "/" + type + "/_search";
            Response response = restHighLevelClient.getLowLevelClient().performRequest("POST", endPoint, Collections.<String, String>emptyMap(), entity);
            String allUser =  EntityUtils.toString(response.getEntity());
            log.info("查询所有用户结束:"+allUser);
            return allUser;
        } catch (IOException e) {
            log.info("查询所有用户异常");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getUser(Long userId) {
        log.info("根据Id查询单个用户开始");
        try {
            GetRequest getRequest = new GetRequest(indexName, type, userId.toString());
            GetResponse getResponse = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);
            log.info("根据Id查询单个用户结束");
            return JSON.toJSONString(getResponse);
        }catch (Exception e){
            log.info("根据Id查询单个用户异常");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String searchUserByUserName(String search) {
        log.info("按用户名搜索用户开始");
        try {
            BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery();
            boolBuilder.must(QueryBuilders.matchQuery("userName", search)); // 这里可以根据字段进行搜索，must表示符合条件的，相反的mustnot表示不符合条件的
            // boolBuilder.must(QueryBuilders.matchQuery("id", tests.getId().toString()));
            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
            sourceBuilder.query(boolBuilder);
            sourceBuilder.from(0);
            sourceBuilder.size(100); // 获取记录数，默认10
            sourceBuilder.fetchSource(new String[] { "userId", "userName","address"}, new String[] {}); // 第一个是获取字段，第二个是过滤的字段，默认获取全部
            SearchRequest searchRequest = new SearchRequest(indexName);
            searchRequest.types(type);
            searchRequest.source(sourceBuilder);
            SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            SearchHits hits = response.getHits();
            SearchHit[] searchHits = hits.getHits();
            for (SearchHit hit : searchHits) {
                log.info("search -> " + hit.getSourceAsString());
            }
//            SearchRequest searchRequest = new SearchRequest(indexName);
//            searchRequest.types(type);
//            // 2、用SearchSourceBuilder来构造查询请求体 ,请仔细查看它的方法，构造各种查询的方法都在这。
//            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
//            sourceBuilder.query(QueryBuilders.termQuery("userName", search));
//            sourceBuilder.from(0);
//            sourceBuilder.size(10);
//            sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
//            //3、发送请求
//            SearchResponse searchResponse = restHighLevelClient.search(searchRequest);
//            RestStatus status = searchResponse.status();
//            TimeValue took = searchResponse.getTook();
//            Boolean terminatedEarly = searchResponse.isTerminatedEarly();
//            boolean timedOut = searchResponse.isTimedOut();
//            //处理搜索命中文档结果
//            SearchHits hits = searchResponse.getHits();
//
//            long totalHits = hits.getTotalHits();
//            float maxScore = hits.getMaxScore();
//            StringBuffer stringBuffer = new StringBuffer();
//            SearchHit[] searchHits = hits.getHits();
//            for (SearchHit hit : searchHits) {
//                // do something with the SearchHit
//
//                String index = hit.getIndex();
//                String type = hit.getType();
//                String id = hit.getId();
//                float score = hit.getScore();
//
//                //取_source字段值 取成json串 取成map对象
//                String sourceAsString = hit.getSourceAsString();
//                log.info("index:" + index + "  type:" + type + "  id:" + id);
//                log.info(sourceAsString);
//                stringBuffer.append(sourceAsString);
//            }
            log.info("按用户名搜索用户结束");
            return JSON.toJSONString(response);
        }catch (Exception e){
            log.info("按用户名搜索用户异常");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String searchUserByPhone(String search) {
        log.info("按手机号搜索用户开始");
        try {
            BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery();
            boolBuilder.must(QueryBuilders.matchQuery("phone", search)); // 这里可以根据字段进行搜索，must表示符合条件的，相反的mustnot表示不符合条件的
            // boolBuilder.must(QueryBuilders.matchQuery("id", tests.getId().toString()));
            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
            sourceBuilder.query(boolBuilder);
            sourceBuilder.from(0);
            sourceBuilder.size(100); // 获取记录数，默认10
            sourceBuilder.fetchSource(new String[] { "userId", "phone","address"}, new String[] {}); // 第一个是获取字段，第二个是过滤的字段，默认获取全部
            SearchRequest searchRequest = new SearchRequest(indexName);
            searchRequest.types(type);
            searchRequest.source(sourceBuilder);
            SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            SearchHits hits = response.getHits();
            SearchHit[] searchHits = hits.getHits();
            for (SearchHit hit : searchHits) {
                log.info("search -> " + hit.getSourceAsString());
            }
//            SearchRequest searchRequest = new SearchRequest(indexName);
//            searchRequest.types(type);
//            // 2、用SearchSourceBuilder来构造查询请求体 ,请仔细查看它的方法，构造各种查询的方法都在这。
//            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
//            sourceBuilder.query(QueryBuilders.termQuery("phone", search));
//            sourceBuilder.from(0);
//            sourceBuilder.size(10);
//            sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
//            //3、发送请求
//            SearchResponse searchResponse = restHighLevelClient.search(searchRequest);
//            RestStatus status = searchResponse.status();
//            TimeValue took = searchResponse.getTook();
//            Boolean terminatedEarly = searchResponse.isTerminatedEarly();
//            boolean timedOut = searchResponse.isTimedOut();
//            //处理搜索命中文档结果
//            SearchHits hits = searchResponse.getHits();
//
//            long totalHits = hits.getTotalHits();
//            float maxScore = hits.getMaxScore();
//
//            SearchHit[] searchHits = hits.getHits();
//            StringBuffer stringBuffer = new StringBuffer();
//            for (SearchHit hit : searchHits) {
//                // do something with the SearchHit
//
//                String index = hit.getIndex();
//                String type = hit.getType();
//                String id = hit.getId();
//                float score = hit.getScore();
//
//                //取_source字段值 取成json串 取成map对象
//                String sourceAsString = hit.getSourceAsString();
//                log.info("index:" + index + "  type:" + type + "  id:" + id);
//                log.info(sourceAsString);
//                stringBuffer.append(sourceAsString);
//            }
            log.info("按手机号搜索用户结束");
            return JSON.toJSONString(response);
        }catch (Exception e){
            log.info("按手机号搜索用户异常");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String searchUserByAdress(String search) {
        log.info("按地址搜索用户开始");
        try {
            BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery();
            boolBuilder.must(QueryBuilders.matchQuery("address", search)); // 这里可以根据字段进行搜索，must表示符合条件的，相反的mustnot表示不符合条件的
            // boolBuilder.must(QueryBuilders.matchQuery("id", tests.getId().toString()));
            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
            sourceBuilder.query(boolBuilder);
            sourceBuilder.from(0);
            sourceBuilder.size(100); // 获取记录数，默认10
            sourceBuilder.fetchSource(); // 第一个是获取字段，第二个是过滤的字段，默认获取全部
            SearchRequest searchRequest = new SearchRequest(indexName);
            searchRequest.types(type);
            searchRequest.source(sourceBuilder);
            SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            SearchHits hits = response.getHits();
            SearchHit[] searchHits = hits.getHits();
            for (SearchHit hit : searchHits) {
                log.info("search -> " + hit.getSourceAsString());
            }
//            SearchRequest searchRequest = new SearchRequest(indexName);
//            searchRequest.types(type);
//            // 2、用SearchSourceBuilder来构造查询请求体 ,请仔细查看它的方法，构造各种查询的方法都在这。
//            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
//            sourceBuilder.query(QueryBuilders.termQuery("address", search));
//            sourceBuilder.from(0);
//            sourceBuilder.size(10);
//            sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
//            //3、发送请求
//            SearchResponse searchResponse = restHighLevelClient.search(searchRequest,);
//            RestStatus status = searchResponse.status();
//            TimeValue took = searchResponse.getTook();
//            Boolean terminatedEarly = searchResponse.isTerminatedEarly();
//            boolean timedOut = searchResponse.isTimedOut();
//            //处理搜索命中文档结果
//            SearchHits hits = searchResponse.getHits();
//
//            long totalHits = hits.getTotalHits();
//            float maxScore = hits.getMaxScore();
//            StringBuffer stringBuffer = new StringBuffer();
//            SearchHit[] searchHits = hits.getHits();
//            for (SearchHit hit : searchHits) {
//                // do something with the SearchHit
//
//                String index = hit.getIndex();
//                String type = hit.getType();
//                String id = hit.getId();
//                float score = hit.getScore();
//
//                //取_source字段值 取成json串 取成map对象
//                String sourceAsString = hit.getSourceAsString();
//                log.info("index:" + index + "  type:" + type + "  id:" + id);
//                log.info(sourceAsString);
//                stringBuffer.append(sourceAsString);
//            }
            log.info("按地址搜索用户结束");
            return JSON.toJSONString(response);
        }catch (Exception e){
            log.info("按地址搜索用户异常");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String batchAddUser(List<User> users) {
        log.info("批量插入用户开始");
        try {
            BulkRequest request = new BulkRequest();
            for (User user:users) {
                IndexRequest indexRequest = new IndexRequest(indexName, type, user.getUserId().toString());
                indexRequest.source(JSON.toJSONString(user), XContentType.JSON);
                request.add(indexRequest);
            }
            BulkResponse bulkResponse = restHighLevelClient.bulk(request,RequestOptions.DEFAULT);
            log.info("批量新增成功结束");
            return JSON.toJSONString(bulkResponse);
        }catch (Exception e){
            log.info("批量插入用户异常");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String batchDeleteUser(List<User> users) {
        log.info("批量删除用户开始");
        try {
            BulkRequest bulkDeleteRequest = new BulkRequest();
            for (User user:users) {
                DeleteRequest deleteRequest = new DeleteRequest(indexName, type, user.getUserId().toString());
                bulkDeleteRequest.add(deleteRequest);
            }
            BulkResponse bulkDeleteResponse = restHighLevelClient.bulk(bulkDeleteRequest, RequestOptions.DEFAULT);
            log.info("批量删除用户结束");
            return  JSON.toJSONString(bulkDeleteResponse);
        }catch (Exception e){
            log.info("批量删除用户异常");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String batchUpdateUser(List<User> users) {
        log.info("批量更新用户开始");
        try {
            BulkRequest bulkUpdateRequest = new BulkRequest();
            for (User user:users) {
                UpdateRequest updateRequest = new UpdateRequest(indexName, type, user.getUserId().toString());
                updateRequest.doc(JSON.toJSONString(user), XContentType.JSON);
                bulkUpdateRequest.add(updateRequest);
            }
            BulkResponse bulkUpdateResponse = restHighLevelClient.bulk(bulkUpdateRequest, RequestOptions.DEFAULT);
            log.info("批量更新用户结束");
            return JSON.toJSONString(bulkUpdateResponse);
        }catch (Exception e){
            log.info("批量更新用户异常");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String mohuSerachUserName(String search) {
        log.info("模糊搜索按用户名开始");
        try {
            SearchRequest searchRequest = new SearchRequest(indexName);
            // 2、用SearchSourceBuilder来构造查询请求体 ,请仔细查看它的方法，构造各种查询的方法都在这。
            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

            sourceBuilder.size(0);
            SuggestionBuilder termSuggestionBuilder =
                    SuggestBuilders.completionSuggestion("userName").prefix(search)
                            .skipDuplicates(true);
            SuggestBuilder suggestBuilder = new SuggestBuilder();
            suggestBuilder.addSuggestion("suggest-user-name", termSuggestionBuilder);
            sourceBuilder.suggest(suggestBuilder);

            searchRequest.source(sourceBuilder);

            //3、发送请求
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

            //4、处理响应
            //搜索结果状态信息
            if(RestStatus.OK.equals(searchResponse.status())) {
                // 获取建议结果
                Suggest suggest = searchResponse.getSuggest();
                CompletionSuggestion termSuggestion = suggest.getSuggestion("suggest-user-name");
                StringBuffer stringBuffer = new StringBuffer();
                for (CompletionSuggestion.Entry entry : termSuggestion.getEntries()) {
                    log.info("text: " + entry.getText().string());
                    for (CompletionSuggestion.Entry.Option option : entry) {
                        String suggestText = option.getText().string();
                        stringBuffer.append(suggestText);
                        log.info("模糊搜索按用户名结果 : " + suggestText);
                    }
                }
                return stringBuffer.toString();
            }
        }catch (Exception e){
            log.info("模糊搜索按用户名异常");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String mohuSerachAdress(String search) {
        log.info("模糊搜索按地址开始");
        try {
            SearchRequest searchRequest = new SearchRequest(indexName);
            // 2、用SearchSourceBuilder来构造查询请求体 ,请仔细查看它的方法，构造各种查询的方法都在这。
            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

            sourceBuilder.size(0);
            SuggestionBuilder termSuggestionBuilder =
                    SuggestBuilders.completionSuggestion("address").analyzer("ik_smart").prefix(search)
                            .skipDuplicates(true);
            SuggestBuilder suggestBuilder = new SuggestBuilder();
            suggestBuilder.addSuggestion("suggest-user-address", termSuggestionBuilder);
            sourceBuilder.suggest(suggestBuilder);

            searchRequest.source(sourceBuilder);

            //3、发送请求
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

            //4、处理响应
            //搜索结果状态信息
            if(RestStatus.OK.equals(searchResponse.status())) {
                // 获取建议结果
                Suggest suggest = searchResponse.getSuggest();
                StringBuffer stringBuffer = new StringBuffer();
                CompletionSuggestion termSuggestion = suggest.getSuggestion("suggest-user-address");
                for (CompletionSuggestion.Entry entry : termSuggestion.getEntries()) {
                    log.info("text: " + entry.getText().string());
                    for (CompletionSuggestion.Entry.Option option : entry) {
                        String suggestText = option.getText().string();
                        stringBuffer.append(suggestText);
                        log.info("模糊搜索按地址结果 : " + suggestText);
                    }
                }
                return stringBuffer.toString();
            }
        }catch (Exception e){
            log.info("模糊搜索按地址异常");
            e.printStackTrace();;
        }
        return null;
    }


    @Override
    public boolean existsIndex() throws IOException {
        GetIndexRequest request = new GetIndexRequest();
        request.indices(indexName);
        return restHighLevelClient.indices().exists(request, RequestOptions.DEFAULT);
    }

    @Override
    public boolean exists(User user) throws IOException {
        GetRequest getRequest = new GetRequest(indexName, type, user.getUserId().toString());
        getRequest.fetchSourceContext(new FetchSourceContext(false));
        getRequest.storedFields("_none_");
        return  restHighLevelClient.exists(getRequest, RequestOptions.DEFAULT);
    }

    @Override
    public String updateUser(User user) {
        log.info("更新用户开始");
        try {
            UpdateRequest request = new UpdateRequest(indexName, type, user.getUserId().toString());
            request.doc(JSON.toJSONString(user), XContentType.JSON);
            UpdateResponse updateResponse = restHighLevelClient.update(request, RequestOptions.DEFAULT);
            return JSON.toJSONString(updateResponse);
        }catch (Exception e){
            log.info("更新用户异常");
            e.printStackTrace();
        }
        log.info("更新用户结束");
        return null;
    }

    @Override
    public String deleteUser(Long userId) {
        log.info("删除用户开始");
        try {
            DeleteRequest deleteRequest = new DeleteRequest(indexName, type, userId.toString());
            DeleteResponse response = restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
            System.out.println("delete: " + JSON.toJSONString(response));
        }catch (Exception e){
            log.info("删除用户异常");
            e.printStackTrace();
        }
        log.info("删除用户结束");
        return null;
    }

    @Override
    public boolean deleteIndex() {
        log.info("删除索引开始");
        try {
            DeleteIndexRequest request = new DeleteIndexRequest(indexName);
            request.indicesOptions(IndicesOptions.lenientExpandOpen());
            AcknowledgedResponse deleteIndexResponse = restHighLevelClient.indices().delete(request,RequestOptions.DEFAULT);
            log.info("删除索引结束");
            return deleteIndexResponse.isAcknowledged();
        }catch (Exception e){
            log.info("删除索引异常");
            e.printStackTrace();;
        }
        return false;
    }

}
