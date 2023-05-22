package com.starQeem.qeemblog.controller.admin;

import com.github.pagehelper.PageInfo;
import com.starQeem.qeemblog.mapper.TagMapper;
import com.starQeem.qeemblog.pojo.Tag;
import com.starQeem.qeemblog.pojo.Type;
import com.starQeem.qeemblog.service.TagService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;

/**
 * @Date: 2023/4/26 13:00
 * @author: Qeem
 */
@Controller
@RequestMapping("/admin")
public class TagController {
    @Resource
    private TagService tagService;
    @Resource
    private TagMapper tagMapper;
    private final static int PAGE_SIZE = 8;
    /*
     *分页查询
     * */
    @GetMapping(value = {"/tags/{page}","/tags"})
    public String TypeListPage(@PathVariable(value = "page",required = false)  Integer page, Model model){
        if (page==null){
            page = 1;
        }
        PageInfo<Tag> pageInfo = tagService.pageTagList(page, PAGE_SIZE);
        model.addAttribute("page",pageInfo);
        return "admin/tags";
    }
    /*
    * 跳转到标签编辑页面
    * */
    @GetMapping("/tags/{id}/edit")
    public String getTagEdit(@PathVariable("id")Long id, Model model){
        Tag tag = tagService.getBaseMapper().selectById(id);
        model.addAttribute("tag",tag);
        return "admin/tags-edit";
    }
    /*
    * 标签编辑提交
    * */
    @PostMapping("/tags")
    public String tagEdit(Tag tag, RedirectAttributes attributes){
        boolean success = tagService.updateTagById(tag);
        if (success){
            attributes.addFlashAttribute("message","修改成功!");
        }else {
            attributes.addFlashAttribute("message","修改失败!不能添加相同标签!");
        }
        return "redirect:/admin/tags";
    }
    /*
    * 跳转到新增标签页面
    * */
    @GetMapping("/tags/input")
    public String gettagInput(){
        return "admin/tags-input";
    }
    /*
    * 标签新增提交
    * */
    @PostMapping("tagsInput")
    public String tagInput(Tag tag,RedirectAttributes attributes){
        boolean success = tagService.saveTag(tag);
        if (success){
            attributes.addFlashAttribute("message","添加成功!");
        }else {
            attributes.addFlashAttribute("message","添加失败!不能添加重复标签!");
        }
        return "redirect:/admin/tags";
    }
    /*
    * 标签删除
    * */
    @RequestMapping("tags/{id}/delete")
    public String tagDeleteById(@PathVariable("id")Long id,RedirectAttributes attributes){
        boolean success = tagService.removeById(id);
        if (success){
            attributes.addFlashAttribute("message","删除成功!");
        }else {
            attributes.addFlashAttribute("message","删除失败!");
        }
        return "redirect:/admin/tags";
    }

}
