package at.campus02.bp2.mbean;


import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

import at.campus02.bp2.model.Partner;
import at.campus02.bp2.mbean.PartnerBean;

import org.primefaces.model.chart.Axis;

@ManagedBean
public class ChartView implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BarChartModel animatedModel;
	
	PartnerBean bean = new PartnerBean();
	
	private int counterJa = bean.getCounterJa();
	private int counterNein = bean.getCounterNein();
	private int countgesamt = counterJa+counterNein;

	@PostConstruct
	public void init() {
		createAnimatedModels();
	}

	public BarChartModel getAnimatedModel() {
		return animatedModel;
	}

	private void createAnimatedModels() {
		animatedModel = initBarModel();
		
		animatedModel.setTitle("Premiumstatus");
		animatedModel.setAnimate(true);
		animatedModel.setLegendPosition("ne");
		Axis yAxis = animatedModel.getAxis(AxisType.Y);
		yAxis.setMin(0);
		yAxis.setMax(countgesamt);
	}

	private BarChartModel initBarModel() {
		BarChartModel model = new BarChartModel();
		
		ChartSeries ja = new ChartSeries();
		ja.setLabel("Ja");
		ja.set("Status", counterJa);
		
		ChartSeries nein = new ChartSeries();
		nein.setLabel("Nein");
		nein.set("Status", counterNein);
		
		model.addSeries(ja);
		model.addSeries(nein);
		
		return model;
	}
	
}
