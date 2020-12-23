/*******************************************************************************
 * Copyright (c) 2019, 2021 Obeo.
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
package org.eclipse.sirius.web.emf.compatibility.operations;

import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;

/**
 * Class owning services used by aql queries.
 *
 * @author lfasani
 */
public class ModelOperationServices {

    public static final String AQL_RENAME_EXPRESSION = "aql:self.renameENamedElementService(''{0}'')"; //$NON-NLS-1$

    public static final String AQL_THROW_ERROR_EXPRESSION = "aql:self.throwErrorService()"; //$NON-NLS-1$

    public void renameENamedElementService(ENamedElement eNamedElement, String newName) {
        eNamedElement.setName(newName);
    }

    public void throwErrorService(EObject eObject) {
        String[] anyString = { "oneString" }; //$NON-NLS-1$
        String value = anyString[10]; // raise an exception
        value.length();
    }
}
