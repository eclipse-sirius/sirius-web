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
package org.eclipse.sirius.components.collaborative.diagrams.export.svg;

import java.io.IOException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.eclipse.sirius.components.diagrams.Diagram;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

/**
 * Registers the images referenced in a {@link Diagram} for later retrieval as \<symbol\>.
 *
 * @author rpage
 */
@Service
@RequestScope
public class ImageRegistry {
    private final Map<URI, UUID> imageRegistry = new HashMap<>();

    private final HttpClient imageFetcher;

    private final URI imageBasePath;

    private final Logger logger = LoggerFactory.getLogger(ImageRegistry.class);

    public ImageRegistry(HttpServletRequest request) throws URISyntaxException {
        Objects.requireNonNull(request);
        URI uri = URI.create(request.getRequestURL().toString());
        this.imageBasePath = new URI(uri.getScheme() + "://" + uri.getHost() + ":" + uri.getPort() + "/api/images"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        this.imageFetcher = this.buildImageClient(request);
    }

    public UUID registerImage(String imageURL) {
        try {
            URI imageFullPath = new URI(this.imageBasePath.toString() + imageURL);
            UUID symbolId = this.imageRegistry.get(imageFullPath);
            if (symbolId == null) {
                symbolId = UUID.randomUUID();
                this.imageRegistry.put(imageFullPath, symbolId);
            }
            return symbolId;
        } catch (URISyntaxException e) {
            this.logger.warn(e.getMessage(), e);
            return null;
        }
    }

    public StringBuilder getReferencedImageSymbols() {
        StringBuilder symbols = new StringBuilder();
        this.imageRegistry.entrySet().forEach(entry -> symbols.append(this.getReferencedImage(entry.getKey(), entry.getValue())));
        return symbols;
    }

    private StringBuilder getReferencedImage(URI imageURI, UUID symbolId) {
        HttpRequest request = HttpRequest.newBuilder().uri(imageURI).GET().build();

        try {
            Optional<String> imageType = Optional.empty();

            byte[] byteContent = this.imageFetcher.send(request, BodyHandlers.ofByteArray()).body();
            String content = new String(byteContent);
            if (content.contains("<svg")) { //$NON-NLS-1$
                imageType = Optional.of("svg"); //$NON-NLS-1$
            } else {
                imageType = this.getImageType(imageURI);
            }

            if (!imageType.isPresent()) {
                this.logger.warn("The type of the image at URI " + imageURI + " is not valid."); //$NON-NLS-1$ //$NON-NLS-2$
            } else {
                return this.addSymbolElement(symbolId, imageType.get(), byteContent);
            }
        } catch (IOException | InterruptedException e) {
            this.logger.warn(e.getMessage(), e);
        }
        return new StringBuilder();

    }

    private StringBuilder addSymbolElement(UUID symbolId, String imageType, byte[] content) {
        StringBuilder symbol = new StringBuilder();
        symbol.append("<symbol id=\"" + symbolId + "\">"); //$NON-NLS-1$ //$NON-NLS-2$
        if ("svg".equals(imageType)) { //$NON-NLS-1$
            symbol.append(this.updateSvgContent(content));
        } else {
            symbol.append("<image xlink:href=\"data:image/" + imageType + ";charset=utf-8;base64,"); //$NON-NLS-1$ //$NON-NLS-2$
            String encodedString = Base64.getEncoder().encodeToString(content);
            symbol.append(encodedString);
            symbol.append("\"/>"); //$NON-NLS-1$
        }
        return symbol.append("</symbol>"); //$NON-NLS-1$
    }

    private String updateSvgContent(byte[] content) {
        String xmlDeclaration = "<\\?xml.*?\\?>"; //$NON-NLS-1$

        String cleanSvgString = new String(content).replaceAll(xmlDeclaration, ""); //$NON-NLS-1$

        String svgWidthPattern = "width=\".*?\""; //$NON-NLS-1$
        cleanSvgString = cleanSvgString.replaceFirst(svgWidthPattern, ""); //$NON-NLS-1$
        String svgHeightPattern = "height=\".*?\""; //$NON-NLS-1$
        cleanSvgString = cleanSvgString.replaceFirst(svgHeightPattern, ""); //$NON-NLS-1$

        return cleanSvgString;
    }

    private Optional<String> getImageType(URI imageURI) {
        String imagePath = imageURI.getPath();
        String fileName = imagePath.substring(imagePath.lastIndexOf("/") + 1); //$NON-NLS-1$
        // @formatter:off
        return Optional.ofNullable(fileName)
                .filter(name -> name.contains("."))  //$NON-NLS-1$
                .map(name -> name.substring(fileName.lastIndexOf(".") + 1)); //$NON-NLS-1$
        // @formatter:on
    }

    private HttpClient buildImageClient(HttpServletRequest request) {
        this.addRequestCookies(request);
        return HttpClient.newBuilder().cookieHandler(CookieHandler.getDefault()).build();
    }

    private void addRequestCookies(HttpServletRequest request) {
        CookieHandler.setDefault(new CookieManager());
        Cookie[] cookies = request.getCookies();
        CookieStore store = ((CookieManager) CookieHandler.getDefault()).getCookieStore();
        if (cookies != null) {
            // @formatter:off
             Arrays.stream(cookies)
                 .forEach(cookie -> {
                     HttpCookie newCookie = new HttpCookie(cookie.getName(), cookie.getValue());
                     newCookie.setPath("/"); //$NON-NLS-1$
                     newCookie.setVersion(0);
                     store.add(this.imageBasePath, newCookie);
                 });
             // @formatter:on
        }
    }
}
