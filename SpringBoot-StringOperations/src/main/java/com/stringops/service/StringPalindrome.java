package com.stringops.service;

import java.util.stream.IntStream;

import org.springframework.stereotype.Service;

@Service
public class StringPalindrome {

	public String palindromeCheck(String originalStr) {

		if (isPalindrome(originalStr))
			return "The given string is Palindrome";
		else
			return "The given string is not Palindrome";
	}

	public boolean isPalindrome(String originalStr) {
		return IntStream.range(0, originalStr.length() / 2)
				.allMatch(i -> originalStr.charAt(i) == originalStr.charAt(originalStr.length() - 1 - i));
	}

}
