package com.project_classes.model.services;

import com.project_classes.model.dao.DaoFactory;
import com.project_classes.model.dao.DepartmentDao;
import com.project_classes.model.entities.Department;

import java.util.List;

public class ServicesDepartamento {

    private DepartmentDao dao = DaoFactory.createDepartmentDao();

    public class DepartmentService {

    }

    public List<Department> findAll() {
        return dao.findAll();
    }

    public void saveOrUpdate(Department obj) {
        if (obj.getId() == null) {
            dao.insert(obj);
        } else {
            dao.update(obj);
        }
    }

    public void remove(Department obj) {
        dao.deleteById(obj.getId());
    }
}

