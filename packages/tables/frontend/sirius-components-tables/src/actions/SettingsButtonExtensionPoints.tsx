/*******************************************************************************
 * Copyright (c) 2024, 2025 CEA LIST.
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

import { ComponentExtensionPoint } from '@eclipse-sirius/sirius-components-core';
import { SettingsButtonMenuEntryProps } from './SettingsButtonExtensionPoints.types';

/**
 * Extension point for table settings button menu entries.
 *
 * This extension point allows the addition of custom menu entries to the settings button of a table.
 * Each contribution can define how a menu entry should be rendered or behave.
 *
 * @since v2025.1.0
 */
export const settingButtonMenuEntryExtensionPoint: ComponentExtensionPoint<SettingsButtonMenuEntryProps> = {
  identifier: 'settingsButton#menuEntry',
  FallbackComponent: () => null,
};
