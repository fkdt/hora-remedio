package br.com.robotrock.horaremedio.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.robotrock.horaremedio.interfaces.RecyclerViewOnClickListenerHack;
import br.com.robotrock.horaremedio.R;
import br.com.robotrock.horaremedio.domain.Remedio;
import br.com.robotrock.horaremedio.extras.ImageHelper;

public class RemedioAdapter extends RecyclerView.Adapter<RemedioAdapter.MyViewHolder> {
    private Context ctx;
    private List<Remedio> listRem;
    private LayoutInflater layoutInflater;
    private RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack;
    private float scale;
    private int width;
    private int height;


    public RemedioAdapter(Context c, List<Remedio> lista){
        ctx = c;
        listRem = lista;
        layoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        scale = ctx.getResources().getDisplayMetrics().density;
        width = ctx.getResources().getDisplayMetrics().widthPixels - (int)(14 * scale + 0.5f);
        height = (width / 16) * 9;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = layoutInflater.inflate(R.layout.item_rem_card, viewGroup, false);
        MyViewHolder mvh = new MyViewHolder(v);
        return mvh;
    }

    public int tipoDosagemToNumber( String tipo ){
        switch(tipo){
            case "Comprimido": return R.drawable.comprimido01;
            case "Gotas" : return R.drawable.comprimido02;
            case "Liquido": return R.drawable.liquido01;
            case "Pomada" : return R.drawable.liquido02;
            default: return R.drawable.liquido03;
        }
    }

    // Sempre chamado quando view é atualizada
    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int position) {

        myViewHolder.tvModel.setText(listRem.get(position).getNome());
        myViewHolder.tvBrand.setText(listRem.get(position).getTipo_dosagem());

        int image_num = tipoDosagemToNumber(listRem.get(position).getTipo_dosagem());

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
           /* myViewHolder.ivTipoRem.setImageResource(listRem.get(position).getTipo_dosagem()); */
            myViewHolder.ivTipoRem.setImageResource( image_num );
        }
        else{
            /* Bitmap bitmap = BitmapFactory.decodeResource( ctx.getResources(), listRem.get(position).getDose()); */
            Bitmap bitmap = BitmapFactory.decodeResource( ctx.getResources(), image_num );
            bitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);

            bitmap = ImageHelper.getRoundedCornerBitmap(ctx, bitmap, 4, width, height, false, false, true, true);
            myViewHolder.ivTipoRem.setImageBitmap(bitmap);
        }

        /*  // Animação
        try{
            YoYo.with(Techniques.Tada)
                    .duration(700)
                    .playOn(myViewHolder.itemView);
        }
        catch(Exception e){}
        */
    }

    @Override
    public int getItemCount() {
        return listRem.size();
    }


    public void setRecyclerViewOnClickListenerHack(RecyclerViewOnClickListenerHack r){
        mRecyclerViewOnClickListenerHack = r;
    }


    public void addListItem(Remedio c, int position){
        listRem.add(c);
        notifyItemInserted(position);
    }


    public void removeListItem(int position){
        listRem.remove(position);
        notifyItemRemoved(position);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ImageView ivTipoRem;
        public TextView tvModel;
        public TextView tvBrand;

        public MyViewHolder(View itemView) {
            super(itemView);

            ivTipoRem = (ImageView) itemView.findViewById(R.id.iv_car);
            tvModel = (TextView) itemView.findViewById(R.id.tv_model);
            tvBrand = (TextView) itemView.findViewById(R.id.tv_brand);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(mRecyclerViewOnClickListenerHack != null){
                mRecyclerViewOnClickListenerHack.onClickListener(v, getPosition());
            }
        }
    }
}
