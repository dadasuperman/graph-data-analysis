package com.wayz.graph.mapper;

import com.wayz.graph.node.Person;
import org.springframework.data.neo4j.repository.Neo4jRepository;


public interface PersonRepository extends Neo4jRepository<Person,Long> {
}
