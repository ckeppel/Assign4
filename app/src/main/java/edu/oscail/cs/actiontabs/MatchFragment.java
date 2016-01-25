package edu.oscail.cs.actiontabs;

import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MatchFragment extends Fragment {

    private AdapterView.OnItemSelectedListener listener;
    final int iTeamA=1;
    final int iTeamB=2;

    int iPenaltyScore=3;
    int iConversionScore=2;
    int iTryScore=5;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.matchfragment, container, false);

        // Setup the SharedPreference/Area to store team names and scores.
        final SharedPreferences sp = this.getActivity().getPreferences(Context.MODE_PRIVATE);

        // Setup the Team Name and Score fields.
        /* Initialize the Edit Text objects so the Team Names can be read */
        final EditText etxtTeamA = (EditText) view.findViewById(R.id.txtTeamNameA);
        final EditText etxtTeamB = (EditText) view.findViewById(R.id.txtTeamNameB);
        final TextView txtTeamATotal = (TextView) view.findViewById(R.id.txtTeamATotal);
        final TextView txtTeamBTotal = (TextView) view.findViewById(R.id.txtTeamBTotal);

        // Setup the buttons on the Match screen
        Button btnPenaltyA = (Button) view.findViewById(R.id.btnPenaltyA);
        Button btnPenaltyB = (Button) view.findViewById(R.id.btnPenaltyB);
        Button btnConversionA = (Button) view.findViewById(R.id.btnConversionA);
        Button btnConversionB = (Button) view.findViewById(R.id.btnConversionB);
        Button btnTryA = (Button) view.findViewById(R.id.btnTryA);
        Button btnTryB = (Button) view.findViewById(R.id.btnTryB);
        Button btnReset = (Button) view.findViewById(R.id.btnReset);
        Button btnSummary = (Button) view.findViewById(R.id.btnSummary);

        //Load previous saved scores if any
        txtTeamATotal.setText(String.valueOf(sp.getInt("intTotalScore1", 0)));
        txtTeamBTotal.setText(String.valueOf(sp.getInt("intTotalScore2", 0)));

        btnPenaltyA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveScore(sp,  iTeamA, iPenaltyScore);
                txtTeamATotal.setText(String.valueOf(sp.getInt("intTotalScore1", 0)));
            }
        });

        btnPenaltyB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveScore(sp,  iTeamB, iPenaltyScore);
                txtTeamBTotal.setText(String.valueOf(sp.getInt("intTotalScore2", 0)));
            }
        });

        btnConversionA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveScore(sp, iTeamA, iConversionScore);
                txtTeamATotal.setText(String.valueOf(sp.getInt("intTotalScore1", 0)));
            }
        });

        btnConversionB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveScore(sp,  iTeamB, iConversionScore);
                txtTeamBTotal.setText(String.valueOf(sp.getInt("intTotalScore2", 0)));
            }
        });

        btnTryA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveScore(sp,  iTeamA, iTryScore);
                txtTeamATotal.setText(String.valueOf(sp.getInt("intTotalScore1", 0)));
            }
        });

        btnTryB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveScore(sp,  iTeamB, iTryScore);
                txtTeamBTotal.setText(String.valueOf(sp.getInt("intTotalScore2", 0)));
            }
        });

           /* Add a listner on the Team A EditText to chatch the change focus so the Team Name can be Saved */
        etxtTeamA.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                SharedPreferences.Editor editor = sp.edit();
                if (!hasFocus) {
                    /* Call the SaveName procedure to save Team A name to the Shared Preferences */
                    SaveTeamName(sp, iTeamA, etxtTeamA.getText().toString());
                }
            }
        });

        /* Add a listner on the Team B EditText to chatch the change focus so the Team Name can be Saved */
        etxtTeamB.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                SharedPreferences.Editor editor = sp.edit();
                if (!hasFocus) {
                    /* Call the SaveName procedure to save Team A name to the Shared Preferences */
                    SaveTeamName(sp, iTeamB, etxtTeamB.getText().toString());
                }
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sp.edit();
                editor.clear();
                editor.commit();
                txtTeamATotal.setText(String.valueOf(0));
                txtTeamBTotal.setText(String.valueOf(0));
            }
        });

        btnSummary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getActivity(),GetScoreSummary(sp),Toast.LENGTH_SHORT).show();

            }
        });


        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.matchfragment, container, false);
        return view;


    }


    public void SaveScore(SharedPreferences sp, int Team, int ScoreType) {
        int intTempCount=0;
        int intTempTotalScore=0;
        String strKeyName="";

       // SharedPreferences sPref = this.getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        switch(ScoreType)
        {
            case 3:
                strKeyName = "intPenalty" + String.valueOf(Team);

            case 2:
                strKeyName = "intConversion" + String.valueOf(Team);

            case 5:
                strKeyName = "intTry" + String.valueOf(Team);
        }

        intTempCount = sp.getInt(strKeyName, 0);
        intTempTotalScore = sp.getInt("intTotalScore" + Team, 0);
        editor.putInt(strKeyName, intTempCount+1);
        editor.putInt("intTotalScore"+ String.valueOf(Team), intTempTotalScore+ScoreType);
        editor.commit();


    }


    /* This procedure saves the name of the specified team to the shared preferences. */
    private void SaveTeamName(SharedPreferences sp,int Team, String TeamName) {

        String strKeyName;
        //SharedPreferences sPref = this.getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        strKeyName = "strTeamName" + String.valueOf(Team);
        editor.putString(strKeyName, TeamName);
        editor.commit();

    }

    /* This procedure takes all the scores, score counts, team names and puts together a string of the
       score summary to be used in the popup and the email.
     */

    private String GetScoreSummary(SharedPreferences sPref) {
        StringBuilder sb=new StringBuilder(250);

        sb.append("SCORE:")
                .append("\n")
                .append(sPref.getString("strTeamName1", "")).append(":").append(sPref.getInt("intTotalScore1", 0)).append(" \n")
                .append(sPref.getString("strTeamName2", "")).append(":").append(sPref.getInt("intTotalScore2", 0))
                .append("\n*** ").append("MATCH STATISTICS").append(" ***\n")
                .append(sPref.getString("strTeamName1", "")).append(": ")
                .append("PENALTIES").append("- ").append(sPref.getInt("intPenalty1", 0))
                .append("CONVERSION").append("- ").append(sPref.getInt("intConversion1", 0))
                .append("TRIES").append("- ").append(sPref.getInt("intTry1", 0))
                .append("\n")
                .append(sPref.getString("strTeamName2", "")).append(": ")
                .append("PENALTIES").append("- ").append(sPref.getInt("intPenalty2", 0))
                .append("CONVERSION").append("- ").append(sPref.getInt("intConversion2", 0))
                .append("TRIES").append("- ").append(sPref.getInt("intTry2", 0));
        return sb.toString();
    }
}

