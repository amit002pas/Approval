package com.yodlee.sms.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.inject.Named;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.yodlee.sms.persistence.MaintenanceAudit;

@Named
public class TTRILSMapper {

	private static Logger logger =LoggerFactory.getLogger(TTRILSMapper.class);
	private static String ttrListLocation;

	@Value("${ttr-list-location}")
	public void setTtrListLocation(String ttrListLocation) {
		TTRILSMapper.ttrListLocation = ttrListLocation;
	}

	public static HashMap<String, Integer> ttrMap = new HashMap<String, Integer>();
	public static HashMap<String, Integer> ilsMap = new HashMap<String, Integer>();
	
	SimpleDateFormat sdf1 = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
	SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy HH:mm");

	public void updateTTRILSMapList() {
		if(ttrMap.isEmpty()) {
			FileInputStream inputStream = null;
			try {
				inputStream = new FileInputStream(new File(ttrListLocation));
			} catch (FileNotFoundException e) {
				logger.info(e.getMessage());
			}

			Workbook workbook = null;
			try {
				workbook = new XSSFWorkbook(inputStream);
			} catch (IOException e) {
				logger.info(e.getMessage());
			}
			Sheet sheet = workbook.getSheetAt(0);
			logger.info("no. of rows:"+sheet.getLastRowNum());
			for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {

				Row row = sheet.getRow(rowIndex);
				logger.info("row->"+row.getCell(2).getStringCellValue());
				Cell cell = row.getCell(10);
				if(cell.getStringCellValue().equals("Yes")) {
					ttrMap.put(row.getCell(2).getStringCellValue(), 1);
				}
				cell = row.getCell(9);
				if(cell.getStringCellValue().equals("Yes")) {
					ilsMap.put(row.getCell(2).getStringCellValue(), 1);
				}
			}
		}
	}
	public List<MaintenanceAudit> updateTTRILSSites(List<MaintenanceAudit> auditList){
		List<MaintenanceAudit> updatedAuditList = new ArrayList<MaintenanceAudit>();
		for(MaintenanceAudit maintenanceAudit: auditList) {
			dateFormatter(maintenanceAudit);
			if(ttrMap.get(maintenanceAudit.getMaintenanceSchedule().getAgentName())!=null) {
				maintenanceAudit.setIsTTR(ttrMap.get(maintenanceAudit.getMaintenanceSchedule().getAgentName()));
			}
			else {
				maintenanceAudit.setIsTTR(0);
			}
			if(ilsMap.get(maintenanceAudit.getMaintenanceSchedule().getAgentName())!=null) {
				maintenanceAudit.setIsILS(ilsMap.get(maintenanceAudit.getMaintenanceSchedule().getAgentName()));
			}
			else {
				maintenanceAudit.setIsILS(0);
			}
			updatedAuditList.add(maintenanceAudit);
		}
		return updatedAuditList;
	}
	private void dateFormatter(MaintenanceAudit maintenanceAudit) {
		String dateFromTable = maintenanceAudit.getCreationDate();
		
		Date date=null;
		try {
			date = sdf1.parse(dateFromTable);
		} catch (ParseException e) {
			logger.info("Conversion no possible");
		}
		String dateToShow = sdf2.format(date);
		maintenanceAudit.setCreationDate(dateToShow);
	}
}
