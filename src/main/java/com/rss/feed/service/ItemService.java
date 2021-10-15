package com.rss.feed.service;

import com.rss.feed.model.Item;

import java.util.List;

public interface ItemService {

    /**
     * Saves an Item in the db.
     *
     * @param item item
     */
    void save(Item item);

    /**
     * Retrieves filtered items from the db.
     *
     * @param page      page number
     * @param size      size of the items to be retrieved per call.
     * @param sort      field which the items will be sorted by.
     * @param direction ascending or descending direction
     * @return List of items.
     */
    List<Item> getItems(int page, int size, String sort, String direction);
}
