package com.task.flow.service;

import com.task.flow.dto.TaskRequest;
import com.task.flow.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:bulk-atomicity;DB_CLOSE_DELAY=-1",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
class TaskServiceBulkTest {


    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void bulkCreationRollsBackWhenAnyInsertFails() {
        jdbcTemplate.execute("ALTER TABLE tasks ADD CONSTRAINT reject_fail_title CHECK (title <> 'fail')");

        assertThrows(RuntimeException.class, () -> taskService.addBulkTask(
                List.of(
                        new TaskRequest("succeeds", "atomicity test", LocalDate.now()),
                        new TaskRequest("fail", "atomicity test", LocalDate.now())
                )
        ));

        assertEquals(0, taskRepository.count());
    }
}
