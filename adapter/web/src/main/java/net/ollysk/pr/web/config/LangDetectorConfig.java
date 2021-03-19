package net.ollysk.pr.web.config;

import com.optimaize.langdetect.LanguageDetector;
import com.optimaize.langdetect.LanguageDetectorBuilder;
import com.optimaize.langdetect.i18n.LdLocale;
import com.optimaize.langdetect.ngram.NgramExtractors;
import com.optimaize.langdetect.profiles.LanguageProfile;
import com.optimaize.langdetect.profiles.LanguageProfileReader;
import java.io.IOException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LangDetectorConfig {

  @Bean
  public LanguageDetector languageDetector() {

    LanguageProfile lpRu = null;
    LanguageProfile lpUk = null;
    LanguageProfile lpEn = null;
    try {
      lpRu = new LanguageProfileReader().readBuiltIn(LdLocale.fromString("ru"));
      lpUk = new LanguageProfileReader().readBuiltIn(LdLocale.fromString("uk"));
      lpEn = new LanguageProfileReader().readBuiltIn(LdLocale.fromString("en"));
    } catch (IOException e) {
      e.printStackTrace();
    }

    return LanguageDetectorBuilder.create(NgramExtractors.standard())
        //.withProfiles(languageProfiles)
        .withProfile(lpRu)
        .withProfile(lpUk)
        .withProfile(lpEn)
        .build();
  }
}
