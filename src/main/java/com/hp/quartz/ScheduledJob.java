package com.hp.quartz;

import org.quartz.JobDetail;
import org.quartz.Trigger;

/**
 * SchedulerJob is an object which holds job's details and trigger object that fires the job
 * @author Halis Ocak
 *
 */

public class ScheduledJob {
	
	private JobDetail jobDetail;
	private Trigger trigger;
	
	public ScheduledJob(JobDetail jobDetail, Trigger trigger){
		this.jobDetail = jobDetail;
		this.trigger = trigger;
	}
	
	public JobDetail getJobDetail() {
		return jobDetail;
	}
	public Trigger getTrigger() {
		return trigger;
	}
	public void setJobDetail(JobDetail jobDetail) {
		this.jobDetail = jobDetail;
	}
	public void setTrigger(Trigger trigger) {
		this.trigger = trigger;
	}
	
	
	
	
	

}
