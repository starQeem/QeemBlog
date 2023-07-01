package com.starQeem.qeemblog.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.starQeem.qeemblog.pojo.friends;
import com.starQeem.qeemblog.service.friendsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;

/**
 * @Date: 2023/6/18 20:10
 * @author: Qeem
 */
@Controller
@RequestMapping("/admin")
public class FriendsController {
    @Resource
    private friendsService friendsService;
    /*
    * 跳转到友链管理页面
    * */
    @GetMapping(value = {"/friends","/friends/{pageNum}"})
    public String friends(@PathVariable(value = "pageNum",required = false)Integer pageNum, Model model){
        PageInfo<friends> pageInfo = friendsService.getFriendsList(pageNum);
        model.addAttribute("page",pageInfo);
        return "admin/friends";
    }
    /*
    * 跳转到友链添加页面
    * */
    @GetMapping("/friends/input")
    public String addFriends() {
         return "admin/friends-input";
    }
    /*
    * 友链添加
    * */
    @PostMapping("/friendsInput")
    public String addFriendsInput(friends friends, RedirectAttributes attributes){
        boolean isSave = friendsService.save(friends);
        if (isSave){
            attributes.addFlashAttribute("message","添加成功!");
        }else {
            attributes.addFlashAttribute("message","添加失败!");
        }
        return "redirect:/admin/friends";
    }
    /*
    * 跳转到友链修改页面
    * */
    @GetMapping("/friends/{id}/edit")
    public String updateFriends(@PathVariable("id")Long id,Model model){
        QueryWrapper<friends> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",id);
        friends friends = friendsService.getBaseMapper().selectOne(queryWrapper);
        model.addAttribute("friends",friends);
        return "admin/friends-edit";
    }
    /*
    * 友链修改
    * */
    @PostMapping("/friendsUpdate")
    public String updateFriendsInput(friends friends,RedirectAttributes attributes){
        boolean isUpdate = friendsService.updateById(friends);
        if (isUpdate){
            attributes.addFlashAttribute("message","修改成功!");
        }else {
            attributes.addFlashAttribute("message","修改失败!");
        }
        return "redirect:/admin/friends";
    }
    /*
    * 友链删除
    * */
    @RequestMapping("/friends/{id}/delete")
    public String deleteFriendsById(@PathVariable("id")Long id,RedirectAttributes attributes){
        boolean isDelete = friendsService.removeById(id);
        if (isDelete){
            attributes.addFlashAttribute("message","删除成功!");
        }else {
            attributes.addFlashAttribute("message","删除失败!");
        }
        return "redirect:/admin/friends";
    }
}