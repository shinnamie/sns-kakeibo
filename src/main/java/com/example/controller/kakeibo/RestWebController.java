package com.example.controller.kakeibo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.domain.kakeibo.CalendarEvent;
import com.example.service.kakeibo.KakeiboService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api/event")
public class RestWebController {

	@Autowired
	KakeiboService kakeiboService;

	@GetMapping(value = "/all")
	public String getDailySummaries() {

		String jsonMsg = null;

		try {
			List<CalendarEvent> events = new ArrayList<CalendarEvent>();
			CalendarEvent event = new CalendarEvent();
			event.setTitle("first event");
			event.setStart("2022-10-01");
			events.add(event);

			event = new CalendarEvent();
			event.setTitle("second event");
			event.setStart("2022-10-11");
			event.setEnd("2022-10-12");
			events.add(event);

			// FullCalendarにエンコード済み文字列を渡す
			ObjectMapper mapper = new ObjectMapper();
			jsonMsg = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(events);

		} catch (IOException ioex) {
			System.out.println(ioex.getMessage());
		}

		return jsonMsg;
	}

}
