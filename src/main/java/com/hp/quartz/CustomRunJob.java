package com.hp.quartz;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

@DisallowConcurrentExecution
@QuartzJob(name = "customRunJob", interval = 60) // 1dk
@Component("customRunJob")
public class CustomRunJob implements Job {
	private static final Logger logger = Logger.getLogger(CustomRunJob.class);

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		// TODO : run any

		System.out.println(CustomRunJob.class.getName() + " " + new Date() + " calisti.");

	}

}
