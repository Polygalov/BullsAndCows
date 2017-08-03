package ua.com.adr.android.bullscows;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import static java.lang.Integer.parseInt;

public class MainActivity extends AppCompatActivity {

    EditText etGuessNumber;
    Button checkBtn;
    TextView tvAttempts, tvBulls, tvCows;
    int yourGuess;
    int randomMob;
    int attempCounter;
    int bulls = 0, cows = 0;
    final Random random = new Random();
    ArrayAdapter<String> adapter;
    ArrayList<String> userAttempts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etGuessNumber = (EditText) findViewById(R.id.etGuessNumber);
        checkBtn = (Button) findViewById(R.id.buttonCheck);
       // tvAttempts = (TextView) findViewById(R.id.tvAttempts);
        ListView listView = (ListView) findViewById(R.id.listView);
        tvBulls = (TextView) findViewById(R.id.tvBulls);
        tvCows = (TextView) findViewById(R.id.tvCows);
        randomMob = randomFourDigits();
        System.out.println("ЗАГАДАЛ - "+ randomMob);

        // Создаём пустой массив для хранения имен котов
        userAttempts = new ArrayList<>();

        // Создаём адаптер ArrayAdapter, чтобы привязать массив к ListView

        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, userAttempts);
        // Привяжем массив через адаптер к ListView
        listView.setAdapter(adapter);

        checkBtn.setEnabled(false);
        etGuessNumber.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(s.toString().trim().length()==4){
                    checkBtn.setEnabled(true);
                } else {
                    checkBtn.setEnabled(false);
                }


            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

            }
        });



    }
    public void onMyButtonClick(View v) {
        cows = 0;
        bulls = 0;
        String guess = etGuessNumber.getText().toString();
        yourGuess = parseInt(guess);
        if (randomMob == yourGuess) {
            tvBulls.setText("Быки - 4");
            tvCows.setText("Коровы - 0");
            attempCounter++;
            setAttemp();
            //tvAttempts.setText(Integer.toString(yourGuess));
            youWin();
        }
        else {
            attempCounter++;
            String randomMobString = randomMob + "";
            System.out.println("!!!!!!!randomMobString - "+ randomMobString);
            String[] splitRndMob = randomMobString.split("");

            String yourGuessString = yourGuess + "";
            System.out.println("!!!!!!!yourGuessString - "+ yourGuessString);
            String[] splitYourGuess = yourGuessString.split("");

            for (int i=1; i<5; i++) {
                System.out.println(splitRndMob[i] + "rnd");
                System.out.println(splitYourGuess[i] + "YOU");
                if (splitRndMob[i].equals(splitYourGuess[i])) {

                    bulls ++;
                }
            }

            for (int i=1; i<5; i++) {
                for (int j = 1; j < 5; j++) {
                    if (splitRndMob[i].equals(splitYourGuess[j])) {
                        cows++;
                    }
                }
            }
            System.out.println("!!!!!!!BULS - "+ bulls);
            cows = cows - bulls;
            System.out.println("!!!!!!!Cows - "+ cows);

            tvBulls.setText("Быки - " + bulls );
            tvCows.setText("Коровы - " + cows);
            setAttemp();
           // tvAttempts.setText(Integer.toString(yourGuess));
        }

    }

    // Генератор искомого числа
    public int randomFourDigits() {
        int a, b, c, d;

        do {
        a = random.nextInt(10);
        }
        while (a==0);

        do {
            b = random.nextInt(10);
        }
        while (a==b);

        do {
            c = random.nextInt(10);
        }
        while (c==b || c==a);

        do {
            d = random.nextInt(10);
        }
        while (d==a || d==b || d==c);

        String resultRnd = "" + a + b + c + d;

        return parseInt(resultRnd);

    }
    public void youWin() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Поздравляем!")
                .setMessage("Вы угадали с "+ attempCounter +"й попытки!")
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setNegativeButton("Это было целью моей жизни!",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void setAttemp() {
        userAttempts.add(0, attempCounter +"). "  + etGuessNumber.getText().toString()+"  "+ bulls + "Б, "+ cows + "К");
        adapter.notifyDataSetChanged();
        etGuessNumber.setText("");
    }

    public void buttonClickAll(View view) {


        switch (view.getId()) {


            case R.id.buttonClr: { // кнопка очистить
                etGuessNumber.setText("");

                break;
            }


            case R.id.buttonBackClr: { // кнопка удаления последнего символа
                etGuessNumber.setText(etGuessNumber.getText().delete(
                        etGuessNumber.getText().length() - 1,
                        etGuessNumber.getText().length()));

                if (etGuessNumber.getText().toString().trim().length() == 0) {
                    etGuessNumber.setText("");
                }

                break;
            }

            default: { // все остальные кнопки (цифры)

                if (etGuessNumber.getText().toString().equals("0")
//                        ||

//                        (commands.containsKey(Symbol.FIRST_DIGIT) && getDouble(entertText
//                                .getText()) == getDouble(commands
//                                .get(Symbol.FIRST_DIGIT)))// если вводится второе
                    // число - то нужно
                    // сбросить текстовое
                    // поле

                        ) {

                    etGuessNumber.setText(view.getContentDescription().toString());
                } else {
                    etGuessNumber.setText(etGuessNumber.getText()
                            + view.getContentDescription().toString());
                }


            }

        }
    }
}
