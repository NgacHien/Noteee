package com.example.noteee.activities;

//NgacNgocHien_11212705

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.noteee.R;
import com.example.noteee.adapters.NotesAdapter;
import com.example.noteee.database.NotesDatabase;
import com.example.noteee.entities.Note;
import com.example.noteee.listeners.NotesListener;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements NotesListener {

    ImageView imageAddNoteMain;
    public static final int REQUEST_CODE_ADD_NOTE = 1;

    public  static  final int REQUEST_CODE_UPDATE_NOTE = 2;

    public static  final int REQUEST_CODE_SHOW_NOTES = 3;

    private RecyclerView notesRecyclerView;
    private List<Note> noteList;
    private NotesAdapter notesAdapter;

    private int noteClickedPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageAddNoteMain = findViewById(R.id.imageAddNoteMain);
        imageAddNoteMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(
                        new Intent(getApplicationContext(), CreateNoteActivity.class), REQUEST_CODE_ADD_NOTE
                );
            }
        });
        notesRecyclerView=findViewById(R.id.notesRecyclerView);
        notesRecyclerView.setLayoutManager(
                new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        );

        noteList = new ArrayList<>();
        notesAdapter = new NotesAdapter(noteList,this );
        notesRecyclerView.setAdapter(notesAdapter);

        getNotes(REQUEST_CODE_SHOW_NOTES,false);

        EditText inputsearch = findViewById(R.id.inputsearch);
        inputsearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                    notesAdapter.cancelTimer();
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (noteList.size() != 0 ){
                        notesAdapter.searchNotes(s.toString());
                }
            }
        });


    }

    @Override
    public void onNoteClicked(Note note, int position) {
    noteClickedPosition = position;
    Intent intent = new Intent(getApplicationContext(), CreateNoteActivity.class);
    intent.putExtra("isViewOrUpdate",true);
    intent.putExtra("note", note);
    startActivityForResult(intent, REQUEST_CODE_UPDATE_NOTE);

    }

    private void getNotes(final int requestcode, final boolean isNoteDeleted) {
        NotesDatabase.getNotesDatabase(getApplicationContext()).noteDao().getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                if (notes != null && !notes.isEmpty()) {
                    // Nếu danh sách notes không rỗng
                    if (noteList.isEmpty()) {
                        // Nếu noteList cũng rỗng, chỉ cần thêm toàn bộ danh sách notes vào noteList
                        noteList.addAll(notes);
                        notesAdapter.notifyDataSetChanged();
                    } else {
                        // Nếu noteList không rỗng, thêm phần tử đầu tiên của danh sách notes vào đầu noteList
                        noteList.add(0, notes.get(0));
                        notesAdapter.notifyItemInserted(0);
                    }
                    // Cuộn đến vị trí đầu tiên trong danh sách
                    notesRecyclerView.smoothScrollToPosition(0);
                } else {
                    // Xử lý khi danh sách notes rỗng
                }

                // Xử lý việc xóa ghi chú nếu có
                if (isNoteDeleted) {
                    // Nếu ghi chú đã được xóa, cập nhật giao diện để xóa ghi chú đó
                    notesAdapter.notifyItemRemoved(noteClickedPosition);
                } else {
                    // Nếu không, thêm phần tử đầu tiên của danh sách notes vào đầu noteList và cập nhật giao diện
                    if (!notes.isEmpty()) {
                        noteList.add(0, notes.get(0));
                        notesAdapter.notifyItemInserted(0);
                    }
                }
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD_NOTE && resultCode==RESULT_OK) {
            getNotes(REQUEST_CODE_SHOW_NOTES, false);

        }else  if (requestCode == REQUEST_CODE_UPDATE_NOTE && resultCode == RESULT_OK){
            if (data != null){
                getNotes(REQUEST_CODE_UPDATE_NOTE, data.getBooleanExtra("isNoteDeleted",false));
            }
        }
    }
}





