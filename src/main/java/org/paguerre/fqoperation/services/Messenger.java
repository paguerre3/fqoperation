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
import org.apache.commons.lang3.tuple.MutablePair;
import org.springframework.stereotype.Service;

@Service
public class Messenger {

	public String getMessage(String[]... messages) {
		if (messages != null) {
			// distinct phrases are here:
			Map<String, Integer> phrasesToOrder = new HashMap<>();
			// original message ready to be streamed:
			List<List<String>> msgsList = new ArrayList<>();
			// message sizes are needed for calculating most frequent size:
			List<Integer> msgsSizes = new ArrayList<>();
			Arrays.stream(messages).filter(arr0 -> ArrayUtils.isNotEmpty(arr0)).forEach(arr -> {
				List<String> msgList = Arrays.asList(arr);
				msgsList.add(msgList);
				msgsSizes.add(msgList.size());
			});
			// format gaps are reduced by message size:
			MutablePair<Integer, Integer> mostFrequentSizes = new MutablePair<>(0, 0);
			msgsSizes.forEach(ms -> {
				int frequency = Collections.frequency(msgsSizes, ms);
				if (frequency > mostFrequentSizes.getRight()) {
					mostFrequentSizes.setLeft(ms);
					mostFrequentSizes.setRight(frequency);
				}
			});
			int mostFrequentSize = mostFrequentSizes.getLeft();
			msgsList.stream().forEach(msgList -> msgList.stream().forEach(msg -> {
				// don't want to filter.stream twice as it must
				// be done in sequence:
				if (StringUtils.isNotBlank(msg)) {
					int index = msgList.indexOf(msg);
					if (msgList.size() != mostFrequentSize) {
						index++;
					}
					Integer indexToCompare = phrasesToOrder.get(msg);
					if (indexToCompare == null || (index < indexToCompare)) {
						phrasesToOrder.put(msg, index);
					}
				}
			}));
			// finally, orderer phrases by lowest index:
			Map<String, Integer> sortedPhrases = phrasesToOrder.entrySet().stream()
					.sorted(Map.Entry.<String, Integer>comparingByValue()).collect(Collectors.toMap(Map.Entry::getKey,
							Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
			return sortedPhrases.keySet().stream().collect(Collectors.joining(StringUtils.SPACE)).toString();
		}
		throw new IllegalArgumentException("All messagers are null.");
	}
}
