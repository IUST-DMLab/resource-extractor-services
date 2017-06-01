package ir.ac.iust.dml.kg.resource.extractor.services.web.rest;

import io.swagger.annotations.Api;
import ir.ac.iust.dml.kg.resource.extractor.IResourceExtractor;
import ir.ac.iust.dml.kg.resource.extractor.IResourceReader;
import ir.ac.iust.dml.kg.resource.extractor.MatchedResource;
import ir.ac.iust.dml.kg.resource.extractor.ResourceCache;
import ir.ac.iust.dml.kg.resource.extractor.services.Application;
import ir.ac.iust.dml.kg.resource.extractor.tree.TreeResourceExtractor;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/rest/v1/extractor/")
@Api(tags = "extractor", description = "سرویس‌های استخراج رابطه و موجودیت")
public class EntityExtractorRestServices {

    private IResourceExtractor extractor;

    public EntityExtractorRestServices() throws Exception {
        extractor = new TreeResourceExtractor();
//        try (IResourceReader reader = new ResourceReaderFromKGStoreV1Service("http://localhost:8091/")) {
//            extractor.setup(reader, 10000);
//        }
        final String jarFilePath = Application.class.getProtectionDomain()
            .getCodeSource().getLocation().toURI().getPath();
        final Path cacheAddress = Paths.get(jarFilePath).getParent().resolve("cache");
        try (IResourceReader reader = new ResourceCache(cacheAddress.toString(), true)) {
          extractor.setup(reader, 10000);
        }
    }

    @RequestMapping(value = "/extract", method = RequestMethod.GET)
    @ResponseBody
    public List<MatchedResource> search(@RequestParam(required = false) String text) throws Exception {
        return extractor.search(text, true);
    }
}
