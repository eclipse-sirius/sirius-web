/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
package org.eclipse.sirius.components.diagrams.layout.api.configuration;

import org.eclipse.sirius.components.diagrams.layout.api.Padding;
import org.eclipse.sirius.components.diagrams.layoutdata.LabelLayoutData;
import org.eclipse.sirius.components.diagrams.layoutdata.Size;

public class LabelLayoutConfiguration {
    private String id;
    private String text;
    private int fontSize;
    private LabelStyle style;
    private LabelAlignment alignment;
    private Padding padding;
    private Size iconSize;
    private int gapBetweenIconAndText;
    private LabelLayoutData previousLabelLayoutData;
}
