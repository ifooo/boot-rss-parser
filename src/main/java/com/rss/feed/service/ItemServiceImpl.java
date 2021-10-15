package com.rss.feed.service;

import com.rss.feed.model.Item;
import com.rss.feed.repository.ItemRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class ItemServiceImpl implements ItemService {

    // region private fields
    private final ItemRepository itemRepository;
    // end region

    // region public methods
    @Override
    public void save(Item item) {
        itemRepository.save(item);
    }

    @Override
    public List<Item> getItems(int page, int size, String sort, String direction) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort));
        Page<Item> all = itemRepository.findAll(pageable);
        return all.getContent();
    }
    // end region
}
