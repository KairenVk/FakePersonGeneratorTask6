package Task6.FakePersonGenerator;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/random", method= RequestMethod.GET)
public class RandomDataEndpoint {

    FakePersonBatch fakePersonBatch = new FakePersonBatch();

    @RequestMapping(value="/persons", method=RequestMethod.GET)
    public JsonNode getRandomPersons(@RequestParam("seed") long seed, @RequestParam("errorsPerRecord") float errorsPerRecord,
                                     @RequestParam("locale") String locale, @RequestParam("page") int page, @RequestParam("limit") int limit) {
        return fakePersonBatch.createFakePersonBatch(seed, errorsPerRecord, locale, page, limit);
    }
}