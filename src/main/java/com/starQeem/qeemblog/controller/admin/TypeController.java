package com.starQeem.qeemblog.controller.admin;

import com.github.pagehelper.PageInfo;
import com.starQeem.qeemblog.mapper.TypeMapper;
import com.starQeem.qeemblog.pojo.Type;
import com.starQeem.qeemblog.service.TypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;

import static com.starQeem.qeemblog.util.constant.PAGE_SIZE;


@Controller
@RequestMapping("/admin")
public class TypeController {
    @Resource
    private TypeService typeService;
    @Resource
    private TypeMapper typeMapper;
    /*
    *分页查询
    * */
    @GetMapping(value = {"/types/{page}","/types"})
    public String TypeListPage(@PathVariable(value = "page",required = false)  Integer page,Model model){
        PageInfo<Type> pageInfo = typeService.pageTypeList(page, PAGE_SIZE);
        model.addAttribute("page",pageInfo);
        return "admin/types";
    }
    /*
    * 跳转分页新增页面
    * */
    @GetMapping("/types/input")
    public String TypeInput(){
        return "admin/types-input";
    }
    /*
    * 新增分类
    * */
    @PostMapping("/types")
    public String insertType(Type type, RedirectAttributes attributes){
        if (type == null){
            attributes.addFlashAttribute("message","新增失败!");
            return "redirect:/admin/types";
        }
        if (typeMapper.getName(type.getName()) != null){
            attributes.addFlashAttribute("message","不能添加重复的分类!");
            return "redirect:/admin/types/input";
        }else {
            typeService.save(type);
            attributes.addFlashAttribute("message","添加成功!");
            return "redirect:/admin/types";
        }
    }
    /*
    * 跳转到分类编辑
    * */
    @GetMapping("/types/{id}/edit")
    public String editType(@PathVariable Long id,Model model){
        Type type = typeService.getById(id);
        model.addAttribute("type",type);
        return "admin/types-edit";
    }
    /*
    * 分类编辑
    * */
    @PutMapping("/types")
    public String updateType(Type type,RedirectAttributes attributes){
        if (typeMapper.getName(type.getName()) != null){
            attributes.addFlashAttribute("message","不能添加重复的分类!");
            return "redirect:/admin/types";
        }else {
            typeService.updateById(type);
            attributes.addFlashAttribute("message","修改分类成功!");
            return "redirect:/admin/types";
        }
    }
    /*
    * 删除分类
    * */
    @RequestMapping ("/types/{id}/delete")
    public String deleteType(@PathVariable("id") Long id,RedirectAttributes attributes){
        boolean remove = typeService.removeById(id);
        if (remove){
            attributes.addFlashAttribute("message","删除成功!");
        }else {
            attributes.addFlashAttribute("massage","删除失败!");
        }
        return "redirect:/admin/types";
    }

}
