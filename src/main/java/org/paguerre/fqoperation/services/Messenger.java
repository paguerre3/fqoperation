package org.paguerre.fqoperation.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class Messenger {

	public String getMessage(String[]... messages) {
		if (messages != null) {
			Map<String, Integer> phrasesToOrder = new HashMap<>();
			List<List<String>> msgsList = new ArrayList<>();
			List<Integer> msgsSizes = new ArrayList<>();
			Arrays.stream(messages).filter(arr0 -> ArrayUtils.isNotEmpty(arr0)).forEach(arr -> {
				List<String> msgList = Arrays.asList(arr);
				msgsList.add(msgList);
				msgsSizes.add(msgList.size());
			});
			final int maxMessageSize = Collections.max(msgsSizes);
			msgsList.stream().forEach(msgList -> msgList.stream().forEach(msg -> {
				// don't want to filter.stream twice as it mast
				// be done in sequence:
				if (StringUtils.isNotBlank(msg)) {
					int index = msgList.indexOf(msg);
					if (msgList.size() < maxMessageSize) {
						index++;
					}
					Integer indexToCompare = phrasesToOrder.get(msg);
					if (indexToCompare == null || (index < indexToCompare)) {
						phrasesToOrder.put(msg, index);
					}
				}
			}));
			// ordered phrases by lowest index
			Map<String, Integer> sortedPhrases = phrasesToOrder.entrySet().stream()
					.sorted(Map.Entry.<String, Integer>comparingByValue()).collect(Collectors.toMap(Map.Entry::getKey,
							Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
			StringBuilder sb = new StringBuilder();
			sortedPhrases.keySet().stream().forEach(ph -> {
				sb.append(ph);
				sb.append(StringUtils.SPACE);
			});
			if (sb.length() > 0)
				sb.deleteCharAt(sb.length() - 1);
			return sb.toString();
		}
		throw new IllegalArgumentException("All messagers are null.");
	}
}
