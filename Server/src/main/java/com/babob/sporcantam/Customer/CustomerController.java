package com.babob.sporcantam.Customer;
import com.babob.sporcantam.CartItem.CartItem;
import com.babob.sporcantam.CartItem.CartItemRepository;
import com.babob.sporcantam.Item.Item;
import com.babob.sporcantam.Item.ItemRepository;
import com.babob.sporcantam.OrderHistory.OrderHistory;
import com.babob.sporcantam.OrderHistory.OrderHistoryRepository;
import com.babob.sporcantam.Order.Order;
import com.babob.sporcantam.Order.OrderRepository;
import com.babob.sporcantam.Seller.Seller;
import com.babob.sporcantam.Seller.SellerRepository;
import com.babob.sporcantam.Utils.CartItemList;
import com.babob.sporcantam.Utils.ItemList;
import com.babob.sporcantam.ViewHistory.ViewHistory;
import com.babob.sporcantam.ViewHistory.ViewHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.babob.sporcantam.Utils.Response;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.GET;


@Controller
@RequestMapping(path="/customer")
public class CustomerController {

    @Autowired
    private SellerRepository sellerRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired    
    private ViewHistoryRepository viewHistoryRepository;
    @Autowired
    private OrderHistoryRepository orderHistoryRepository;
    private OrderRepository orderRepository;
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

