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

import javax.servlet.http.HttpServletRequest;
import java.beans.PropertyEditorSupport;
import java.io.IOException;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping(PinnedFilesController.PATH)
public class PinnedFilesController {
    public static final String PATH = "/orders/{orderId}/pinned_files";

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

    @ModelAttribute("user_id")
    public Integer getUserId(HttpServletRequest request) {//fixme this method is presented in about all controllers. Use hierarchy for writing it only once
        return ((Integer) request.getAttribute("user_id"));
    }


    @RequestMapping
    public List<PinnedFile> getPinnedFiles(
        @PathVariable("orderId") Integer orderId,
        @ModelAttribute("user_id") Integer userId
    ) {
        return pinnedItemsService.getPinnedFiles(userId, orderId);
    }

    @RequestMapping("/{fileId}")
    public PinnedFile getPinnedFile(
        @PathVariable("orderId") Integer orderId,
        @PathVariable("fileId") Integer fileId,
        @ModelAttribute("user_id") Integer userId
    ) {
        return pinnedItemsService.getPinnedFile(userId, orderId, fileId);
    }

    @RequestMapping(value = "/{fileId}", method = DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePinnedFile(
        @PathVariable("orderId") Integer orderId,
        @PathVariable("fileId") Integer fileId,
        @ModelAttribute("user_id") Integer userId
    ) {
        pinnedItemsService.deletePinnedItem(userId, orderId, fileId);
    }

    @RequestMapping(method = POST)
    public ResponseEntity<Void> addPinnedFile(
        @PathVariable("orderId") Integer orderId,
        @RequestParam("file_info") AddPinnedFileRequestPart fileInfo,

        @RequestPart("content") MultipartFile fileContent,

        @ModelAttribute("user_id") Integer userId,

        UriComponentsBuilder builder
    ) {
        try {
            Integer pinnedFileId = pinnedItemsService
                .addPinnedFile(userId, orderId, fileInfo, fileContent.getBytes(), fileContent.getOriginalFilename());

            UriComponents uriComponents = builder
                .path(PATH + "/{pinnedFileId}")
                .buildAndExpand(orderId, pinnedFileId);

            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(uriComponents.toUri());

            return new ResponseEntity<>(headers, HttpStatus.CREATED);
        } catch (IOException e) {
            throw new MultipartFileIOException(e);
        }
    }

    @RequestMapping("/{fileId}/content")
    public ResponseEntity<byte[]> getPinnedFileContent(
        @PathVariable("orderId") Integer orderId,
        @PathVariable("fileId") Integer fileId,
        @ModelAttribute("user_id") Integer userId
    ) {
        byte[] content = pinnedItemsService.getPinnedFileContent(userId, orderId, fileId);
        String filename = pinnedItemsService.getPinnedFileFilename(userId, orderId, fileId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", filename);
        headers.setContentLength(content.length);

        return new ResponseEntity<>(content, headers, HttpStatus.OK);
    }
}
