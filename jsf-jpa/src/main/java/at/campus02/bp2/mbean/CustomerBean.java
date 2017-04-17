package at.campus02.bp2.mbean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.primefaces.event.CellEditEvent;

import at.campus02.bp2.model.Customer;
import at.campus02.bp2.utils.EntityManagerFactoryProvider;

@ManagedBean
@SessionScoped
public class CustomerBean {
	
	private EntityManager entityManager;
	
	private Customer newCustomer = new Customer();
	private List<Customer> customerList = new ArrayList<Customer>();
	
	@PostConstruct
	public void createEntityManager() {
		entityManager = EntityManagerFactoryProvider.get().createEntityManager();
	}

	@PreDestroy
	public void closeEntityManager() {
		entityManager.close();
	}
	
	public void loadCustomerFromDB() {
		customerList = entityManager.createQuery("from Customer", Customer.class).getResultList();
	}
	
	public void save() {
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.merge(newCustomer);
		transaction.commit();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Der Kunde " + newCustomer.getFirstName() + " " + newCustomer.getLastName() + " " + newCustomer.getStreet() + " " + newCustomer.getAddress() + " wurde gespeichert"));
	}
	
	public List<Customer> getCustomerList() {
		loadCustomerFromDB();
		return customerList;
	}
	
	public void setCustomerList(List<Customer> customerList) {
		this.customerList = customerList;
	}
	
	public Customer getNewCustomer() {
		return newCustomer;
	}
	public void setNewCustomer(Customer newCustomer) {
		this.newCustomer = newCustomer;
	}
	
	public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();
         
        if(newValue != null && !newValue.equals(oldValue)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

}
