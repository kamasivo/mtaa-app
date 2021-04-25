package com.example.moneyapp.ui.home
//
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.TextView
//import androidx.recyclerview.widget.RecyclerView
//import com.example.moneyapp.R
//import com.example.moneyapp.api.models.Bill
//import com.example.moneyapp.api.models.BillsArray
//
//class `BillRecyclerAdapter-samo` : RecyclerView.Adapter<RecyclerView.ViewHolder>()
//{
//    private lateinit var items: BillsArray
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//        return BillViewHolder(
//                LayoutInflater.from(parent.context).inflate(R.layout.bill_item_view, parent, false)
//        )
//    }
//
//    override fun getItemCount(): Int {
//        return items.bills.size
//    }
//
//    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        when(holder){
//            is BillViewHolder ->{
//                holder.bind(items.bills.get(position))
//            }
//        }
//    }
//
//    fun submitList(billList: BillsArray){
//        items = billList
//    }
//
//    class BillViewHolder constructor(
//            itemView: View
//    ): RecyclerView.ViewHolder(itemView){
//        val bill_name: TextView = itemView.findViewById(R.id.bill_name)
//
//
//
//        fun bind(bill: Bill){
//
//            bill_name.setText(bill.name)
//        }
//
//    }
//
//
//
//}