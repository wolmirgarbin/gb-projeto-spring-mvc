package br.com.materjunior.controller;

import br.com.materjunior.entities.Contato;
import br.com.materjunior.controller.util.JsfUtil;
import br.com.materjunior.controller.util.PaginationHelper;
import br.com.materjunior.entities.ContatoJpaController;

import java.io.Serializable;
import java.util.ResourceBundle;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.persistence.Persistence;

@ManagedBean(name = "contatoController")
@SessionScoped
public class ContatoController implements Serializable {

    private static final long serialVersionUID = 1L;
    private Contato current;
    private DataModel items = null;
    private ContatoJpaController jpaController = null;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public ContatoController() {
    }

    public Contato getSelected() {
        if (current == null) {
            current = new Contato();
            selectedItemIndex = -1;
        }
        return current;
    }

    private ContatoJpaController getJpaController() {
        if (jpaController == null) {
            jpaController = new ContatoJpaController(Persistence.createEntityManagerFactory("materjuniorPU"));
        }
        return jpaController;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {

                @Override
                public int getItemsCount() {
                    return getJpaController().getContatoCount();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getJpaController().findContatoEntities(getPageSize(), getPageFirstItem()));
                }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareView() {
        current = (Contato) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Contato();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getJpaController().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ContatoCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Contato) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getJpaController().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ContatoUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Contato) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "List";
        }
    }

    private void performDestroy() {
        try {
            getJpaController().destroy(current.getIdcontato());
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ContatoDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getJpaController().getContatoCount();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getJpaController().findContatoEntities(1, selectedItemIndex).get(0);
        }
    }

    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    private void recreateModel() {
        items = null;
    }

    private void recreatePagination() {
        pagination = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(getJpaController().findContatoEntities(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(getJpaController().findContatoEntities(), true);
    }

    @FacesConverter(forClass = Contato.class)
    public static class ContatoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ContatoController controller = (ContatoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "contatoController");
            return controller.getJpaController().findContato(getKey(value));
        }

        java.lang.Long getKey(String value) {
            java.lang.Long key;
            key = Long.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Long value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Contato) {
                Contato o = (Contato) object;
                return getStringKey(o.getIdcontato());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Contato.class.getName());
            }
        }

    }

}
