package com.babob.sporcantam.Item;

import com.babob.sporcantam.Customer.Customer;
import com.babob.sporcantam.Customer.CustomerController;
import com.babob.sporcantam.Customer.CustomerRepository;
import com.babob.sporcantam.OrderHistory.OrderHistoryRepository;
import com.babob.sporcantam.Seller.SellerRepository;
import com.babob.sporcantam.Utils.ItemList;
import com.babob.sporcantam.Utils.Response;
import com.babob.sporcantam.ViewHistory.ViewHistory;
import com.babob.sporcantam.ViewHistory.ViewHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Iterator;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;


@Controller
@RequestMapping(path="/item")
public class ItemController {
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private SellerRepository sellerRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ViewHistoryRepository viewHistoryRepository;
    @Autowired
    private OrderHistoryRepository orderHistoryRepository;

    @RequestMapping(method = POST, path = "/add")
    public @ResponseBody
    Response addNewItem(@CookieValue(name = "JSESSIONID") String sessionID
            , @RequestParam String item_title
            , @RequestParam Float price, @RequestParam String item_description
            , @RequestParam String shipping_info, @RequestParam Integer stock_count
            , @RequestParam String UUID
            , @RequestParam String category){

        Item item = new Item();
        item.setItem_title(item_title);
        item.setPrice(price);
        item.setSeller(sellerRepository.findBySessionID(sessionID).iterator().next().getCompany_name());
        item.setDescription(item_description);
        item.setShipping_info(shipping_info);
        item.setStock_count(stock_count);
        item.setUUID(UUID);
        item.setPublish_date(LocalDate.now());
        item.setCategory(category);

        try {
            itemRepository.save(item);
            return new Response("Item Created", true);
        } catch (Exception e) {
            return new Response(e.getMessage(), false);
        }
    }

    @RequestMapping(method = GET, path = "/{UUID}")
    public @ResponseBody Item showItem(@PathVariable("UUID") String UUID
            , @CookieValue(name = "JSESSIONID") String sessionID) {
        try{
            Item item = itemRepository.findByUUID(UUID).iterator().next();
            if(sessionID!=null){ //if its not non-registered user.
                Collection<Customer> customerList = customerRepository.findBySessionID(sessionID);
                if(!customerList.isEmpty()){
                    Customer customer =  customerList.iterator().next();
                    ViewHistory new_history = new ViewHistory();
                    new_history.setUUID(UUID);
                    new_history.setCustomer_email(customer.getEmail());
                    viewHistoryRepository.save(new_history);
                }
            }
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
        , @RequestParam(value = "shipping_info", required = false, defaultValue = " ") String shipping_info
        , @RequestParam(value = "category", required = false, defaultValue = " ") String category) {

        Iterator<Item> it = itemRepository.findByUUID(UUID).iterator();
        Item item =  it.next();

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
            if(!category.equals(" "))
                item.setCategory(category);
            itemRepository.save(item);
            return new Response("Item information updated succesfully.",true);

        }catch (Exception e){
            return new Response("Cannot update item info!",false);
        }
    }
    @RequestMapping(method = POST, path = "/{UUID}/delete")
    public @ResponseBody Response deleteItem(@PathVariable("UUID") String UUID) {
        try{
            Iterator<Item> it = itemRepository.findByUUID(UUID).iterator();
            Item item =  it.next();
            itemRepository.delete(item);
            return new Response("Item Deleted",true);
        }catch (Exception e){
            return new Response("Couldn't find the item",false);
        }
    }
    @RequestMapping(method = POST, path = "/getItems")
    public @ResponseBody
    ItemList getItems() {
        Collection<Item> items = itemRepository.getAllItems();
        ItemList itemList = new ItemList(items);
        return itemList;
    }

    @RequestMapping(method = GET, path = "/{category}")
    public @ResponseBody
    ItemList getItemsByCategory(@PathVariable("category") String category) {
        Collection<Item> items = itemRepository.getItemsByCategory(category);
        ItemList itemList = new ItemList(items);
        return itemList;
    }
}


