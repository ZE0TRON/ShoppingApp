package com.babob.sporcantam.Customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping(path="/customer") // This means URL's start with /demo (after Application path)
public class CustomerController {
    @Autowired
    private CustomerRepository customerRepository;
    @RequestMapping(method=POST,path="/add") // Map ONLY GET Requests
    public @ResponseBody
    String addCustomer (@CookieValue(name = "JSESSIONID") String sessionID, @RequestParam String email
            ,@RequestParam String password, @RequestParam String first_name,@RequestParam String last_name,@RequestParam String userType){
        Customer customer = new Customer();
        customer.setEmail(email);
        customer.setFirst_name(first_name);
        customer.setLast_name(last_name);
        customer.setPassword(password);
        customer.setSessionID(sessionID);
        try {
            customerRepository.save(customer);
            return "success";
        }
        catch (Exception e){

            return "false"+e.getMessage();
        }
    }
    @RequestMapping(method=POST,path="/email") // Map ONLY GET Requests
    public @ResponseBody
    String addCustomer(@CookieValue(name = "JSESSIONID") String sessionID){
        Example<Customer> example = Example.of(new Customer());
       Collection<Customer> customers = customerRepository.findBySessionID(sessionID);
       Customer realCustomer = new Customer();
       for(Customer customer : customers){
           realCustomer = customer;
       }
       if (realCustomer.getEmail()==null) {
           return "Login first";
       }
       return realCustomer.getEmail();
    }

}
