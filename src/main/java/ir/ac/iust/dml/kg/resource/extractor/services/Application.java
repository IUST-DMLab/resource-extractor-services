package ir.ac.iust.dml.kg.resource.extractor.services;

import ir.ac.iust.dml.kg.raw.utils.ConfigReader;
import ir.ac.iust.dml.kg.resource.extractor.IResourceReader;
import ir.ac.iust.dml.kg.resource.extractor.ResourceCache;
import ir.ac.iust.dml.kg.resource.extractor.readers.ResourceReaderFromVirtuoso;
import ir.ac.iust.dml.kg.resource.extractor.services.web.Jackson2ObjectMapperPrettier;
import ir.ac.iust.dml.kg.resource.extractor.services.web.filter.FilterRegistrationConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@SpringBootApplication
//@ImportResource(value = {})
@EnableAutoConfiguration(exclude = {
    Jackson2ObjectMapperPrettier.class,
    FilterRegistrationConfiguration.class})
@ComponentScan
public class Application {

  public static void main(String[] args) throws Exception {
    final String jarFilePath = Application.class.getProtectionDomain()
        .getCodeSource().getLocation().toURI().getPath();
    final Path cacheAddress = Paths.get(jarFilePath).getParent().resolve("cache");
    if (!Files.exists(cacheAddress)) {
      Files.createDirectories(cacheAddress);
      final ResourceCache cache = new ResourceCache(cacheAddress.toAbsolutePath().toString(),
          true);
      final ConfigReader cfg = ConfigReader.INSTANCE;
      try (IResourceReader reader = new ResourceReaderFromVirtuoso(
          cfg.getString("virtuoso.host", "194.225.227.161"),
          cfg.getString("virtuoso.port", "1111"),
          cfg.getString("virtuoso.user", "dba"),
          cfg.getString("virtuoso.password", "fkgVIRTUOSO2017"),
          cfg.getString("virtuoso.graph", "http://fkg.iust.ac.ir/new"))) {
        cache.cache(reader, 10000);
      }
    }
    SpringApplication.run(Application.class, args);
  }

}
