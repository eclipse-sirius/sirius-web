/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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

package org.eclipse.sirius.components.codegen.emf.internal;

import java.util.List;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.Diagnostician;
import org.eclipse.emf.ecore.util.EcoreUtil;

public final class GenmodelValidator {
    public List<LoadedGenmodel> validateAll(ResourceSet resourceSet, List<LoadedGenmodel> genmodels) {
        EcoreUtil.resolveAll(resourceSet);
        boolean hasErrors = false;
        for (Resource resource : List.copyOf(resourceSet.getResources())) {
            hasErrors |= this.validate(resource);
        }
        if (hasErrors) {
            throw new IllegalStateException("Validation failed. Fix reported errors before generation.");
        }
        return genmodels;
    }

    private boolean validate(Resource resource) {
        boolean hasErrors = false;
        for (EObject root : resource.getContents()) {
            hasErrors |= this.reportErrors(resource.getURI(), Diagnostician.INSTANCE.validate(root));
        }
        if (!hasErrors) {
            System.out.println("✓ Validated " + resource.getURI());
        }
        return hasErrors;
    }

    private boolean reportErrors(URI resourceUri, Diagnostic diagnostic) {
        boolean hasErrors = diagnostic.getSeverity() == Diagnostic.ERROR;
        if (hasErrors) {
            System.err.println("Validation error in " + resourceUri + ": " + diagnostic.getMessage());
        }
        for (Diagnostic child : diagnostic.getChildren()) {
            hasErrors |= this.reportErrors(resourceUri, child);
        }
        return hasErrors;
    }
}
