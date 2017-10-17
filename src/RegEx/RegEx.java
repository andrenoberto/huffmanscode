package RegEx;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;


public class RegEx {
    private String manipulableText;
    //Not sure if we'll need to retrieve the original content but anyway it's stored over down here
    //private String textIn;
    private StringBuilder textOut = new StringBuilder();
    private Pattern pPattern;

    public RegEx(String text, String pattern) {
        //this.textIn = text;
        this.manipulableText = text;
        pPattern = Pattern.compile(pattern);
    }

    public String regExToERL() {
        while (this.manipulableText.length() > 0) {
            this.findNextFullMatch();
        }
        return textOut.toString();
    }

    private void findNextFullMatch() {
        Matcher matcher = pPattern.matcher(this.manipulableText);
        if (matcher.find()) {
            if (matcher.start() == 0 && matcher.group().length() > 3) {
                this.validateMatchGroup(matcher.group());
            } else {
                validateMatchGroup(Character.toString(this.manipulableText.charAt(0)));
            }
        } else if (this.manipulableText.length() > 0) {
            validateMatchGroup(Character.toString(this.manipulableText.charAt(0)));
        }
        matcher.reset();
    }

    private void validateMatchGroup(String matchString) {
        Integer subStringLength = -1;
        if (matchString.length() > 3) {
            StringBuilder textOut = new StringBuilder();

            //Pattern pattern = Pattern.compile("([\\S\\d]{1,}?)\\1");
            Pattern pattern = Pattern.compile("([\\S\\d]+?)\\1");
            Matcher matcher = pattern.matcher(matchString);
            if (matcher.find()) {
                for (int i = 0; i < (matcher.end() / 2); i++) {
                    textOut.append(Character.toString(matcher.group().charAt(i)));
                }
            }
            matcher.reset();

            Integer compressionFactor = matchString.length() / textOut.length();
            Integer matchStringNumbersAmount = Integer.toString(compressionFactor).length();

            if ((textOut.length() + 3 + matchStringNumbersAmount) < matchString.length()) {
                this.textOut.append("!").append(compressionFactor).append("&").append(textOut).append("$");
            } else {
                this.textOut.append(matchString.charAt(0));
                subStringLength = 0;
            }
        } else {
            this.textOut.append(matchString.charAt(0));
            subStringLength = 0;
        }

        subStringLength = subStringLength == 0 ? 1 : matchString.length();
        this.manipulableText = this.manipulableText.substring(subStringLength);
    }

    public String rleDecompress(String string) {
        int beginningFlag = 0;
        int endingFlag = 0;
        int r = 0;

        StringBuilder decompressedString = new StringBuilder();
        StringBuilder stringToRepeat = new StringBuilder();
        String blockOfString = "";
        for (int i = 0; i < string.length(); i++) {
            if (string.charAt(i) == '!') {
                beginningFlag = (beginningFlag == 0) ? 1 : 0;
            } else {
                if (beginningFlag == 1) {
                    if (string.charAt(i) != '&') {
                        stringToRepeat.append(string.charAt(i));
                    } else {
                        r = parseInt(stringToRepeat.toString());
                        stringToRepeat = new StringBuilder();
                        beginningFlag = 0;
                        endingFlag = 1;
                    }
                } else {
                    if (endingFlag == 1) {
                        if (string.charAt(i) != '$') {
                            //parte = parte + string.charAt(i);
                            blockOfString += string.charAt(i);
                        } else {
                            for (int j = 0; j < r; j++) {
                                decompressedString.append(blockOfString);
                            }
                            r = 0;
                            beginningFlag = 0;
                            endingFlag = 0;
                            blockOfString = "";
                        }
                    } else {
                        decompressedString.append(string.charAt(i));
                    }
                }
            }
        }
        return decompressedString.toString();
    }
}