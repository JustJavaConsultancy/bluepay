package com.techcrunch.bluepay.cloudinary;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class CloudinaryService {

    @Resource
    private Cloudinary cloudinary;

    public String uploadFile(MultipartFile file, String folderName) {
        try{
            HashMap<Object, Object> options = new HashMap<>();

            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            System.out.println("0 The Cloudinary======================" +
                    "================================"+uploadResult);

            String secureUrl = (String) uploadResult.get("secure_url");

            System.out.println("1 The Cloudinary======================" +
                    "================================"+uploadResult);

            return secureUrl;

        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }
    public String uploadVideo(MultipartFile file, String folderName) {
        try{
            HashMap<Object, Object> options = new HashMap<>();

            options.put("folder", folderName);
            options.put("resource_type", "video");

            Map videoMap=cloudinary.uploader().upload(file.getBytes(),
                    ObjectUtils.asMap("resource_type", "video",
                            "public_id", file.getName()));

            System.out.println("0 The Cloudinary video url================" +
                    "==================================" +
                    "======================="+videoMap);
            String secureUrl = (String) videoMap.get("secure_url");

            System.out.println("1 The Cloudinary video url======================" +
                    "================================"+secureUrl);
            return secureUrl;

        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

}
