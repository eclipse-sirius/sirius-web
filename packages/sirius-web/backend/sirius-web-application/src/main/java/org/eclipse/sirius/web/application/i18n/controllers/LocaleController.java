/*******************************************************************************
 * Copyright (c) 2025 Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.web.application.i18n.controllers;

import java.util.Objects;

import org.eclipse.sirius.components.graphql.api.URLConstants;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * The entry point of the HTTP API to get locales.
 * <p>
 * This endpoint will be available on the API base path prefix with locales segment and followed by the language and the namespace JSON file containing the
 * language content for the namespace.
 * </p>
 *
 * <p>
 * In the intended use, the frontend will only request for namespace JSON files present in src/main/resources/i18n/{language}/
 * </p>
 *
 * @author gcoutable
 */
@Controller
@RequestMapping(URLConstants.LOCALE_BASE_PATH + "/{language}/{namespace}.json")
public class LocaleController {

    private final ResourceLoader resourceLoader;

    public LocaleController(ResourceLoader resourceLoader) {
        this.resourceLoader = Objects.requireNonNull(resourceLoader);
    }

    @ResponseBody
    @GetMapping
    public ResponseEntity<Resource> getLocale(@PathVariable String language, @PathVariable String namespace, @RequestHeader HttpHeaders requestHeaders) {

        var resource = this.resourceLoader.getResource(String.format("classpath:i18n/%s/%s.json", language, namespace));
        if (resource.exists()) {
            return new ResponseEntity<>(resource, new HttpHeaders(), HttpStatus.OK);
        }

        return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }
}
