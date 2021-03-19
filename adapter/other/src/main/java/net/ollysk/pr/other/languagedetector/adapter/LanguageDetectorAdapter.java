package net.ollysk.pr.other.languagedetector.adapter;

import com.google.common.base.Optional;
import com.optimaize.langdetect.LanguageDetector;
import com.optimaize.langdetect.i18n.LdLocale;
import com.optimaize.langdetect.text.CommonTextObjectFactories;
import com.optimaize.langdetect.text.TextObject;
import com.optimaize.langdetect.text.TextObjectFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.ollysk.pr.port.out.LangDetectorPort;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class LanguageDetectorAdapter implements LangDetectorPort {

  private final LanguageDetector languageDetector;

  public int getLangId(String text) {
    TextObjectFactory textObjectFactory = CommonTextObjectFactories.forDetectingOnLargeText();
    TextObject textObject = textObjectFactory.forText(text);
    Optional<LdLocale> lang = languageDetector.detect(textObject);
    int langId = 0;
    if (lang.isPresent()) {
      String langCode = lang.get().getLanguage();
      switch (langCode) {
        case "ru":
          langId = 1;
          break;
        case "uk":
          langId = 2;
          break;
        case "en":
          langId = 3;
          break;
        default:
          log.info("no match");
      }
    }
    return langId;
  }
}
