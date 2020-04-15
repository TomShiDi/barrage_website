//package barrage.demo.serviceImpl;
//
//import barrage.demo.barrageapi.entity.DepartmentLogInfo;
//import barrage.demo.service.DepartmentLogInfoService;
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import static org.junit.Assert.*;
//
///**
// * @Author TomShiDi
// * @Since 2019/6/17
// * @Version 1.0
// */
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class DepartmentLogInfoServiceImplTest {
//
//    @Autowired
//    private DepartmentLogInfoService departmentLogInfoService;
//
//
//    @Test
//    public void findAll() {
//        PageRequest request = PageRequest.of(1, 3);
//        Page<DepartmentLogInfo> departmentLogInfoPage = departmentLogInfoService.findAll(request);
//        Assert.assertNotEquals(0,departmentLogInfoPage.getTotalElements());
//    }
//
//    @Test
//    public void findByDepartmentId() {
//        PageRequest request = PageRequest.of(1, 3);
//        Page<DepartmentLogInfo> departmentLogInfoPage = departmentLogInfoService.findByDepartmentId(1, request);
//        Assert.assertNotEquals(0, departmentLogInfoPage.getTotalElements());
//    }
//
//    @Test
//    public void findByOperatorId() {
//        PageRequest request = PageRequest.of(1, 3);
//        Page<DepartmentLogInfo> departmentLogInfoPage = departmentLogInfoService.findByOperatorId(1, request);
//        Assert.assertNotEquals(0, departmentLogInfoPage.getTotalElements());
//    }
//
//    @Test
//    public void save() {
//        DepartmentLogInfo departmentLogInfo = new DepartmentLogInfo();
//        departmentLogInfo.setDepartmentId(1);
//        departmentLogInfo.setOperatorId(1);
//        departmentLogInfo.setOperationType("修改");
//        departmentLogInfo.setOperationContent(String.format("{'oldName':%s,'newName':%s}", "原名", "新名"));
//        int result = departmentLogInfoService.save(departmentLogInfo);
//        Assert.assertNotEquals(0, result);
//    }
//}