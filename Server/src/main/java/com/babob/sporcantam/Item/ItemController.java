package com.babob.sporcantam.Item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path="/item")
public class ItemController {
    @Autowired
    private ItemRepository itemRepository;
}
