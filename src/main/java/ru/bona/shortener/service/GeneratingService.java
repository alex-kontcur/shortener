package ru.bona.shortener.service;

import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;
import ru.bona.shortener.domain.model.URLEntry;
import ru.bona.shortener.domain.repository.URLRepository;

import javax.inject.Inject;

/**
 * GeneratingService
 *
 * @author Kontsur Alex (bona)
 * @since 24.11.14
 */
@Component
public class GeneratingService {

    @Inject
    private URLRepository urlRepository;

    @Inject
    private ShorteningService shorteningService;

    @Inject
    private CacheManager cacheManager;


    public URLEntry prepareURLEntry(String sourceUrl) {
        URLEntry urlEntry = urlRepository.findBySourceURL(sourceUrl);
        if (urlEntry != null) {
            return urlEntry;
        }

        URLEntry entry = new URLEntry(sourceUrl);
        entry = urlRepository.save(entry);
        String shorted = shorteningService.shortURL(entry.getId());
        entry.setShortedURL(shorted);
        return urlRepository.save(entry);
    }
}
