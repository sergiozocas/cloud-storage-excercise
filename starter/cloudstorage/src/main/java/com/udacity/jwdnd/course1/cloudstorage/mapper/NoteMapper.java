package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.data.Note;
import org.apache.ibatis.annotations.*;

@Mapper
public interface NoteMapper {

    @Select("select * from notes where noteid=#{noteid}")
    Note getNote(Integer noteid);

    @Select("select * from notes where userid=#{userid}")
    Note[] getUserNotes(Integer userid);

    @Insert("insert into notes (notetitle, notedescription, userid) " +
            "values (#{notetitle}, #{notedescription}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "noteid")
    int insertNote(Note note);

    @Delete("delete from notes where noteid=#{noteid}")
    int deleteNote(Integer noteid);

    @Update("update notes set notetitle=#{notetitle}, notedescription=#{notedescription}, userid=#{userid} "+
            "where noteid=#{noteid}")
    int updateNote(Note note);
}
