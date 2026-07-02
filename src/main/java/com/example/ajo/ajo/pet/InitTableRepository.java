 package com.example.ajo.ajo.pet;

import org.springframework.data.jpa.repository.JpaRepository;


public interface InitTableRepository extends JpaRepository<InitTable, Integer>{

    InitTable findByPageno(String pageno);

}

