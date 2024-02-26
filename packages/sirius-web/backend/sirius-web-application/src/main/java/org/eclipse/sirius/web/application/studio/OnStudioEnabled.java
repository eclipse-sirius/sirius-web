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
package org.eclipse.sirius.web.application.studio;

import java.util.Arrays;

import org.eclipse.sirius.web.application.configurationproperties.SiriusWebProperties;
import org.springframework.boot.autoconfigure.condition.ConditionMessage;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * Custom condition used to figure out if support for studio is enabled or not.
 *
 * @author sbegaudeau
 */
public class OnStudioEnabled extends SpringBootCondition {

    @Override
    public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
        var message = ConditionMessage.forCondition(this.getClass().getSimpleName()).notAvailable("sirius.web");
        ConditionOutcome outcome = ConditionOutcome.noMatch(message);

        var siriusWebEnabled = context.getEnvironment().getProperty("sirius.web.enabled", "");
        var siriusWebEnabledFeatures = Arrays.stream(siriusWebEnabled.split(",")).map(String::trim).toList();

        var siriusWebDisabled = context.getEnvironment().getProperty("sirius.web.disabled", "");
        var siriusWebDisabledFeatures = Arrays.stream(siriusWebDisabled.split(",")).map(String::trim).toList();

        if (siriusWebEnabledFeatures.contains(SiriusWebProperties.EVERYTHING)) {
            message = ConditionMessage.forCondition(this.getClass().getSimpleName()).available("sirius.web.enabled=" + SiriusWebProperties.EVERYTHING);
            outcome = ConditionOutcome.match(message);
        } else if (siriusWebEnabledFeatures.contains(SiriusWebProperties.STUDIO)) {
            message = ConditionMessage.forCondition(this.getClass().getSimpleName()).available("sirius.web.enabled=" + SiriusWebProperties.STUDIO);
            outcome = ConditionOutcome.match(message);
        }

        if (siriusWebDisabledFeatures.contains(SiriusWebProperties.STUDIO)) {
            message = ConditionMessage.forCondition(this.getClass().getSimpleName()).available("sirius.web.disabled=" + SiriusWebProperties.STUDIO);
            outcome = ConditionOutcome.noMatch(message);
        }
        return outcome;
    }
}
