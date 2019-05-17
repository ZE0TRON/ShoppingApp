package com.babob.sporcantam.Customer;
import com.babob.sporcantam.CartItem.CartItem;
import com.babob.sporcantam.CartItem.CartItemRepository;
import com.babob.sporcantam.Item.Item;
import com.babob.sporcantam.Item.ItemRepository;
import com.babob.sporcantam.Utils.CartItemList;
import com.babob.sporcantam.Utils.ItemList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.babob.sporcantam.Utils.Response;

import java.util.Collection;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping(path="/customer")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private CartItemRepository cartItemRepository;
    @RequestMapping(method=POST,path="/add")
    public @ResponseBody
    Response addCustomer (@CookieValue(name = "JSESSIONID") String sessionID, @RequestParam String email
            ,@RequestParam String password, @RequestParam String first_name,@RequestParam String last_name){
        Customer customer = new Customer();
        customer.setEmail(email);
        customer.setFirst_name(first_name);
        customer.setLast_name(last_name);
        customer.setPassword(passwordEncoder.encode(password));
        customer.setSessionID(sessionID);
        long cartID = email.hashCode();
        try {
            customer.setCartID(cartID);
            customerRepository.save(customer);

            return new Response("Customer Created",true);
        }
        catch (Exception e){
            return new Response(e.getMessage(),false);
        }
    }

    @RequestMapping(method=POST,path="/email")
    public @ResponseBody
    Response getCustomerEmail(@CookieValue(name = "JSESSIONID") String sessionID){
        Collection<Customer> customerCollection = customerRepository.findBySessionID(sessionID);
        Customer customer = customerCollection.iterator().next();
        if (customer.getEmail()==null) {
            return new Response("Login first",false);
        }
        return new Response(customer.getEmail(), true);
    }
    @RequestMapping(method=POST,path="/addToCart")
    public @ResponseBody
    Response addToCart(@RequestParam String itemID, @CookieValue(name = "JSESSIONID") String sessionID){
        Collection<Customer> customerCollection = customerRepository.findBySessionID(sessionID);
        Customer customer = customerCollection.iterator().next();
        if(customer!=null) {
            Collection<Item> itemCollection = itemRepository.findByUUID(itemID);
            Item item = itemCollection.iterator().next();
            if(item!=null) {
                CartItem cartItem = new CartItem(item);
                cartItem.setShoppingCartID(customer.getCartID());
                try {
                    cartItemRepository.save(cartItem);
                    return new Response("Item added to Shopping Cart",true);
                }
                catch (Exception e){
                    return new Response(e.getMessage(),false);
                }
            }
            else {
                return new Response("No such item",false);
            }
        } else {
            return new Response("Login first",false);
        }

    }
    @RequestMapping(method=POST,path="/viewCart")
    public @ResponseBody
    CartItemList viewCart(@CookieValue(name = "JSESSIONID") String sessionID){
        Collection<Customer> customerCollection = customerRepository.findBySessionID(sessionID);
        Customer customer = customerCollection.iterator().next();
        if(customer!=null) {
            Collection<CartItem> items = cartItemRepository.getItemsById(customer.getCartID());
            CartItemList itemList = new CartItemList(items);
            return itemList;
            }
         else {
            return null;
        }

    }
    @RequestMapping(method=POST,path="/")
    public @ResponseBody
    Customer getCustomer(@CookieValue(name = "JSESSIONID") String sessionID){
        Collection<Customer> customerCollection = customerRepository.findBySessionID(sessionID);
        Customer customer = customerCollection.iterator().next();
        if(customer!=null) {
            return customer;
        }
        else {
            return null;
        }

    }
    @RequestMapping(method=POST,path="/removeFromCart")
    public @ResponseBody
    Response removeFromCart(@RequestParam String itemID,@CookieValue(name = "JSESSIONID") String sessionID){
        Collection<Customer> customerCollection = customerRepository.findBySessionID(sessionID);
        Customer customer = customerCollection.iterator().next();
        if(customer!=null) {
            long cartID = customer.getCartID();
            Collection<CartItem> items = cartItemRepository.getCartItems(itemID, cartID);
            if (items.iterator().hasNext()) {
                CartItem willDeletedItem = items.iterator().next();
                cartItemRepository.delete(willDeletedItem);
                return new Response("Item deleted from cart", true);
            } else {
                return new Response("Couldn't find the item", false);

            }
        }else {
            return new Response("Login first", false);
        }

    }

    @RequestMapping(method=POST,path="/login")
    public @ResponseBody
    Response customerLogin(@RequestParam String email, @RequestParam String password,
                         @CookieValue(name = "JSESSIONID") String sessionID){
        try{
            Customer customer = customerRepository.findByEmail(email).iterator().next(); //get first (and oly) customer
            if(passwordEncoder.matches(password,customer.getPassword()))
            {
                customer.setSessionID(sessionID);
                customerRepository.save(customer);
                return new Response("Login successful",true);
            }

            else{
                return new Response("Incorrect password",false);
            }

        }
        catch(Exception e){
            return new Response("No such user",false);
        }
    }

    @RequestMapping(method=POST,path ="/update")
    public @ResponseBody
    Response updateCustomerInfo(@CookieValue(name = "JSESSIONID") String sessionID
            ,@RequestParam(value="password", required = false, defaultValue=" ") String password
            ,@RequestParam(value="first_name", required = false, defaultValue=" ") String first_name
            ,@RequestParam(value="last_name", required = false, defaultValue=" ") String last_name
            ,@RequestParam(value="address", required = false, defaultValue=" ") String address) {
        Customer customer = customerRepository.findBySessionID(sessionID).iterator().next();
        try{
            if(password!= " ")
                customer.setPassword(passwordEncoder.encode(password));
            if(first_name!= " ")
                customer.setFirst_name(first_name);
            if(last_name!= " ")
                customer.setLast_name(last_name);
            if(address!= " ")
                customer.setAddress(address);
            customerRepository.save(customer);
            return new Response("Customer information updated succesfully.",true);

        }
        catch (Exception e){
            return new Response("Cannot update customer info!",false);
        }
    }
}
