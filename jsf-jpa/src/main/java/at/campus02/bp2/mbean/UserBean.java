package at.campus02.bp2.mbean;


import java.util.ArrayList;
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

import at.campus02.bp2.model.User;
import at.campus02.bp2.utils.EntityManagerFactoryProvider;

@ManagedBean
@SessionScoped

public class UserBean {
	
	private EntityManager entityManager;
	
	private User newUser = new User();
	private List<User> userList = new ArrayList<User>();
	private List<User> selectedUser;

	@PostConstruct
	public void createEntityManager() {
		entityManager = EntityManagerFactoryProvider.get().createEntityManager();
	}

	@PreDestroy
	public void closeEntityManager() {
		entityManager.close();
	}
	
	public void loadUserFromDB(){
		userList = entityManager.createQuery("from User", User.class).getResultList();
	}
	
	public void save() {
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.merge(newUser);
		transaction.commit();
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Der User " + newUser.getNachname() + " wurde hinzugefuegt" ));
	}
	
	public User getNewUser() {
		return newUser;
	}

	public void setNewUser(User newUser) {
		this.newUser = newUser;
	}

	public List<User> getUserList() {
		loadUserFromDB();
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}
	
	//SelectedUser auswaehlen und loeschen
	public List<User> getSelectedUser() {
		return selectedUser;
	}

	public void setSelectedUser(List<User> selectedUser) {
		//this.selectedUser.add(selectedUser);
		this.selectedUser = selectedUser;
	}
	
	public void deleteUser() {
		
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		for(User u : selectedUser)
		{
			entityManager.remove(u);
		}
		transaction.commit();
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Der User " + newUser.getNachname() + " wurde geloescht" ));
	}
	
    public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();
        
        String currentColumn = ((Column)event.getColumn()).getId();
        
        if(newValue != null && !newValue.equals(oldValue)) {
        	
        	User currentUser = entityManager.find(User.class, Integer.valueOf(event.getRowKey()));
        	
        	switch(currentColumn) {
        	case "userVorname":
            	currentUser.setVorname(newValue.toString());
        		break;
        	case "userNachname":
            	currentUser.setNachname(newValue.toString());
        		break;
        	case "userStrasse":
            	currentUser.setStrasse(newValue.toString());
        		break;
        	case "userAdresse":
            	currentUser.setAdresse(newValue.toString());
        		break;
        	case "userPlz":
            	currentUser.setPlz((int)newValue);
        		break;
        	}
        	
    		EntityTransaction transaction = entityManager.getTransaction();
    		transaction.begin();
    		entityManager.merge(currentUser);
    		transaction.commit();
        	
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Feld geaendert", "Alt: " + oldValue + ", Neu: " + newValue);
            FacesContext.getCurrentInstance().addMessage(null, msg);   
        }
    }


}
