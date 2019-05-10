package com.pic.optimize.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pic.optimize.BookDetailActivity;
import com.pic.optimize.R;
import com.pic.optimize.http.Book;

import java.util.ArrayList;


public class RecyclerBookAdapter extends RecyclerView.Adapter<RecyclerBookAdapter.BookHolder> {

    private Context mContext;
    private ArrayList<Book> mBookList = new ArrayList<>();
    private LayoutInflater mLayoutInflater;

    public RecyclerBookAdapter(Context context, ArrayList<Book> bookList) {
        mBookList = bookList;
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);

    }

    public void setList(ArrayList<Book> list) {
        this.mBookList = list;
        notifyDataSetChanged();
    }


    @Override
    public BookHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.book_recycleview_item, parent, false);
        return new BookHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BookHolder holder, final int position) {
        holder.nameTV.setText(mBookList.get(position).bookName);
        holder.description.setText(mBookList.get(position).book_description);
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Book book = mBookList.get(position);
                BookDetailActivity.startActivity(mContext, book);
            }
        });
    }


    @Override
    public int getItemCount() {
        return mBookList.size();
    }


    public class BookHolder extends RecyclerView.ViewHolder {

        private TextView nameTV;
        private TextView description;
        private View mView;


        private BookHolder(View itemView) {
            super(itemView);
            mView = itemView;
            nameTV = (TextView)itemView.findViewById(R.id.book_name);
            description = (TextView)itemView.findViewById(R.id.description);
        }
    }

}
