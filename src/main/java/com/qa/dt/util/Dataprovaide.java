package com.qa.dt.util;

import java.util.ArrayList;
import java.util.Iterator;

import org.testng.annotations.DataProvider;

public class Dataprovaide {
	Xls_Reader reader;
	ArrayList<Object[]> myData;
	
	@DataProvider(name = "RegData")
	public Iterator<Object[]> getRegData() {
		myData = readData();
		
		return myData.iterator();
	}
	
	public ArrayList<Object[]> readData() {
		ArrayList<Object[]> myData = new ArrayList<Object[]>();
	
	reader = new Xls_Reader();
	
	for (int rowNum =  2; rowNum <= reader.getRowCount("RegData");rowNum++) {
		String userName = reader.getCellData("RegData", "userName",rowNum);
		String firstName = reader.getCellData("RegData", "firstName",rowNum);
		String lastName = reader.getCellData("RegData", "lastName",rowNum);
		String Password = reader.getCellData("RegData", "Password",rowNum);
		String cPassword = reader.getCellData("RegData", "cPassword",rowNum);
		
		Object ob[]= {userName,firstName,lastName,Password,cPassword};
		myData.add(ob);
	}
	return myData;
	
}
	
}