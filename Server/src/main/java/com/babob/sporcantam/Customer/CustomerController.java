package com.babob.sporcantam.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.babob.sporcantam.Utils.Response;
import java.util.Collection;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping(path="/customer") // This means URL's start with /demo (after Application path)
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping(method=POST,path="/add") // Map ONLY GET Requests
    public @ResponseBody
    Response addCustomer (@CookieValue(name = "JSESSIONID") String sessionID, @RequestParam String email
            ,@RequestParam String password, @RequestParam String first_name,@RequestParam String last_name,@RequestParam String userType){
        Customer customer = new Customer();
        customer.setEmail(email);
        customer.setFirst_name(first_name);
        customer.setLast_name(last_name);
        customer.setPassword(passwordEncoder.encode(password));
        customer.setSessionID(sessionID);
        try {
            customerRepository.save(customer);
            return new Response("Customer Created",true);
        }
        catch (Exception e){
            return new Response(e.getMessage(),false);
        }
    }

    @RequestMapping(method=POST,path="/email") // Map ONLY GET Requests
    public @ResponseBody
    Response getCustomer(@CookieValue(name = "JSESSIONID") String sessionID){
        Collection<Customer> customerCollection = customerRepository.findBySessionID(sessionID);
        Customer customer = customerCollection.iterator().next();
        if (customer.getEmail()==null) {
            return new Response("Login first",false);
        }
        return new Response(customer.getEmail(), true);
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
    Response updateSellerInfo(@CookieValue(name = "JSESSIONID") String sessionID
            ,@RequestParam(value="password", defaultValue=" ") String password
            ,@RequestParam(value="first_name", defaultValue=" ") String first_name
            ,@RequestParam(value="last_name", defaultValue=" ") String last_name
            ,@RequestParam(value="address", defaultValue=" ") String address) {
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
            return new Response("Customer information updated succesfully.",true);

        }
        catch (Exception e){
            return new Response("Cannot update customer info!",false);
        }
    }
}
