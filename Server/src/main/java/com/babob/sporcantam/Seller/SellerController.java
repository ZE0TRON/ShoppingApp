package com.babob.sporcantam.Seller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping(path="/seller") // This means URL's start with /demo (after Application path)
public class SellerController {
    @Autowired
    private SellerRepository sellerRepository;

    @RequestMapping(method=POST,path="/add")  // Map ONLY GET Requests
    public @ResponseBody
    String addNewUser (@RequestParam String email
            ,@RequestParam String password, @RequestParam String first_name,@RequestParam String last_name,@RequestParam String userType,
                       @RequestParam(value="company_name", defaultValue=" ") String company_name) {
        Seller seller = new Seller();
        seller.setEmail(email);
        seller.setFirst_name(first_name);
        seller.setLast_name(last_name);
        seller.setPassword(password);
        seller.setCompany_name(company_name);
        try {
            sellerRepository.save(seller);
            return "success";
        }
        catch (Exception e){
            return "fail";
        }
    }
}