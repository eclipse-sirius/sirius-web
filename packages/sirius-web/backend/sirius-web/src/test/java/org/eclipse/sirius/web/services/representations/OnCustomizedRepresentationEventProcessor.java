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
package org.eclipse.sirius.web.services.representations;

import java.util.Arrays;

import org.springframework.boot.autoconfigure.condition.ConditionMessage;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * Used to trigger the registration of the test representation event processor flux customizer.
 *
 * @author sbegaudeau
 */
public class OnCustomizedRepresentationEventProcessor extends SpringBootCondition {

    @Override
    public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
        var message = ConditionMessage.forCondition(this.getClass().getSimpleName()).notAvailable("sirius.web.test.enabled");
        ConditionOutcome outcome = ConditionOutcome.noMatch(message);

        var siriusWebTestEnabled = context.getEnvironment().getProperty("sirius.web.test.enabled", "");
        var siriusWebTestEnabledFeatures = Arrays.stream(siriusWebTestEnabled.split(",")).map(String::trim).toList();

        if (siriusWebTestEnabledFeatures.contains("representationeventprocessorfluxcustomizer")) {
            message = ConditionMessage.forCondition(this.getClass().getSimpleName()).available("sirius.web.test.enabled=representationeventprocessorfluxcustomizer");
            outcome = ConditionOutcome.match(message);
        }

        return outcome;
    }
}
