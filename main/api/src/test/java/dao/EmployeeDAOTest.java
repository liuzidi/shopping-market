package dao;

import com.lzd.ApiApplication;
import com.lzd.dao.EmployeeDAO;
import com.lzd.entity.Employee;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApiApplication.class)
public class EmployeeDAOTest {
    @Resource
    private EmployeeDAO employeeDAO;

    @Test
    public void queryEmployeeByName() {
        Employee e = employeeDAO.queryEmployeeByName("Peter");
        System.out.println(e);
    }
}