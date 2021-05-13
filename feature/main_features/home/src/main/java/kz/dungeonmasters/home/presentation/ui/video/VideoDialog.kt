package kz.dungeonmasters.home.presentation.ui.video

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.*
import android.widget.MediaController
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import kz.dungeonmasters.home.databinding.DialogVideoBinding


class VideoDialog(
    fm: FragmentManager,
    val url: String
) : DialogFragment() {

    init {
        show(fm, "")
    }

    private lateinit var binding: DialogVideoBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogVideoBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = this@VideoDialog.viewLifecycleOwner
        }
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
    }

    private fun initData() {
        with(binding.videoView) {

            val params = layoutParams
            params.width = resources.displayMetrics.widthPixels
            params.height = resources.displayMetrics.heightPixels
            setVideoPath(url)
            start()

            val mediaController = MediaController(requireContext())
            mediaController.setMediaPlayer(this)
            setMediaController(mediaController)
            requestFocus()
        }
    }
}