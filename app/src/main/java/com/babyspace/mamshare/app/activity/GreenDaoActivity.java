package com.babyspace.mamshare.app.activity;

import android.app.ListActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.babyspace.mamshare.R;
import com.babyspace.mamshare.app.service.DBService;
import com.babyspace.mamshare.bean.DefaultResponseEvent;
import com.babyspace.mamshare.bean.GreenNote;
import com.babyspace.mamshare.bean.GreenNoteDao;
import com.babyspace.mamshare.bean.HomeGuidanceEvent;
import com.babyspace.mamshare.bean.HotWordEvent;
import com.babyspace.mamshare.bean.MArea;
import com.babyspace.mamshare.commons.UrlConstants;
import com.babyspace.mamshare.controller.DBController;
import com.babyspace.mamshare.framework.db.DaoMaster;
import com.babyspace.mamshare.framework.db.DaoSession;
import com.google.gson.JsonObject;
import com.michael.core.okhttp.OkHttpExecutor;
import com.michael.library.debug.L;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import de.greenrobot.event.EventBus;

public class GreenDaoActivity extends ListActivity {

    private SQLiteDatabase db;

    private EditText editText;

    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private GreenNoteDao noteDao;
    private GreenNoteDao noteDao2;

    private Cursor cursor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_green_dao);

        EventBus.getDefault().register(this);

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "notes-db", null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        noteDao = daoSession.getNoteDao();

        String textColumn = GreenNoteDao.Properties.Text.columnName;
        String orderBy = textColumn + " COLLATE LOCALIZED ASC";
        cursor = db.query(noteDao.getTablename(), noteDao.getAllColumns(), null, null, null, null, orderBy);
        String[] from = {textColumn, GreenNoteDao.Properties.Comment.columnName};


        noteDao2 = DBController.getGreenNoteDao(this);
        // Select
        for (GreenNote note : noteDao2.loadAll()) {
            L.d("NoteActivityOnCreate", note.toString());
        }

        for (MArea note : DBService.getAllArea("1")) {
            L.d("GreenDaoActivityMArea-all", note.toString());
        }

        int[] to = {android.R.id.text1, android.R.id.text2};

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, cursor, from,
                to);
        setListAdapter(adapter);

        editText = (EditText) findViewById(R.id.editTextNote);
        addUiListeners();

        JsonObject jsonParameter = new JsonObject();
        jsonParameter.addProperty("num", "10");
        jsonParameter.addProperty("start", "0");

        OkHttpExecutor.query(UrlConstants.AddCollection, jsonParameter,DefaultResponseEvent.class, false, this);

        OkHttpExecutor.query(UrlConstants.HotWords, HotWordEvent.class, false, this);

    }

    protected void addUiListeners() {
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    addNote();
                    return true;
                }
                return false;
            }
        });

        final View button = findViewById(R.id.buttonAdd);
        button.setEnabled(false);
        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                boolean enable = s.length() != 0;
                button.setEnabled(enable);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    public void onMyButtonClick(View view) {
        addNote();
    }

    private void addNote() {
        String noteText = editText.getText().toString();
        editText.setText("");

        final DateFormat df = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);
        String comment = "Added on " + df.format(new Date());
        GreenNote note = new GreenNote(null, noteText, comment, new Date());
        noteDao.insert(note);
        noteDao2.insert(note);

        cursor.requery();
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        noteDao.deleteByKey(id);
        noteDao2.deleteByKey(id);
        Log.d("DaoExample", "Deleted note, ID: " + id);
        cursor.requery();
    }

    @Override
    protected void onDestroy() {
        //TODO 销毁注册信息
        if (EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    public void onEventMainThread(DefaultResponseEvent event) {
        L.d(OkHttpExecutor.TAG, "DefaultResponseEvent->" + event.getResultStr());

        String string=event.getData();
        L.d(OkHttpExecutor.TAG, "DefaultResponseEvent-getData " + string);

    }
    public void onEventMainThread(HotWordEvent event) {
        L.d(OkHttpExecutor.TAG, "HotWordEvent->" + event.getResultStr());

        List<String> strings=event.getData();
        L.d(OkHttpExecutor.TAG,"HotWordEvent-getData "+strings.get(2));

    }


}
