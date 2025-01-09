package test.quartz.quartz.service;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@NoArgsConstructor
@Log4j2
@Service
public class QuartzService implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        log.info("Starting Quartz");
        JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        String url = jobDataMap.getString("url");
        String method = jobDataMap.getString("method");
        String body = jobDataMap.getString("body");
//        Map<String,String> headers = (Map<String,String>) jobDataMap.get("headers");
//        HttpHeaders httpHeaders = new HttpHeaders();
//        if(headers!= null) {
//            headers.putAll(headers);
//        }
        HttpEntity<String> httpEntity = new HttpEntity<>(body);

        RestTemplate template = new RestTemplate();
        template.exchange(URI.create(url), HttpMethod.valueOf(method), httpEntity , String.class);
    }
}
