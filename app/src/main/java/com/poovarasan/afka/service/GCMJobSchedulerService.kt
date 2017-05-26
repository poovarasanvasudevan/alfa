package com.poovarasan.afka.service

import com.birbit.android.jobqueue.JobManager
import com.birbit.android.jobqueue.scheduling.GcmJobSchedulerService
import com.poovarasan.afka.core.JOB

/**
 * Created by poovarasanv on 24/5/17.
 
 * @author poovarasanv
 * *
 * @project Afka
 * *
 * @on 24/5/17 at 5:49 PM
 */

class GCMJobSchedulerService : GcmJobSchedulerService() {
	override fun getJobManager(): JobManager {
		return JOB
	}
	
}
