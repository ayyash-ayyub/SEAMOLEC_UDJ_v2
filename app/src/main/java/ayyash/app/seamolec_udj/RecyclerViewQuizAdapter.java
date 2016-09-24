package ayyash.app.seamolec_udj;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Abdul Rizal Adompo on 9/17/2016.
 */
public class RecyclerViewQuizAdapter extends RecyclerView.Adapter<RecyclerViewQuizAdapter.ViewHolder> {

    private final Context context;

    List<ModelQuiz> getDataAdapter;
    Button btnTampan;

    ModelQuiz getDataAdapter1;
    ViewHolder holderLuar;


    public RecyclerViewQuizAdapter(List<ModelQuiz> getDataAdapter, Context context){
        super();

        this.getDataAdapter = getDataAdapter;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_items, parent, false);

        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        this.holderLuar =holder;

        getDataAdapter1 =  getDataAdapter.get(position);

        holder.id_kelas.setText(": "+String.valueOf(getDataAdapter1.getId_quiz()).toString());

        holder.nama_quiz.setText(": "+getDataAdapter1.getNama_quiz().toString());

        holder.tgl_selesai.setText(": "+getDataAdapter1.getTgl_selesai());

        holder.durasi.setText(": "+String.valueOf(String.valueOf(getDataAdapter1.getDurasi()).toString()));

        btnTampan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int id_quiz = getDataAdapter.get(position).getId_quiz();
                Intent i = new Intent(context, SoalActivity.class);

                i.putExtra("kirimanIDQuiz", id_quiz);
                context.startActivity(i);


            }
        });



    }

    @Override
    public int getItemCount() {

        return getDataAdapter.size();

    }







    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView id_kelas;
        public TextView nama_quiz;
        public TextView tgl_selesai;
        public TextView durasi;



        public ViewHolder(View itemView) {

            super(itemView);


            id_kelas = (TextView) itemView.findViewById(R.id.tampilIdKelas) ;
            nama_quiz = (TextView) itemView.findViewById(R.id.tampilNamaQuiz) ;
            tgl_selesai = (TextView) itemView.findViewById(R.id.batasAkhir) ;
            durasi = (TextView) itemView.findViewById(R.id.tampilDurasi) ;



            btnTampan = (Button)itemView.findViewById(R.id.btnPengantarKeQuiz);





           // btnTampan.setOnClickListener(new View.OnClickListener() {
             //   @Override
             //   public void onClick(View v) {
             //       Log.d("Uye", "BAYAR");
//            /        Intent i = new Intent(context, Lemparan.class);
//                        context.startActivity(i);
//                       ((MainActivity)context).finish();
                 //   String aa = getDataAdapter1.get


                    //Toast.makeText(context, "H"+ aa,Toast.LENGTH_SHORT).show();

           //     }
           // });




        }
    }
}
