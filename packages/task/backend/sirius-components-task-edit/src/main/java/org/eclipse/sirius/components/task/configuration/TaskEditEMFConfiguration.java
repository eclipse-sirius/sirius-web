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
package org.eclipse.sirius.components.task.configuration;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.sirius.components.task.provider.TaskItemProviderAdapterFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration of the EMF support for Task MM.
 *
 * @author lfasani
 */
@Configuration
public class TaskEditEMFConfiguration {
    @Bean
    AdapterFactory taskAdapterFactory() {
        return new TaskItemProviderAdapterFactory();
    }
}
