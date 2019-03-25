package com.babob.sporcantam.Item;

import com.babob.sporcantam.Utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.time.LocalDate;
import static org.springframework.web.bind.annotation.RequestMethod.POST;


@Controller
@RequestMapping(path="/item")
public class ItemController {
    @Autowired
    private ItemRepository itemRepository;

    @RequestMapping(method = POST, path = "/add")
    public @ResponseBody
    Response addNewItem(@RequestParam String item_title, @RequestParam String seller
            , @RequestParam float price, @RequestParam String description
            , @RequestParam String shipping_info, @RequestParam int stock_count
            , @RequestParam String UUID){

        Item item = new Item();
        item.setItem_title(item_title);
        item.setPrice(price);
        item.setSeller(seller);
        item.setDescription(description);
        item.setShipping_info(shipping_info);
        item.setStock_count(stock_count);
        item.setUUID(UUID);
        item.setPublish_date(LocalDate.now());

        try {
            itemRepository.save(item);
            return new Response("Item Created", true);
        } catch (Exception e) {
            return new Response(e.getMessage(), false);
        }
    }
}


