package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.data.Credential;
import org.apache.ibatis.annotations.*;

@Mapper
public interface CredentialMapper {

    @Select("select * from Credentials where credentialid=#{credentialid}")
    public Credential getCredential(Integer credentialid);

    @Select("select * from Credentials where url=#{url}")
    public Credential getCredentialByUrl(String url);

    @Select("select * from Credentials where userid=#{userid}")
    public Credential[] getUserCredentials(Integer userid);

    @Insert("insert into Credentials (url, username, key, password, userid) "+
            "values (#{url}, #{username}, #{key}, #{password}, #{userid}) ")
    @Options(useGeneratedKeys = true, keyProperty = "credentialid")
    public int insertCredential(Credential credential);

    @Update("update Credentials set url=#{url}, username=#{username}, password=#{password}, userid=#{userid}, key=#{key} " +
            "where credentialid=#{credentialid}")
    public int updateCredential(Credential credential);

    @Delete("delete from Credentials where credentialid=#{credentialid}")
    public int deleteCredential(Integer credentialid);
}
