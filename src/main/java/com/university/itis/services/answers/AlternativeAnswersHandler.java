package com.university.itis.services.answers;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class AlternativeAnswersHandler {

    public AnswerClass extractAnswerClass(String propertyType, String correctAnswer) {

        final Pattern patternInt = Pattern.compile("^-?\\d*(\\d+)?$");
        final Pattern patternFloat = Pattern.compile("^-?\\d*(\\.\\d+)?$");
        final Matcher matcherInt = patternInt.matcher(correctAnswer);
        final Matcher matcherFloat = patternFloat.matcher(correctAnswer);

        if (propertyType.equals("http://www.w3.org/2001/XMLSchema#gYear")) {
            return AnswerClass.YEAR;

        } else if (propertyType.equals("http://www.w3.org/2001/XMLSchema#date")) {
            return AnswerClass.DATE;

        } else if (matcherInt.find()) {
            return AnswerClass.INT;

        } else if (matcherFloat.find()) {
            return AnswerClass.FLOAT;
        } else {
            return AnswerClass.OTHER;
        }
    }

    public List<String> getAlternativeAnswersForClass(AnswerClass answerClass) {
        List<String> result = new ArrayList<>();
        result.add("123");
        result.add("444");
        result.add("343");
        return result;
    }
}
