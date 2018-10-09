package me.manishmahalwal.android.ass3;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;


public class Fragment1 extends Fragment {
    private static final String TAG = "Fragment1";

    ArrayList<Questions> questions;
    SQLiteOpenHelper mDatabase;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.fragment1_layout, container, false);

        mDatabase = new DatabaseManager(getContext());

        questions = new ArrayList<Questions>();
        initDatabase();
        loadQuestions(questions);
        initViews(view);

        return view;
    }

    public void loadQuestions(ArrayList<Questions> questions)
    {
        Cursor cursor =((DatabaseManager) mDatabase).getAllQuestions();
        Log.e("TAG", cursor.getCount()+" Count");
        if (cursor.moveToFirst()) {
            do {
                questions.add(new Questions(
                        cursor.getInt(0) + "",
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3)));
            } while (cursor.moveToNext());
        }
        mDatabase.close();
    }

    private void initDatabase()
    {

        Log.e("DB", "initDatabase");
        if(doesDatabaseExist(getActivity(), "QuestionsDatabase"))
            return;

        int ret = ((DatabaseManager) mDatabase).addQuestion("Question", "Whaling / Whaling attack is a kind of phishing attacks that target senior executives and other high profile to access valuable information.", "");
        Toast.makeText(getActivity(), "Employee Added "+ret, Toast.LENGTH_SHORT).show();
//

       ((DatabaseManager) mDatabase).addQuestion("Question", "Whaling / Whaling attack is a kind of phishing attacks that target senior executives and other high profile to access valuable information.", "");
       ((DatabaseManager) mDatabase).addQuestion("Question", "Whaling / Whaling attack is a kind of phishing attacks that target senior executives and other high profile to access valuable information.", "");
       ((DatabaseManager) mDatabase).addQuestion("Question", "Whaling / Whaling attack is a kind of phishing attacks that target senior executives and other high profile to access valuable information.", "");
       ((DatabaseManager) mDatabase).addQuestion("Question", "Whaling / Whaling attack is a kind of phishing attacks that target senior executives and other high profile to access valuable information.", "");
       ((DatabaseManager) mDatabase).addQuestion("Question", "Whaling / Whaling attack is a kind of phishing attacks that target senior executives and other high profile to access valuable information.", "");
       ((DatabaseManager) mDatabase).addQuestion("Question", "Whaling / Whaling attack is a kind of phishing attacks that target senior executives and other high profile to access valuable information.", "");
       ((DatabaseManager) mDatabase).addQuestion("Question", "Whaling / Whaling attack is a kind of phishing attacks that target senior executives and other high profile to access valuable information.", "");
       ((DatabaseManager) mDatabase).addQuestion("Question", "Whaling / Whaling attack is a kind of phishing attacks that target senior executives and other high profile to access valuable information.", "");
       ((DatabaseManager) mDatabase).addQuestion("Question", "Whaling / Whaling attack is a kind of phishing attacks that target senior executives and other high profile to access valuable information.", "");
       ((DatabaseManager) mDatabase).addQuestion("Question", "Whaling / Whaling attack is a kind of phishing attacks that target senior executives and other high profile to access valuable information.", "");
       ((DatabaseManager) mDatabase).addQuestion("Question", "Whaling / Whaling attack is a kind of phishing attacks that target senior executives and other high profile to access valuable information.", "");
       ((DatabaseManager) mDatabase).addQuestion("Question", "Whaling / Whaling attack is a kind of phishing attacks that target senior executives and other high profile to access valuable information.", "");
       ((DatabaseManager) mDatabase).addQuestion("Question", "Whaling / Whaling attack is a kind of phishing attacks that target senior executives and other high profile to access valuable information.", "");

       mDatabase.close();

    }

    private static boolean doesDatabaseExist(Context context, String dbName) {
        File dbFile = context.getDatabasePath(dbName);
        return dbFile.exists();
    }

    private void initViews(View view){
        RecyclerView recyclerView = view.findViewById(R.id.card_recycler_view);
//        recyclerView.setHasFixedSize(true);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        questions = new ArrayList<Questions>();

//        loadQuestions();


        RecyclerView.Adapter adapter = new DataAdapter(questions);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            GestureDetector gestureDetector = new GestureDetector(getActivity().getApplicationContext(), new GestureDetector.SimpleOnGestureListener() {

                @Override public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

            });
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

                View child = rv.findChildViewUnder(e.getX(), e.getY());
                if(child != null && gestureDetector.onTouchEvent(e)) {
                    int position = rv.getChildAdapterPosition(child);
                    Toast.makeText(getActivity(), questions.get(position).id, Toast.LENGTH_SHORT).show();


                    //Pass data to fragment
                    Bundle bundle = new Bundle();
                    bundle.putString("message", questions.get(position).question);
                    bundle.putString("qID", questions.get(position).id);
                    Log.e("FUCK", questions.get(position).id+" qid from frag 1");

                    //set Fragmentclass Arguments
                    Fragment2 fragobj = new Fragment2();
                    fragobj.setArguments(bundle);


                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.containter, fragobj, "NewFragmentTag");
                    ft.addToBackStack(null);
                    ft.commit();
                }

                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
    }
}
