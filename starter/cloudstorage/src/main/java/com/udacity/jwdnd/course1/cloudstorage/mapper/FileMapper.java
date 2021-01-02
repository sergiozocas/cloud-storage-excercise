package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.data.File;
import org.apache.ibatis.annotations.*;

@Mapper
public interface FileMapper {
    @Select("select fileid, filename, userid  from files where userid=#{userid}")
    File[] getUserFiles(Integer userid);

    @Select("select * from files where fileid=#{fileid}")
    File getFile(Integer fileid);

    @Select("select * from files where filename=#{filename}")
    File getFileByName(String filename);

    @Insert("insert into files (filename, contenttype, filesize, userid, filedata) " +
            "values (#{filename}, #{contenttype}, #{filesize}, #{userid}, #{filedata})")
    @Options(useGeneratedKeys = true, keyProperty = "fileid")
    int insertFile(File file);

    @Delete("delete from files where fileid=#{fileid}")
    int deleteFile(Integer fileid);
}
