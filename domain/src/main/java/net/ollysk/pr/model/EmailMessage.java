package net.ollysk.pr.model;

import java.util.Date;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class EmailMessage {

  private String from;
  private String replyTo;
  private String[] to;
  private String[] cc;
  private String[] bcc;
  private Date sentDate;
  private String subject;
  private String text;
}
