package com.tinder.tinder.dto.request;

import java.util.List;

public class UpdateUserImagesDTO {
    private List<UserImageDTO> newImages;
    private List<String> deletedImages;

    public List<UserImageDTO> getNewImages() {
        return newImages;
    }

    public void setNewImages(List<UserImageDTO> newImages) {
        this.newImages = newImages;
    }

    public List<String> getDeletedImages() {
        return deletedImages;
    }

    public void setDeletedImages(List<String> deletedImages) {
        this.deletedImages = deletedImages;
    }
}
