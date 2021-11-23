package com.stringops.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.stringops.model.StringModel;
import com.stringops.service.StringPalindrome;
import com.stringops.service.StringPatternMatch;

@RestController
public class StringOpsController {
	
	@Autowired
	private StringPalindrome strPalinSvc;
	
	@Autowired
	private StringPatternMatch strPtnMchSvc;
	
	@PostMapping("/strPalindromCheck")
	public String strPalindromCheck(@RequestBody StringModel strModel) {
		return strPalinSvc.palindromeCheck(strModel.getOriginalStr());
	}
	
	@PostMapping("/strPatternMatch")
	public String strPatternMatch(@RequestBody StringModel strModel) {
		return strPtnMchSvc.matchingindex(strModel);
	}

}