    @RequestMapping(method=POST,path="/logout")
    public @ResponseBody
    Response customerLogout( @CookieValue(name = "JSESSIONID") String sessionID){
        try{
            Customer customer = customerRepository.findBySessionID(sessionID).iterator().next(); //get first (and only) customer
            customer.setSessionID(null);
            customerRepository.save(customer);
            return new Response("Logged out successfully.",true);
        }
        catch(Exception e){
            return new Response("Could not logout.",false);
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


    @RequestMapping(method=POST,path ="/showViewHistory")
    public @ResponseBody
    Collection<Item> showViewHistory(@CookieValue(name = "JSESSIONID") String sessionID) {
        Customer customer = customerRepository.findBySessionID(sessionID).iterator().next();
        String customer_email = customer.getEmail();
        Collection<String> history_uuids = viewHistoryRepository.findUUIDsByCustomerEmail(customer_email);
        Collection<Item> history_items = itemRepository.findByUUIDList(history_uuids);
        return history_items;
    }

    @RequestMapping(method=POST,path ="/showOrderHistory")
    public @ResponseBody
    Collection<Order> showOrderHistory(@CookieValue(name = "JSESSIONID") String sessionID) {
        Customer customer = customerRepository.findBySessionID(sessionID).iterator().next();
        String customer_email = customer.getEmail();
        Collection<String> history_sale_ids = orderHistoryRepository.findSaleIdsByCustomerEmail(customer_email);
        Collection<Order> order_list = orderRepository.findByOrderIDList(history_sale_ids);
        return order_list;
    }

    @RequestMapping(method=GET,path ="/showOrder/{order_id}")
    public @ResponseBody
    Order showOrder(@CookieValue(name = "JSESSIONID") String sessionID, @PathVariable("order_id") String order_id) {
        Customer customer = customerRepository.findBySessionID(sessionID).iterator().next();
        String customer_email = customer.getEmail();
        Order order = orderRepository.findByOrderID(order_id).iterator().next();
        if(!order.getCustomerEmail().equals(customer_email)){
            return null;
        }
        return order;
    }


    @RequestMapping(method=POST,path ="/checkout")
    public @ResponseBody
    Response checkout(@CookieValue(name = "JSESSIONID") String sessionID
            ,@RequestParam Long cartID) {

        try{
            Customer customer = customerRepository.findBySessionID(sessionID).iterator().next();
            Collection<CartItem> items = cartItemRepository.getItemsById(cartID);
            Iterator<CartItem> it = items.iterator();
            ArrayList<String> shipping_list =new  ArrayList<String>();
            Set<Seller> sellerList = new HashSet<Seller>();
            ArrayList<String> itemIDs = new ArrayList<String>();

            double item_cost = 0;
            double total = 0;
            int shipping_count = 0;
            while(it.hasNext()){
               CartItem item = it.next();
               if(!shipping_list.contains(item.getShipping_info())){
                   shipping_count+=1;
                   shipping_list.add(item.getShipping_info());
                }
               itemIDs.add(item.getUUID());
               item_cost += item.getPrice()+((item.getPrice()/100)*18);
               Seller seller = sellerRepository.findByCompanyName(item.getSeller()).iterator().next();
               seller.setBalance(seller.getBalance()+item.getPrice());
               sellerList.add(seller);
            }
            total = item_cost+shipping_count*5;
            if(customer.getBalance()>= total){
                customer.setBalance(customer.getBalance()-total);
                Iterator<Seller> sit = sellerList.iterator();
                while(sit.hasNext()){
                    Seller seller = sit.next();
                    sellerRepository.save(seller);
                }
                customerRepository.save(customer);
                Order order = new Order();
                order.setItems(itemIDs);
                order.setCustomerEmail(customer.getEmail());
                order.setOrder_id(order.getId().toString());
                orderRepository.save(order);
                OrderHistory new_orderhistory = new OrderHistory();
                new_orderhistory.setCustomer_email(customer.getEmail());
                new_orderhistory.setSale_id(order.getOrder_id());
                orderHistoryRepository.save(new_orderhistory);
                it = items.iterator();
                while(it.hasNext()){
                    cartItemRepository.delete(it.next()); //empty the cart.
                }
                return new Response("Order has given",true);
            }
            else {
                return new Response("Insufficent balance",false);
            }

        }
        catch (Exception e){
            return new Response("Cannot update customer info!",false);
        }
    }

    boolean isCardValid(String card){
        if (card == null)
            return false;
        char checkDigit = card.charAt(card.length() - 1);
        if (card == null)
            return false;
        String digit;
        /* convert to array of int for simplicity */
        int[] digits = new int[card.length()];
        for (int i = 0; i < card.length(); i++) {
            digits[i] = Character.getNumericValue(card.charAt(i));
        }

        /* double every other starting from right - jumping from 2 in 2 */
        for (int i = digits.length - 1; i >= 0; i -= 2)	{
            digits[i] += digits[i];

            /* taking the sum of digits grater than 10 - simple trick by substract 9 */
            if (digits[i] >= 10) {
                digits[i] = digits[i] - 9;
            }
        }
        int sum = 0;
        for (int i = 0; i < digits.length; i++) {
            sum += digits[i];
        }
        /* multiply by 9 step */
        sum = sum * 9;

        /* convert to string to be easier to take the last digit */
        digit = sum + "";
        digit =  digit.substring(digit.length() - 1);
        return checkDigit == digit.charAt(0);
    }


    @RequestMapping(method=POST,path ="/addBalance")
    public @ResponseBody
    Response checkout(@CookieValue(name = "JSESSIONID")String sessionID,@RequestParam String cardNumber,@RequestParam String cvc, @RequestParam String expireDate,@RequestParam Double balance) {

        try {
            Customer customer = customerRepository.findBySessionID(sessionID).iterator().next();
            if(cardNumber.length()!=16){
                return new Response("Invalid payment info", false);
            }
            if(cvc.length()!=3){
                return new Response("Invalid payment info", false);
            }
            if(!isCardValid(cardNumber)){
                return new Response("Invalid payment info", false);
            }
            Date date = new Date();
            String expireString = "00-"+expireDate;
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            Date expireDateTime = dateFormat.parse(expireString, new ParsePosition(0));
            if(expireDateTime.before(date)){
                return new Response("Card expired", false);
            }
            customer.setBalance(customer.getBalance()+balance);
            customerRepository.save(customer);
            return new Response("Balance successfully added",true);
        } catch (Exception e) {
            return new Response("Cannot update customer info!", false);
        }
    }
    @RequestMapping(method = POST, path = "/getBalance")
    public Double deleteItem(@CookieValue(name = "JSESSIONID") String sessionID
    ) {
        try {
            Customer customer = customerRepository.findBySessionID(sessionID).iterator().next();
            return customer.getBalance();
        }
        catch (Exception e) {
            return 0.0;
        }
    }
}
