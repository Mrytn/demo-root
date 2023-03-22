package com.demo.springboot;

import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.springboot.entity.Person;

import java.util.List;

/**
 * @author PeiDong Yan
 * @date 2023/03/03 16:37
 */
public interface PersonService extends IService<Person> {

    void create(Person Person);

    void update(Person Person);

    Person getInfo(Integer id);

    List<Person> getList();

}
