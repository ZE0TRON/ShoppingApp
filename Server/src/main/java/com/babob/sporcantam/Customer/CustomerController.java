package com.babob.sporcantam.Customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping(path="/customer") // This means URL's start with /demo (after Application path)
public class CustomerController {
    @Autowired
    private CustomerRepository customerRepository;
    @RequestMapping(method=POST,path="/add") // Map ONLY GET Requests
    public @ResponseBody
    String addCustomer (@RequestParam String email
            ,@RequestParam String password, @RequestParam String first_name,@RequestParam String last_name,@RequestParam String userType){
        Customer customer = new Customer();
        customer.setEmail(email);
        customer.setFirst_name(first_name);
        customer.setLast_name(last_name);
        customer.setPassword(password);
        try {
            customerRepository.save(customer);
            return "success";
        }
        catch (Exception e){
            return "false";
        }
    }

}
