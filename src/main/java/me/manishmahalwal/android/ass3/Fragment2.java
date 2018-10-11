package me.manishmahalwal.android.ass3;

import android.content.Context;
import com.opencsv.CSVWriter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;


public class Fragment2 extends Fragment {
    private static final String TAG = "Fragment1";
    File file=null;

    Context mCtx;
    SQLiteOpenHelper mDatabase;

    String currQuestionID;

    Questions currQuestion;

    ArrayList<Questions> questions;

    private Button btnNavFrag1;
    private CheckBox checkBoxTrue, checkBoxFalse;
    private TextView textView;
    private Button submitQuiz;
    ProgressBar simpleProgressBar2;

//    int progress = 0;
    int progress2 = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.fragment2_layout, container, false);


        questions = new ArrayList<Questions>();
        mDatabase = new DatabaseManager(getActivity());

//        simpleProgressBar = (ProgressBar) view.findViewById(R.id.simpleProgressBar); // initiate the progress bar
//        simpleProgressBar.setMax(100); // 100 maximum value for the progress bar

        simpleProgressBar2 = (ProgressBar) view.findViewById(R.id.simpleProgressBar2);
        simpleProgressBar2.setMax(100);

        loadEmployeesFromDatabaseAgain();

        currQuestionID = getArguments().getString("qID");


        Log.e("FUCK", "currQuestionID "+ currQuestionID);

        btnNavFrag1 = view.findViewById(R.id.btnNavFrag1);
        checkBoxTrue = view.findViewById(R.id.checkBoxTrue);
        checkBoxFalse = view.findViewById(R.id.checkBoxFalse);
        textView = view.findViewById(R.id.textView);
        submitQuiz = view.findViewById(R.id.submitQuiz);


        for(Questions q: questions)
        {
            if(q.id.equals(currQuestionID))
            {
                if(q.answer.equals(""))
                    break;
                if(q.answer.equals("true"))
                    checkBoxTrue.toggle();
                else if(q.answer.equals("false"))
                    checkBoxFalse.toggle();
                else
                {
                    if(checkBoxFalse.isChecked())
                        checkBoxFalse.toggle();
                    if(checkBoxTrue.isChecked())
                        checkBoxTrue.toggle();

                }
            }
        }




        String questionText = getArguments().getString("message");
        if(questionText!=null)
            textView.setText(questionText);

        checkBoxTrue.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(checkBoxFalse.isChecked())
                    checkBoxFalse.toggle();
            }
        });

        checkBoxFalse.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(checkBoxTrue.isChecked())
                    checkBoxTrue.toggle();
            }
        });

        btnNavFrag1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(getActivity(), "Going to Fragment 1", Toast.LENGTH_SHORT).show();

                String checkedAns;
                if(checkBoxTrue.isChecked())
                    checkedAns = "true";
                else if (checkBoxFalse.isChecked())
                    checkedAns = "false";
                else checkedAns = "";

                if (((DatabaseManager) mDatabase).updateQuestion(Integer.parseInt(currQuestionID), checkedAns)) {
                    Toast.makeText(getActivity(), "Ans updated", Toast.LENGTH_SHORT).show();
                    loadEmployeesFromDatabaseAgain();
                }



                Bundle bundle = new Bundle();
                bundle.putString("backPressed", "yes");
//                bundle.putString("currQuestionID", questions.get(position).id);

                //set Fragmentclass Arguments
                Fragment1 fragobj = new Fragment1();
                fragobj.setArguments(bundle);


                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.containter, fragobj, "NewFragmentTag");
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        submitQuiz.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
//                simpleProgressBar.setVisibility(View.VISIBLE);
                Toast.makeText(getActivity(), "Generating CSV", Toast.LENGTH_SHORT).show();
