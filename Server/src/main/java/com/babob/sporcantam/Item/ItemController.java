package com.babob.sporcantam.Item;

import com.babob.sporcantam.Seller.SellerRepository;
import com.babob.sporcantam.Utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;


@Controller
@RequestMapping(path="/item")
public class ItemController {
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private SellerRepository sellerRepository;

    @RequestMapping(method = POST, path = "/add")
    public @ResponseBody
    Response addNewItem(@CookieValue(name = "JSESSIONID") String sessionID
            , @RequestParam String item_title
            , @RequestParam Float price, @RequestParam String item_description
            , @RequestParam String shipping_info, @RequestParam Integer stock_count
            , @RequestParam String UUID){

        Item item = new Item();
        item.setItem_title(item_title);
        item.setPrice(price);
        item.setSeller(sellerRepository.findBySessionID(sessionID).iterator().next().getCompany_name());
        item.setDescription(item_description);
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

    @RequestMapping(method = GET, path = "/{UUID}")
    public @ResponseBody Item showItem(@PathVariable("UUID") String UUID) {
        try{
            Item item = itemRepository.findByID(UUID).iterator().next();
            return item;
        }catch (Exception e){
            return null;
        }
    }

    @RequestMapping(method = POST, path = "/{UUID}/update")
    public @ResponseBody
    Response updateItemInfo(@PathVariable("UUID") String UUID
        , @RequestParam(value = "item_title", required = false, defaultValue = " ") String item_title
        , @RequestParam(value = "price", required = false, defaultValue = "-1.0") float price
        , @RequestParam(value = "description", required = false, defaultValue = " ") String description
        , @RequestParam(value = "stock_count", required = false, defaultValue = "-1") int stock_count
        , @RequestParam(value = "shipping_info", required = false, defaultValue = " ") String shipping_info) {

        Item item = itemRepository.findByID(UUID).iterator().next();
        try{
            if(!item_title.equals(" "))
                item.setItem_title(item_title);
            if(price != -1.0)
                item.setPrice(price);
            if(stock_count != -1)
                item.setStock_count(stock_count);
            if(!description.equals(" "))
                item.setDescription(description);
            if(!shipping_info.equals(" "))
                item.setShipping_info(shipping_info);
                itemRepository.save(item);
            return new Response("Item information updated succesfully.",true);

        }catch (Exception e){
            return new Response("Cannot update item info!",false);
        }
    }
    @RequestMapping(method = POST, path = "/{UUID}/delete")
    public @ResponseBody Response deleteItem(@PathVariable("UUID") String UUID) {
        try{
            Item item = itemRepository.findByID(UUID).iterator().next();
            itemRepository.delete(item);
            return new Response("Item Deleted",true);
        }catch (Exception e){
            return new Response("Couldn't find the item",false);
        }
    }
}


