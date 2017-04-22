package at.campus02.bp2.model;

import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletResponse;

import org.primefaces.context.RequestContext;
 
@ManagedBean
public class UserLoginView {
     
    private String username;
     
    private String password;
    
 
    public String getUsername() {
        return username;
    }
    
    public String gettoIndex() {
        return "user.xhtml";
    }
 
    public void setUsername(String username) {
        this.username = username;
    }
 
    public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }
   
    public void login(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage message = null;
        
        boolean loggedIn = false;
         
        if(username != null && username.equals("a") && password != null && password.equals("a")) {
            loggedIn = true; 
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Wilkommen", username);
            
            
            FacesContext recontext = FacesContext.getCurrentInstance();
            try {
				recontext.getExternalContext().redirect("/bp2/user.xhtml");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
      
            
        } else {
            loggedIn = false;
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Loggin Error", "Invalid credentials");
        }
         
        FacesContext.getCurrentInstance().addMessage(null, message);
        context.addCallbackParam("loggedIn", loggedIn);
    }   
  
    
    
    
    
    
    
    
    
    
    
    
}
