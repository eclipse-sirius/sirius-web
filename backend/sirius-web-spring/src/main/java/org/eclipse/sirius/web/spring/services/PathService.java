/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
package org.eclipse.sirius.web.spring.services;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.sirius.web.services.api.IPathService;
import org.springframework.stereotype.Service;

/**
 * Implements the IPathService.
 *
 * @author hmarchadour
 */
@Service
public class PathService implements IPathService {

    public static final String HASH_PATH_PREFIX = "/hash/"; //$NON-NLS-1$

    private static final String JAR_SEPARATOR = "!"; //$NON-NLS-1$

    private static final String JAR_REGEX = "(.+)\\" + JAR_SEPARATOR + "(/.+)"; //$NON-NLS-1$ //$NON-NLS-2$

    private static final String HASH_PATH_SUFFIX = JAR_SEPARATOR;

    private static final String HASH_REGEX = HASH_PATH_PREFIX + "(.+)\\" + HASH_PATH_SUFFIX + "(.+)"; //$NON-NLS-1$ //$NON-NLS-2$

    private final Map<String, String> map = new ConcurrentHashMap<>();

    @Override
    public String resolvePath(String path) {
        String result = path;
        if (this.isObfuscated(path)) {
            Pattern pattern = Pattern.compile(HASH_REGEX);
            Matcher matcher = pattern.matcher(path);
            if (matcher.find()) {
                String hash = matcher.group(1);
                String resolvedPath = this.map.get(hash);
                if (resolvedPath != null) {
                    String localPath = matcher.group(2);
                    result = resolvedPath + JAR_SEPARATOR + localPath;
                }
            }
        }
        return result;
    }

    @Override
    public boolean isObfuscated(String path) {
        return path != null && path.matches(HASH_REGEX);
    }

    @Override
    public String obfuscatePath(String path) {
        String result = path;
        if (path != null && !this.isObfuscated(path)) {
            Pattern pattern = Pattern.compile(JAR_REGEX);
            Matcher matcher = pattern.matcher(path);
            if (matcher.find()) {
                String jarPath = matcher.group(1);
                String hash = this.hashAndSave(jarPath);
                String localPath = matcher.group(2);
                result = HASH_PATH_PREFIX + hash + HASH_PATH_SUFFIX + localPath;
            }
        }
        return result;
    }

    /**
     * Store the given path and return an hash. <br/>
     * The hash can be use to retrieve the original path.
     */
    private String hashAndSave(String path) {
        String hash = UUID.nameUUIDFromBytes(path.getBytes()).toString();
        if (!this.map.containsKey(hash)) {
            this.map.put(hash, path);
        }
        return hash;
    }

}
