package com.rss.feed.controller;

import com.rss.feed.model.Item;
import com.rss.feed.service.ItemService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class ItemController {

    // region private fields
    private final ItemService itemService;
    // end region

    // region public methods
    @GetMapping("/items")
    public List<Item> getItems(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                               @RequestParam(value = "size", required = false, defaultValue = "10") Integer size,
                               @RequestParam(value = "sort", required = false, defaultValue = "publicationDate") String sort,
                               @RequestParam(value = "direction", required = false, defaultValue = "desc") String direction) {
        return itemService.getItems(page, size, sort, direction);
    }
    // end region
}
