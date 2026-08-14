package com.gof.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.gof.interfaces.EntityIdentifier;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name ="E_IR_CURVE_SPOT_USR")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class IrCurveSpotUsr implements Serializable, EntityIdentifier {	
	
	private static final long serialVersionUID = 8405894865559378104L;
	
	@Id	
	private String baseDate; 
	
	@Id	
	@Column(name ="IR_CURVE_ID")
	private String irCurveId;
	
	@Id
	private String matCd;	
	
	private Double spotRate;		
	private String lastModifiedBy;	
	private LocalDateTime lastUpdateDate;	
	
	@ManyToOne
	@JoinColumn(name ="IR_CURVE_ID", insertable=false, updatable= false)
	private IrCurve irCurve;		
	
    public IrCurveSpot convert() {

        IrCurveSpot spot = new IrCurveSpot();

        spot.setBaseDate(this.baseDate);
        spot.setIrCurveId(this.irCurveId);
        spot.setMatCd(this.matCd);
        
//        spot.setSpotRate(this.spotRate);
        // Continuous Spot → Annual Discrete Spot
        spot.setSpotRate(Math.exp(this.spotRate) - 1.0);
        spot.setLastModifiedBy( "GESG_" + this.getClass().getSimpleName());
        spot.setLastUpdateDate(LocalDateTime.now());

        return spot;
    }
    
}
