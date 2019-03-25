package com.babob.sporcantam.Seller;

import com.babob.sporcantam.Utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping(path="/seller")
public class SellerController {
    @Autowired
    private SellerRepository sellerRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping(method = POST, path = "/add")  // Map ONLY GET Requests
    public @ResponseBody
    Response addNewUser(@CookieValue(name = "JSESSIONID") String sessionID, @RequestParam String email
            , @RequestParam String password, @RequestParam String first_name, @RequestParam String last_name, @RequestParam String userType,
                        @RequestParam(value = "company_name") String company_name) {
        Seller seller = new Seller();
        seller.setEmail(email);
        seller.setFirst_name(first_name);
        seller.setLast_name(last_name);
        seller.setPassword(passwordEncoder.encode(password));
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
            Seller seller = sellerRepository.findByEmail(email).iterator().next(); //get first (and oly) customer
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

    @RequestMapping(method=POST,path ="/update")
    public @ResponseBody
    Response updateSellerInfo(@CookieValue(name = "JSESSIONID") String sessionID
            ,@RequestParam(value="password", defaultValue=" ") String password
            ,@RequestParam(value="first_name", defaultValue=" ") String first_name
            ,@RequestParam(value="last_name", defaultValue=" ") String last_name
            ,@RequestParam(value="address", defaultValue=" ") String IBAN
            ,@RequestParam(value="address", defaultValue=" ") String phone_number
            ,@RequestParam(value="address", defaultValue=" ") String company_address) {
        Seller seller = sellerRepository.findBySessionID(sessionID).iterator().next();
        try{
            if(password!= " ")
                seller.setPassword(passwordEncoder.encode(password));
            if(first_name!= " ")
                seller.setFirst_name(first_name);
            if(last_name!= " ")
                seller.setLast_name(last_name);
            if(IBAN!= " ")
                seller.setIBAN(IBAN);
            if(company_address!= " ")
                seller.setCompany_address(company_address);
            if(phone_number!= " ")
                seller.setPhone_number(phone_number);

            return new Response("Seller information updated succesfully.",true);
        }
        catch (Exception e){
            return new Response("Cannot update seller info!",false);
        }
    }
}