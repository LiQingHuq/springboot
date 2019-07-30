package com.scetc.web.controller;

import com.scetc.web.service.CompanyService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 类功能描述：单位信息controller
 * 修改说明：
 *
 * @author hyc
 * @date 2019/3/28
 * @version v1.0
 * @since jdk1.8
 */
@RestController
@RequestMapping("/get")
public class CompanyController  {
    @Autowired
    private CompanyService companyService;

    @GetMapping("/getAll")
    @ResponseBody
    public String  getAll(){
      return  companyService.getAll();
    }

}
