package com.thymeleaf.service;

import com.thymeleaf.repository.CarDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarDetailService {

    @Autowired
    CarDetailRepository detailRepository;

}
