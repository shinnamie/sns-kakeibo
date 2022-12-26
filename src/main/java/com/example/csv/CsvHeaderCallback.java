package com.example.csv;

import java.io.IOException;
import java.io.Writer;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileHeaderCallback;
import org.springframework.stereotype.Component;

@Component
@StepScope
public class CsvHeaderCallback implements FlatFileHeaderCallback {

	@Override
	public void writeHeader(Writer writer) throws IOException {
		
		writer.write("決済日付, 費目, 支出金額, 収入金額, 決済, 利用店舗, 備考, 登録日");
	}

}
