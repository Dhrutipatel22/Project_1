package com.dhruti.project_1

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BadgeAdapter(private val badges: List<BadgeUiModel>) :
    RecyclerView.Adapter<BadgeAdapter.BadgeViewHolder>() {

    inner class BadgeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val icon: ImageView = view.findViewById(R.id.imgBadgeIcon)
        val name: TextView = view.findViewById(R.id.txtBadgeName)
        val status: TextView = view.findViewById(R.id.txtBadgeStatus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BadgeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_badge, parent, false)
        return BadgeViewHolder(view)
    }

    override fun onBindViewHolder(holder: BadgeViewHolder, position: Int) {
        val item = badges[position]
        holder.name.text = item.badge.name

        if (item.unlocked) {
            holder.icon.setImageResource(android.R.drawable.btn_star_big_on)
            holder.icon.alpha = 1.0f
            holder.status.text = "Unlocked"
            holder.status.setTextColor(Color.parseColor("#22C55E"))
        } else {
            holder.icon.setImageResource(android.R.drawable.ic_secure)
            holder.icon.alpha = 0.4f
            holder.status.text = "Locked"
            holder.status.setTextColor(Color.parseColor("#6B7280"))
        }
    }

    override fun getItemCount() = badges.size
}