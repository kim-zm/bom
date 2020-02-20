package com.web.boot.service;

import com.web.boot.domain.Search;

public interface BookService {
	public Search findBookName(String app_key, Search search, String searchType, String searchValue);

}
