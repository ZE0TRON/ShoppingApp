package com.babob.sporcantam.Seller;

import com.babob.sporcantam.Item.Item;
import com.babob.sporcantam.Item.ItemRepository;
import com.babob.sporcantam.Utils.ItemList;
import com.babob.sporcantam.Utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping(path="/seller")
public class SellerController {
    @Autowired
    private SellerRepository sellerRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    private Item findItem (Seller seller, String UUID) {
        Collection<Item> items= itemRepository.findBySeller(seller.getCompany_name());
        Item item = itemRepository.findByUUID(UUID).iterator().next();
        if(items.contains(item)){
            return item;
        }
        else return null;
    }


    @RequestMapping(method = POST, path = "/add")  // Map ONLY GET Requests
    public @ResponseBody
    Response addNewUser(@CookieValue(name = "JSESSIONID") String sessionID, @RequestParam String email
            , @RequestParam String password, @RequestParam String first_name, @RequestParam String last_name
            , @RequestParam(value = "company_name") String company_name) {
        Seller seller = new Seller();
        seller.setEmail(email);
        seller.setFirst_name(first_name);
        seller.setLast_name(last_name);
        System.out.println(password);
        seller.setPassword(passwordEncoder.encode(password));
        System.out.println(passwordEncoder.encode(password));
        System.out.println(passwordEncoder.encode(password));
        seller.setCompany_name(company_name);
        seller.setSessionID(sessionID);
        try {
            sellerRepository.save(seller);
            return new Response("Seller Created", true);
        } catch (Exception e) {

            return new Response(e.getMessage(), false);
        }
    }

    @RequestMapping(method = POST, path = "/login")
    public @ResponseBody
    Response customerLogin(@RequestParam String email, @RequestParam String password,
                           @CookieValue(name = "JSESSIONID") String sessionID) {
        try {
            Seller seller = sellerRepository.findByEmail(email).iterator().next();
            System.out.println(password);
            System.out.println(seller.getPassword());//get first (and oly) customer
            if (passwordEncoder.matches(password, seller.getPassword())) {
                seller.setSessionID(sessionID);
                sellerRepository.save(seller);
                return new Response("Login successful", true);
            } else {
                return new Response("Incorrect password", false);
            }

        } catch (Exception e) {
            return new Response("No such user", false);
        }
    }

    @RequestMapping(method = POST, path = "/update")
    public @ResponseBody
    Response updateSellerInfo(@CookieValue(name = "JSESSIONID") String sessionID
            , @RequestParam(value = "password", required = false, defaultValue = " ") String password
            , @RequestParam(value = "first_name", required = false, defaultValue = " ") String first_name
            , @RequestParam(value = "last_name", required = false, defaultValue = " ") String last_name
            , @RequestParam(value = "address", required = false, defaultValue = " ") String IBAN
            , @RequestParam(value = "address", required = false, defaultValue = " ") String phone_number
            , @RequestParam(value = "address", required = false, defaultValue = " ") String company_address) {
        Seller seller = sellerRepository.findBySessionID(sessionID).iterator().next();
        try {
            if (password != " ")
                seller.setPassword(passwordEncoder.encode(password));
            if (first_name != " ")
                seller.setFirst_name(first_name);
            if (last_name != " ")
                seller.setLast_name(last_name);
            if (IBAN != " ")
                seller.setIBAN(IBAN);
            if (company_address != " ")
                seller.setCompany_address(company_address);
            if (phone_number != " ")
                seller.setPhone_number(phone_number);

            return new Response("Seller information updated succesfully.", true);
        } catch (Exception e) {
            return new Response("Cannot update seller info!", false);
        }
    }

    @RequestMapping(method = POST, path = "/my-items")
    public @ResponseBody
    ItemList getItemsOfUser(@CookieValue(name = "JSESSIONID") String sessionID) {
        Seller seller = sellerRepository.findBySessionID(sessionID).iterator().next();
        Collection<Item> items= itemRepository.findBySeller(seller.getCompany_name());
        return new ItemList(items);
    }

    @RequestMapping(method = POST, path = "/my-items/{UUID}/update")
    public String updateItemInfo(@CookieValue(name = "JSESSIONID") String sessionID,
                                                 @PathVariable("UUID") String UUID) {
        Seller seller = sellerRepository.findBySessionID(sessionID).iterator().next();
        Item item = findItem(seller,UUID);
        if(item!= null){
            return "forward:/item/"+UUID+"/update";
        }
        else{
            return "You have no item with id "+ UUID;
        }
    }

    @RequestMapping(method = POST, path = "/my-items/{UUID}/delete")
    public String deleteItem(@CookieValue(name = "JSESSIONID") String sessionID,
                                 @PathVariable("UUID") String UUID) {
        Seller seller = sellerRepository.findBySessionID(sessionID).iterator().next();
        Item item = findItem(seller,UUID);
        if(item!= null){
            System.out.println("Forwarding item with UUID" +UUID);
            return "forward:/item/"+UUID+"/delete";
        }
        else{
            return "You have no item with id "+ UUID;
        }


    }
}