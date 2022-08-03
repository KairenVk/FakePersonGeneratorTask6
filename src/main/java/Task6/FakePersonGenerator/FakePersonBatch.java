package Task6.FakePersonGenerator;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import net.datafaker.Faker;
import java.util.Locale;
import java.util.Random;


public class FakePersonBatch {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public JsonNode createFakePersonBatch(long seed, float errorsPerRecord, String locale, int page, int limit) {

        long seedPage = seed + page;
        int index = page*10;

        Faker faker = new Faker(new Locale(locale), new Random(seedPage));
        ArrayNode persons = objectMapper.createArrayNode();

        for (int i = 1; i <= limit; i++) {
            String name = faker.name().nameWithMiddle();
            String address = faker.address().streetAddress();
            String phone = faker.phoneNumber().phoneNumber();
            String[] fieldsWithErrors = new String[] {name, address, phone};
            fieldsWithErrors = createErrorsPerRecord(fieldsWithErrors, errorsPerRecord, seedPage, i);
            persons.add(objectMapper.createObjectNode()
                    .put("index", index + i)
                    .put("identifier", faker.random().nextInt(1, Integer.MAX_VALUE))
                    .put("name", fieldsWithErrors[0])
                    .put("address", fieldsWithErrors[1])
                    .put("phone", fieldsWithErrors[2]));
        }
        index += limit;
        return persons;
    }

    public String[] createErrorsPerRecord(String[] fieldsWithErrors, float errorsPerRecord, long seed, int record) {

        if (record % 2 == 0 && errorsPerRecord % 1 != 0) {
            errorsPerRecord += 0.5;
        }
        else if (errorsPerRecord % 1 != 0){
            errorsPerRecord -= 0.5;
        }

        Random random = new Random(seed);
        StringBuilder sb = new StringBuilder();
        int selectedValue = 0;

        for(int i = 0; i < errorsPerRecord; i++) {
            int randValue = random.nextInt(0,3);
            switch (randValue) {
                case 0 -> {
                    sb.append(fieldsWithErrors[0]);
                    selectedValue = 0;
                }
                case 1 -> {
                    sb.append(fieldsWithErrors[1]);
                    selectedValue = 1;
                }
                case 2 -> {
                    sb.append(fieldsWithErrors[2]);
                    selectedValue = 2;
                }
            }
            randValue = random.nextInt(0,3);
            switch(randValue) {
                case 0:
                    if (sb.length() > 0) {
                        randValue = random.nextInt(0, sb.length());
                        sb.deleteCharAt(randValue);
                    }
                    break;
                case 1:
                    if (sb.length() > 0) {
                        randValue = random.nextInt(0, sb.length()+1);
                    }
                    else {
                        randValue = 0;
                    }
                    char randChar;
                    if (selectedValue != 2) {
                        randChar = (char) (random.nextInt(26) + 'a');
                    }
                    else {
                        randChar = (char) (random.nextInt(10) + '0');
                    }
                    sb.insert(randValue, randChar);
                    break;
                case 2:
                    if (sb.length() > 1) {
                        randValue = random.nextInt(0, sb.length());
                        char temp = sb.charAt((randValue));
                        if (randValue > 0) {
                            sb.setCharAt(randValue, sb.charAt(randValue - 1));
                            sb.setCharAt(randValue - 1, temp);
                        }
                        else {
                            sb.setCharAt(randValue, sb.charAt(randValue+1));
                            sb.setCharAt(randValue+1, temp);
                        }
                    }
                    break;
            }
            fieldsWithErrors[selectedValue] = sb.toString();
            sb.setLength(0);
        }
        return fieldsWithErrors;
    }

}