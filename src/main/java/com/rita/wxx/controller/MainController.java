package com.rita.wxx.controller;

import com.rita.wxx.po.tables.pojos.PersonPo;
import com.rita.wxx.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lhbac
 * @create 2023/6/6 20:19
 */
@RestController
@RequestMapping("/main")
public class MainController {

    @Autowired
    private MainService mainService;

    @RequestMapping("/hello")
    public PersonPo hello() {
        return mainService.hello();
    }

    @RequestMapping("/transact")
    public PersonPo transact() {
        return mainService.transact();
    }
}
