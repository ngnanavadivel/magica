package com.codemagic.magica.mapping.operation;

import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class AgeFinder implements Operation {

	public Object operate(Object... args) {
		if(args != null && args.length > 1) {
			String  dob = (String) args[0];
			String pattern = (String)args[1];
			
			ZonedDateTime old = LocalDate.parse(dob, DateTimeFormatter.ofPattern(pattern)).atStartOfDay(ZoneId.systemDefault());
			Duration duration = Duration.between(old, ZonedDateTime.now());
			return Long.valueOf(duration.toDays() / 360).intValue();
		}
		return null;
	}

}
