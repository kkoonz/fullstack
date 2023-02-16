package demo.helloworld.controller;

import demo.helloworld.dao.HelloMybatisRepository;
import demo.helloworld.entity.HelloJPAEntity;
import demo.helloworld.model.HelloMybatisEntity;
import demo.helloworld.repository.HelloJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@EnableJpaAuditing
public class HelloworldController {

    @GetMapping("hello-thymeleaf")
    public String HelloThymeleaf(Model model) {
        model.addAttribute("data", "thymeleaf");
        return "hello-thymeleaf";
    }

    @GetMapping("hello-get")
    public String HelloGet(@RequestParam(value = "data", required = false) String data, Model model) {
        model.addAttribute("data", data);
        return "hello-thymeleaf";
    }

    @GetMapping("hello-body")
    @ResponseBody
    public String HelloBody(@RequestParam(value = "data", required = false) String data, Model model) {
        return "hello " + data + "!";
    }

    @GetMapping("hello-json")
    @ResponseBody
    public Hello HelloJson(@RequestParam(value = "data", required = false) String data, Model model) {
        Hello h = new Hello();
        h.data = data;
        return h;
    }

    static class Hello {
        public String data;
    }

    @Value(value = "${app.name}")
    String appName;

    @GetMapping("hello-prop")
    @ResponseBody
    public String HelloProp(Model model) {
        return appName;
    }

    @Autowired
    private HelloMybatisRepository mDAO;

    @ResponseBody
    @GetMapping("hello-mybatis")
    public List<HelloMybatisEntity> list() throws Exception {
        List<HelloMybatisEntity> list = mDAO.list();
        return list;
    }

    @RequestMapping(method = RequestMethod.GET, path="hello-url/{data}")
    @ResponseBody
    public String helloURL(@PathVariable("data") String data) {
        return "Hello " + data;
    }

    @Autowired
    private HelloJPARepository jDAO;

    @ResponseBody
    @GetMapping("hello-JPA")
    public List<HelloJPAEntity> helloJPA() {
        HelloJPAEntity e = new HelloJPAEntity("Hi");
        jDAO.save(e);
        List<HelloJPAEntity> list = jDAO.findAll();
        jDAO.delete(e);
        return list;
    }
}