/*
* Reference:
* https://stackoverflow.com/questions/25398200/uploading-file-in-php-server-from-android-device
* */
                class UploadFileAsync extends AsyncTask<String, Void, String> {

                    @Override
                    protected String doInBackground(String... params) {

                        try {
                            String sourceFileUri = file.getAbsolutePath();
                            Log.e("UPLOAD",sourceFileUri);

                            HttpURLConnection conn = null;
                            DataOutputStream dos = null;
                            String lineEnd = "\r\n";
                            String twoHyphens = "--";
                            String boundary = "*****";
                            int bytesRead, bytesAvailable, bufferSize;
                            byte[] buffer;
                            int maxBufferSize = 1 * 1024 * 1024;
                            File sourceFile = new File(sourceFileUri);

                            if (sourceFile.isFile()) {

                                try {
                                    String upLoadServerUri = "http://192.168.43.2/abc.php";

                                    // open a URL connection to the Servlet
                                    Log.e("UPLOAD", "idhar bhi aaya maakichu");
                                    FileInputStream fileInputStream = new FileInputStream(
                                            sourceFile);
                                    URL url = new URL(upLoadServerUri);

                                    // Open a HTTP connection to the URL
                                    conn = (HttpURLConnection) url.openConnection();
                                    conn.setDoInput(true); // Allow Inputs
                                    conn.setDoOutput(true); // Allow Outputs
                                    conn.setUseCaches(false); // Don't use a Cached Copy
                                    conn.setRequestMethod("POST");
                                    conn.setRequestProperty("Connection", "Keep-Alive");
                                    conn.setRequestProperty("ENCTYPE",
                                            "multipart/form-data");
                                    conn.setRequestProperty("Content-Type",
                                            "multipart/form-data;boundary=" + boundary);
                                    conn.setRequestProperty("bill", sourceFileUri);

                                    dos = new DataOutputStream(conn.getOutputStream());

                                    dos.writeBytes(twoHyphens + boundary + lineEnd);
                                    dos.writeBytes("Content-Disposition: form-data; name=\"filename\";filename=\""
                                            + sourceFileUri + "\"" + lineEnd);

                                    dos.writeBytes(lineEnd);

                                    // create a buffer of maximum size
                                    bytesAvailable = fileInputStream.available();

                                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                                    buffer = new byte[bufferSize];

                                    Log.e("UPLOAD", "yahan!!!");
                                    // read file and write it into form...
                                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                                    while (bytesRead > 0) {

                                        dos.write(buffer, 0, bufferSize);
                                        bytesAvailable = fileInputStream.available();
                                        bufferSize = Math
                                                .min(bytesAvailable, maxBufferSize);
                                        bytesRead = fileInputStream.read(buffer, 0,
                                                bufferSize);
                                        Log.e("UPLOAD", "loop mein fasa pada hai");
                                    }

                                    // send multipart form data necesssary after file
                                    // data...
                                    dos.writeBytes(lineEnd);
                                    dos.writeBytes(twoHyphens + boundary + twoHyphens
                                            + lineEnd);

                                    // Responses from the server (code and message)
                                    int serverResponseCode = conn.getResponseCode();
                                    String serverResponseMessage = conn.getResponseMessage();

                                    Log.e("UPLOAD", "server response " + serverResponseMessage +" "+Integer.toString(serverResponseCode));
                                    if (true) {
                                        Toast.makeText(getActivity(), "File Upload Complete.", Toast.LENGTH_SHORT).show();
                                        Log.e("UPLOAD", "upload done in serverresponsecode");
                                    }
                                    else {
                                        Toast.makeText(getActivity(), "File Upload  not Complete.", Toast.LENGTH_SHORT).show();
                                        Log.e("UPLOAD", "upload not done error");
                                    }



                                    // close the streams //
                                    fileInputStream.close();
                                    dos.flush();
                                    dos.close();

                                    Log.e("UPLOAD", "andar to khatam");

                                } catch (Exception e) {

                                    // dialog.dismiss();
                                    e.printStackTrace();

                                }
                                // dialog.dismiss();

                            } // End else block


                        } catch (Exception ex) {
                            // dialog.dismiss();

                            ex.printStackTrace();
                        }
                        return "Executed";
                    }

                    @Override
                    protected void onPostExecute(String result) {


                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                // yourMethod();
                            }
                        }, 1000000);   //5 seconds

