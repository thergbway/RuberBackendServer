package com.ruber.service.vk;

import com.ruber.controller.dto.GetItemsResponse;
import com.ruber.controller.dto.Item;
import com.ruber.service.vk.command.*;
import com.ruber.service.vk.dto.AddMarketItemResponse;
import com.ruber.service.vk.dto.GetGroupsResponse;
import com.ruber.service.vk.dto.LoadMarketPictureResponse;
import com.ruber.service.vk.dto.SaveMarketPhotoResponse;
import org.springframework.stereotype.Service;

@Service
public class VkService {
    public GetGroupsResponse getGroups(Integer count, Integer offset, String vkAccessToken) throws VkException {
        return new GetGroupsCommand(count, offset, vkAccessToken).execute();
    }

    public void editGroup(Integer groupId, Integer market, Integer messages, String vkAccessToken) {
        new EditGroupCommand(groupId, market, messages, vkAccessToken).execute();
    }

    public GetItemsResponse getMarketItems(Integer ownerId, Integer count, Integer offset, String vkAccessToken) {
        return new GetMarketItemsCommand(ownerId, count, offset, vkAccessToken).execute();
    }

    public Item getMarketItem(Integer ownerId, Integer id, String vkAccessToken) {
        return new GetMarketItemCommand(ownerId, id, vkAccessToken).execute();
    }

    public void deleteMarketItem(Integer ownerId, Integer id, String vkAccessToken) {
        new DeleteMarketItemCommand(ownerId, id, vkAccessToken).execute();
    }

    public AddMarketItemResponse addMarketItem(String vkAccessToken, Integer ownerId, String name, String description, Integer category_id,
                                               Double price, Integer deleted, Integer mainPhotoId, Integer... photoIds) {
        AddMarketItemResponse response = new AddMarketItemCommand(vkAccessToken, ownerId, name, description, category_id,
            price, deleted, mainPhotoId, photoIds).execute();

        return response;
    }

    public void editMarketItem(String vkAccessToken, Integer itemId, Integer ownerId, String name,
                               String description, Integer categoryId, Double price, Integer deleted,
                               Integer mainPhotoId, Integer... photoIds) {
        new EditMarketItemCommand(vkAccessToken, itemId, ownerId, name, description, categoryId,
            price, deleted, mainPhotoId, photoIds)
            .execute();
    }

    public SaveMarketPhotoResponse saveMainMarketItemPicture(Integer groupId, Integer cropX, Integer cropY, Integer cropWidth,
                                                             String vkAccessToken, byte[] pictureBytes, String pictureFileName) {
        return saveBasicMarketItemPhoto(groupId, 1, cropX, cropY, cropWidth, vkAccessToken, pictureBytes, pictureFileName);
    }

    public SaveMarketPhotoResponse saveNotMainMarketItemPicture(Integer groupId, String vkAccessToken,
                                                                byte[] pictureBytes, String pictureFileName) {
        SaveMarketPhotoResponse saveMarketPhotoResponse = saveBasicMarketItemPhoto(groupId, 0, null, null, null, vkAccessToken, pictureBytes, pictureFileName);

        return saveMarketPhotoResponse;
    }

    private SaveMarketPhotoResponse saveBasicMarketItemPhoto(Integer groupId, Integer mainPhoto, Integer cropX, Integer cropY, Integer cropWidth,
                                                             String vkAccessToken, byte[] pictureBytes, String pictureFileName) {
        String marketUploadServerUrl = new GetMarketUploadServerCommand(-groupId, mainPhoto,
            cropX, cropY, cropWidth, vkAccessToken)
            .execute()
            .getUpload_url();

        LoadMarketPictureResponse loadResponse =
            new LoadMarketPictureCommand(marketUploadServerUrl, pictureBytes, pictureFileName).execute();

        SaveMarketPhotoResponse saveMarketPhotoResponse = new SaveMarketPhotoCommand(loadResponse.getPhoto(),
            loadResponse.getServer(), loadResponse.getHash(),
            -groupId, loadResponse.getCrop_data(), loadResponse.getCrop_hash(), vkAccessToken)
            .execute();

        return saveMarketPhotoResponse;
    }

}