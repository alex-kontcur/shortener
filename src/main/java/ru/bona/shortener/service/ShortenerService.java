package ru.bona.shortener.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.bona.shortener.domain.model.URLEntry;
import ru.bona.shortener.domain.repository.URLRepository;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

/**
 * ShortenerService
 *
 * @author Kontsur Alex (bona)
 * @since 23.11.14
 */
@Controller
@RequestMapping(value = "/shortener", method = {RequestMethod.GET, RequestMethod.POST})
public class ShortenerService {

    private static final Logger logger = LoggerFactory.getLogger(ShortenerService.class);

    @Inject
    private URLRepository urlRepository;

    @Inject
    private GeneratingService generatingService;


    @RequestMapping("shortemall")
    @ResponseBody
    public String shortemall(@NotNull @RequestParam("sourceUrl") String sourceUrl) {
        logger.info("Start to process shortemall -> sourceUrl = {}", sourceUrl);
        URLEntry entry = generatingService.prepareURLEntry(sourceUrl);
        return entry.getShortedURL();
    }

    @RequestMapping("findByShorted")
    @ResponseBody
    public String findByShorted(@NotNull @RequestParam("shortedUrl") String shortedUrl) {
        logger.info("Start to process findByShortedURL -> shortedUrl = {}", shortedUrl);
        URLEntry urlEntry = urlRepository.findByShortedURL(shortedUrl);
        return urlEntry != null ? urlEntry.getSourceURL() : "Unknown URL : " + shortedUrl;
    }

}
