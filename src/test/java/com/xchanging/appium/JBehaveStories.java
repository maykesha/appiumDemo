package com.xchanging.appium;

import java.util.Arrays;
import java.util.List;

import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.reporters.Format;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.CandidateSteps;
import org.jbehave.core.steps.InstanceStepsFactory;


public class JBehaveStories extends JUnitStories {
	
	
	
	 public JBehaveStories() {
		 
		 configuredEmbedder().embedderControls().doGenerateViewAfterStories(true).doIgnoreFailureInStories(true)

         .doIgnoreFailureInView(true).useThreads(2).useStoryTimeoutInSecs(5000);

	 }

	
    // Here we specify the configuration, starting from default MostUsefulConfiguration, and changing only what is needed
    @Override
    public Configuration configuration() {
    	
        return new MostUsefulConfiguration()
                // where to find the stories
                .useStoryLoader(new LoadFromClasspath(this.getClass()))
                .useStoryReporterBuilder(  
                        new StoryReporterBuilder()
                        	
                           .withFormats(Format.STATS,Format.HTML));
                
              
    }

    // Here we specify the steps classes
    @Override
    public List<CandidateSteps> candidateSteps() {
        // var args, can have more that one steps classes
        return new InstanceStepsFactory(configuration(), new EnrollmentSteps()).createCandidateSteps();
    }

	@Override
	protected List<String> storyPaths() {
		return Arrays.asList("com//xchanging//stories//Enrollment.story");
		
	}


}