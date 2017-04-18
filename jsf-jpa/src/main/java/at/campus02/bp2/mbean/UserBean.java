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

import at.campus02.bp2.model.User;
import at.campus02.bp2.utils.EntityManagerFactoryProvider;

@ManagedBean
@SessionScoped

public class UserBean {
	
	private EntityManager entityManager;
	
	private User newUser = new User();
	private List<User> userList = new ArrayList<User>();
	
	private User selectedUser;
	
	public UserBean(){
	
	}

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
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Der User " + newUser.getNachname() + " wurde hinzugefügt" ));
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
	
	//SelectedUser auswählen und löschen
	public User getSelectedUser() {
		return selectedUser;
	}

	public void setSelectedUser(User selectedUser) {
		this.selectedUser = selectedUser;
	}
	
	public void deleteUser() {
		userList.remove(selectedUser);
		selectedUser = null;
	}

}
