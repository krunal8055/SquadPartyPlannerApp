package com.example.squadpartyplannerapp;


import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;


public class UpcomingEventDetails extends Fragment {
    ProgressBar progressBar;
    Context context;
    ImageView eventImage;
    TextView eventName, eventStartDate,eventEndDate, eventStartTime, eventEndTime, eventPlace, eventType, eventInstruction, eventExtras, eventNoOfGuest, eventHostID;
    String ID,HostID, EventImage, EventName, EventStartDate,EventEndDate, EventStartTime, EventEndTime, EventPlace, EventType, EventInstruction, NoOfGuest, Extras,TAG;
    Uri imageURI;
    Bundle bundle;
    String Imageextention;
    SharedPreferences eventData;
    View view;
    String Message;


    public UpcomingEventDetails() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_upcoming_event_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        eventData = getActivity().getSharedPreferences("EventInfo",Context.MODE_PRIVATE);
        bundle = new Bundle();
        this.view = view;
        context = getActivity().getApplicationContext();
        progressBar = view.findViewById(R.id.progress_Upcomingdetails);
        eventImage = view.findViewById(R.id.eventImage_upcomingDetails);
        eventName = view.findViewById(R.id.eventName_upcomingDetails);
        eventStartDate = view.findViewById(R.id.eventStartDate_upcomingDetails);
        eventEndDate = view.findViewById(R.id.eventEndDate_upcomingDetails);
        eventStartTime = view.findViewById(R.id.eventStart_upcomingDetails);
        eventEndTime = view.findViewById(R.id.eventEnd_upcomingDetails);
        eventPlace = view.findViewById(R.id.eventPlace_upcomingDetails);
        eventType = view.findViewById(R.id.eventType_upcomingDetails);
        eventInstruction = view.findViewById(R.id.eventInstruction_upcomingDetails);
        eventExtras = view.findViewById(R.id.eventExtras_upcomingDetails);
        eventNoOfGuest = view.findViewById(R.id.eventNoGuest_upcomingDetails);
        eventHostID = view.findViewById(R.id.eventHostID_upcomingDetails);

        ID = eventData.getString("event__ID","");
        HostID = eventData.getString("host__ID","");
        EventImage = eventData.getString("event__Image","");
        EventName = eventData.getString("event__Name","");
        EventStartDate = eventData.getString("event__StartDate","");
        EventEndDate = eventData.getString("event__EndDate","");
        EventStartTime = eventData.getString("event__StartTime","");
        EventEndTime = eventData.getString("event__EndTime","");
        EventPlace = eventData.getString("event__Place","");
        EventType = eventData.getString("event__Type","");
        EventInstruction = eventData.getString("event__Instruction","");
        NoOfGuest = eventData.getString("noOf__Guest","");
        Extras = eventData.getString("__extras","");
        TAG = eventData.getString("TAG","");


        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(EventName);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //((AppCompatActivity) getActivity()).getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);


        imageURI = Uri.parse(EventImage);
        Picasso.get().load(imageURI).into(eventImage);
        eventName.setText(EventName);
        eventStartDate.setText(EventStartDate);
        eventEndDate.setText(EventEndDate);
        eventStartTime.setText(EventStartTime);
        eventEndTime.setText(EventEndTime);
        eventPlace.setText(EventPlace);
        eventType.setText(EventType);
        eventInstruction.setText(EventInstruction);
        eventExtras.setText(Extras);
        eventNoOfGuest.setText(NoOfGuest);
        eventHostID.setText(HostID);

