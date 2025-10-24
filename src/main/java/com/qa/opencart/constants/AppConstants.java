package com.qa.opencart.constants;

import java.util.List;

public class AppConstants {
	
	public static final String ACCOUNT_PAGE_TITLE="My Account";
	public static final int DEFAULT_TIMEOUT=5;
	public static final int MEDIUM_DEFAULT_TIMEOUT=10;
	public static final int LONG_DEFAULT_TIMEOUT=20;
	public static final String LOGIN_PAGE_TITLE="Account Login";
	public static final String LOGIN_PAGE_FRACTION_URL="?route=account/login";
	public static final String ACCOUNT_PAGE_FRACTION_URL="?route=account/account";
			
	public static final String SEARCH_RESULT_FRACTION_TITLE="Search";
	public static  List<String> headersList=List.of("My Account", "My Orders","My Affiliate Account","Newsletter");
	public static final String PRODUCT_INFO_PAGE_FRACTION_URL="?route=product/product";
	public static final String REGISTRATION_PAGE_FRACTION_URL="?route=account/register";
	public static final String REGISTER_SUCCESS_FRACTION_TEXT="Your Account Has Been Created!";
}
