package com.stringops.service;

import org.springframework.stereotype.Service;

import com.stringops.model.StringModel;

@Service
public class StringPatternMatch {

	public String matchingindex(StringModel strModel) {

		String originalStr = strModel.getOriginalStr();
		String patternStr = strModel.getPatternStr();
		StringBuilder resultStrBuilder = new StringBuilder();
		int orgStrLen = originalStr.length();
		int ptnStrLen = patternStr.length();

		for (int i = 0; i < orgStrLen; i++) {
			if (i <= (orgStrLen - ptnStrLen)) {
				String tempStr = originalStr.substring(i, i + ptnStrLen);
				if (tempStr.equals(patternStr)) {
					resultStrBuilder.append(i);
					resultStrBuilder.append(" ");
				}
			}
		}
		return "Pattern found at\n" + resultStrBuilder.toString();
	}

}
