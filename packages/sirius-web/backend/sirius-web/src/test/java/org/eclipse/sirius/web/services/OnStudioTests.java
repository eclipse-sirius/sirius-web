/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
package org.eclipse.sirius.web.services;

import java.util.Arrays;

import org.eclipse.sirius.web.application.configurationproperties.SiriusWebProperties;
import org.springframework.boot.autoconfigure.condition.ConditionMessage;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * Custom condition used to detect that a studio based test is running.
 *
 * @author sbegaudeau
 */
public class OnStudioTests extends SpringBootCondition {

    @Override
    public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
        var message = ConditionMessage.forCondition(this.getClass().getSimpleName()).notAvailable("sirius.web.test.enabled");
        ConditionOutcome outcome = ConditionOutcome.noMatch(message);

        var siriusWebTestEnabled = context.getEnvironment().getProperty("sirius.web.test.enabled", "");
        var siriusWebTestEnabledFeatures = Arrays.stream(siriusWebTestEnabled.split(",")).map(String::trim).toList();

        if (siriusWebTestEnabledFeatures.contains("*")) {
            message = ConditionMessage.forCondition(this.getClass().getSimpleName()).available("sirius.web.test.enabled=" + SiriusWebProperties.EVERYTHING);
            outcome = ConditionOutcome.match(message);
        } else if (siriusWebTestEnabledFeatures.contains("studio")) {
            message = ConditionMessage.forCondition(this.getClass().getSimpleName()).available("sirius.web.test.enabled=" + SiriusWebProperties.STUDIO);
            outcome = ConditionOutcome.match(message);
        }

        return outcome;
    }
}
