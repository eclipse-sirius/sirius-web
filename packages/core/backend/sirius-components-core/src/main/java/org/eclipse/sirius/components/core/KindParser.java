/*******************************************************************************
 * Copyright (c) 2021, 2022 Obeo.
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
package org.eclipse.sirius.components.core;

import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.sirius.components.core.api.IKindParser;
import org.springframework.stereotype.Service;

/**
 * Used to parse the kind of an object.
 *
 * @author sbegaudeau
 */
@Service
public class KindParser implements IKindParser {

    @Override
    public Map<String, List<String>> getParameterValues(String kind) {
        Map<String, List<String>> parameterValues = new HashMap<>();

        List<String> queryParameters = Arrays.asList(URI.create(kind).getQuery().split("&"));
        for (String queryParameter : queryParameters) {
            String[] data = queryParameter.split("=");
            if (data.length == 2 && !data[0].isBlank() && !data[1].isBlank()) {
                var key = URLDecoder.decode(data[0], StandardCharsets.UTF_8);
                var value = URLDecoder.decode(data[1], StandardCharsets.UTF_8);

                List<String> values = parameterValues.getOrDefault(key, new ArrayList<>());
                values.add(value);

                parameterValues.put(key, values);
            }
        }

        return parameterValues;
    }
}
