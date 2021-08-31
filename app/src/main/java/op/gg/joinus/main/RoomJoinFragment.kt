package op.gg.joinus.main

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import op.gg.joinus.R
import op.gg.joinus.databinding.FragmentRoomJoinBinding
import op.gg.joinus.util.getTier
import op.gg.joinus.util.setImg

class RoomJoinFragment(private val item: HomeRoomListItem) : Fragment() {
    private lateinit var binding: FragmentRoomJoinBinding
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //data bind
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_room_join,container,false)
        // set MainActivity toolbar
        setToolbar()
        // initialize View
        initView()
        // set button click listener
        setButton()

        return binding.root
    }
    override fun onDestroy() {
        returnToolbar()
        super.onDestroy()
    }
    private fun returnToolbar(){
        val toolbar = (activity as MainActivity).getBinding().toolbarMain
        toolbar.navigationIcon = null
        toolbar.setNavigationOnClickListener {  }
        (activity as MainActivity).getBinding().toolbarMainTitle.text =""
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun setToolbar(){
        val toolbar = (activity as MainActivity).getBinding().toolbarMain
        toolbar.navigationIcon = resources.getDrawable(R.drawable.ic_toolbar_navigation,resources.newTheme())
        toolbar.setNavigationOnClickListener {
            (activity as MainActivity).supportFragmentManager.popBackStack()
        }
        (activity as MainActivity).getBinding().toolbarMainTitle.text = "매칭 참가"
        (activity as MainActivity).getBinding().toolbarMainTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP,17f)

    }

    private fun initView(){
        // set roomTitle
        binding.txtDialogRoomJoinTitle.text = item.title
        // set room Game Img
        setImg(binding.imgDialogRoomJoinGame.context,item.imgGame,binding.imgDialogRoomJoinGame)
        // set room numOf People
        binding.txtDialogRoomJoinPeople.text = (item.numPeople + " 명")
        // set able Voice
        if (item.room.voice_chat){
            binding.txtDialogRoomJoinVoice.text = "가능"
        }
        else{
            binding.txtDialogRoomJoinVoice.text = "불가능"
        }
        // set tier
        val tier:String = getTier(item.room.game_name,item.room.lowest_tier) + " 이상 " + getTier(item.room.game_name,item.room.highest_tier) + " 이하"
        binding.txtDialogRoomJoinTier.text = tier
        // set Start Date/Time
        val date = item.room.start_date.year.toString() + "년 " + item.room.start_date.month.toString() + "월 " + item.room.start_date.day.toString() + "일 " + item.room.start_date.hours.toString() + "시 " + item.room.start_date.minutes.toString() + "분"
        binding.txtDialogRoomJoinTime.text = date
        // set Joining user
        val layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL, false)
        binding.rcDialogRoomJoin.layoutManager = layoutManager
        val userListAdapter = UserJoinListAdapter(requireContext())
        userListAdapter.itemList = item.room.user_list
        binding.rcDialogRoomJoin.adapter = userListAdapter
    }

    private fun setButton(){
        binding.btnDialogRoomJoin.setOnClickListener {

        }
    }
}