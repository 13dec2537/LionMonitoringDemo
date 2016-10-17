package com.example.kuybeer26092016.lionmonitoringdemo.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kuybeer26092016.lionmonitoringdemo.R;
import com.example.kuybeer26092016.lionmonitoringdemo.models.Mis_monitoringitem;
import com.kosalgeek.android.photoutil.CameraPhoto;
import com.kosalgeek.android.photoutil.GalleryPhoto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by KuyBeer26092016 on 14/10/2559.
 */

public class AdapterAccount extends RecyclerView.Adapter<AdapterAccount.ViewHolder> {
    List<Mis_monitoringitem> mlist = new ArrayList<>();
    Context mContext;
    private CameraPhoto cameraphoto;
    private GalleryPhoto galleryphoto;
    private final int CAMERA_REQUEST = 13323;
    private final int GALLERY_REQUEST = 22131;
    private String selectphoto;
    public AdapterAccount() {
        this.mlist = mlist;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_editimage,parent,false);
        mContext = view.getContext();
        cameraphoto = new CameraPhoto(mContext);
        galleryphoto = new GalleryPhoto(mContext);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Mis_monitoringitem setList = mlist.get(position);
        holder.txtId.setText(setList.getMc_id());
        holder.txtId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ((Activity)mContext).startActivityForResult(cameraphoto.takePhotoIntent(),CAMERA_REQUEST);
                    cameraphoto.addToGallery();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public void addList(List<Mis_monitoringitem> list) {
        mlist.clear();
        mlist.addAll(list);
        this.notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtId;
        public ViewHolder(View itemView) {
            super(itemView);
            txtId = (TextView) itemView.findViewById(R.id.XML_ID);
        }
    }
}
