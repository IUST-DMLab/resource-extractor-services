package ir.ac.iust.dml.kg.resource.extractor.services.web.rest;

import io.swagger.annotations.Api;
import ir.ac.iust.dml.kg.resource.extractor.IResourceExtractor;
import ir.ac.iust.dml.kg.resource.extractor.IResourceReader;
import ir.ac.iust.dml.kg.resource.extractor.MatchedResource;
import ir.ac.iust.dml.kg.resource.extractor.readers.ResourceReaderFromKGStoreV1Service;
import ir.ac.iust.dml.kg.resource.extractor.tree.TreeResourceExtractor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/v1/extractor/")
@Api(tags = "extractor", description = "سرویس‌های استخراج رابطه و موجودیت")
public class EntityExtractorRestServices {

    private IResourceExtractor extractor;

    public EntityExtractorRestServices() throws Exception {
        extractor = new TreeResourceExtractor();
        try (IResourceReader reader = new ResourceReaderFromKGStoreV1Service("http://localhost:8091/")) {
            extractor.setup(reader, 10000);
        }
    }

    @RequestMapping(value = "/extract", method = RequestMethod.GET)
    @ResponseBody
    public List<MatchedResource> search(@RequestParam(required = false) String text) throws Exception {
        return extractor.search(text, true);
    }
}
