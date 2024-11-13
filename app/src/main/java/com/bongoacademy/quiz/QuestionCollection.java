package com.bongoacademy.quiz;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.bongoacademy.quiz.R;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class QuestionCollection extends AppCompatActivity {
    RadioGroup radioGroup;
    TextView lblQuestion;
    RadioButton optionA;
    RadioButton optionB;
    RadioButton optionC;
    RadioButton optionD;
    Button confirm;
    String rightAnswer;
    String Answer;
    public static List<QuestionModule> question_list;
    int score;
    public static String SUBJECT_NAME = "";
    public static ArrayList <ArrayList<QuestionModule>> questionBank = new ArrayList<>();
    public static ArrayList <HashMap<String, String>> subjectList = new ArrayList<>();
    LinearLayout rootLay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        rootLay = findViewById(R.id.rootLay);
        confirm = findViewById(R.id.confirm);
        lblQuestion = findViewById(R.id.lblPergunta);
        optionA = findViewById(R.id.opcaoA);
        optionB = findViewById(R.id.opcaoB);
        optionC = findViewById(R.id.opcaoC);
        optionD = findViewById(R.id.opcaoD);
        score = 0;
        radioGroup = findViewById(R.id.radioGroup);
        loadQuestion();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        loadQuestion();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (rootLay != null) {
            rootLay.startAnimation(AnimationUtils.loadAnimation(QuestionCollection.this, R.anim.middle_to_top));
        }
    }

    @Override
    public void onBackPressed() {
        // Do nothing to prevent back button action during quiz
    }

    private void loadQuestion() {
        if (question_list.size() > 0) {
            QuestionModule q = question_list.remove(0);
            lblQuestion.setText(q.getQuestion());
            List<String> answers = q.getAnswers();
            optionA.setText(answers.get(0));
            optionB.setText(answers.get(1));
            optionC.setText(answers.get(2));
            optionD.setText(answers.get(3));
            rightAnswer = q.getRightAnswer();
        } else {
            Intent intent = new Intent(this, ScoreActivity.class);
            intent.putExtra("score", score);
            startActivity(intent);
            finish();
        }
    }

    public void loadAnswer(View view) {
        int op = radioGroup.getCheckedRadioButtonId();

        // Using if-else instead of switch to avoid runtime constant expression error
        if (op == R.id.opcaoA) {
            Answer = "A";
        } else if (op == R.id.opcaoB) {
            Answer = "B";
        } else if (op == R.id.opcaoC) {
            Answer = "C";
        } else if (op == R.id.opcaoD) {
            Answer = "D";
        } else {
            return; // Do nothing if no option is selected
        }

        // Clear selection after answer is recorded
        radioGroup.clearCheck();

        // Navigate to the appropriate screen (Right or Wrong)
        startActivity(isRightOrWrong(Answer));
    }


    private Intent isRightOrWrong(String Answer) {
        Intent screen;
        if (Answer.equals(rightAnswer)) {
            this.score += 1;
            screen = new Intent(this, RightActivity.class);
        } else {
            screen = new Intent(this, WrongActivity.class);
        }
        return screen;
    }

    // =================================================================
    // Example of creating a set of questions
    public static ArrayList<QuestionModule> questions;

    public static void createQuestionBank() {
        QuestionCollection.subjectList = new ArrayList<>();
        QuestionCollection.questionBank = new ArrayList<>();

        // Example of Subject 1: General Knowledge
        questions = new ArrayList<QuestionModule>() {{
            add(new QuestionModule("এই অ্যাপটি কার স্মরণে তৈরি?", "A", "অফসারুল ইসলাম আদর", "তৌফিক আলম", "মাসুমুল হক", "জুবায়ের হোসেন"));
            add(new QuestionModule("বাংলাদেশের রাজধানীর নাম কি?", "A", "ঢাকা", "খুলনা", "বরিশাল", "ফেনী"));
            add(new QuestionModule("বাংলাদেশের মুদ্রার নাম কি?", "A", "টাকা", "ডলার", "রুপি", "বাথ"));
            add(new QuestionModule("বিজয় দিবস কত তারিখে পালন করা হয়?", "D", "২৬ মার্চ", "২১ ফেব্রুয়ারি", "১৪ ডিসেম্বর", "১৬ ডিসেম্বর"));
            add(new QuestionModule("স্মৃতিসৌধের কয়টি স্তম্ভ?", "B", "৫ টি", "৭ টি", "৯ টি", "১০ টি"));
        }};
        QuestionModule.createQuestionsForSubject("সাধারন জ্ঞান", R.drawable.category_icon1, questions);

        // Subject 2: Bangladesh
        questions = new ArrayList<QuestionModule>() {{
            add(new QuestionModule("বাংলাদেশ ও ভারতের মধ্যে চলাচলকারী ট্রেনের নাম কী?", "D", "মৈত্রী এক্সপ্রেস", "বন্ধন এক্সপ্রেস", "মিতালী এক্সপ্রেস", "ওপরের সবগুলাে"));
            add(new QuestionModule("বঙ্গবন্ধু শেখ মুজিব ভ্রাম্যমাণ রেল জাদুঘর কবে উদ্বোধন করা হয়?", "A", "২৭ এপ্রিল ২০২২", "৩০ এপ্রিল ২০২২", "২ মে ২০২২", "৫ মে ২০২২"));
        }};
        QuestionModule.createQuestionsForSubject("বাংলাদেশ", R.drawable.category_icon2, questions);

        // Add other subjects similarly...

    }
}
