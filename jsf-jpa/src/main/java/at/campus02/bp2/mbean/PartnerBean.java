package at.campus02.bp2.mbean;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.primefaces.component.column.Column;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.CellEditEvent;

import at.campus02.bp2.model.Partner;
import at.campus02.bp2.utils.EntityManagerFactoryProvider;

@ManagedBean
@SessionScoped

public class PartnerBean {
	
	private EntityManager entityManager;
	
	private Partner newPartner = new Partner();
	private List<Partner> partnerList = new ArrayList<Partner>();
	private List<Partner> selectedPartner;

	@PostConstruct
	public void createEntityManager() {
		entityManager = EntityManagerFactoryProvider.get().createEntityManager();
	}

	@PreDestroy
	public void closeEntityManager() {
		entityManager.close();
	}

	public void loadPartnerFromDB(){
		partnerList = entityManager.createQuery("from Partner", Partner.class).getResultList();
	}
	
	public void save() {
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.merge(newPartner);
		transaction.commit();
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Der Partner " + newPartner.getFirmenname() + " wurde hinzugefuegt" ));
	}
	
	public Partner getNewPartner() {
		return newPartner;
	}

	public void setNewPartner(Partner newPartner) {
		this.newPartner = newPartner;
	}

	public List<Partner> getPartnerList() {
		loadPartnerFromDB();
		return partnerList;
	}

	public void setPartnerList(List<Partner> partnerList) {
		this.partnerList = partnerList;
	}
	
	//SelectedPartner auswaehlen und loeschen
	public List<Partner> getSelectedPartner() {
		return selectedPartner;
	}

	public void setSelectedPartner(List<Partner> selectedPartner) {
		//this.selectedPartner.add(selectedPartner);
		this.selectedPartner = selectedPartner;
	}
	
	public void deletePartner() {
		
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		for(Partner u : selectedPartner)
		{
			entityManager.remove(u);
		}
		transaction.commit();
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Der Partner " + newPartner.getFirmenname() + " wurde geloescht" ));
	}
	
    public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();
        
        String currentColumn = ((Column)event.getColumn()).getId();
        
        if(newValue != null && !newValue.equals(oldValue)) {
        	
        	Partner currentPartner = entityManager.find(Partner.class, Integer.valueOf(event.getRowKey()));
        	
        	switch(currentColumn) {
        	case "partnerFirmenname":
            	currentPartner.setFirmenname(newValue.toString());
        		break;
        	case "partnerPremiumstatus":
            	currentPartner.setPremiumstatus(newValue.toString());
        		break;
        	case "partnerStrasse":
            	currentPartner.setStrasse(newValue.toString());
        		break;
        	case "partnerAdresse":
            	currentPartner.setAdresse(newValue.toString());
        		break;
        	case "partnerPlz":
            	currentPartner.setPlz((int)newValue);
        		break;
        	}
        	
    		EntityTransaction transaction = entityManager.getTransaction();
    		transaction.begin();
    		entityManager.merge(currentPartner);
    		transaction.commit();
        	
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
            FacesContext.getCurrentInstance().addMessage(null, msg);   
        }
    }
}
