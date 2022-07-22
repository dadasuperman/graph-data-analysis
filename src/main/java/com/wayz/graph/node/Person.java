package com.wayz.graph.node;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.Set;

/**
 * @Auther: zd
 * @Description: person节点
 * @Date: 2022/7/22 14:08
 */
@Node
@Data
public class Person {
    @GeneratedValue
    @Id
    private Long id;
    private String name;
    private String sex;

    @Relationship(type = "房产",direction = Relationship.Direction.OUTGOING)
    public Set<House> houses;

    @Relationship(type = "车",direction = Relationship.Direction.OUTGOING)
    public Set<Car> cars;

    @Relationship(type = "同事",direction = Relationship.Direction.INCOMING)
    public Set<Person> associates;


}
