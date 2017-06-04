package at.campus02.bp2.mbean;


import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

import org.primefaces.model.chart.MeterGaugeChartModel;

import at.campus02.bp2.mbean.PartnerBean;

@ManagedBean
public class ChartViewUser implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MeterGaugeChartModel meterGaugeModel;
	
	UserBean user = new UserBean();

	@PostConstruct
	public void init() {
		createAnimatedModels();
	}

	public MeterGaugeChartModel getMeterGaugeModel() {
		return meterGaugeModel;
	}
	
	private MeterGaugeChartModel initMeterGaugeModel() {
		List<Number> intervals = new ArrayList<Number>() {{
			add(10);
			add(40);
			add(60);
			add(100);
		}};
		return new MeterGaugeChartModel(user.getUserGesamt(), intervals);
	}

	private void createAnimatedModels() {
		meterGaugeModel = initMeterGaugeModel();
		meterGaugeModel.setTitle("Useranzahl "+ user.getUserGesamt());
		meterGaugeModel.setGaugeLabel("User");
		meterGaugeModel.setSeriesColors("cc6666, e7e658, 93b75f, 69cc69");
		meterGaugeModel.setGaugeLabelPosition("bottom");
	}

	
	
}
