package com.babob.sporcantam.User;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller    // This means that this class is a Controller
@RequestMapping(path="/user",method = POST)
public class UserController {
    @RequestMapping(method=POST,path ="/add")
    public
    String addNewUser (@RequestParam String email
            ,@RequestParam String password, @RequestParam String first_name,@RequestParam String last_name,@RequestParam String userType,
            @RequestParam(value="company_name", defaultValue=" ") String company_name) {
        if(userType.equals("Customer")){
            System.out.println("I AM here");
            return "forward:/customer/add";
        }
        else {
            return "forward:/seller/add";
        }
    }
}
