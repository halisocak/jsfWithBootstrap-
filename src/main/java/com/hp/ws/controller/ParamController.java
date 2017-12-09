package com.hp.ws.controller;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.quartz.JobExecutionContext;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hp.ws.cache.AppParameterCache;
import com.hp.ws.constant.RequestMappingConstants;
import com.hp.ws.datasource.dao.PersonDAO;
import com.hp.ws.datasource.entity.AppParameter;
import com.hp.ws.exception.WsException;
import com.hp.ws.message.Response;

/**
 * @author 5019hoca
 *
 * 16 May 2017 14:44:52
 */
@Controller
public class ParamController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(BaseController.class);
	@Autowired
	AppParameterCache appParameterCache;
	@Autowired
	PersonDAO  personDAO;
	
	
	
	@Autowired
	ApplicationContext appContext;

	@RequestMapping(value = RequestMappingConstants.GET_SERVER_PARAMETERS, method = RequestMethod.GET)
	public @ResponseBody Response serverParameters() throws WsException {
		List<AppParameter> appParameters = new ArrayList<AppParameter>();
		appParameters = appParameterCache.getAllParameters();
		return getResponse(appParameters);

	}
	
	@RequestMapping(value = RequestMappingConstants.HOME_PAGE, method = RequestMethod.GET)
	public String home(Locale locale, Model model, HttpServletRequest request) {
		logger.debug("Welcome home! the client locale is "+ locale.toString());
		Scheduler scheduler = appContext.getBean(Scheduler.class);
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		personDAO.listPersons();
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		String jobDetail ="";
		try {
			List<JobExecutionContext>  executionContexts = scheduler.getCurrentlyExecutingJobs();
			for (JobExecutionContext jobExecutionContext : executionContexts) {
				 jobDetail = jobExecutionContext.getJobInstance().getClass().getName();
				 jobDetail =jobDetail +" getScheduledFireTime:" +jobExecutionContext.getScheduledFireTime()+
						 " getFireTime:" +jobExecutionContext.getFireTime()+
						 " getNextFireTime:" +jobExecutionContext.getNextFireTime()+
						 " getPreviousFireTime:" +jobExecutionContext.getPreviousFireTime()+"<br>";
			}
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("jobDetail", jobDetail );
		return "index";
	}
	
}
