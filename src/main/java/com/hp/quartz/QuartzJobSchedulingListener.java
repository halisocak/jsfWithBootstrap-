package com.hp.quartz;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.repeatSecondlyForever;
import static org.quartz.TriggerBuilder.newTrigger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.AnnotationUtils;

/**
 * @author 5019hoca
 *
 * 16 May 2017 14:41:52
 */
public class QuartzJobSchedulingListener implements
		ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private Scheduler scheduler;

	public void onApplicationEvent(ContextRefreshedEvent event) {
		try {
			//get context from event
			ApplicationContext applicationContext = event.getApplicationContext();
			//load all the jobs
			List<ScheduledJob> scheduledJobs = this.loadJobs(applicationContext);
			//start to run jobs
			this.scheduleJobs(scheduledJobs);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private List<ScheduledJob> loadJobs(ApplicationContext applicationContext) {

		// get QuartzJob annotated classes from applicationContext
		Map<String, Object> quartzJobBeans = applicationContext.getBeansWithAnnotation(QuartzJob.class);
		Set<String> beanNames = quartzJobBeans.keySet();

		// create triggers map
		List<ScheduledJob> scheduledJobs = new ArrayList<ScheduledJob>();

		JobDetail jobDetail = null;
		Trigger trigger = null;
		
		for (String beanName : beanNames) {
			//getting the job
			Job job = ((Job)quartzJobBeans.get(beanName));
			//getting annotation details of the job
			QuartzJob quartzJobAnnotation = AnnotationUtils.findAnnotation(
					job.getClass(), QuartzJob.class);

			//be sure that your job implements quartz's Job interface 
			if (Job.class.isAssignableFrom(job.getClass())) {
				jobDetail = newJob(job.getClass())
						.withIdentity(quartzJobAnnotation.name(),
								quartzJobAnnotation.group()).build();

				trigger = newTrigger()
						.withIdentity(quartzJobAnnotation.name() + "_trigger",
								quartzJobAnnotation.group())
						.startNow()
						.withSchedule(repeatSecondlyForever(quartzJobAnnotation.interval())).build();
				

			} else {
				throw new RuntimeException(job.getClass() + " doesn't implemented " + Job.class);
			}

			if (jobDetail != null && trigger != null) {
				ScheduledJob schObject = new ScheduledJob(jobDetail, trigger);
				scheduledJobs.add(schObject);
			}
		}
		return scheduledJobs;
	}

	protected void scheduleJobs(List<ScheduledJob> schedulingObjects) {
		try {
			 for(ScheduledJob schObject : schedulingObjects){
				scheduler.scheduleJob(schObject.getJobDetail(),
						schObject.getTrigger());
			}
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
}


