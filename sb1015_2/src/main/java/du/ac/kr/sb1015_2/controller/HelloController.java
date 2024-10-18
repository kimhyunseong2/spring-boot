package du.ac.kr.sb1015_2.controller;

import du.ac.kr.sb1015_2.entity.MyData;
import du.ac.kr.sb1015_2.repository.MyDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class HelloController {
    final MyDataRepository repository;

    @GetMapping("/")
    public String index(@ModelAttribute("formModel")MyData mydata, Model model) {
        List<MyData> list = repository.findAll();
        model.addAttribute("datalist", list);
        return "index";
    }

    @PostMapping("/")
    public String form(MyData mydata) {
        repository.saveAndFlush(mydata);
        return "redirect:/";
    }


    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id,@ModelAttribute MyData myData ,Model model) {
        Optional<MyData> myData1 = repository.findById(id);
        model.addAttribute("formModel", myData1.get());
        return "edit";
    }
    @PostMapping("/edit")
    public String update(@ModelAttribute MyData myData) {
        repository.saveAndFlush(myData);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id,@ModelAttribute MyData myData ,Model model) {
        Optional<MyData> myData2 = repository.findById(id);
        model.addAttribute("formModel", myData2.get());
        return "delete";
    }

    @PostMapping("/delete")
    public String delete(@ModelAttribute MyData myData) {
        repository.delete(myData);
        return "redirect:/";
    }
}
