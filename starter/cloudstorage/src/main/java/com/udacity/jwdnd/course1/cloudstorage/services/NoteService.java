package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.data.Note;
import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import org.springframework.stereotype.Service;

@Service
public class NoteService {
    private NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public Note[] getUserNotes(Integer userid) {
        return noteMapper.getUserNotes(userid);
    }

    public Note getNote(Integer noteid) {
        return noteMapper.getNote(noteid);
    }

    public int insertNote(Note note) {
        return noteMapper.insertNote(note);
    }

    public int deleteNote(Integer noteid) {
        return noteMapper.deleteNote(noteid);
    }

    public int updateNote(Note note){
        return noteMapper.updateNote(note);
    }

}
