package com.example.springbootmd.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springbootmd.PersonService;
import com.example.springbootmd.entity.Person;
import com.example.springbootmd.mapper.PersonMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author PeiDong Yan
 * @date 2023/03/03 16:38
 */
@Service
public class PersonServiceImpl extends ServiceImpl<PersonMapper, Person> implements PersonService {

    @Override
    public void create(Person person) {
        save(person);
    }

    @Override
    public void update(Person person) {
        saveOrUpdate(person);
    }

    @Override
    public Person getInfo(Integer id) {
        Person person = baseMapper.selectById(id);
        return person;
    }

    @Override
    public List<Person> getList() {
        List<Person> list = lambdaQuery().list();
        return list;
    }
}
