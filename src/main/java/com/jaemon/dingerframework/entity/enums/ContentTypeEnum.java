/*
 * Copyright ©2015-2020 Jaemon. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jaemon.dingerframework.entity.enums;

import okhttp3.MediaType;

/**
 * ContentType
 *
 * @author Jaemon
 * @since 1.0
 */
public enum ContentTypeEnum {
    JSON(MediaType.parse("application/json; charset=utf-8")),
    XML(MediaType.parse("application/xml; charset=utf-8"));

    private MediaType mediaType;

    ContentTypeEnum(MediaType mediaType) {
        this.mediaType = mediaType;
    }

    public MediaType mediaType() {
        return mediaType;
    }
}