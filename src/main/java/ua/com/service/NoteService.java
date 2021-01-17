package ua.com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.Utils.DateUtil;
import ua.com.domain.Note;
import ua.com.domain.User;
import ua.com.repos.NoteRepo;
import ua.com.repos.UserRepo;

import java.security.Principal;
import java.util.Date;
import java.util.List;

import static java.util.Objects.nonNull;

@Service
public class NoteService {

    @Autowired
    private NoteRepo noteRepo;
    @Autowired
    private UserRepo userRepo;

    public void addTodaysNote(final Note note, final Principal principal) {
        Note todaysNoteForCurrentUser = findTodaysNoteForCurrentUser(principal);
        if(nonNull(todaysNoteForCurrentUser))
        {
            todaysNoteForCurrentUser.setText(note.getText());
            noteRepo.save(todaysNoteForCurrentUser);
        }
        else {
            note.setDate(new Date());
            note.setAuthor(userRepo.findByUsername(principal.getName()));
            noteRepo.save(note);
        }

    }
    
    public Note findTodaysNoteForCurrentUser(final Principal principal)
    {
        final User user = userRepo.findByUsername(principal.getName());
        try
        {
            List<Note> notesFroCurrentUser = noteRepo.findNotesFroCurrentUser(user.getId());
            return notesFroCurrentUser.stream().filter(this::filterForCurrentDate).findFirst().orElse(null);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
    
    private boolean filterForCurrentDate(final Note note)
    {
        return DateUtil.compareDateToCurrentDateToTheDay(note.getDate());
    }
}

