package com.dhruti.project_1

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class QuestAdapter(
    private val quests: List<Quest>,
    private val onComplete: (Quest) -> Unit
) : RecyclerView.Adapter<QuestAdapter.QuestViewHolder>() {

    inner class QuestViewHolder(view: android.view.View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.txtQuestTitle)
        val meta: TextView = view.findViewById(R.id.txtQuestMeta)
        val checkBox: CheckBox = view.findViewById(R.id.checkComplete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_quest, parent, false)
        return QuestViewHolder(view)
    }

    override fun onBindViewHolder(holder: QuestViewHolder, position: Int) {
        val quest = quests[position]
        holder.title.text = quest.title
        holder.meta.text = "${quest.category} • ${quest.priority} • +${quest.xpReward}XP"
        holder.checkBox.setOnCheckedChangeListener(null) // avoid recycled-view listener bugs
        holder.checkBox.isChecked = quest.isCompleted
        holder.checkBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) onComplete(quest)
        }
    }

    override fun getItemCount() = quests.size
}