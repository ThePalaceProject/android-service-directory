package org.thepalaceproject.servicedirectory.demo

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import org.thepalaceproject.servicedirectory.boot.ServiceDirectoryStatus
import org.thepalaceproject.servicedirectory.boot.Services

class MainErrorFragment : Fragment(R.layout.main_error_fragment) {

  private lateinit var textView: TextView

  override fun onViewCreated(
    view: View,
    savedInstanceState: Bundle?
  ) {
    super.onViewCreated(view, savedInstanceState)

    this.textView =
      view.findViewById(R.id.textView)
  }

  override fun onStart() {
    super.onStart()

    Services.status.observe(this, Observer { status ->
      return@Observer when (status) {
        is ServiceDirectoryStatus.Available -> Unit
        is ServiceDirectoryStatus.Failed -> {
          this.textView.text = status.message
        }
        is ServiceDirectoryStatus.Starting -> Unit
      }
    })
  }
}
