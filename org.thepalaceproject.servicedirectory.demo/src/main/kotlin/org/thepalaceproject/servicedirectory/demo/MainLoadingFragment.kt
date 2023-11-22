package org.thepalaceproject.servicedirectory.demo

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import org.thepalaceproject.servicedirectory.boot.ServiceDirectoryStatus
import org.thepalaceproject.servicedirectory.boot.Services

class MainLoadingFragment : Fragment(R.layout.main_loading_fragment) {

  private lateinit var progressText: TextView

  override fun onViewCreated(
    view: View,
    savedInstanceState: Bundle?
  ) {
    super.onViewCreated(view, savedInstanceState)

    this.progressText =
      view.findViewById(R.id.progressText)
  }

  override fun onStart() {
    super.onStart()

    Services.status.observe(this, Observer { status ->
      return@Observer when (status) {
        is ServiceDirectoryStatus.Available -> Unit
        is ServiceDirectoryStatus.Failed -> Unit
        is ServiceDirectoryStatus.Starting -> {
          this.progressText.text = status.message
        }
      }
    })
  }
}
