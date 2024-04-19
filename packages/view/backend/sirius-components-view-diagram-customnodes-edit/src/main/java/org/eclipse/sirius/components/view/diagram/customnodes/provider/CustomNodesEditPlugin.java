/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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
package org.eclipse.sirius.components.view.diagram.customnodes.provider;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.sirius.components.view.diagram.provider.DiagramEditPlugin;
import org.eclipse.sirius.components.view.provider.ViewEditPlugin;

/**
 * This is the central singleton for the Customnodes edit plugin. <!-- begin-user-doc --> <!-- end-user-doc -->
 *
 * @generated NOT
 */
public final class CustomNodesEditPlugin extends EMFPlugin {
    /**
     * Keep track of the singleton. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public static final CustomNodesEditPlugin INSTANCE = new CustomNodesEditPlugin();

    /**
     * Keep track of the singleton. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private static Implementation plugin;

    /**
     * Create the instance. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public CustomNodesEditPlugin() {
        super(new ResourceLocator[] { DiagramEditPlugin.INSTANCE, ViewEditPlugin.INSTANCE, });
    }

    /**
     * Returns the singleton instance of the Eclipse plugin. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the singleton instance.
     * @generated NOT
     */
    public static Implementation getPlugin() {
        return plugin;
    }

    /**
     * Computes the file extension to be used with the key to specify an image resource.
     *
     * @param key
     *            the key for the imagine.
     * @return the file extension to be used with the key to specify an image resource.
     * @generated NOT
     */
    protected static String extensionFor(String key) {
        String result = ".gif";
        int index = key.lastIndexOf('.');
        if (index != -1) {
            String extension = key.substring(index + 1);
            if ("png".equalsIgnoreCase(extension)|| "gif".equalsIgnoreCase(extension) || "bmp".equalsIgnoreCase(extension) || "ico".equalsIgnoreCase(extension) || "jpg".equalsIgnoreCase(extension)
                    || "jpeg".equalsIgnoreCase(extension) || "tif".equalsIgnoreCase(extension) || "tiff".equalsIgnoreCase(extension) || "svg".equalsIgnoreCase(extension)) {
                result = "";
            }
        }
        return result;
    }

    @Override
    protected Object doGetImage(String key) throws IOException {
        URL url = new URL(this.getBaseURL() + "icons/" + key + extensionFor(key));
        InputStream inputStream = url.openStream();
        inputStream.close();
        return url;
    }

    /**
     * Returns the singleton instance of the Eclipse plugin. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the singleton instance.
     * @generated NOT
     */
    public ResourceLocator getPluginResourceLocator() {
        return plugin;
    }

    /**
     * The actual implementation of the Eclipse <b>Plugin</b>. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public static class Implementation extends EclipsePlugin {
        /**
         * Creates an instance. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        public Implementation() {
            super();

            // Remember the static instance.
            //
            plugin = this;
        }
    }

}
