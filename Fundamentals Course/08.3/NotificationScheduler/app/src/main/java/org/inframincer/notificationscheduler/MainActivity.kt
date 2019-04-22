package org.inframincer.notificationscheduler

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast
import android.widget.SeekBar

private const val JOB_ID = 0

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val scheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler

        scheduleJobButton.setOnClickListener { scheduleJob(scheduler) }
        cancelJobsButton.setOnClickListener { cancelJobs(scheduler) }
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                if (i > 0) {
                    seekBarProgress.text = getString(R.string.seconds, i)
                } else {
                    seekBarProgress.setText(R.string.not_set)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}

            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })
    }

    private fun scheduleJob(jobScheduler: JobScheduler?) {
        val selectedNetworkId = networkOptions.checkedRadioButtonId
        val selectedNetworkOption = when (selectedNetworkId) {
            R.id.noNetwork -> JobInfo.NETWORK_TYPE_NONE
            R.id.anyNetwork -> JobInfo.NETWORK_TYPE_ANY
            R.id.wifiNetwork -> JobInfo.NETWORK_TYPE_UNMETERED
            else -> throw IllegalArgumentException()
        }
        val seekBarInteger = seekBar.progress
        val seekBarSet = seekBarInteger > 0

        val serviceName = ComponentName(packageName, NotificationJobService::class.java.name)
        val jobInfoBuilder = JobInfo.Builder(JOB_ID, serviceName).apply {
            setRequiredNetworkType(selectedNetworkOption)
            setRequiresDeviceIdle(idleSwitch.isChecked)
            setRequiresCharging(chargingSwitch.isChecked)
            if (seekBarSet) {
                setOverrideDeadline(seekBarInteger * 1000L)
            }
        }

        val constraintSet = (selectedNetworkOption != JobInfo.NETWORK_TYPE_NONE)
                || idleSwitch.isChecked || chargingSwitch.isChecked
        if (constraintSet) {
            jobScheduler?.let {
                it.schedule(jobInfoBuilder.build())
                toast(R.string.job_scheduled)
            }
        } else {
            toast(R.string.no_constraint_toast)
        }
    }

    private fun cancelJobs(jobScheduler: JobScheduler?) {
        jobScheduler?.let {
            it.cancelAll()
            toast(R.string.jobs_canceled)
            null
        }
    }
}
