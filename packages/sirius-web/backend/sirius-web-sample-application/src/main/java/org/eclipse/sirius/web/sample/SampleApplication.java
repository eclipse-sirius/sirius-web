/*******************************************************************************
 * Copyright (c) 2019, 2022 Obeo.
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
package org.eclipse.sirius.web.sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Main class of the server, used as the entry point which will start the whole server properly initialized with a
 * Spring ApplicationContext (see {@link org.springframework.context.ApplicationContext}).
 * <p>
 * Thanks to the annotation {@link SpringBootApplication}, this class will act as a configuration which allows us to
 * declare beans and configure other features but we will not use this capacity in order to properly separate our code.
 * As such our configurations will be contained in dedicated classes elsewhere.
 * </p>
 * <p>
 * Starting this class will also trigger the scan of the classpath. In order to build our ApplicationContext Spring will
 * only scan the current package and its subpackages by default. Beans outside of those packages will not be discovered
 * automatically unless specified by additional information in our annotations.
 * </p>
 *
 * @author sbegaudeau
 */
@SpringBootApplication
@ComponentScan({ "org.eclipse.sirius.web", "org.eclipse.sirius.components" })
public class SampleApplication {

    /**
     * The entry point of the server.
     *
     * @param args
     *            The command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(SampleApplication.class, args);
    }
}
