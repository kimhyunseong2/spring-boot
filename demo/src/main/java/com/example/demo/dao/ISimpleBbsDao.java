package com.example.demo.dao;

import com.example.demo.dto.SimpleBbsDto;

import java.util.List;

public interface ISimpleBbsDao {
    public List<SimpleBbsDto> listDao();
    public SimpleBbsDto viewDao(String id);
    public int writeDao(String writer, String title, String content);
    public int updateDao(String writer, String title, String content, String id);
    public int deleteDao(String id);
}
