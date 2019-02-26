package com.ry.es.esdemo.service;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class UserService {

    private String indexName = "user"; //数据库名称
    private String indexType = "test_es";    //数据表名称

    @Autowired
    private TransportClient client;

    public GetResponse getById(String id) {
        return this.client.prepareGet(indexName, indexType, id).get();
    }

    public IndexResponse add(String name, Integer age, String address, String mobile) throws Exception {

        XContentBuilder content = XContentFactory.jsonBuilder()
                .startObject()
                .field("name", name)
                .field("age", age)
                .field("address", address)
                .field("mobile", mobile)
                .endObject();

        IndexResponse response = this.client.prepareIndex(indexName, indexType)
                .setSource(content)
                .get();

        return response;

    }

    public DeleteResponse remove(String id) {
        return this.client.prepareDelete(indexName, indexType, id).get();
    }

    public UpdateResponse modify(String id, String name, Integer age, String address, String mobile) throws Exception {
        UpdateRequest request = new UpdateRequest(indexName, indexType, id);

        XContentBuilder builder = XContentFactory.jsonBuilder()
                .startObject();

        if (name != null) {
            builder.field("name", name);
        }
        if (age != null) {
            builder.field("age", age);
        }
        if (address != null) {
            builder.field("address", address);
        }
        if (mobile != null) {
            builder.field("mobile", mobile);
        }
        builder.endObject();

        request.doc(builder);
        return this.client.update(request).get();
    }
}
