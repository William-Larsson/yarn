package se.umu.yarn.ui.interests;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telecom.Call;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;

import se.umu.yarn.CallActivity;
import se.umu.yarn.DatabaseClass;
import se.umu.yarn.EntityClass.InterestListModel;
import se.umu.yarn.EntityClass.UserModel;
import se.umu.yarn.InterestsActivity;
import se.umu.yarn.MainActivity;
import se.umu.yarn.R;

import static android.provider.AlarmClock.EXTRA_MESSAGE;
import static android.util.Log.d;

public class InterestsFragment extends Fragment {

    //TODO
    //Get name of users, ask how Robin saves it
    //Get interests from database and add it into the textViews.

    private InterestsViewModel interestsViewModel;
    RadioButton one, two, three, four;
    List<String> interestList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        interestsViewModel =
                new ViewModelProvider(this).get(InterestsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_interests, container, false);


        final Button confirmButton = root.findViewById(R.id.confirmButton);
        d("Alice", "Start fragment");
        confirmButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
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
                UserModel user = new UserModel("Alice", "Trees");
                user.setName("Alice");
                d("Alice", "UserKey: " + user.getKey());
                UserModel user1 = new UserModel("Agnes", "Gardening");
                user1.setName("Agnes");
                d("Alice", "UserKey1: " + user.getKey());

                List<String> test = interestListModel.getInterestList();
                for (int i = 0; i < test.size(); i++) {
                    //INSERT INTO table (interests)
                    //VALUES ($name);

                    String name = test.get(i);
                    d("Alice", " test Name: " + name);
                }

                /*Intent intent = new Intent(String.valueOf(InterestsActivity.class));
                intent.putExtra(EXTRA_MESSAGE, "user");
                startActivity(intent);*/

                    }



                });


                one = root.findViewById(R.id.radioButton1);
                two = root.findViewById(R.id.radioButton2);
                three = root.findViewById(R.id.radioButton3);
                four = root.findViewById(R.id.radioButton4);


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

             return root;
        }



        }