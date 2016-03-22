package ru.bona.shortener.domain.repository;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bona.shortener.domain.model.URLEntry;

/**
 * URLRepository
 *
 * @author Kontsur Alex (bona)
 * @since 23.11.14
 */
@Repository
public interface URLRepository extends JpaRepository<URLEntry, Long> {

    @Cacheable(value="shorted", unless="#result == null")
    URLEntry findByShortedURL(String shortedURL);

    @Cacheable(value="source", unless="#result == null")
    URLEntry findBySourceURL(String sourceURL);

}