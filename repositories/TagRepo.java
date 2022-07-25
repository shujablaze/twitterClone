package com.shuja.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shuja.blog.entities.Tag;

public interface TagRepo extends JpaRepository<Tag,Long>{

}
