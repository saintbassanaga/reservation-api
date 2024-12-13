package tech.saintbassanaga.authservice.models.mapped;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Lob;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class Biometric {

  @Lob
  @Column(columnDefinition = "BLOB NOT NULL")
  protected byte[] photo;
  private String idCard;
  private String passportID;

  private String fingerprint;  // Store fingerprint data as a String (e.g., JSON, Base64, etc.)

  @Lob
  @Column(columnDefinition = "BLOB")
  private byte[] signature;    // Store a digital signature as binary data

  @Column(length = 20, unique = true, nullable = false)
  private String uniqueID;     // A unique identifier for this biometric entry

  private String notes;        // Additional notes or metadata
}