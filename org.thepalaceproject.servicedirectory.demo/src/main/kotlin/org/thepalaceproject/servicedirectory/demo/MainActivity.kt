package org.thepalaceproject.servicedirectory.demo

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import org.thepalaceproject.servicedirectory.boot.ServiceDirectoryStatus
import org.thepalaceproject.servicedirectory.boot.Services

/**
 * The main activity's sole responsibility is to perform screen transitions. A loading screen
 * fragment is shown if, at any point, the service directory has not started up. Fragments are
 * required to be stateless and are unceremoniously deleted and replaced every time this activity
 * starts up.
 */

class MainActivity : AppCompatActivity(R.layout.main) {

  override fun onStart() {
    super.onStart()

    val mainFragment = MainLoadingFragment()
    this.supportFragmentManager.beginTransaction()
      .replace(R.id.mainFragmentHolder, mainFragment, "MAIN")
      .commit()

    Services.status.observe(this, Observer { status ->
      return@Observer when (status) {
        is ServiceDirectoryStatus.Available -> {
          this.supportFragmentManager.beginTransaction()
            .replace(R.id.mainFragmentHolder, MainFragment(), "MAIN")
            .commit()
          Unit
        }
        is ServiceDirectoryStatus.Failed -> Unit
        is ServiceDirectoryStatus.Starting -> Unit
      }
    })
  }
}
