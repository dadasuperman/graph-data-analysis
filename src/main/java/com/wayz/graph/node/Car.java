package com.wayz.graph.node;

/**
 * @Auther: zd
 * @Description:
 * @Date: 2022/7/22 14:18
 */

import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node
@Data
public class Car {
    @GeneratedValue
    @Id
    private Long id;
    private String name;
}
