package com.architect.process_service.service;

import com.architect.process_service.service.workflow.job.UnzipJobHandler;
import com.architect.process_service.service.workflow.repository.JobDetailRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@ExtendWith(MockitoExtension.class)
public class UnzipJobHandlerTest {
    @InjectMocks
    private UnzipJobHandler unzipJobHandler;

    @Mock
    private JobDetailRepository jobRepository;

    @Test
    void test() {
        assertTrue(unzipJobHandler.canHandle("test.zip"));
//        when(jobRepository.existsById(10L)).thenReturn(false);
//        assertNotNull(unzipJobHandler);
    }

    @ParameterizedTest
    @CsvSource({
            "test.zip, true",
            "doc.pdf, false",
            "'', false"
    })
    void testCanHandleParameterized(String fileName, boolean expected) {
        assertEquals(expected, unzipJobHandler.canHandle(fileName));
    }
}
