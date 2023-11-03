package com.example.java_lr14.Controllers;

import com.example.java_lr14.Models.Post;
import com.example.java_lr14.Repositories.PostRepository;
import jakarta.servlet.annotation.MultipartConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class BlogController {
    @Autowired
    private PostRepository postRepository;
    @GetMapping("/blog")
    public String getBlog(Model model){
        Iterable<Post> iterable = postRepository.findAll();
        model.addAttribute("posts",iterable);
        return "blog";
    }

    @GetMapping("/blog/newPost")
    public String getNewPost(Model model){
        return "newPost";
    }

    @PostMapping("/blog/newPost")
    public String getNewPostToPost(@RequestParam String header, @RequestParam String context, @RequestParam MultipartFile image, Model model){
        Post post = new Post();
        post.setContext(context);
        post.setHeader(header);
        try {
            String filename = image.getOriginalFilename();
            String filePath = "src/main/resources/static/" + filename;
            try(var out = new FileOutputStream(filePath)) {
                out.write(image.getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            post.setLinkImg(filename);
            postRepository.save(post);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
        return "redirect:/blog";
    }
    @GetMapping("/blog/{id}/edit")
    public String postEdit(@PathVariable(value = "id") Long id, Model model){
        Post post = postRepository.findById(id).orElse(null);
        if (post!=null){
            model.addAttribute("post",post);
        }
        return "edit";
    }
    @PostMapping("/blog/{id}/edit")
    public String postEditSave(@PathVariable(value = "id")Long id,
                               @RequestParam String header,
                               @RequestParam String context,

                               Model model){
        Post post = postRepository.findById(id).orElse(null);
        assert post != null;
        if (!context.equals(post.getContext())){
            post.setContext(context);
        }
        if(!header.equals(post.getHeader())){
            post.setHeader(header);
        }
        postRepository.save(post);
        return "redirect:/blog";
    }

    @GetMapping("/blog/{id}/delete")
    public String postDelete(@PathVariable(value = "id")Long id,Model model){
        Post post = postRepository.findById(id).orElse(null);
        assert post != null;
        postRepository.delete(post);
        return "redirect:/blog";
    }
    @GetMapping("/blog/{id}")
    public String getInfo(@PathVariable(value = "id") Long id, Model model)
    {
        Post post = postRepository.findById(id).orElse(null);
        if (post!=null){
            model.addAttribute("post",post);
        }
        return "postInfo";
    }

}
