package com.ngn.utm.manager.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ParsingStringUtil {
	public static Optional<List<String>> parsingSystemLog(String line){
		String partern="([a-zA-Z]{3}\\ +[0-9]{1,2}\\ +[0-9]{2}:[0-9]{2}:[0-9]{2})\\ +(.*?)\\ +(.*?)(?::\\ +)?(?:\\[([0-9:]*)\\])?:?\\ +(.*)";
		return Optional.ofNullable(parsingLogs(partern, line));
	}
	
	public static Optional<List<String>> parsingGatewayLog(String line){
		String partern="([a-zA-Z]{3}\\ +[0-9]{1,2}\\ +[0-9]{2}:[0-9]{2}:[0-9]{2})\\ +(.*?)\\ +(.*?)(?::\\ +)?(?:\\[([0-9:]*)\\])?:?\\ +(.*)";
		return Optional.ofNullable(parsingLogs(partern, line));
	}
	
	public static Optional<List<String>> parsingFirewallLog(String line){
		String partern="(.*)\\s(.*)\\sfilterlog\\[[0-9]+\\]:\\s(.*)";
		return Optional.ofNullable(parsingLogs(partern, line));
	}
	
	private static List<String> parsingLogs(String partern, String line){
		try {
			List<List<String>> matches = Pattern.compile(partern,  Pattern.DOTALL)
		            .matcher(line)
		            .results()
		            .map(mr -> {
		                List<String> list = new ArrayList<>();
		                int bound = mr.groupCount();
		                for (int i = 0; i <= bound; i++) {
		                    String group = mr.group(i);
		                    list.add(group);
		                }
		                return list;
		            }).collect(Collectors.toList());
			if(matches.size()>0) {
				return matches.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
