package com.web.boot.service.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.web.boot.domain.Search;
import com.web.boot.service.BookService;

@Service
public class BookServiceImpl implements BookService{
	
	private static final String API_SERVER_HOST = "https://dapi.kakao.com/";
	private static final String BOOK_PATH = "v3/search/book";
	
	@Override
	public Search findBookName(String app_key, Search search, String searchType, String searchValue) {
		String reqURL = API_SERVER_HOST + BOOK_PATH;
		//Search search = null;
		
        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("GET");            
            conn.setRequestProperty("Host", reqURL);
            conn.setRequestProperty("Authorization", "KakaoAK "+app_key);
            
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("target="+searchType);
            sb.append("&query="+searchValue);
            sb.append("&page="+search.getPage());
            bw.write(sb.toString());
            bw.flush();
            
            int responseCode = conn.getResponseCode();
            //System.out.println("responseCode : " + responseCode);
 
            if(responseCode == 200) {
            	//search = new Search();
            	search.initPageAndSize(search.getPage(), 10);
            	search.setResponseCode(responseCode);
            	
            	BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line = "";
                String result = "";
                
                while ((line = br.readLine()) != null) {
                    result += line;
                }
                //System.out.println("response body : " + result);
                
                JsonParser parser = new JsonParser();
                JsonElement element = parser.parse(result);
                
                JsonElement meta = element.getAsJsonObject().get("meta");
                //System.out.println("meta : " + meta.isJsonNull());
                //System.out.println("meta : " + meta.isJsonArray());
                if(!meta.isJsonNull()) {                	
                	boolean isEnd = meta.getAsJsonObject().get("is_end").getAsBoolean();
                	int pageableCount = meta.getAsJsonObject().get("pageable_count").getAsInt();
                	int totalCount = meta.getAsJsonObject().get("total_count").getAsInt();
                	search.setIsEnd(isEnd);
                	search.setPageableCount(pageableCount);
                	search.setTotalCount(totalCount);
                	//System.out.println("isEnd : " + isEnd);
                	//System.out.println("pageableCount : " + pageableCount);
                	//System.out.println("totalCount : " + totalCount);
                }
                
                JsonElement documents = element.getAsJsonObject().get("documents");
                //System.out.println("documents : " + documents.isJsonNull());
                //System.out.println("documents : " + documents.isJsonArray());
                if(!documents.isJsonNull()) {
                	if(documents.isJsonArray()) {
                		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
                		JsonArray arr = documents.getAsJsonArray();
                		if(arr.size() > 0){
	                		arr.forEach(i->{
	                			JsonObject obj = i.getAsJsonObject();
	                			Iterator<String> itr = obj.keySet().iterator();
	                			Map<String, Object> book = new HashMap<String, Object>();
	                			//Book book = new Book();
	                			while(itr.hasNext()){
	                				String key = itr.next();
	                				Object value;
	                				if(key.contains("authors")) value = obj.get(key).getAsJsonArray();
	                				else if(key.contains("translators")) value = obj.get(key).getAsJsonArray();
	                				else if(key.contains("price")) value = obj.get(key).getAsInt();
	                				else if(key.contains("sale_price")) value = obj.get(key).getAsInt();
	                				else value = obj.get(key).getAsString();
	                				book.put(key, value);
	                			}
	                			list.add(book);
	                			//System.out.println(" ========== "+list.size());
	                		});
	                		search.setList(list);
                		}
                	}
                }
                
                //System.out.println("meta : " + meta);
                //System.out.println("documents : " + documents);
                
                br.close();            	
            }
            
            bw.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
        
        return search;
	}

}