//                        FragmentTransaction ft = getFragmentManager().beginTransaction();
//                        ft.replace(R.id.containter, new Fragment1(), "NewFragmentTag");
//
                        getActivity().deleteDatabase("QuestionsDatabase");

//                        FragmentManager fm = getActivity().getSupportFragmentManager();
//                        for(int i = 0; i < fm.getBackStackEntryCount(); ++i) {
//                            fm.popBackStack();
//                        }

//
//                        ft.commit();

                    }

                    @Override
                    protected void onPreExecute() {

                    }

                    @Override
                    protected void onProgressUpdate(Void... values) {
                    }

                }


                exportDB();
//                setProgressValue(progress);


                if(isNetworkConnected(getActivity())){

                    setProgressValue2(progress2);
                    simpleProgressBar2.setVisibility(View.VISIBLE);
                    Log.e("UPLOAD", "call hona shuru hua");
                    new UploadFileAsync().execute("");
                    Toast.makeText(getActivity(), "Uploaded", Toast.LENGTH_SHORT).show();
                    Log.e("UPLOAD", "call hona khatam hua");
                }

                else
                {
                    Toast.makeText(getActivity(), "No internet!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

//https://abhiandroid.com/ui/progressbar

    private void setProgressValue2(final int progress2) {

        // set the progress
        simpleProgressBar2.setProgress(progress2);
        // thread is used to change the progress2 value
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                setProgressValue2(progress2 + 100);
            }
        });
        thread.start();
    }

    public boolean isNetworkConnected(Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    public boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("google.com");
            //You can replace it with your name
            return !ipAddr.equals("");

        } catch (Exception e) {
            return false;
        }
    }

/*
*
*   Reference:
*   https://stackoverflow.com/a/31367824/6883821
*
*   */

    private void exportDB() {

//        Log.e("CSV", "idhar to aaya bro");

        DatabaseManager dbhelper = new DatabaseManager(getActivity());
        File exportDir = new File(getActivity().getFilesDir(), "");

        if (!exportDir.exists())
        {
            exportDir.mkdirs();
        }


         file = new File(exportDir, "upload.csv");

        Log.e("CSV", file.getAbsolutePath() + " path");
        try
        {
            file.createNewFile();
            CSVWriter csvWrite = new CSVWriter(new FileWriter(file));
            SQLiteDatabase db = dbhelper.getReadableDatabase();
            Cursor curCSV = db.rawQuery("SELECT * FROM questions",null);
            csvWrite.writeNext(curCSV.getColumnNames());
            while(curCSV.moveToNext())
            {
                //Which column you want to exprort
                String arrStr[] = {curCSV.getString(0),curCSV.getString(1), curCSV.getString(2), curCSV.getString(3)};

                Log.e("CSV", arrStr[2]);

                csvWrite.writeNext(arrStr);
            }
            csvWrite.close();
            curCSV.close();
        }
        catch(Exception sqlEx)
        {
            Log.e("MainActivity", sqlEx.getMessage(), sqlEx);
        }
        dbhelper.close();
    }

    public void loadEmployeesFromDatabaseAgain(){

        questions.clear();
        Cursor cursor =((DatabaseManager) mDatabase).getAllQuestions();
        Log.e("DB", cursor.getCount()+" Count Fragment 2");
        if (cursor.moveToFirst()) {
            do {
                questions.add(new Questions(
                        cursor.getInt(0) + "",
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3)));
            } while (cursor.moveToNext());
        }

//        notifyDataSetChanged();
        mDatabase.close();
    }
}
