package rjt.example.com.tmrealestate.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;

import rjt.example.com.tmrealestate.R;
import rjt.example.com.tmrealestate.volley.VolleyAppController;

/**
 * Created by shutenmei on 2016/12/7.
 */
public class DisplayPropertyAdapter extends RecyclerView.Adapter<DisplayPropertyAdapter.ViewHolder> {

    private static final String TAG=DisplayPropertyAdapter.class.getSimpleName();
    Context mContext;
    ArrayList<Property> mPropertyList;
    OnItemClickListener mItemClickListener;

    public DisplayPropertyAdapter(Context mContext,ArrayList<Property> data){
        this.mContext=mContext;
        this.mPropertyList=data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View custom_p_card_view= LayoutInflater.from(mContext).inflate(R.layout.card_single_property,parent,false);
        return new ViewHolder(custom_p_card_view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Property temp_Property=mPropertyList.get(position);
        holder.image_property.setImageUrl(temp_Property.getP_image()[0],holder.mImageLoader);
        if(temp_Property.getStatus().equals("ON RENT")){
            holder.text_p_statu.setTextColor(Color.GREEN);
            holder.text_p_price.setText(temp_Property.getP_price());
        }else{
            holder.text_p_statu.setTextColor(Color.RED);
            if (!temp_Property.getP_morgage().equals("0"))
                holder.text_p_price.setText(temp_Property.getP_price() + " - $" + temp_Property.getP_morgage() + "/mon");
            else
                holder.text_p_price.setText(temp_Property.getP_price());
        }
        holder.text_p_statu.setText(temp_Property.getStatus());

        holder.text_p_address.setText(temp_Property.getP_address());
        holder.text_p_info.setText(temp_Property.getP_num_bedroom() + " Beds " + temp_Property.getP_num_bath() + " Baths " + temp_Property.getP_num_car_allow() + " Cars Allowed");
    }

    @Override
    public int getItemCount() {
        return mPropertyList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        NetworkImageView image_property;
        TextView text_p_address,text_p_price,text_p_statu,text_p_info;
        ImageLoader mImageLoader;
        ImageView image_hide_card,image_like_card,image_favorite;
        public ViewHolder(final View itemView) {
            super(itemView);
            image_property=(NetworkImageView)itemView.findViewById(R.id.image_card_property);
            text_p_address = (TextView) itemView.findViewById(R.id.text_card_address);
            text_p_price = (TextView) itemView.findViewById(R.id.text_card_price);
            text_p_statu = (TextView)itemView.findViewById(R.id.text_card_status);
            text_p_info = (TextView)itemView.findViewById(R.id.text_card_property_info);

            mImageLoader= VolleyAppController.getmInstance().getmImageLoader();
            image_hide_card = (ImageView) itemView.findViewById(R.id.image_card_hide);
            image_like_card = (ImageView) itemView.findViewById(R.id.image_card_favorite);
            image_hide_card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //Remove the item from recycler view
                    final AlertDialog.Builder alertDialog=new AlertDialog.Builder(mContext);
                    alertDialog.setMessage("Would you like to hide this property?");
                    alertDialog.setTitle("Remove permission");
                    alertDialog.setCancelable(true);
                    alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            removeAt(getAdapterPosition());
                        }
                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    }).show();
                }
            });
            image_like_card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    image_favorite=(ImageView)v.findViewById(R.id.image_card_favorite);
                    image_favorite.setImageResource(R.drawable.red_fav_heart);
                }
            });

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(mItemClickListener!=null){
                mItemClickListener.onItemClick(v, getPosition());
            }
        }
    }

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener){
        this.mItemClickListener=mItemClickListener;
    }

    public void removeAt(int position){
        mPropertyList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,mPropertyList.size());
    }
}
