package du.ac.kr.sb1018.controller;

import du.ac.kr.sb1018.entity.Dept;
import du.ac.kr.sb1018.entity.Emp;
import du.ac.kr.sb1018.service.EmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MyController {
    @Autowired
    private EmService emService;

    @GetMapping("/")
    public String index() {
        return "redirect:/openDeptList";
    }

    @GetMapping("/openDeptList")
    public String openDeptList(Model model) {
        List<Dept> list = emService.selectDeptList();
        model.addAttribute("list", list);
        return "deptList";
    }

    @GetMapping("/dept/{deptNo}/openEmpList")
    public String openEmpList(@PathVariable int deptNo, Model model) {
        List<Emp> list = emService.selectEmpList(deptNo);
        model.addAttribute("list", list);
        return "empList";
    }

    @GetMapping("/removeDept")
    public String removeDept(int deptNo) {
        emService.removeDept(deptNo);
        return "redirect:/openDeptList";
    }

    @RequestMapping("/persistDept")
    public String persistDept(Dept dept) {
        emService.persistDept(dept);
        return "redirect:/openDeptList";
    }

    @GetMapping("/opentWrite")
    public String opentWrite() {
        return "deptWrite";
    }

}
