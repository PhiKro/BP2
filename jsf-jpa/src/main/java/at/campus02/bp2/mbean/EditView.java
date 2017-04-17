package at.campus02.bp2.mbean;

import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.CellEditEvent;

import at.campus02.bp2.model.Customer;
 
@ManagedBean(name="dtEditView")
@ViewScoped
public class EditView implements Serializable {
     
    private List<Customer> customer1;
         
//    @ManagedProperty("#{carService}")
//    private CarService service;
     
//    @PostConstruct
//    public void init() {
//        customer1 = service.createCars(10);
//        customer2 = service.createCars(10);
//    }
 
    public List<Customer> getCustomer1() {
        return customer1;
    }
 
     
//    public List<String> getBrands() {
//        return service.getBrands();
//    }
//     
//    public List<String> getColors() {
//        return service.getColors();
//    }
// 
//    public void setService(CarService service) {
//        this.service = service;
//    }
     
    public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();
         
        if(newValue != null && !newValue.equals(oldValue)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
}
