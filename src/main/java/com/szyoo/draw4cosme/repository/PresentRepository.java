package com.szyoo.draw4cosme.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.szyoo.draw4cosme.entity.Present;

public interface PresentRepository extends JpaRepository<Present, Long> {

    long countByIsDrawn(boolean isDrawn);

    long count();

}
