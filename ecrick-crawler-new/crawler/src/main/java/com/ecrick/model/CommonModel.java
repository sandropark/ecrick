package com.ecrick.model;

import com.ecrick.entity.Library;
import com.ecrick.service.CrawlerModel;

public abstract class CommonModel implements CrawlerModel {
    protected Library library;

    public CommonModel setLibrary(Library library) {
        this.library = library;
        return this;
    }
}
