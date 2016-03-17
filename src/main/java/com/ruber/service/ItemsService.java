//package com.ruber.service;
//
//import com.ruber.controller.dto.*;
//import com.ruber.service.vk.VkService;
//import com.ruber.service.vk.dto.AddMarketItemResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.util.Arrays;
//
//@Service
//public class ItemsService {
//    @Autowired
//    private AuthService authService;
//
//    @Autowired
//    private VkService vkService;
//
//    public GetItemsResponse getItems(Integer ownerId, Integer count, Integer offset, String accessToken) {
//        if (!authService.checkAccessToken(accessToken))
//            throw new RuntimeException("Invalid accessToken");
//
//        if (count == null)
//            count = 100;
//        if (offset == null)
//            offset = 0;
//
//        String vkAccessToken = authService.getVkAccessToken(accessToken);
//
//        return vkService.getMarketItems(ownerId, count, offset, vkAccessToken);
//    }
//
//    public GetItemResponse getItem(Integer ownerId, Integer id, String accessToken) {
//        if (!authService.checkAccessToken(accessToken))
//            throw new RuntimeException("Invalid accessToken");
//
//        String vkAccessToken = authService.getVkAccessToken(accessToken);
//
//        Item item = vkService.getMarketItem(ownerId, id, vkAccessToken);
//
//        return new GetItemResponse(item);
//    }
//
//    public void deleteMarketItem(Integer ownerId, Integer id, String accessToken) {
//        if (!authService.checkAccessToken(accessToken))
//            throw new RuntimeException("Invalid accessToken");
//
//        String vkAccessToken = authService.getVkAccessToken(accessToken);
//
//        vkService.deleteMarketItem(ownerId, id, vkAccessToken);
//    }
//
//    public AddMarketItemResponse addItem(String accessToken, AddItemRequestPart itemInfo,
//                                         MultipartFile mainPhotoRaw, Integer mainPhotoId,
//                                         MultipartFile[] photoRawArray, Integer[] photoIds) {
//        try {
//            if (!authService.checkAccessToken(accessToken))
//                throw new RuntimeException("Invalid accessToken");
//
//            String vkAccessToken = authService.getVkAccessToken(accessToken);
//
//            if (mainPhotoId == null) {
//                mainPhotoId = vkService.saveMainMarketItemPicture(itemInfo.getOwner_id(),
//                    itemInfo.getMain_photo_crop_x(), itemInfo.getMain_photo_crop_y(),
//                    itemInfo.getMain_photo_crop_width(),
//                    vkAccessToken, mainPhotoRaw.getBytes(), mainPhotoRaw.getOriginalFilename()).getId();
//            }
//
//            Integer[] filledPhotosIds = Arrays.copyOf(photoIds, photoIds.length);
//
//            if (itemInfo.getHas_photo_1() && photoIds[0] == null)
//                filledPhotosIds[0] = vkService.saveNotMainMarketItemPicture(itemInfo.getOwner_id(), vkAccessToken,
//                    photoRawArray[0].getBytes(), photoRawArray[0].getOriginalFilename()).getId();
//
//            if (itemInfo.getHas_photo_2() && photoIds[1] == null)
//                filledPhotosIds[1] = vkService.saveNotMainMarketItemPicture(itemInfo.getOwner_id(), vkAccessToken,
//                    photoRawArray[1].getBytes(), photoRawArray[1].getOriginalFilename()).getId();
//
//            if (itemInfo.getHas_photo_3() && photoIds[2] == null)
//                filledPhotosIds[2] = vkService.saveNotMainMarketItemPicture(itemInfo.getOwner_id(), vkAccessToken,
//                    photoRawArray[2].getBytes(), photoRawArray[2].getOriginalFilename()).getId();
//
//            if (itemInfo.getHas_photo_4() && photoIds[3] == null)
//                filledPhotosIds[3] = vkService.saveNotMainMarketItemPicture(itemInfo.getOwner_id(), vkAccessToken,
//                    photoRawArray[3].getBytes(), photoRawArray[3].getOriginalFilename()).getId();
//
//            AddMarketItemResponse addMarketItemResponse = vkService.addMarketItem(vkAccessToken, itemInfo.getOwner_id(),
//                itemInfo.getName(), itemInfo.getDescription(), itemInfo.getCategory_id(),
//                itemInfo.getPrice(), itemInfo.getDeleted(), mainPhotoId, filledPhotosIds);
//
//            return addMarketItemResponse;
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public void editItem(String accessToken, Integer itemId, EditItemRequestPart itemInfo,
//                         MultipartFile mainPhotoRaw, Integer mainPhotoId,
//                         MultipartFile[] photoRawArray, Integer[] photoIds) {
//        try {
//            if (!authService.checkAccessToken(accessToken))
//                throw new RuntimeException("Invalid accessToken");
//
//            String vkAccessToken = authService.getVkAccessToken(accessToken);
//
//            if (mainPhotoId == null) {
//                mainPhotoId = vkService.saveMainMarketItemPicture(itemInfo.getOwner_id(),
//                    itemInfo.getMain_photo_crop_x(), itemInfo.getMain_photo_crop_y(),
//                    itemInfo.getMain_photo_crop_width(),
//                    vkAccessToken, mainPhotoRaw.getBytes(), mainPhotoRaw.getOriginalFilename()).getId();
//            }
//
//            Integer[] filledPhotosIds = Arrays.copyOf(photoIds, photoIds.length);
//
//            if (itemInfo.getHas_photo_1() && photoIds[0] == null)
//                filledPhotosIds[0] = vkService.saveNotMainMarketItemPicture(itemInfo.getOwner_id(), vkAccessToken,
//                    photoRawArray[0].getBytes(), photoRawArray[0].getOriginalFilename()).getId();
//
//            if (itemInfo.getHas_photo_2() && photoIds[1] == null)
//                filledPhotosIds[1] = vkService.saveNotMainMarketItemPicture(itemInfo.getOwner_id(), vkAccessToken,
//                    photoRawArray[1].getBytes(), photoRawArray[1].getOriginalFilename()).getId();
//
//            if (itemInfo.getHas_photo_3() && photoIds[2] == null)
//                filledPhotosIds[2] = vkService.saveNotMainMarketItemPicture(itemInfo.getOwner_id(), vkAccessToken,
//                    photoRawArray[2].getBytes(), photoRawArray[2].getOriginalFilename()).getId();
//
//            if (itemInfo.getHas_photo_4() && photoIds[3] == null)
//                filledPhotosIds[3] = vkService.saveNotMainMarketItemPicture(itemInfo.getOwner_id(), vkAccessToken,
//                    photoRawArray[3].getBytes(), photoRawArray[3].getOriginalFilename()).getId();
//
//            vkService.editMarketItem(vkAccessToken, itemId, itemInfo.getOwner_id(),
//                itemInfo.getName(), itemInfo.getDescription(), itemInfo.getCategory_id(),
//                itemInfo.getPrice(), itemInfo.getDeleted(), mainPhotoId, filledPhotosIds);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//}