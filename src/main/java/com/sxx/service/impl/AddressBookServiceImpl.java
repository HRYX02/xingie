package com.sxx.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sxx.dao.AddressBookDao;
import com.sxx.entity.AddressBook;
import com.sxx.service.AddressBookService;
import org.springframework.stereotype.Service;

/**
 * @author SHIXINXI
 * @description
 * @create 2023-05-12-9:27
 */
@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookDao, AddressBook> implements AddressBookService {
}