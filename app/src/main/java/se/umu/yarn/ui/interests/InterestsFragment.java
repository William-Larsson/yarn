package se.umu.yarn.ui.interests;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.util.ArrayList;
import java.util.List;

import se.umu.yarn.EntityClass.InterestListModel;
import se.umu.yarn.EntityClass.UserModel;
import se.umu.yarn.R;
import se.umu.yarn.ui.settings.SettingsFragment;

import static android.util.Log.d;

public class InterestsFragment extends Fragment {

    //TODO
    //Get name of users, ask how Robin saves it
    //Get interests from database and add it into the textViews.

    private InterestsViewModel interestsViewModel;
    RadioButton one, two, three, four, five, six;
    ArrayList<String> interestList = new ArrayList<>();
    private GoogleSignInAccount account;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        interestsViewModel =
                new ViewModelProvider(this).get(InterestsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_interests, container, false);
        account = requireActivity().getIntent().getParcelableExtra("se.umu.yarn.account");

        UserModel user = new UserModel();

        TextView textView = root.findViewById(R.id.textView1);
        textView.setText("Hello " + account.getGivenName() + ", choose your interests!");
        //d("Alice" , account.getDisplayName());

        //user.setName(account.getDisplayName());

        final Button confirmButton = root.findViewById(R.id.saveInterestBtn);
        d("Alice", "Start fragment");
        confirmButton.setOnClickListener(view -> {
            Toast.makeText(requireContext(), "Interests has been saved!", Toast.LENGTH_LONG).show();

            d("Alice", "Clicked");

            //Get all the checked boxes and insert into database

            for (int i = 0; i < interestList.size(); i++) {
                //INSERT INTO table (interests)
                //VALUES ($name);

                String name = interestList.get(i);
                d("Alice", "NAme: " + name);
            }

            InterestListModel interestListModel = new InterestListModel();
            interestListModel.setInterestList(interestList);


            List<String> test = interestListModel.getInterestList();
            for (int i = 0; i < test.size(); i++) {
                //INSERT INTO table (interests)
                //VALUES ($name);

                String name = test.get(i);
                d("Alice", " test Name: " + name);
            }

            //user.setInterests( interestList);

            SettingsFragment fragment = new SettingsFragment();
            Bundle bundle = new Bundle();
            bundle.putStringArrayList("listan", interestList);
            fragment.setArguments(bundle);

            d("Alice", bundle.toString());

            d("Alice" , "test");


                });


                one = root.findViewById(R.id.radioButton1);
                two = root.findViewById(R.id.radioButton2);
                three = root.findViewById(R.id.radioButton3);
                four = root.findViewById(R.id.radioButton4);
                five = root.findViewById(R.id.radioButton5);
                six = root.findViewById(R.id.radioButton6);


                final boolean[] one_check = {one.isChecked()};

                one.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        TextView interests = root.findViewById(R.id.textView2);
                        String interest = (String) interests.getText();
                        d("Alice", "Clicked radio" + interest);
                        d("Alice", "isChecked? " + one_check);


                        if (one_check[0]) {
                            one.setChecked(false);
                            one_check[0] = false;
                            int i = interestList.indexOf(interest);
                            interestList.remove(i);
                            d("Alice", "Index: " + i);

                        } else {
                            one.setChecked(true);
                            one_check[0] = true;
                            interestList.add(interest);
                        }

                        d("Alice", "List: " + interestList);

                        //Get all the checked boxes and insert into database

                        //"SELECT id FROM interests WHERE Name ='".$interests."'";
                    }

                });

                final boolean[] two_check = {two.isChecked()};

                two.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        TextView interests = root.findViewById(R.id.textView3);
                        String interest = (String) interests.getText();
                        d("Alice", "Clicked radio" + interest);
                        d("Alice", "isChecked? " + one_check);


                        if (two_check[0]) {
                            two.setChecked(false);
                            two_check[0] = false;
                            int i = interestList.indexOf(interest);
                            interestList.remove(i);
                            d("Alice", "Index: " + i);

                        } else {
                            two.setChecked(true);
                            two_check[0] = true;
                            interestList.add(interest);
                        }

                        d("Alice", "List: " + interestList);

                        //Get all the checked boxes and insert into database

                        //"SELECT id FROM interests WHERE Name ='".$interests."'";
                    }
                });

                final boolean[] three_check = {three.isChecked()};

                three.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        TextView interests = root.findViewById(R.id.textView4);
                        String interest = (String) interests.getText();
                        d("Alice", "Clicked radio" + interest);
                        d("Alice", "isChecked? " + one_check);


                        if (three_check[0]) {
                            three.setChecked(false);
                            three_check[0] = false;
                            int i = interestList.indexOf(interest);
                            interestList.remove(i);
                            d("Alice", "Index: " + i);

                        } else {
                            three.setChecked(true);
                            three_check[0] = true;
                            interestList.add(interest);
                        }

                        d("Alice", "List: " + interestList);

                        //Get all the checked boxes and insert into database

                        //"SELECT id FROM interests WHERE Name ='".$interests."'";
                    }

                });

        final boolean[] four_check = {four.isChecked()};

        four.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TextView interests = root.findViewById(R.id.textView5);
                String interest = (String) interests.getText();
                d("Alice", "Clicked radio" + interest);
                d("Alice", "isChecked? " + four_check);


                if (four_check[0]) {
                    four.setChecked(false);
                    four_check[0] = false;
                    int i = interestList.indexOf(interest);
                    interestList.remove(i);
                    d("Alice", "Index: " + i);

                } else {
                    four.setChecked(true);
                    four_check[0] = true;
                    interestList.add(interest);
                }

                d("Alice", "List: " + interestList);

                //Get all the checked boxes and insert into database

                //"SELECT id FROM interests WHERE Name ='".$interests."'";
            }
        });
        final boolean[] five_check = {five.isChecked()};

        five.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TextView interests = root.findViewById(R.id.textView6);
                String interest = (String) interests.getText();
                d("Alice", "Clicked radio" + interest);
                d("Alice", "isChecked? " + five_check);


                if (five_check[0]) {
                    five.setChecked(false);
                    five_check[0] = false;
                    int i = interestList.indexOf(interest);
                    interestList.remove(i);
                    d("Alice", "Index: " + i);

                } else {
                    five.setChecked(true);
                    five_check[0] = true;
                    interestList.add(interest);
                }

                d("Alice", "List: " + interestList);

                //Get all the checked boxes and insert into database

                //"SELECT id FROM interests WHERE Name ='".$interests."'";
            }
        });
        final boolean[] six_check = {six.isChecked()};

        six.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TextView interests = root.findViewById(R.id.textView7);
                String interest = (String) interests.getText();
                d("Alice", "Clicked radio" + interest);
                d("Alice", "isChecked? " + six_check);


                if (six_check[0]) {
                    six.setChecked(false);
                    six_check[0] = false;
                    int i = interestList.indexOf(interest);
                    interestList.remove(i);
                    d("Alice", "Index: " + i);

                } else {
                    six.setChecked(true);
                    six_check[0] = true;
                    interestList.add(interest);
                }

                d("Alice", "List: " + interestList);

                //Get all the checked boxes and insert into database

                //"SELECT id FROM interests WHERE Name ='".$interests."'";
            }
        });

             return root;
        }



        }