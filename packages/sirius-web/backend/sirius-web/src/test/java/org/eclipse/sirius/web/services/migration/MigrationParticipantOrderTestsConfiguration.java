/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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

package org.eclipse.sirius.web.services.migration;

import org.eclipse.sirius.components.collaborative.representations.migration.IRepresentationMigrationParticipant;
import org.eclipse.sirius.components.emf.migration.api.IMigrationParticipant;
import org.springframework.context.annotation.Bean;

/**
 * The configuration specific to the {@link MigrationParticipantOrderTests} which register a {@link IMigrationParticipant} and a {@link IRepresentationMigrationParticipant}.
 *
 * @author gcoutable
 */
public class MigrationParticipantOrderTestsConfiguration {

    @Bean
    public IRepresentationMigrationParticipant addMostRecentRepresentationMigrationParticipant() {
        return new IRepresentationMigrationParticipant() {

            @Override
            public String getVersion() {
                return "9999.12.99-300012310900";
            }

            @Override
            public String getKind() {
                return "siriusComponents://representation?type=TreeMap";
            }
        };
    }

    @Bean
    public IRepresentationMigrationParticipant addSecondMostRecentRepresentationMigrationParticipant() {
        return new IRepresentationMigrationParticipant() {

            @Override
            public String getVersion() {
                return "9999.6.99-300012310900";
            }

            @Override
            public String getKind() {
                return "siriusComponents://representation?type=TreeMap";
            }
        };
    }

    @Bean
    public IMigrationParticipant addMostRecentMigrationParticipant() {
        return new IMigrationParticipant() {
            @Override
            public String getVersion() {
                return "9999.12.99-300012310901";
            }
        };
    }

    @Bean
    public IMigrationParticipant addSecondMostRecentMigrationParticipant() {
        return new IMigrationParticipant() {
            @Override
            public String getVersion() {
                return "9999.6.99-300012310901";
            }
        };
    }

}
