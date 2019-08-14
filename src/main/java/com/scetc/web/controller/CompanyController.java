package com.scetc.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scetc.web.common.JsonUtils;
import com.scetc.web.entity.Company;
import com.scetc.web.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.rmi.server.ExportException;

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
    public String  getAll()throws IOException {
          String company="{\"id\":\"1\",\"code\":\"2019-08-13\"}\n";
          Company company1=new Company();
          company1 = JsonUtils.jsonToEntity(company, Company.class);
          System.out.println(company1);
          System.out.println("----------------------------------------------");
          company1.setCode("2019-08-13");
          company1.setId("1");
          String code="";
          code= JsonUtils.toJson(company1);
          System.out.println(code);
            return companyService.getAll()+code;
    }

}
