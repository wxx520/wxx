package com.rita.wxx.service.impl;

import com.rita.wxx.po.Tables;
import com.rita.wxx.po.tables.pojos.PersonPo;
import com.rita.wxx.po.tables.records.PersonRecord;
import com.rita.wxx.service.MainService;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * @author lhbac
 * @create 2023/6/6 20:23
 */
@Service
@Slf4j
public class MainServiceImpl implements MainService {

    @Autowired
    private DSLContext dslContext;

    @Override
    public PersonPo hello() {
        return dslContext.selectFrom(Tables.PERSON)
                .where(Tables.PERSON.ID.eq(1L))
                .fetchOneInto(PersonPo.class);
    }

    @Transactional
    @Override
    public PersonPo transact() {
        log.info("transact......");
        PersonPo personPo = new PersonPo();
        personPo.setAge(15);
        personPo.setName("rita");
        personPo.setSex(0);
        // personPo.setCreateTime(LocalDateTime.now());
        // personPo.setUpdateTime(LocalDateTime.now());
        dslContext.newRecord(Tables.PERSON, personPo).insert();

        dslContext.update(Tables.PERSON)
                .set(Tables.PERSON.ID, 1L)
                .where(Tables.PERSON.ID.eq(2L))
                .execute();
        return personPo;
    }
}
