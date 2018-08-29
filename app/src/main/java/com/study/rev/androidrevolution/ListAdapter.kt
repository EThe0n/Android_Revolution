package com.study.rev.androidrevolution

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.list_column.view.*

/**
 * listview parsing을 위한 adapter
 */
class ListAdapter : ArrayAdapter<ListColumn>{
    var con : Context
    var listSet : ArrayList<ListColumn>

    constructor(con : Context?, textViewResourceId : Int, items : ArrayList<ListColumn>):
            super(con, textViewResourceId, items){
        this.notifyDataSetChanged()
        this.con = context
        this.listSet = items
    }

    override fun getView(pos : Int, convertView : View?, parent : ViewGroup?) : View?{
        var view : View? = convertView

        if(view == null){
            val vi : LayoutInflater = con.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = vi.inflate(R.layout.list_column, null)
        }

        val currentPointer : ListColumn = listSet[pos]
        val dot : String = "..."

        view?.view_name?.let{
            if(currentPointer.name.length > 12) {
                it.text = currentPointer.name.substring(0, 12) + dot
            }
            else {
                it.text = currentPointer.name
            }
        }

        view?.view_subject?.let {
            if(currentPointer.subject.length > 5){
                it.text = currentPointer.subject.substring(0, 5) + dot
            }
            else{
                it.text = currentPointer.subject
            }
        }

        view?.view_borrower?.let {
            if(currentPointer.borrower.length > 3){
                it.text = currentPointer.borrower.substring(0, 3) + dot
            }
            else{
                it.text = currentPointer.borrower
            }
        }
<<<<<<< HEAD
        else{
            view?.view_borrower?.text = currentPointer.borrower
        }
=======
>>>>>>> origin/master

        return view
    }

}