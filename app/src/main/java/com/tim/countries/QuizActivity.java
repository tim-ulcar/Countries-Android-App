package com.tim.countries;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class QuizActivity extends AppCompatActivity {

    String quizType;
    String selectedLength;
    Button correctButton;
    int score;
    int attempts;
    Button country1Button;
    Button country2Button;
    Button country3Button;
    Button country4Button;
    Button country5Button;
    Button country6Button;
    ArrayList<Button> buttons;
    ImageView imageView;
    TextView scoreTextView;
    TextView questionTextView;
    ArrayList<Country> europeCountries;
    ArrayList<Country> southAmericaCountries;
    ArrayList<Country> northAmericaCountries;
    ArrayList<Country> africaCountries;
    ArrayList<Country> asiaCountries;
    ArrayList<Country> oceaniaCountries;
    ArrayList<Country> usedCorrectCountries;
    ArrayList<Country> countriesBeingTested;
    boolean useAfrica;
    boolean useAsia;
    boolean useEurope;
    boolean useNorthAmerica;
    boolean useSouthAmerica;
    boolean useOceania;

    public void clicked (final View button){
        boolean answeredCorrectly;
        for (Button b : buttons) {
            b.setEnabled(false);
        }
        if (button.equals(correctButton)) {
            answeredCorrectly = true;
            Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
            score++;
            button.setBackgroundColor(Color.argb(100, 140, 200, 60));
        }
        else {
            answeredCorrectly = false;
            Toast.makeText(this, "Wrong!", Toast.LENGTH_SHORT).show();
            button.setBackgroundColor(Color.argb(100, 240, 70, 50));
            correctButton.setBackgroundColor(Color.argb(100, 250, 240, 60));
        }
        attempts++;
        Handler handler = new Handler();
        if (answeredCorrectly) {
            handler.postDelayed(new Runnable() {
                public void run() {
                    button.setBackgroundColor(Color.argb(255, 154, 218, 230));
                    correctButton.setBackgroundColor(Color.argb(255, 154, 218, 230));
                    updateActivity();
                }
            }, 500);
        }
        else {
            handler.postDelayed(new Runnable() {
                public void run() {
                    button.setBackgroundColor(Color.argb(255, 154, 218, 230));
                    correctButton.setBackgroundColor(Color.argb(255, 154, 218, 230));
                    updateActivity();
                }
            }, 1000);
        }

    }


    public void updateActivity() {

        if (attempts >= Integer.parseInt(selectedLength)) {
            int finalScore = score;
            Intent intent = new Intent(getApplicationContext(), FinishedActivity.class);
            intent.putExtra("finalScore", finalScore);
            intent.putExtra("selectedLength", selectedLength);
            startActivity(intent);
            finish();
        }


        for (Button b : buttons) {
            b.setEnabled(true);
        }


        //Select a new correct country
        Random rnd = new Random();
        int index = rnd.nextInt(countriesBeingTested.size());
        Country country = countriesBeingTested.get(index);


        //Set up imageview
        int countryPictureId = getResources().getIdentifier(country.pictureName, "drawable", getPackageName());
        int flagPictureId = getResources().getIdentifier(country.flagName, "drawable", getPackageName());
        imageView.setImageResource(countryPictureId);
        if (quizType.equals("flags")) {
            imageView.setImageResource(flagPictureId);
        }

        //Set up correct button
        int correctAnswer = rnd.nextInt(6);
        correctButton = buttons.get(correctAnswer);
        correctButton.setText(country.attribute(quizType));

        //Select wrong countries
        ArrayList<Country> usedCountries = new ArrayList<Country>();
        usedCountries.add(country);
        for (int i = 0; i <= 5; i++) {
            if (i != correctAnswer) {
                Button button = buttons.get(i);
                int ind = rnd.nextInt(countriesBeingTested.size());
                Country countryIncorrect = countriesBeingTested.get(ind);
                while (usedCountries.contains(countryIncorrect)) {
                    ind = rnd.nextInt(countriesBeingTested.size());
                    countryIncorrect = countriesBeingTested.get(ind);
                }
                if (quizType.equals("gdp")) {
                    while (countryIncorrect.gdp >= country.gdp * 0.9 && countryIncorrect.gdp <= country.gdp * 1.1) {
                        ind = rnd.nextInt(countriesBeingTested.size());
                        countryIncorrect = countriesBeingTested.get(ind);
                    }
                }
                if (quizType.equals("population")) {
                    while (countryIncorrect.population.equals(country.population)) {
                        ind = rnd.nextInt(countriesBeingTested.size());
                        countryIncorrect = countriesBeingTested.get(ind);
                    }
                }
                usedCountries.add(countryIncorrect);
                button.setText(countryIncorrect.attribute(quizType));
            }
        }

        //Set up questionTextView
        String forQuestionTextView = "SCORE: " + score + " / " + attempts;

        if (quizType.equals("countries")) {
            forQuestionTextView = "What is the highlighted country?";
        }
        else if(quizType.equals("flags")) {
            forQuestionTextView = "Flag of what country is above?";
        }
        else if (quizType.equals("capitals")) {
            forQuestionTextView = "What is the capital of <b>" + country.name.toUpperCase() + "</b>?";
        }
        else if (quizType.equals("population")) {
            forQuestionTextView = "What is the population of <b>" + country.name.toUpperCase() + "</b>?";
        }
        else if (quizType.equals("gdp")) {
            forQuestionTextView = "What is the nominal GDP per capita of <b>" + country.name.toUpperCase() + "</b>?";
        }
        else {
            forQuestionTextView = "Error - Wrong quizType!";
        }
        questionTextView.setText(Html.fromHtml(forQuestionTextView));

        //Set up scoreTextView
        String forScoreTextView = "SCORE: " + score + " / " + selectedLength;
        scoreTextView.setText(forScoreTextView);

    }







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        score = 0;
        attempts = 0;

        Intent intent = getIntent();
        quizType = intent.getStringExtra("quizType");
        selectedLength = intent.getStringExtra("selectedLength");
        useEurope = intent.getBooleanExtra("useEurope", false);
        useAfrica = intent.getBooleanExtra("useAfrica", false);
        useAsia = intent.getBooleanExtra("useAsia", false);
        useSouthAmerica = intent.getBooleanExtra("useSouthAmerica", false);
        useNorthAmerica = intent.getBooleanExtra("useNorthAmerica", false);
        useOceania = intent.getBooleanExtra("useOceania", false);

        imageView = (ImageView) findViewById(R.id.countryImageView);
        scoreTextView = (TextView) findViewById(R.id.scoreTextView);
        questionTextView = (TextView) findViewById(R.id.questionTextView);
        usedCorrectCountries = new ArrayList<Country>();

        country1Button = (Button) findViewById(R.id.country1Button);
        country2Button = (Button) findViewById(R.id.country2Button);
        country3Button = (Button) findViewById(R.id.country3Button);
        country4Button = (Button) findViewById(R.id.country4Button);
        country5Button = (Button) findViewById(R.id.country5Button);
        country6Button = (Button) findViewById(R.id.country6Button);
        buttons = new ArrayList<Button>();
        buttons.add(country1Button);
        buttons.add(country2Button);
        buttons.add(country3Button);
        buttons.add(country4Button);
        buttons.add(country5Button);
        buttons.add(country6Button);


        europeCountries = new ArrayList<Country>();
        Country country = new Country("Albania","Tirana", "2.9 million", 5300);
        europeCountries.add(country);
        country = new Country("Albania", "Tirana", "2.9 million", 5300);
        europeCountries.add(country);
        country = new Country("Andorra", "Andorra la Vella", "78 000", 42000);
        europeCountries.add(country);
        country = new Country("Austria", "Vienna", "8.9 million", 54000);
        europeCountries.add(country);
        country = new Country("Belarus", "Minsk", "9.5 million", 6500);
        europeCountries.add(country);
        country = new Country("Belgium", "Brussels", "12 million", 47000);
        europeCountries.add(country);
        country = new Country("Bosnia and Herzegovina", "Sarajevo", "3.5 million", 6000);
        europeCountries.add(country);
        country = new Country("Bulgaria", "Sofia", "7.1 million", 9500);
        europeCountries.add(country);
        country = new Country("Croatia", "Zagreb", "4.1 million", 15000);
        europeCountries.add(country);
        country = new Country("Cyprus", "Nicosia", "1.2 million", 29000);
        europeCountries.add(country);
        country = new Country("Czech Republic", "Prague", "11 million", 25000);
        europeCountries.add(country);
        country = new Country("Denmark", "Copenhagen", "5.8 million", 64000);
        europeCountries.add(country);
        country = new Country("Estonia", "Tallinn", "1.3 million", 25000);
        europeCountries.add(country);
        country = new Country("Finland", "Helsinki", "5.5 million", 50000);
        europeCountries.add(country);
        country = new Country("France", "Paris", "67 million", 42000);
        europeCountries.add(country);
        country = new Country("Germany", "Berlin", "83 million", 47000);
        europeCountries.add(country);
        country = new Country("Greece", "Athens", "11 million", 20000);
        europeCountries.add(country);
        country = new Country("Hungary", "Budapest", "10 million", 17000);
        europeCountries.add(country);
        country = new Country("Iceland", "Reykjavík", "350 000", 76000);
        europeCountries.add(country);
        country = new Country("Ireland", "Dublin", "4.9 million", 78000);
        europeCountries.add(country);
        country = new Country("Italy", "Rome", "60 million", 33000);
        europeCountries.add(country);
        country = new Country("Latvia", "Riga", "1.9 million", 19000);
        europeCountries.add(country);
        country = new Country("Liechtenstein", "Vaduz", "38 000", 143000);
        europeCountries.add(country);
        country = new Country("Lithuania", "Vilnius", "2.8 million", 20000);
        europeCountries.add(country);
        country = new Country("Luxembourg", "Luxembourg", "600 000", 113000);
        europeCountries.add(country);
        country = new Country("Malta", "Valletta", "480 000", 32000);
        europeCountries.add(country);
        country = new Country("Moldova", "Chisinau", "3.4 million", 3400);
        europeCountries.add(country);
        country = new Country("Monaco", "Monaco", "37 000", 167000);
        europeCountries.add(country);
        country = new Country("Montenegro", "Podgorica", "640 000", 8700);
        europeCountries.add(country);
        country = new Country("Netherlands", "Amsterdam", "17 million", 53000);
        europeCountries.add(country);
        country = new Country("North Macedonia", "Skopje", "2.1 million", 6100);
        europeCountries.add(country);
        country = new Country("Norway", "Oslo", "5.3 million", 83000);
        europeCountries.add(country);
        country = new Country("Poland", "Warsaw", "38 million", 16000);
        europeCountries.add(country);
        country = new Country("Portugal", "Lisbon", "10.3 million", 23000);
        europeCountries.add(country);
        country = new Country("Romania", "Bucharest", "20 million", 13000);
        europeCountries.add(country);
        country = new Country("Russia", "Moscow", "145 million", 11000);
        europeCountries.add(country);
        country = new Country("San Marino", "San Marino", "33 000", 45000);
        europeCountries.add(country);
        country = new Country("Serbia", "Belgrade", "7.0 million", 8200);
        europeCountries.add(country);
        country = new Country("Slovakia", "Bratislava", "5.4 million", 20000);
        europeCountries.add(country);
        country = new Country("Slovenia", "Ljubljana", "2.1 million", 27000);
        europeCountries.add(country);
        country = new Country("Spain", "Madrid", "48 million", 30000);
        europeCountries.add(country);
        country = new Country("Sweden", "Stockholm", "10 million", 51000);
        europeCountries.add(country);
        country = new Country("Switzerland", "Bern", "8.5 million", 83000);
        europeCountries.add(country);
        country = new Country("Turkey", "Ankara", "81 million", 9000);
        europeCountries.add(country);
        country = new Country("Ukraine", "Kiev", "44 million", 3200);
        europeCountries.add(country);
        country = new Country("United Kingdom", "London", "66 million", 41000);
        europeCountries.add(country);
        country = new Country("Vatican City", "Vatican City", "842", 21000);
        europeCountries.add(country);


        southAmericaCountries = new ArrayList<Country>();
        country = new Country("Argentina ","Buenos Aires", "45 million", 9900);
        southAmericaCountries.add(country);
        country = new Country("Bolivia ","Sucre", "12 million", 3800);
        southAmericaCountries.add(country);
        country = new Country("Brazil ","Brasilia", "212 million", 9000);
        southAmericaCountries.add(country);
        country = new Country("Chile","Santiago", "19 million", 15000);
        southAmericaCountries.add(country);
        country = new Country("Colombia ","Bogota", "51 million", 7000);
        southAmericaCountries.add(country);
        country = new Country("Ecuador ","Quito", "18 million", 6200);
        southAmericaCountries.add(country);
        country = new Country("Guyana","Georgetown", "780 000", 5300);
        southAmericaCountries.add(country);
        country = new Country("Paraguay","Asuncion", "7.1 million", 6200);
        southAmericaCountries.add(country);
        country = new Country("Peru","Lima", "33 million", 7100);
        southAmericaCountries.add(country);
        country = new Country("Suriname","Paramaribo", "580 000", 6900);
        southAmericaCountries.add(country);
        country = new Country("Uruguay","Montevideo", "3.5 million", 18000);
        southAmericaCountries.add(country);
        country = new Country("Venezuela","Caracas", "28 million", 2500);
        southAmericaCountries.add(country);
        country = new Country("French Guiana","Cayenne", "300 000", 18000);
        southAmericaCountries.add(country);


        africaCountries = new ArrayList<Country>();
        country = new Country("Algeria","Algiers", "43 million", 4200);
        africaCountries.add(country);
        country = new Country("Angola","Luanda", "32 million", 4100);
        africaCountries.add(country);
        country = new Country("Benin","Porto-Novo", "12 million", 970);
        africaCountries.add(country);
        country = new Country("Botswana","Gaborone", "2.4 million", 8300);
        africaCountries.add(country);
        country = new Country("Burkina Faso","Ouagadougou", "20 million", 790);
        africaCountries.add(country);
        country = new Country("Burundi","Gitega", "12 million", 310);
        africaCountries.add(country);
        country = new Country("Cameroon","Yaounde", "25 million", 1500);
        africaCountries.add(country);
        country = new Country("Cape Verde","Praia", "560 000", 3700);
        africaCountries.add(country);
        country = new Country("Central African Republic","Bangui", "4.8 million", 400);
        africaCountries.add(country);
        country = new Country("Chad","N'Djamena", "16 million", 890);
        africaCountries.add(country);
        country = new Country("Comoros","Moroni", "850 000", 1300);
        africaCountries.add(country);
        country = new Country("Democratic Republic of the Congo","Kinshasa", "87 million", 501);
        africaCountries.add(country);
        country = new Country("Republic of the Congo","Brazzaville", "5.5 million", 2400);
        africaCountries.add(country);
        country = new Country("Ivory Coast","Yamoussoukro", "26 million", 2200);
        africaCountries.add(country);
        country = new Country("Djibouti","Djibouti City", "970 000", 2100);
        africaCountries.add(country);
        country = new Country("Egypt","Cairo", "101 million", 3000);
        africaCountries.add(country);
        country = new Country("Equatorial Guinea","Malabo", "1.3 million", 9100);
        africaCountries.add(country);
        country = new Country("Eritrea","Asmara", "5.3 million", 1300);
        africaCountries.add(country);
        country = new Country("Eswatini","Lobamba", "1.4 million", 4200);
        africaCountries.add(country);
        country = new Country("Ethiopia","Addis Ababa", "110 million", 950);
        africaCountries.add(country);
        country = new Country("Gabon","Libreville", "2.1 million", 8400);
        africaCountries.add(country);
        country = new Country("Gambia","Banjul", "2.2 million", 490);
        africaCountries.add(country);
        country = new Country("Ghana","Accra", "30 million", 2300);
        africaCountries.add(country);
        country = new Country("Guinea","Conakry", "13 million", 700);
        africaCountries.add(country);
        country = new Country("Guinea Bissau","Bissau", "2.0 million", 850);
        africaCountries.add(country);
        country = new Country("Kenya","Nairobi", "52 million", 2000);
        africaCountries.add(country);
        country = new Country("Lesotho","Maseru", "2.3 million", 1400);
        africaCountries.add(country);
        country = new Country("Liberia","Monrovia", "5.0 million", 700);
        africaCountries.add(country);
        country = new Country("Libya","Tripoli", "6.6 million", 7800);
        africaCountries.add(country);
        country = new Country("Madagascar","Antananarivo", "27 million", 470);
        africaCountries.add(country);
        country = new Country("Malawi","Lilongwe", "20 million", 370);
        africaCountries.add(country);
        country = new Country("Mali","Bamako", "20 million", 890);
        africaCountries.add(country);
        country = new Country("Mauritania","Nouakchott", "4.7 million", 1300);
        africaCountries.add(country);
        country = new Country("Mauritius","Port Louis", "1.3 million", 12000);
        africaCountries.add(country);
        country = new Country("Morocco","Rabat", "37 million", 3400);
        africaCountries.add(country);
        country = new Country("Mozambique","Maputo", "31 million", 490);
        africaCountries.add(country);
        country = new Country("Namibia","Windhoek", "2.6 million", 5900);
        africaCountries.add(country);
        country = new Country("Niger","Niamey", "23 million", 510);
        africaCountries.add(country);
        country = new Country("Nigeria","Abuja", "201 million", 2200);
        africaCountries.add(country);
        country = new Country("Rwanda","Kigali", "13 million", 830);
        africaCountries.add(country);
        country = new Country("Sao Tome and Principe","Sao Tome", "210 000", 1700);
        africaCountries.add(country);
        country = new Country("Senegal","Dakar", "17 million", 1500);
        africaCountries.add(country);
        country = new Country("Seychelles","Victoria", "96 000", 16000);
        africaCountries.add(country);
        country = new Country("Sierra Leone","Freetown", "7.9 million", 500);
        africaCountries.add(country);
        country = new Country("Somalia","Mogadishu", "16 million", 720);
        africaCountries.add(country);
        country = new Country("South Africa","Cape Town", "58 million", 6300);
        africaCountries.add(country);
        country = new Country("South Sudan","Juba", "13 million", 250);
        africaCountries.add(country);
        country = new Country("Sudan","Khartoum", "43 million", 810);
        africaCountries.add(country);
        country = new Country("Tanzania","Dodoma", "61 million", 1200);
        africaCountries.add(country);
        country = new Country("Togo","Lome", "8.2 million", 680);
        africaCountries.add(country);
        country = new Country("Tunisia","Tunis", "12 million", 3600);
        africaCountries.add(country);
        country = new Country("Uganda","Kampala", "46 million", 770);
        africaCountries.add(country);
        country = new Country("Zambia","Lusaka", "18 million", 1300);
        africaCountries.add(country);
        country = new Country("Zimbabwe","Harare", "17 million", 5300);
        africaCountries.add(country);





        asiaCountries = new ArrayList<Country>();
        country = new Country("Afghanistan","Kabul", "27 million", 600);
        asiaCountries.add(country);
        country = new Country("Armenia","Yerevan", "3.0 million", 4400);
        asiaCountries.add(country);
        country = new Country("Azerbaijan","Baku", "9.6 million", 4500);
        asiaCountries.add(country);
        country = new Country("Bahrain","Manama", "1.3 million", 27000);
        asiaCountries.add(country);
        country = new Country("Bangladesh","Dhaka", "161 million", 1900);
        asiaCountries.add(country);
        country = new Country("Bhutan","Thimphu", "720 000", 3100);
        asiaCountries.add(country);
        country = new Country("Brunei","Bandar Seri Begawan", "410 000", 30000);
        asiaCountries.add(country);
        country = new Country("Cambodia","Phnom Penh", "15 million", 1600);
        asiaCountries.add(country);
        country = new Country("China","Beijing", "1.3 billion", 10000);
        asiaCountries.add(country);
        country = new Country("Georgia","Tbilisi", "4.6 million", 4800);
        asiaCountries.add(country);
        country = new Country("India","New Delhi", "1.2 billion", 2200);
        asiaCountries.add(country);
        country = new Country("Indonesia","Jakarta", "249 million", 4500);
        asiaCountries.add(country);
        country = new Country("Iran","Tehran", "79 million", 5500);
        asiaCountries.add(country);
        country = new Country("Iraq","Baghdad", "36 million", 6100);
        asiaCountries.add(country);
        country = new Country("Israel","Jerusalem", "7.6 million", 43000);
        asiaCountries.add(country);
        country = new Country("Japan","Tokyo", "127 million", 41000);
        asiaCountries.add(country);
        country = new Country("Jordan","Amman", "6.5 million", 4200);
        asiaCountries.add(country);
        country = new Country("Kazakhstan","Nur-Sultan", "18 million", 8800);
        asiaCountries.add(country);
        country = new Country("North Korea","Pyongyang", "25 million", 1300);
        asiaCountries.add(country);
        country = new Country("South Korea","Seoul", "51 million", 31000);
        asiaCountries.add(country);
        country = new Country("Kuwait","Kuwait City", "3.3 million", 28000);
        asiaCountries.add(country);
        country = new Country("Kyrgyzstan","Bishkek", "5.5 million", 1300);
        asiaCountries.add(country);
        country = new Country("Laos","Vientiane", "6.6 million", 2700);
        asiaCountries.add(country);
        country = new Country("Lebanon","Beirut", "6.0 million", 9700);
        asiaCountries.add(country);
        country = new Country("Malaysia","Kuala Lumpur", "31 million", 11000);
        asiaCountries.add(country);
        country = new Country("Maldives","Male", "400 000", 15000);
        asiaCountries.add(country);
        country = new Country("Mongolia","Ulaanbaatar", "3.2 million", 4200);
        asiaCountries.add(country);
        country = new Country("Myanmar","Nay Pyi Taw", "55 million", 1200);
        asiaCountries.add(country);
        country = new Country("Nepal","Kathmandu", "30 million", 920);
        asiaCountries.add(country);
        country = new Country("Oman","Muscat", "3.1 million", 18000);
        asiaCountries.add(country);
        country = new Country("Pakistan","Islamabad", "210 million", 1400);
        asiaCountries.add(country);
        country = new Country("Philippines","Manila", "101 million", 3500);
        asiaCountries.add(country);
        country = new Country("Qatar","Doha", "2.3 million", 66000);
        asiaCountries.add(country);
        country = new Country("Saudi Arabia","Riyadh", "32 million", 24000);
        asiaCountries.add(country);
        country = new Country("Singapore","Singapore", "5.4 million", 66000);
        asiaCountries.add(country);
        country = new Country("Sri Lanka","Sinhala", "21 million", 4000);
        asiaCountries.add(country);
        country = new Country("Syria","Damascus", "23 million", 830);
        asiaCountries.add(country);
        country = new Country("Tajikistan","Dushanbe", "7.8 million", 800);
        asiaCountries.add(country);
        country = new Country("Thailand","Bangkok", "67 million", 7600);
        asiaCountries.add(country);
        country = new Country("Timor Leste","Dili", "1.1 million", 2400);
        asiaCountries.add(country);
        country = new Country("Turkmenistan","Ashgabat", "5.1 million", 7400);
        asiaCountries.add(country);
        country = new Country("United Arab Emirates","Abu Dhabi", "9.6 million", 41000);
        asiaCountries.add(country);
        country = new Country("Uzbekistan","Tashkent", "30 million", 1200);
        asiaCountries.add(country);
        country = new Country("Vietnam","Hanoi", "92 million", 2700);
        asiaCountries.add(country);
        country = new Country("Yemen","Sana'a", "26 million", 930);
        asiaCountries.add(country);
        country = new Country("Palestine","Ramallah", "4.8 million", 3100);
        asiaCountries.add(country);
        country = new Country("Taiwan","Taipei", "23 million", 25000);
        asiaCountries.add(country);




        northAmericaCountries = new ArrayList<Country>();
        country = new Country("Antigua and Barbuda","St. John's", "96 000", 18000);
        northAmericaCountries.add(country);
        country = new Country("The Bahamas","Nassau", "390 000", 34000);
        northAmericaCountries.add(country);
        country = new Country("Barbados","Bridgetown", "280 000", 18000);
        northAmericaCountries.add(country);
        country = new Country("Belize","Belmopan", "410 000", 4900);
        northAmericaCountries.add(country);
        country = new Country("Canada","Ottawa", "38 million", 46000);
        northAmericaCountries.add(country);
        country = new Country("Costa Rica","San Jose", "5.0 million", 12000);
        northAmericaCountries.add(country);
        country = new Country("Cuba","Havana", "11 million", 8400);
        northAmericaCountries.add(country);
        country = new Country("Dominica","Roseau", "72 000", 6900);
        northAmericaCountries.add(country);
        country = new Country("Dominican Republic","Santo Domingo", "11 million", 9200);
        northAmericaCountries.add(country);
        country = new Country("El Salvador","San Salvador", "6.4 million", 4000);
        northAmericaCountries.add(country);
        country = new Country("Grenada","St. George's", "110 000", 12000);
        northAmericaCountries.add(country);
        country = new Country("Guatemala","Guatemala City", "17 million", 4600);
        northAmericaCountries.add(country);
        country = new Country("Haiti","Port-au-Prince", "11 million", 720);
        northAmericaCountries.add(country);
        country = new Country("Honduras","Tegucigalpa", "9.6 million", 2800);
        northAmericaCountries.add(country);
        country = new Country("Jamaica","Kingston", "2.9 million", 5400);
        northAmericaCountries.add(country);
        country = new Country("Mexico","Mexico City", "127 million", 10000);
        northAmericaCountries.add(country);
        country = new Country("Nicaragua","Managua", "6.2 million", 2100);
        northAmericaCountries.add(country);
        country = new Country("Panama","Panama City", "4.2 million", 17000);
        northAmericaCountries.add(country);
        country = new Country("Saint Kitts and Nevis","Basseterre", "52 000", 19000);
        northAmericaCountries.add(country);
        country = new Country("Saint Lucia","Castries", "180 000", 10000);
        northAmericaCountries.add(country);
        country = new Country("Trinidad and Tobago","Port of Spain", "1.4 million", 16000);
        northAmericaCountries.add(country);
        country = new Country("United States","Washington, D.C.", "328 million", 65000);
        northAmericaCountries.add(country);




        oceaniaCountries = new ArrayList<Country>();
        country = new Country("Australia","Canberra", "26 million", 54000);
        oceaniaCountries.add(country);
        country = new Country("Federated States of Micronesia","Palikir", "100 000", 3700);
        oceaniaCountries.add(country);
        country = new Country("Fiji","Suva", "890 000", 5900);
        oceaniaCountries.add(country);
        country = new Country("Kiribati","South Tarawa", "100 000", 1600);
        oceaniaCountries.add(country);
        country = new Country("Marshall Islands","Majuro", "68 000", 3900);
        oceaniaCountries.add(country);
        country = new Country("Nauru","Yaren", "9400", 8600);
        oceaniaCountries.add(country);
        country = new Country("New Zealand","Wellington", "5.0 million", 42000);
        oceaniaCountries.add(country);
        country = new Country("Palau","Ngerulmud", "21 000", 17000);
        oceaniaCountries.add(country);
        country = new Country("Papua New Guinea","Port Moresby", "6.3 million", 2500);
        oceaniaCountries.add(country);
        country = new Country("Samoa","Apia", "190 000", 4400);
        oceaniaCountries.add(country);
        country = new Country("Solomon Islands","Honiara", "580 000", 2400);
        oceaniaCountries.add(country);
        country = new Country("Tonga","Nukuʻalofa", "106 000", 4900);
        oceaniaCountries.add(country);
        country = new Country("Tuvalu","Funafuti", "11 000", 3600);
        oceaniaCountries.add(country);
        country = new Country("Vanuatu","Port Vila", "260 000", 3300);
        oceaniaCountries.add(country);
        country = new Country("Albania","Tirana", "2.9 million", 5300);
        oceaniaCountries.add(country);





        countriesBeingTested = new ArrayList<Country>();
        if (useAfrica) {
            countriesBeingTested.addAll(africaCountries);
        }
        if (useAsia) {
            countriesBeingTested.addAll(asiaCountries);
        }
        if (useEurope) {
            countriesBeingTested.addAll(europeCountries);
        }
        if (useNorthAmerica) {
            countriesBeingTested.addAll(northAmericaCountries);
        }
        if (useSouthAmerica) {
            countriesBeingTested.addAll(southAmericaCountries);
        }
        if (useOceania) {
            countriesBeingTested.addAll(oceaniaCountries);
        }



        updateActivity();
    }
}


class Country {
    String name;
    String pictureName;
    String flagName;
    String capital;
    String population;
    int gdp;

    Country(String name, String capital, String population, int gdp) {
        this.name = name;
        this.pictureName = name.replaceAll(" ", "").toLowerCase();
        this.flagName = pictureName + "f";
        this.capital = capital;
        this.population = population;
        this.gdp = gdp;
    }

    String attribute(String quizType) {
        String countryAttribute;
        if (quizType.equals("countries") || quizType.equals("flags")) {
            countryAttribute = this.name;
        }
        else if (quizType.equals("capitals")) {
            countryAttribute = this.capital;
        }
        else if (quizType.equals("population")) {
            countryAttribute = this.population;
        }
        else if (quizType.equals("gdp")) {
            countryAttribute = "$" + Integer.toString(this.gdp);
        }
        else {
            countryAttribute = "Error - non-existent attribute";
        }

        return countryAttribute;
    }

}



















