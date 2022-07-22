package com.wayz.graph.node;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

/**
 * @Auther: zd
 * @Description:
 * @Date: 2022/7/22 14:18
 */
@Node
@Data
public class House {
    @GeneratedValue
    @Id
    private Long id;
    private String address;
}
