package com.chs.filepicker.filepicker.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chs.filepicker.R;
import com.chs.filepicker.filepicker.model.FileEntity;
import com.chs.filepicker.filepicker.util.FileUtils;

import java.io.File;
import java.io.FileFilter;
import java.util.List;

/**
 * 作者：chs on 2017-08-08 14:40
 * 邮箱：657083984@qq.com
 */

public class AllFileAdapter extends RecyclerView.Adapter<FilePickerViewHolder> {
    private List<FileEntity> mListData;
    private Context mContext;
    private FileFilter mFileFilter;
    private OnFileItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnFileItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public AllFileAdapter(Context context, List<FileEntity> listData, FileFilter fileFilter) {
        mListData = listData;
        mContext = context;
        mFileFilter = fileFilter;
    }

    @Override
    public FilePickerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_file_picker, parent, false);
        return new FilePickerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final FilePickerViewHolder holder, int positon) {
        final FileEntity entity = mListData.get(positon);
        final File file = entity.getFile();
        holder.tvName.setText(file.getName());
        if (file.isDirectory()) {
            holder.ivType.setImageResource(R.mipmap.file_picker_folder);
            holder.ivChoose.setVisibility(View.GONE);
        } else {
            setImage(file, holder.ivType);
            holder.ivChoose.setVisibility(View.VISIBLE);
            holder.tvDetail.setText(FileUtils.getReadableFileSize(file.length()));
            if (entity.isSelected()) {
                holder.ivChoose.setImageResource(R.mipmap.file_choice);
            } else {
                holder.ivChoose.setImageResource(R.mipmap.file_no_selection);
            }
        }
        holder.layoutRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.click(holder.getAdapterPosition());
                }
            }
        });
    }

    private void setImage(File file, ImageView ivType) {
        String name = file.getName().toLowerCase();
        if (name.endsWith("pdf")) {
            ivType.setImageResource(R.mipmap.file_picker_pdf);
        } else if (name.endsWith("doc") || name.endsWith("docx") || name.endsWith("dot") || name.endsWith("dotx")) {
            ivType.setImageResource(R.mipmap.file_picker_word);
        } else if (name.endsWith("xls") || name.endsWith("xlsx")) {
            ivType.setImageResource(R.mipmap.file_picker_excle);
        } else if (name.endsWith("ppt") || name.endsWith("pptx")) {
            ivType.setImageResource(R.mipmap.file_picker_ppt);
        } else if (name.endsWith("txt")) {
            ivType.setImageResource(R.mipmap.file_picker_txt);
        } else if (name.endsWith("png") || name.endsWith("jpg") || name.endsWith("jpeg")) {
            Glide.with(mContext).load(file).into(ivType);
        } else {
            ivType.setImageResource(R.mipmap.file_picker_def);
        }
    }

    @Override
    public int getItemCount() {
        return mListData.size();
    }

    /**
     * 更新数据源
     *
     * @param mListData
     */
    public void updateListData(List<FileEntity> mListData) {
        this.mListData = mListData;
    }
}
