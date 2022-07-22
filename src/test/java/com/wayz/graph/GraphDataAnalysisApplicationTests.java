package com.wayz.graph;

import com.wayz.graph.mapper.PersonRepository;
import com.wayz.graph.node.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GraphDataAnalysisApplicationTests {
    @Autowired
    private PersonRepository personRepository;

    @Test
    void contextLoads() {
        Person person = new Person();
        personRepository.save(person);
    }

}
