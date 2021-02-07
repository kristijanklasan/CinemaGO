package android.unipu.cinema.fragmenti;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.icu.text.UnicodeSetSpanner;
import android.net.Uri;
import android.os.Bundle;

import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.unipu.cinema.model.UserModel;
import android.unipu.cinema.retrofit.IRegistration;
import android.unipu.cinema.retrofit.RetrofitClient;
import android.unipu.cinema.startCinema;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.unipu.cinema.R;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import android.unipu.cinema.model.UserModel;

import static android.app.Activity.RESULT_OK;

public class AccountFragment extends Fragment {

    CompositeDisposable compositeDisposible = new CompositeDisposable();
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private IRegistration iRegistration;
    private ImageView imageView;
    private EditText imeEdit, prezimeEdit, telefonEdit, emailEdit, lozinkaEdit;
    private String email_novo;
    private TextView idKorisnika;
    private UserModel user = new UserModel();
    private String mParam1;
    private String mParam2;
    private Button btnPromijeni;
    private UserModel modelUser;
    private String kljuc;
    private CheckBox lozinkaBox;
    private FrameLayout frameProfil;
    private UserModel userModel;
    private EditText lozinkaPonavljanje;

    public AccountFragment() {
        // Required empty public constructor
    }

    public static AccountFragment newInstance(String param1, String param2) {
        AccountFragment fragment = new AccountFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_account, container, false);

