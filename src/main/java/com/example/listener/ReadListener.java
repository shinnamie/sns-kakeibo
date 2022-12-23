package com.example.listener;

import org.springframework.batch.core.ItemReadListener;
import org.springframework.stereotype.Component;

import com.example.domain.kakeibo.Kakeibo;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ReadListener implements ItemReadListener<Kakeibo> {
	
	@Override
	public void beforeRead() {
		
		// Do nothing
	}

	@Override
	public void afterRead(Kakeibo item) {
		
		log.debug("AfterRead: {}", item);
	}

	@Override
	public void onReadError(Exception ex) {
		
		log.error("ReadError: {}", ex.getMessage(), ex);
	}

}
