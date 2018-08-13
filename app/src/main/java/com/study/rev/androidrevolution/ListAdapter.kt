package com.study.rev.androidrevolution

import android.content.Context
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.list_column.view.*

class ListAdapter : ArrayAdapter<ListColumn>{
    var con : Context
    var listSet : ArrayList<ListColumn>

    constructor(con : Context?, textViewResourceId : Int, items : ArrayList<ListColumn>):
            super(con, textViewResourceId, items){
        this.notifyDataSetChanged()
        this.con = context
        this.listSet = items
    }

    override fun getView(pos : Int, convertView : View?, parent : ViewGroup) : View?{
        var view : View? = convertView


        if(view == null){
            var vi : LayoutInflater = con.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = vi.inflate(R.layout.list_column, null)
        }

        var currentPointer : ListColumn = listSet.get(pos)

        if(currentPointer != null){
            view?.view_name?.text = currentPointer.name
            view?.view_subject?.text = currentPointer.subject
            view?.view_borrower?.text = currentPointer.borrower

            if(view?.view_borrower?.text != ""){
                view?.setBackgroundColor(Color.parseColor("#FF0000"));
            }
        }

        return view
    }

}