        userModel = new UserModel();
        Toolbar toolbar = view.findViewById(R.id.toolbarRacun);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);

        kljuc = user.getKljuc();
        modelUser = new UserModel();

        lozinkaPonavljanje = (EditText) view.findViewById(R.id.ponavljanjeLozinkaEdit);
        frameProfil = (FrameLayout) view.findViewById(R.id.frameProfil);
        lozinkaBox = (CheckBox) view.findViewById(R.id.lozinkaBox);
        imageView = (ImageView) view.findViewById(R.id.imageView2);
        imeEdit = (EditText) view.findViewById(R.id.imeEdit);
        prezimeEdit = (EditText) view.findViewById(R.id.prezimeEdit);
        telefonEdit = (EditText) view.findViewById(R.id.telefonEdit);
        emailEdit = (EditText) view.findViewById(R.id.emailEdit);
        lozinkaEdit = (EditText) view.findViewById(R.id.lozinkaEdit);
        idKorisnika = (TextView) view.findViewById(R.id.idKorisnika);
        btnPromijeni = (Button) view.findViewById(R.id.btnPromijeni);
        //email = getFile(view);

        Retrofit retrofitClient = RetrofitClient.getInstance2();
        iRegistration = retrofitClient.create(IRegistration.class);

        email_novo = user.getEmail();
        getUserData(kljuc);

        imageView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent =new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(intent,100);
            }
        });

        btnPromijeni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkPassword()){
                    if(lozinkaBox.isChecked()){
                        updateUser(view);
                    }
                    else{
                        updateUser2(view);
                    }
                }
            }
        });

        lozinkaBox.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                if(lozinkaBox.isChecked()){
                    lozinkaBox.setText("Da");
                    frameProfil.setVisibility(View.VISIBLE);
                }
                else{
                    lozinkaBox.setText("Ne");
                    frameProfil.setVisibility(View.GONE);
                }
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), startCinema.class);
                startActivity(i);
            }
        });


        return view;
    }

    private Boolean checkPassword(){
        Boolean flag = false;
        String pass1 = lozinkaPonavljanje.getText().toString();
        String pass2 = lozinkaEdit.getText().toString();

        if(pass1.equals(pass2)){
            flag = true;
        }

        return flag;
    }
    private void updateUser(View view){
        try{
            String ime = imeEdit.getText().toString();
            String prezime = prezimeEdit.getText().toString();
            String email = emailEdit.getText().toString();
            String telefon = telefonEdit.getText().toString();
            String lozinka = lozinkaEdit.getText().toString();
            String slika = userModel.getSlika();

            int id = Integer.parseInt(idKorisnika.getText().toString());
            final View parentLayout = view.findViewById(R.id.relative_layout);
            user.setEmail(email);

            //byte[]  slika = uploadImage();

            if(imageView.getDrawable() != null) {
            }
            compositeDisposible.add(iRegistration.updateUser(ime,prezime, telefon, email, lozinka, slika, id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<String>() {
                        @Override
                        public void accept(String response) throws Exception {

                            if(response.equals("true")) {
                                Snackbar.make(parentLayout, "Podaci su uspješno promijenjeni!", Snackbar.LENGTH_LONG).show();
                                getUserData(kljuc);
                            }else{
                                Toast.makeText(getActivity(), "Podaci nisu promijenjeni!",Toast.LENGTH_LONG).show();
                            }
                        }
                    })
            );

        }catch(Exception e){
            e.printStackTrace();
        }

    }

    private void updateUser2(View view){
        try{
            String ime = imeEdit.getText().toString();
            String prezime = prezimeEdit.getText().toString();
            String email = emailEdit.getText().toString();
            String telefon = telefonEdit.getText().toString();
            String slika = userModel.getSlika();

            int id = Integer.parseInt(idKorisnika.getText().toString());
            final View parentLayout = view.findViewById(R.id.relative_layout);
            user.setEmail(email);

            if(imageView.getDrawable() != null) {
            }

            compositeDisposible.add(iRegistration.updateUser2(ime,prezime, telefon, email, slika, id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<String>() {
                        @Override
                        public void accept(String response) throws Exception {

                            if(response.equals("true")) {
                                Snackbar.make(parentLayout, "Podaci su uspješno promijenjeni!", Snackbar.LENGTH_LONG).show();
                                getUserData(kljuc);
                            }else{
                                Toast.makeText(getActivity(), "Podaci nisu promijenjeni!",Toast.LENGTH_LONG).show();
                            }
                        }
                    })
            );
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void getUserData(String kljuc){

        int id = Integer.parseInt(kljuc);
        Call<List<UserModel>> call = iRegistration.getUserId(id);
        call.enqueue(new Callback<List<UserModel>>() {
            @Override
            public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {
                List<UserModel> lista = response.body();

                for(int i=0; i<lista.size(); i++) {
                    imeEdit.setText(lista.get(i).getIme());
                    prezimeEdit.setText(lista.get(i).getPrezime());
                    telefonEdit.setText(lista.get(i).getTelefon());
                    emailEdit.setText(lista.get(i).getEmail());
                    user.setLozinka(lista.get(i).getLozinka());
                    userModel.setSlika(lista.get(i).getSlika());
                    idKorisnika.setText(lista.get(i).getId());
                    loadImageFromStorage();
                }
            }

            @Override
            public void onFailure(Call<List<UserModel>> call, Throwable t) {
            }
        });

    }

    private String spremiInternaMemorija(Bitmap bitmapImage){

        ContextWrapper image = new ContextWrapper(getActivity());

        File direktorij = image.getDir("slikaDir", Context.MODE_PRIVATE);
        File path = new File(direktorij, direktorij.getName());

        FileOutputStream fos = null;
        userModel.setSlika(path.getAbsolutePath());

        try{
            fos = new FileOutputStream(path);
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                fos.close();
            }catch(IOException e){
                e.printStackTrace();
            }
        }

        return direktorij.getAbsolutePath();
    }

    private void loadImageFromStorage(){

        try{
            String path = userModel.getSlika();
            File file = new File(path);
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(file));
            imageView.setImageBitmap(b);

        }catch(Exception e){
            e.printStackTrace();
        }
    }


    private byte[] uploadImage(){

        //Bitmap bitmap=((BitmapDrawable)imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.cinema_logo);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream); //compress to which format you want.
        byte [] byte_arr = stream.toByteArray();
        //String image_str = Base64.encodeToString(byte_arr, Base64.DEFAULT);
        //Toast.makeText(getActivity(),""+image_str,Toast.LENGTH_LONG).show();
        return byte_arr;

    }

    /*
    private String getFile(View v){
        try {
            FileInputStream fileIn= getActivity().openFileInput("User.txt");
            InputStreamReader InputRead= new InputStreamReader(fileIn);

            List<String> lista = new ArrayList<String>();
            BufferedReader reader;
            StringBuffer buf = new StringBuffer();

            reader = new BufferedReader(new InputStreamReader(fileIn));
            String line = reader.readLine();
            while(line != null){
                line = reader.readLine();
                lista.add(line);
            }
            InputRead.close();
            email = lista.get(1);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return email;
    }

     */

    @Override
    public void onStop(){
        compositeDisposible.clear();
        super.onStop();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==100 && resultCode==RESULT_OK){
            Uri imageData = data.getData();
            imageView.setImageURI(imageData);
            imageView.invalidate();
            BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
            Bitmap bitmap = drawable.getBitmap();
            spremiInternaMemorija(bitmap);
        }
    }
}
