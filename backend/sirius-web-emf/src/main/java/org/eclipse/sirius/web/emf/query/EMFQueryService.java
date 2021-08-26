/*******************************************************************************
 * Copyright (c) 2021 Obeo.
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
package org.eclipse.sirius.web.emf.query;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.sirius.web.core.api.ErrorPayload;
import org.eclipse.sirius.web.core.api.IEditingContext;
import org.eclipse.sirius.web.core.api.IPayload;
import org.eclipse.sirius.web.emf.services.IEditingContextEPackageService;
import org.eclipse.sirius.web.interpreter.AQLInterpreter;
import org.eclipse.sirius.web.interpreter.Result;
import org.eclipse.sirius.web.spring.collaborative.api.IQueryService;
import org.eclipse.sirius.web.spring.collaborative.dto.QueryBasedIntInput;
import org.eclipse.sirius.web.spring.collaborative.dto.QueryBasedIntSuccessPayload;
import org.springframework.stereotype.Service;

/**
 * A specific implementation of {@link IQueryService} for EMF.
 *
 * @author fbarbin
 */
@Service
public class EMFQueryService implements IQueryService {

    private final IEditingContextEPackageService editingContextEPackageService;

    public EMFQueryService(IEditingContextEPackageService editingContextEPackageService) {
        this.editingContextEPackageService = Objects.requireNonNull(editingContextEPackageService);
    }

    @Override
    public IPayload execute(IEditingContext editingContext, QueryBasedIntInput input) {
        Result result = this.executeQuery(editingContext, input);
        if (result.asInt().isPresent()) {
            return new QueryBasedIntSuccessPayload(input.getId(), result.asInt().getAsInt());
        } else {
            return new ErrorPayload(input.getId(), "An error occured while evaluation the expression. Status : " + result.getStatus()); //$NON-NLS-1$
        }
    }

    private Result executeQuery(IEditingContext editingContext, QueryBasedIntInput input) {
        List<Class<?>> classes = List.of(EditingContextServices.class);
        List<EPackage> ePackages = this.editingContextEPackageService.getEPackages(editingContext.getId());

        Map<String, Object> variables = new HashMap<>();
        variables.put(IEditingContext.EDITING_CONTEXT, editingContext);

        var interpreter = new AQLInterpreter(classes, ePackages);
        String query = input.getQuery();
        return interpreter.evaluateExpression(variables, query);
    }
}
