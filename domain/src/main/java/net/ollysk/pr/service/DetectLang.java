package net.ollysk.pr.service;

import lombok.AllArgsConstructor;
import net.ollysk.pr.port.in.LangDetectorService;
import net.ollysk.pr.port.out.LangDetectorPort;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class DetectLang implements LangDetectorService {

  private final LangDetectorPort langDetector;

  public int getLangId(String text) {
    return langDetector.getLangId(text);
  }
}
