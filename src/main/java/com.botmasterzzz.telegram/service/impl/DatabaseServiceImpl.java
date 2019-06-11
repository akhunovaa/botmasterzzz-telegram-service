package com.botmasterzzz.telegram.service.impl;

import com.botmasterzzz.telegram.service.DatabaseService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;


@Profile("dev")
@Service
public class DatabaseServiceImpl implements DatabaseService {


}
