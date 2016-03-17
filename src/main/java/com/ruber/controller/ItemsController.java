//package com.ruber.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.ruber.controller.dto.AddItemRequestPart;
//import com.ruber.controller.dto.EditItemRequestPart;
//import com.ruber.controller.dto.GetItemResponse;
//import com.ruber.controller.dto.GetItemsResponse;
//import com.ruber.service.ItemsService;
//import com.ruber.service.vk.dto.AddMarketItemResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.WebDataBinder;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.util.UriComponents;
//import org.springframework.web.util.UriComponentsBuilder;
//
//import java.beans.PropertyEditorSupport;
//import java.io.IOException;
//
//@RestController
//@RequestMapping(ItemsController.PATH)
//public class ItemsController {
//    public static final String PATH = "/items";
//
//    @Autowired
//    private ItemsService itemsService;
//
//    @InitBinder
//    private void initBinder(WebDataBinder binder) {
//        binder.registerCustomEditor(AddItemRequestPart.class,
//            new PropertyEditorSupport() {
//                @Override
//                public void setAsText(String text) {
//                    try {
//                        setValue(new ObjectMapper().readValue(text, AddItemRequestPart.class));
//                    } catch (IOException e) {
//                        throw new RuntimeException(e);
//                    }
//                }
//            });
//
//        binder.registerCustomEditor(EditItemRequestPart.class,
//            new PropertyEditorSupport() {
//                @Override
//                public void setAsText(String text) {
//                    try {
//                        setValue(new ObjectMapper().readValue(text, EditItemRequestPart.class));
//                    } catch (IOException e) {
//                        throw new RuntimeException(e);
//                    }
//                }
//            });
//    }
//
//    @RequestMapping(method = RequestMethod.GET)
//    public GetItemsResponse getItems(
//        @RequestParam(value = "owner_id", required = true) Integer ownerId,
//        @RequestParam(value = "count", required = false) Integer count,
//        @RequestParam(value = "offset", required = false) Integer offset,
//        @RequestParam(value = "access_token", required = true) String accessToken) {
//
//        return itemsService.getItems(ownerId, count, offset, accessToken);
//    }
//
//    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
//    public GetItemResponse getItem(
//        @RequestParam(value = "owner_id", required = true) Integer ownerId,
//        @PathVariable("id") Integer id,
//        @RequestParam(value = "access_token", required = true) String accessToken) {
//
//        return itemsService.getItem(ownerId, id, accessToken);
//    }
//
//    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void deleteItem(
//        @RequestParam(value = "owner_id", required = true) Integer ownerId,
//        @PathVariable("id") Integer id,
//        @RequestParam(value = "access_token", required = true) String accessToken) {
//
//        itemsService.deleteMarketItem(ownerId, id, accessToken);
//    }
//
//    @RequestMapping(method = RequestMethod.POST)
//    public ResponseEntity<Void> addItem(
//        @RequestParam(value = "access_token", required = true) String accessToken,
//        @RequestParam(value = "item_info", required = true) AddItemRequestPart itemInfo,
//
//        @RequestParam(value = "main_photo_id", required = false) Integer mainPhotoId,
//        @RequestParam(value = "photo_1_id", required = false) Integer photo1Id,
//        @RequestParam(value = "photo_2_id", required = false) Integer photo2Id,
//        @RequestParam(value = "photo_3_id", required = false) Integer photo3Id,
//        @RequestParam(value = "photo_4_id", required = false) Integer photo4Id,
//
//        @RequestPart(value = "main_photo_raw", required = false) MultipartFile mainPhotoRaw,
//        @RequestPart(value = "photo_1_raw", required = false) MultipartFile photo1Raw,
//        @RequestPart(value = "photo_2_raw", required = false) MultipartFile photo2Raw,
//        @RequestPart(value = "photo_3_raw", required = false) MultipartFile photo3Raw,
//        @RequestPart(value = "photo_4_raw", required = false) MultipartFile photo4Raw,
//
//        UriComponentsBuilder builder) {
//
//        AddMarketItemResponse addMarketItemResponse =
//            itemsService.addItem(accessToken, itemInfo,
//                mainPhotoRaw, mainPhotoId,
//                new MultipartFile[]{photo1Raw, photo2Raw, photo3Raw, photo4Raw},
//                new Integer[]{photo1Id, photo2Id, photo3Id, photo4Id});
//
//        UriComponents uriComponents = builder
//            .path(PATH + "/{id}")
//            .buildAndExpand(addMarketItemResponse.getMarket_item_id());
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setLocation(uriComponents.toUri());
//
//        return new ResponseEntity<>(headers, HttpStatus.CREATED);
//    }
//
//    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
//    @ResponseStatus(HttpStatus.OK)
//    public void editItem(
//        @PathVariable("id") Integer itemId,
//        @RequestParam(value = "access_token", required = true) String accessToken,
//        @RequestParam(value = "item_info", required = true) EditItemRequestPart itemInfo,
//
//        @RequestParam(value = "main_photo_id", required = false) Integer mainPhotoId,
//        @RequestParam(value = "photo_1_id", required = false) Integer photo1Id,
//        @RequestParam(value = "photo_2_id", required = false) Integer photo2Id,
//        @RequestParam(value = "photo_3_id", required = false) Integer photo3Id,
//        @RequestParam(value = "photo_4_id", required = false) Integer photo4Id,
//
//        @RequestPart(value = "main_photo_raw", required = false) MultipartFile mainPhotoRaw,
//        @RequestPart(value = "photo_1_raw", required = false) MultipartFile photo1Raw,
//        @RequestPart(value = "photo_2_raw", required = false) MultipartFile photo2Raw,
//        @RequestPart(value = "photo_3_raw", required = false) MultipartFile photo3Raw,
//        @RequestPart(value = "photo_4_raw", required = false) MultipartFile photo4Raw
//    ) {
//        itemsService.editItem(accessToken, itemId, itemInfo,
//            mainPhotoRaw, mainPhotoId,
//            new MultipartFile[]{photo1Raw, photo2Raw, photo3Raw, photo4Raw},
//            new Integer[]{photo1Id, photo2Id, photo3Id, photo4Id});
//    }
//}