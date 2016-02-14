package com.ruber.service.vk.command;

import com.ruber.service.vk.VkException;

public interface VkCommand<ResultType> {
    ResultType execute() throws VkException;
}