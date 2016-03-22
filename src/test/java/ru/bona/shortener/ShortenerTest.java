package ru.bona.shortener;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * ShortenerTest
 *
 * @author Kontsur Alex (bona)
 * @since 24.11.14
 */
@SuppressWarnings("JUnitTestMethodWithNoAssertions")
public class ShortenerTest extends AbstractMVCTest {

    @Value("${shortener.parent.url}")
    private String parentURL;

    @Test
    public void locationResolutionWorks() throws Exception {
        long start = System.currentTimeMillis();
        int count = 1000;
        String testURL = "http://www.vogella.com/";
        List<String> shortenedList = new ArrayList<>();
        for (int i = 0; i < count; i++) {

            String url = testURL + i;
            MvcResult mvcResult = perform(get("/shortener/shortemall?sourceUrl=" + url))
                .andExpect(status().isOk())
                .andExpect(content().string(anyOf(startsWith(parentURL))))
                .andReturn();

            shortenedList.add(mvcResult.getResponse().getContentAsString());
        }
        long delta = System.currentTimeMillis() - start;
        logger.info("");
        logger.info("-----------------------------------------------------------");
        logger.info("{} generations took : " + delta / 1000 + " seconds", count);
        logger.info("-----------------------------------------------------------");
        logger.info("");

        start = System.currentTimeMillis();
        for (String shortened : shortenedList) {
            perform(get("/shortener/findByShorted?shortedUrl=" + shortened))
                .andExpect(status().isOk())
                .andExpect(content().string(anyOf(startsWith(testURL))))
                .andReturn();
        }
        delta = System.currentTimeMillis() - start;
        logger.info("");
        logger.info("-----------------------------------------------------------");
        logger.info("{} resolves took : " + delta / 1000 + " seconds", shortenedList.size());
        logger.info("-----------------------------------------------------------");
        logger.info("");
    }

}