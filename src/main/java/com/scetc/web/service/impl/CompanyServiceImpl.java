package com.scetc.web.service.impl;

import com.scetc.web.mapper.CompanyMapper;
import com.scetc.web.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyServiceImpl implements CompanyService {
    @Autowired
    private CompanyMapper companyMapper;
    @Override
    public String getAll() {
        return companyMapper.getAll();
    }
}
