package org.paguerre.fqoperation.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class Messenger {

	public String getMessage(String[]... messages) {
		if (ArrayUtils.isNotEmpty(messages)) {
			Set<String> phrases = new LinkedHashSet<>();
			List<List<String>> msgsList = new ArrayList<>();
			Arrays.stream(messages).forEach(arr -> {
				List<String> msgList = Arrays.asList(arr);
				phrases.addAll(msgList);
				msgsList.add(msgList);
			});
			phrases.remove(StringUtils.EMPTY);

		}
		throw new IllegalArgumentException("all messagers are null or empty");
	}

	public static void main(String[] args) {
		Messenger msg = new Messenger();
		String[] m1 = { "este", "", "", "mensaje", "" };
		String[] m2 = { "", "es", "", "", "secreto" };
		String[] m3 = { "este", "", "un", "", "" };
		System.out.println(msg.getMessage(m1, m2, m3));
	}
}
