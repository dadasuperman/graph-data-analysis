package com.wayz.graph.config;

import com.arangodb.ArangoCursor;
import com.arangodb.ArangoDB;
import com.arangodb.entity.CollectionType;
import com.arangodb.entity.EdgeDefinition;
import com.arangodb.entity.GraphEntity;
import com.arangodb.mapping.ArangoJack;
import com.arangodb.model.CollectionCreateOptions;
import com.arangodb.model.GraphCreateOptions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 * @Auther: zd
 * @Description: arango
 * @Date: 2022/7/22 17:15
 */
public class ArangoConfig {
    public static ArangoDB getSession(){
        return  new ArangoDB.Builder()
                .user("root")
                .password("root")
                .serializer(new ArangoJack())
                .build();
    }

    public static void main(String[] args) {
        ArangoDB arangoDB = getSession();
//        arangoDB.createDatabase("database");
//        arangoDB.db("database").graph("myGraph").drop();
//        arangoDB.db("database").collection("v_user").drop();
//        arangoDB.db("database").collection("e_user_relation").drop();



        GraphEntity graphEntity = arangoDB.db("database").getGraphs().stream().filter(x -> x.getName().equals("myGraph")).findFirst().get();
        System.out.println("graphEntity = " + graphEntity);
    }

    public void createGraph(){
        ArangoDB arangoDB = getSession();
        //创建顶点集合v_user
        arangoDB.db("database").createCollection("v_user");

        //创建边集合e_user_relation
        CollectionCreateOptions coptions = new CollectionCreateOptions();
        coptions.type(CollectionType.EDGES);
        arangoDB.db("database").createCollection("e_user_relation", coptions);

        //批量插入顶点数据
        Collection<String> data = new ArrayList<String>();
        data.add("{" +
                "  \"_key\": \"u3\"," +
                "  \"name\":\"zhangsan\"" +
                "}");
        data.add("{" +
                "  \"_key\": \"u4\"," +
                "  \"name\":\"lisi\"" +
                "}");
        data.add("{" +
                "  \"_key\": \"u5\"," +
                "  \"name\":\"wangwu\"" +
                "}");
        data.add("{" +
                "  \"_key\": \"u6\"," +
                "  \"name\":\"zhaoliu\"" +
                "}");

        arangoDB.db("database").collection("v_user").insertDocuments(data);

        //批量插入边数据
        data = new ArrayList<String>();
        data.add("{" +
                "  \"_key\": \"u3_u4\"," +
                "  \"_from\": \"v_user/u3\"," +
                "  \"_to\": \"v_user/u4\"," +
                "  \"relation\": \"is_father\"" +
                "}");
        data.add("{" +
                "  \"_key\": \"u4_u5\"," +
                "  \"_from\": \"v_user/u4\"," +
                "  \"_to\": \"v_user/u5\"," +
                "  \"relation\": \"is_father\"" +
                "}");

        arangoDB.db("database").collection("e_user_relation").insertDocuments(data);

        //创建图
        Collection<EdgeDefinition> edgeDefinitions = new ArrayList<EdgeDefinition>();
        EdgeDefinition edgeDefinition = new EdgeDefinition();

        edgeDefinition.collection("e_user_relation");

        edgeDefinition.from("v_user");

        edgeDefinition.to("v_user");

        edgeDefinitions.add(edgeDefinition);

        GraphCreateOptions goptions = new GraphCreateOptions();
        goptions.orphanCollections("myGraph");

        arangoDB.db("database").createGraph("myGraph", edgeDefinitions, goptions);

        //使用AQL查询, 结果 (u3)-[is_father]->(u4)-[is_father]->(u5)
        String query = "FOR v,e,p IN 2 OUTBOUND 'v_user/u3' GRAPH 'myGraph' " +
                "return p";

        ArangoCursor<Map> result = arangoDB.db("database").query(query, null, null, Map.class);

        while (result.hasNext()) {
            Map path = result.next();
            System.out.println(path);
        }

        System.out.println("-------------");
        //新增一个顶点
        String newVertex = "{" +
                "  \"_key\": \"u7\"," +
                "  \"name\":\"chen7\"" +
                "}";
        arangoDB.db("database").graph("myGraph").vertexCollection("v_user").insertVertex(newVertex);

        //新增加一条表 (u4)-[is_father]->(u6)
        String newEdge = "{" +
                "  \"_key\": \"u4_u7\"," +
                "  \"_from\": \"v_user/u4\"," +
                "  \"_to\": \"v_user/u7\"," +
                "  \"relation\": \"is_father\"" +
                "}";
        arangoDB.db("database").graph("myGraph").edgeCollection("e_user_relation").insertEdge(newEdge);

        //使用AQL查询, 将有两条结果 (u3)-[is_father]->(u4)-[is_father]->(u5)，(u3)-[is_father]->(u4)-[is_father]->(u7)
        query = "FOR v,e,p IN 2 OUTBOUND 'v_user/u3' GRAPH 'myGraph' " +
                "return p";

        result = arangoDB.db("database").query(query, null, null, Map.class);

        while (result.hasNext()) {
            Map path = result.next();
            System.out.println(path);
        }
    }
}
