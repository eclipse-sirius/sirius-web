/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
import { GQLOmniboxCommand } from './useWorkbenchOmniboxCommands.types';

export interface OmniboxCommandOverrideContribution {
  canHandle: (action: GQLOmniboxCommand) => boolean;
  component: React.ComponentType<OmniboxCommandComponentProps>;
}

export interface OmniboxCommandComponentProps {
  command: GQLOmniboxCommand;
  onKeyDown: React.KeyboardEventHandler<HTMLDivElement>;
  onClose: () => void;
}
