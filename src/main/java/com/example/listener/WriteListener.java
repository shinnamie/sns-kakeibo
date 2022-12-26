package com.example.listener;

import java.util.List;

import org.springframework.batch.core.ItemWriteListener;
import org.springframework.stereotype.Component;

import com.example.domain.kakeibo.Kakeibo;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class WriteListener implements ItemWriteListener<Kakeibo> {
	
	@Override
	public void beforeWrite(List<? extends Kakeibo> items) {
		
		// Do nothing
	}

	@Override
	public void afterWrite(List<? extends Kakeibo> items) {
		
		log.debug("AfterWrite: count = {}", items.size());
	}

	@Override
	public void onWriteError(Exception exception, List<? extends Kakeibo> items) {
		
		log.error("WriteError: errorMessage = {}", exception.getMessage(), exception);
	}

}