        if (imageURI.toString().contains(".jpg") || imageURI.toString().contains(".JPG")) {
            Imageextention = "jpg";
        } else if (imageURI.toString().contains(".png") || imageURI.toString().contains(".PNG")) {
            Imageextention = "png";
        } else if (imageURI.toString().contains(".jpeg") || imageURI.toString().contains(".JPEG")) {
            Imageextention = "JPEG";
        }


    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if(TAG.contentEquals("Accepted_Event"))
        {
            menu.clear();
        }
        else
        {
            menu.clear();
            inflater.inflate(R.menu.event_options, menu);
        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.invite_EventMenu:

                bundle.putString("event_ID",ID);
                bundle.putString("host_ID",HostID);
                bundle.putString("event_Name",EventName);
                bundle.putString("event_Start_Date",EventStartDate);
                bundle.putString("event_End_Date",EventEndDate);
                bundle.putString("event_StartTime",EventStartTime);
                bundle.putString("event_EndTime",EventEndTime);
                bundle.putString("event_Place",EventPlace);

                Navigation.findNavController(view).navigate(R.id.action_upcomingEventDetails_to_inviteUser,bundle);
                //Toast.makeText(context, "Invite Guests Clicked!", Toast.LENGTH_LONG).show();
                break;
            case R.id.shareEvent_EventMenu:
                getEventMessageFromDB();
                break;
            case R.id.seeGuest_EventMenu:
                Navigation.findNavController(view).navigate(R.id.action_upcomingEventDetails_to_guestList);
                //Toast.makeText(context, "See Guest List Menu Clicked!", Toast.LENGTH_LONG).show();
                break;
            case R.id.update_EventMenu:
                //Toast.makeText(context,"Update Event Clicked!",Toast.LENGTH_LONG).show();
                UpdateEventmethod();
                break;
            case R.id.delete_EventMenu:
                //Toast.makeText(context,"Delete Event Clicked!",Toast.LENGTH_LONG).show();
                DeleteEvent();
                break;
        }
        return super.onOptionsItemSelected(item);

    }

    private void  getEventMessageFromDB() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("Invitations");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    for(DataSnapshot ds : dataSnapshot.getChildren())
                    {
                        if(ds.getKey().contentEquals(ID)) {
                            for (DataSnapshot ds1 : ds.getChildren()) {
                                Message = ds1.child("Invitation_Message").getValue().toString();
                                Intent i = new Intent(Intent.ACTION_SEND);
                                i.setType("text/plain");
                                i.putExtra(Intent.EXTRA_TEXT,Message);
                                i.putExtra(Intent.EXTRA_SUBJECT,"Invitation");
                                startActivity(Intent.createChooser(i,"Send Email"));
                            }
                        }
                    }
                }
                else {
                    Toast.makeText(context,"For Share this Event you need to Invite atleast one person.",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(context,databaseError.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    private void DeleteEvent() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Delete Account");
        builder.setMessage("Are you sure you want to delete this Event ? Once You delete this event, you will not be able to see this event again!")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        DeleteEventDetails();
                    }

                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                });
        builder.show();

    }

    private void DeleteEventDetails() {
        progressBar.setVisibility(View.VISIBLE);

        Handler uiHandler = new Handler(Looper.getMainLooper());
        uiHandler.post(new Runnable() {
            @Override
            public void run() {

                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                final DatabaseReference databaseReference = firebaseDatabase.getReference("Events");
                FirebaseStorage storage = FirebaseStorage.getInstance();
                FirebaseAuth auth = FirebaseAuth.getInstance();
                String uid = auth.getCurrentUser().getUid();
                StorageReference storageReference = storage.getReference();

                StorageReference deleteRef = storageReference.child("Events/" + uid + "/" + ID).child("Event_Image." + Imageextention);
                deleteRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        databaseReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                        if (ID.contentEquals(ds.getKey())) {

                                            progressBar.setVisibility(View.GONE);
                                            Log.e("DS Ref: ", ds.getKey());
                                            ds.getRef().removeValue();


                                            /*FragmentManager fragmentManager = getParentFragmentManager();
                                            fragmentManager.popBackStack();*/
                                            Intent i = new Intent(context,Main3Activity.class);
                                            startActivity(i);
                                            getActivity().finish();
                                            Toast.makeText(context, "Event Successfully Deleted!", Toast.LENGTH_LONG).show();

                                        }
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(context, databaseError.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

            }
        });
    }

    private void UpdateEventmethod() {
        Intent i = new Intent(context, Main4Activity.class);
        i.putExtra("flag", true);
        i.putExtra("eventID", ID);
        i.putExtra("hostID", HostID);
        i.putExtra("eventImage", EventImage);
        i.putExtra("eventName", EventName);
        i.putExtra("eventStartDate", EventStartDate);
        i.putExtra("eventEndDate",EventEndDate);
        i.putExtra("eventStartTime", EventStartTime);
        i.putExtra("eventEndTime", EventEndTime);
        i.putExtra("eventPlace", EventPlace);
        i.putExtra("eventType", EventType);
        i.putExtra("eventInstruction", EventInstruction);
        i.putExtra("noOfGuest", NoOfGuest);
        i.putExtra("extras", Extras);
        startActivity(i);
    }


}
