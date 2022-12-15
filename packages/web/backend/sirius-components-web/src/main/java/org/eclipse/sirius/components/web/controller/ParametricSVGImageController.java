/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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
package org.eclipse.sirius.components.web.controller;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.EnumMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.eclipse.sirius.components.collaborative.diagrams.api.IParametricSVGImageFactory;
import org.eclipse.sirius.components.collaborative.diagrams.api.SVGAttribute;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;

/**
 * The entry point of the HTTP API to get SVG images that will be dynamically computed.
 * <p>
 * This endpoint will be available on the API base path prefix with image segment and followed by the image path used as
 * a suffix. As such, users will be able to send image request to the following URL:
 * </p>
 *
 * <pre>
 * PROTOCOL://DOMAIN.TLD(:PORT)/API_BASE_PATH/svgimages/SVG_ID
 * </pre>
 *
 * <p>
 * In a development environment, the URL will most likely be:
 * </p>
 *
 * <pre>
 * http://localhost:8080/api/parametricsvgs/SVG_ID
 * </pre>
 *
 * @author lfasani
 */
@Controller
@RequestMapping(URLConstants.PARAMETRIC_IMAGE_BASE_PATH + "/*")
public class ParametricSVGImageController {

    private static final MediaType IMAGE_SVG = MediaType.valueOf("image/svg+xml");

    private static final String TIMER = "sirius-components_parametricsvg";

    private final List<IParametricSVGImageFactory> svgImageFactories;

    private final Timer timer;

    public ParametricSVGImageController(List<IParametricSVGImageFactory> svgImageFactories, MeterRegistry meterRegistry) {
        this.svgImageFactories = Objects.requireNonNull(svgImageFactories);

        this.timer = Timer.builder(TIMER).register(meterRegistry);
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity<? extends Resource> getImage(HttpServletRequest request) {
        ResponseEntity<? extends Resource> response = new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.NOT_FOUND);

        long start = System.currentTimeMillis();

        String requestURI = request.getRequestURI();
        String imageType = URLDecoder.decode(requestURI.substring(URLConstants.PARAMETRIC_IMAGE_BASE_PATH.length() + 1), StandardCharsets.UTF_8);
        EnumMap<SVGAttribute, String> attributesValues = new EnumMap<>(SVGAttribute.class);
        for (SVGAttribute attribute : SVGAttribute.values()) {
            Object attributeValue = request.getParameter(attribute.getLabel());
            if (attributeValue instanceof String) {
                attributesValues.put(attribute, (String) attributeValue);
            }
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(IMAGE_SVG);

        // @formatter:off
        response = this.svgImageFactories.stream()
                .map(factory -> factory.createSvg(imageType, attributesValues))
                .filter(Optional::isPresent)
                .findFirst()
                .map(Optional::get)
                .map(ByteArrayResource::new)
                .map(resource -> new ResponseEntity<>(resource, headers, HttpStatus.OK))
                .orElse(new ResponseEntity<ByteArrayResource>(null, new HttpHeaders(), HttpStatus.NOT_FOUND));
        // @formatter:on

        long end = System.currentTimeMillis();
        this.timer.record(end - start, TimeUnit.MILLISECONDS);

        return response;
    }
}
