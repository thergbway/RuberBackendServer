package com.ruber.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruber.controller.dto.AddPinnedFileRequestPart;
import com.ruber.controller.dto.PinnedFile;
import com.ruber.exception.MultipartFileIOException;
import com.ruber.service.PinnedItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.beans.PropertyEditorSupport;
import java.io.IOException;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping(PinnedFilesController.PATH)
public class PinnedFilesController {
    public static final String PATH = "/markets/{market_vk_id}/orders/{orderId}/pinned_files";

    @Autowired
    private PinnedItemsService pinnedItemsService;

    @InitBinder
    private void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(AddPinnedFileRequestPart.class,
            new PropertyEditorSupport() {
                @Override
                public void setAsText(String text) {
                    try {
                        setValue(new ObjectMapper().readValue(text, AddPinnedFileRequestPart.class));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
    }

    @RequestMapping(method = GET)
    public List<PinnedFile> getPinnedFiles(
        @PathVariable("orderId") Integer orderId,
        @ModelAttribute("user_id") Integer userId,
        @PathVariable("market_vk_id") Integer marketVkId
    ) {
        return pinnedItemsService.getPinnedFiles(userId, marketVkId, orderId);
    }

    @RequestMapping(value = "/{fileId}", method = GET)
    public PinnedFile getPinnedFile(
        @PathVariable("orderId") Integer orderId,
        @PathVariable("fileId") Integer fileId,
        @ModelAttribute("user_id") Integer userId,
        @PathVariable("market_vk_id") Integer marketVkId
    ) {
        return pinnedItemsService.getPinnedFile(userId, marketVkId, orderId, fileId);
    }

    @RequestMapping(value = "/{fileId}", method = DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePinnedFile(
        @PathVariable("orderId") Integer orderId,
        @PathVariable("fileId") Integer fileId,
        @ModelAttribute("user_id") Integer userId,
        @PathVariable("market_vk_id") Integer marketVkId
    ) {
        pinnedItemsService.deletePinnedItem(userId, marketVkId, orderId, fileId);
    }

    @RequestMapping(method = POST)
    public ResponseEntity<PinnedFile> addPinnedFile(
        @PathVariable("orderId") Integer orderId,
        @PathVariable("market_vk_id") Integer marketVkId,
        @RequestParam("file_info") AddPinnedFileRequestPart fileInfo,

        @RequestPart("content") MultipartFile fileContent,

        @ModelAttribute("user_id") Integer userId,

        UriComponentsBuilder builder
    ) {
        try {
            Integer fileId = pinnedItemsService
                .addPinnedFile(userId, marketVkId, orderId, fileInfo, fileContent.getBytes(), fileContent.getOriginalFilename());

            UriComponents uriComponents = builder
                .path(PATH + "/{pinnedFileId}")
                .buildAndExpand(marketVkId, orderId, fileId);

            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(uriComponents.toUri());

            PinnedFile addedPinnedFile = pinnedItemsService.getPinnedFile(userId, marketVkId, orderId, fileId);

            return new ResponseEntity<>(addedPinnedFile, headers, HttpStatus.CREATED);
        } catch (IOException e) {
            throw new MultipartFileIOException(e);
        }
    }

    @RequestMapping(value = "/{fileId}/content", method = GET)
    public ResponseEntity<byte[]> getPinnedFileContent(
        @PathVariable("orderId") Integer orderId,
        @PathVariable("fileId") Integer fileId,
        @ModelAttribute("user_id") Integer userId,
        @PathVariable("market_vk_id") Integer marketVkId
    ) {
        byte[] content = pinnedItemsService.getPinnedFileContent(userId, marketVkId, orderId, fileId);
        String filename = pinnedItemsService.getPinnedFileFilename(userId, marketVkId, orderId, fileId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", filename);
        headers.setContentLength(content.length);

        return new ResponseEntity<>(content, headers, HttpStatus.OK);
    }
}
