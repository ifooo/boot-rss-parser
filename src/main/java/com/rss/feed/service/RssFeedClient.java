package com.rss.feed.service;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import com.rss.feed.model.Item;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class RssFeedClient {

    // region private fields
    @Value("${feed.resource}")
    private URL feedSource;

    private final ItemService itemService;
    // end region

    // region .ctor
    public RssFeedClient(ItemService itemService) {
        this.itemService = itemService;
    }
    // end region


    // region private methods

    /**
     * Cron job for connecting and fetching data from external rss feeder.
     *
     * @throws IOException IO exception
     * @throws FeedException Feed exception
     */
    @Scheduled(cron = "${feed.rss.interval}")
    private void readRssFeed() throws IOException, FeedException {
        log.info("Cron job for fetching rss feed has started at [{}]", new Date());
        SyndFeedInput input = new SyndFeedInput();
        SyndFeed feed = input.build(new XmlReader(feedSource));

        processEntries(feed.getEntries());
    }

    private void processEntries(List<SyndEntry> entries) {
        entries.forEach(syndEntry -> {
            Item item = Item
                    .builder()
                    .title(syndEntry.getTitle())
                    .link(syndEntry.getLink())
                    .publicationDate(syndEntry.getPublishedDate())
                    .build();

            try {
                itemService.save(item);
            } catch (DataIntegrityViolationException e) {
                log.info("Item with title [{}] and link [{}] is already saved in db.", syndEntry.getTitle(), syndEntry.getLink());
            }
        });
    }
}
