package com.gof.process;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.gof.dao.IrCurveSpotDao;
import com.gof.entity.IrCurveSpot;
import com.gof.entity.IrCurveSpotUsr;
import com.gof.enums.EJob;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Esg140_ExternalSpot extends Process {	
	
	public static final Esg140_ExternalSpot INSTANCE = new Esg140_ExternalSpot();
	public static final String jobId = INSTANCE.getClass().getSimpleName().toUpperCase().substring(0, ENTITY_LENGTH);
	

	
	public static List<IrCurveSpot> loadSpotFromUsr(String bssd, String irCurveId) {
		
		List<IrCurveSpotUsr> rst = new ArrayList<IrCurveSpotUsr>();
		
		rst = IrCurveSpotDao.getIrCurveSpotUsr(bssd,irCurveId);

		
		log.info("{}({}) loads [{}] External Spot Rate data from [E_IR_CURVE_SPOT_USR] Table. They are inserted into [{}] Table", jobId, EJob.valueOf(jobId).getJobName(), rst.size(), toPhysicalName(IrCurveSpot.class.getSimpleName()));
		
		return rst.stream()
				  .map(s -> s.convert())
                  .collect(Collectors.toList());

	}	

}

